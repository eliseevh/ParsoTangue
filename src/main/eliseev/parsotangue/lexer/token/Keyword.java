package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

import java.util.Set;

public final class Keyword extends Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("let", "return", "if", "else");

    public Keyword(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public Keyword(final Object value) {
        super(value);
    }
}
