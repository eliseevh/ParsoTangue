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

    private void testTokenizableProgram(final String program) {
        final CharSource source = new StringCharSource(program);
        final Lexer lexer = new Lexer(source);
        try {
            final List<Token> tokens = lexer.tokenize();
            System.out.println(tokens.stream().map(Object::toString).collect(Collectors.joining()));
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
            testTokenizableProgram(program);
            if (testIncorrect) {
                final int insertPosition = random.nextInt(program.length() + 1);
                final int insertion = random.nextInt(INCORRECT_INSERTIONS.size());
                testNonTokenizableProgram(
                        program.substring(0, insertPosition) + INCORRECT_INSERTIONS.get(insertion) + program.substring(insertPosition));
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
                List.of(new Keyword(Keyword.parseType("let")), new Typename(Typename.parseType("void")),
                        new Ident("main"), new Parenthesis(true), new Parenthesis(false), new CurlyBracket(true),
                        new Typename(Typename.parseType("String")), new Ident("first"), new Assign(),
                        new StringLiteral("Hello"), new Semicolon(), new Typename(Typename.parseType("String")),
                        new Ident("second"), new Assign(), new StringLiteral("World"), new Semicolon(),
                        new Ident("print"), new Parenthesis(true), new Ident("first"), new Arithmetical("+"),
                        new Ident("second"), new Parenthesis(false), new Semicolon(), new CurlyBracket(false));
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
                           return a<=      x;}""");
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
