package eliseev.parsotangue.parser;

import eliseev.parsotangue.generator.ProgramGenerator;
import eliseev.parsotangue.lexer.*;
import eliseev.parsotangue.parser.ast.Program;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.fail;

public class ParserTest {
    private final List<String> INCORRECT_INSERTIONS =
            List.of(" + >+ ", "\"qwerty\"", "-<", "><", "===", ";", "Boolan x := true;");

    private void testCorrectProgram(final String program, final boolean printAST) {
        final CharSource source = new StringCharSource(program);
        final Lexer lexer = new Lexer(source);
        try {
            final Parser parser = new Parser(lexer.tokenize());
            final Program ast = parser.parse();
            if (printAST) {
                System.out.println(ast);
            }
        } catch (final LexerException | ParserException e) {
            fail(e.getMessage());
        }
    }

    private void testIncorrectProgram(final String program) {
        final CharSource source = new StringCharSource(program);
        final Lexer lexer = new Lexer(source);
        try {
            final Parser parser = new Parser(lexer.tokenize());
            parser.parse();
            fail("Expected to throw on input \"" + System.lineSeparator() + program + System.lineSeparator() + "\"");
        } catch (final LexerException | ParserException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void generated() {
        final ProgramGenerator generator = new ProgramGenerator(10, 20, 10, 1, 5, 15, 5, 5);
        final int numberOfPrograms = 100;
        final Random random = new Random(42);
        for (int i = 0; i < numberOfPrograms; i++) {
            final boolean testIncorrect = random.nextBoolean();
            final String program = generator.generateProgram(3, 20, !testIncorrect);
            testCorrectProgram(program, false);
            if (testIncorrect) {
                final int insertPosition = random.nextInt(program.length() + 1);
                final int insertion = random.nextInt(INCORRECT_INSERTIONS.size());
                testIncorrectProgram(program.substring(0, insertPosition) + INCORRECT_INSERTIONS.get(insertion) +
                                     program.substring(insertPosition));
            }
        }
    }

    @Test
    public void writeAST() {
        testCorrectProgram("""
                           let void main() {
                           Integer a := 42;
                           Integer b := 42;
                           Integer result := bad_pow(a, 3) + bad_pow(b, 3);
                           print(result);
                           }
                           let Integer bad_pow(Integer x, Integer p) {
                           Integer result := 1; Integer i := 1;
                           if (i <= p) {
                           result := result * x;
                           }
                           return result;
                           }
                           let void main()
                           {
                           String first := "Hello";
                           String second := "World";
                           print(first + second);
                           }
                           let Boolean check_range(Integer x, Integer a, Integer b) {
                           return a <= x;
                           }
                           let void main() {
                           Integer temp := 36;
                           Integer c_first := read();
                           Integer bSecond := read();
                           if (checkRange(c_first, bSecond)) {
                           print("Yes!");
                           }
                           else if (checkRange(c_first * 20, bSecond * 30)) {
                           print("Maybe!");
                           } else {
                           print("No!");
                           }
                           }""", true);
    }

    @Test
    public void correctFiles() {
        final Path correctFilesFolder = Path.of("src", "test_files", "correct");
        final Path astFolder;
        try {
            astFolder = Files.createDirectories(correctFilesFolder.getParent().resolve("ASTs"));
        } catch (final IOException e) {
            fail(e.getMessage());
            return;
        }
        class CorrectFileVisitor extends SimpleFileVisitor<Path> {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                try (final Reader reader = Files.newBufferedReader(file)) {
                    Files.writeString(astFolder.resolve(correctFilesFolder.relativize(file)),
                                      new Parser(new Lexer(new ReaderCharSource(reader)).tokenize()).parse()
                                                                                                    .toString());
                } catch (final ParserException | LexerException e) {
                    throw new IOException(e);
                }
                return super.visitFile(file, attrs);
            }
        }
        try {
            Files.walkFileTree(correctFilesFolder, new CorrectFileVisitor());
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void incorrectFiles() {
        final Path incorrectFilesFolder = Path.of("src", "test_files", "incorrect");
        final Path errorsFolder;
        try {
            errorsFolder = Files.createDirectories(incorrectFilesFolder.getParent().resolve("errors"));
        } catch (final IOException e) {
            fail(e.getMessage());
            return;
        }
        class CorrectFileVisitor extends SimpleFileVisitor<Path> {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                try (final Reader reader = Files.newBufferedReader(file)) {
                    new Parser(new Lexer(new ReaderCharSource(reader)).tokenize()).parse();
                    fail("Expected to throw in file " + file);
                } catch (final ParserException | LexerException e) {
                    Files.writeString(errorsFolder.resolve(incorrectFilesFolder.relativize(file)), e.getMessage());
                }
                return super.visitFile(file, attrs);
            }
        }
        try {
            Files.walkFileTree(incorrectFilesFolder, new CorrectFileVisitor());
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }
}
