package eliseev.parsotangue.parser;

import eliseev.parsotangue.generator.ProgramGenerator;
import eliseev.parsotangue.lexer.CharSource;
import eliseev.parsotangue.lexer.Lexer;
import eliseev.parsotangue.lexer.LexerException;
import eliseev.parsotangue.lexer.StringCharSource;
import eliseev.parsotangue.parser.ast.Program;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ParserTest {
    private final List<String> INCORRECT_INSERTIONS = List.of(" + + ", "\"qwerty\"", "-<", "><", "===", ";", "Boolan x := true;");

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
    public void testGenerated() {
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
}
