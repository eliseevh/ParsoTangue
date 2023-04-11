package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

import java.util.Set;

public final class BooleanLiteral extends Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("true", "false");

    public BooleanLiteral(final Boolean value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public BooleanLiteral(final Object value) {
        super(value);
    }
}
