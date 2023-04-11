package eliseev.parsotangue;

import eliseev.parsotangue.lexer.*;
import eliseev.parsotangue.parser.Parser;
import eliseev.parsotangue.parser.ParserException;
import eliseev.parsotangue.parser.ast.Program;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParsoTangueParser {
    public static Program parse(final CharSource source) throws LexerException, ParserException {
        return new Parser(new Lexer(source).tokenize()).parse();
    }

    public static Program parse(final String source) throws LexerException, ParserException {
        return parse(new StringCharSource(source));
    }

    public static Program parse(final Path sourceFile) throws LexerException, ParserException, IOException {
        try (final Reader reader = Files.newBufferedReader(sourceFile)) {
            return parse(new ReaderCharSource(reader));
        }
    }
}
