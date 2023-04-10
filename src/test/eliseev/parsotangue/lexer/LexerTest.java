package eliseev.parsotangue.lexer;

import eliseev.parsotangue.generator.ProgramGenerator;
import eliseev.parsotangue.lexer.token.*;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LexerTest {
    private final List<String> INCORRECT_INSERTIONS = List.of("\"", " ! ", "@##@", "&a^", " = ");

    private void testTokenizableProgram(final String program, final boolean printTokenized) {
        final CharSource source = new StringCharSource(program);
        final Lexer lexer = new Lexer(source);
        try {
            final List<Token> tokens = lexer.tokenize();
            if (printTokenized) {
                System.out.println(tokens.stream().map(Object::toString).collect(Collectors.joining()));
            }
        } catch (final LexerException e) {
            fail(e.getMessage());
        }
    }

    private void testNonTokenizableProgram(final String program) {
        final CharSource source = new StringCharSource(program);
        final Lexer lexer = new Lexer(source);
        try {
            lexer.tokenize();
            fail("Expected to throw on input \"" + System.lineSeparator() + program + System.lineSeparator() + "\"");
        } catch (final LexerException e) {
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
            testTokenizableProgram(program, false);
            if (testIncorrect) {
                final int insertPosition = random.nextInt(program.length() + 1);
                final int insertion = random.nextInt(INCORRECT_INSERTIONS.size());
                testNonTokenizableProgram(program.substring(0, insertPosition) + INCORRECT_INSERTIONS.get(insertion) +
                                          program.substring(insertPosition));
            }
        }
    }

    @Test
    public void testCount() {
        final CharSource source = new StringCharSource("""
                                                       let void main()
                                                       {
                                                       String first := "Hello";
                                                       String second := "World";
                                                       print(first + second);
                                                       }
                                                       let Boolean check_range(Integer x, Integer a, Integer b) {
                                                       return a <= x;
                                                       }""");
        final Lexer lexer = new Lexer(source);
        try {
            final List<Token> tokens = lexer.tokenize();
            System.out.println(tokens.stream().map(Object::toString).collect(Collectors.joining()));
            assertEquals(44, tokens.size());
        } catch (final LexerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testContent() {
        final CharSource source = new StringCharSource("""
                                                       let void main()
                                                       {
                                                       String first := "Hello";
                                                       String second := "World";
                                                       print(first + second);
                                                       }""");
        final List<Token> actual =
                List.of(new Keyword("let"), new Typename("void"),
                        new Ident("main"), new SpecialSymbol("("), new SpecialSymbol(")"), new SpecialSymbol("{"),
                        new Typename("String"), new Ident("first"), new SpecialSymbol(":="),
                        new StringLiteral("Hello"), new SpecialSymbol(";"), new Typename("String"),
                        new Ident("second"), new SpecialSymbol(":="), new StringLiteral("World"), new SpecialSymbol(
                                ";"),
                        new Ident("print"), new SpecialSymbol("("), new Ident("first"), new Operation("+"),
                        new Ident("second"), new SpecialSymbol(")"), new SpecialSymbol(";"), new SpecialSymbol("}"));
        final Lexer lexer = new Lexer(source);
        try {
            final List<Token> tokens = lexer.tokenize();
            System.out.println(tokens.stream().map(Object::toString).collect(Collectors.joining()));
            assertEquals(actual, tokens);
        } catch (final LexerException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testWeirdWhitespaces() {
        testTokenizableProgram("""
                                                                                
                                                                                
                                                                                
                               let void main()
                               {
                               \s\s\s\sString     first:="Hello"     ;
                               String \tsecond := "World";
                               print(first \t\s
                               \t+ second);
                               }let Boolean   check_range \t(\tInteger   x ,Integer a,Integer b){
                               return a<=      x;}""", false);
    }

    @Test
    public void writeTokenized() {
        testTokenizableProgram("""
                               let void main() {
                               Integer a := 42;
                               Integer b := 42;
                               Integer result := bad_pow(a, 3) + bad_pow(b, 3);
                               print(result);
                               }
                               let Integer bad_pow(Integer x, Integer p) {
                               Integer result := 1; Integer i := 1;
                               if (i<=p) {
                               result := result * x;
                               }
                               return result;
                               }
                               let void main()
                               {
                               String first :="Hello";
                               String second := "World";
                               print(first + second);
                               }
                               let Boolean  check_range(Integer x, Integer a, Integer b) {
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
    public void testFails() {
        testNonTokenizableProgram("""
                                  let void main()
                                  {
                                  String first := "Hello";
                                  String second := "World";
                                  print(first + second);
                                  }
                                  let Boolean check_range(Integer x, Integer a, Integer b) {
                                  return a < = x;
                                  }""");
    }
}
