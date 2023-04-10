package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

public final class Operation extends Token {
    public Operation(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public Operation(final Object value) {
        super(value);
    }
}
