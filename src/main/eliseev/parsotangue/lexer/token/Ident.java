package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

public final class Ident extends Token {
    public Ident(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public Ident(final Object value) {
        super(value);
    }
}
