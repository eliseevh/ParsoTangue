package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

public final class IntegerLiteral extends Token {
    public IntegerLiteral(final Integer value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public IntegerLiteral(final Object value) {
        super(value);
    }
}
