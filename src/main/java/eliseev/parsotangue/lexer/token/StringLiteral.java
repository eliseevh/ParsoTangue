package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

public final class StringLiteral extends Token {

    public StringLiteral(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public StringLiteral(final Object value) {
        super(value);
    }
}
