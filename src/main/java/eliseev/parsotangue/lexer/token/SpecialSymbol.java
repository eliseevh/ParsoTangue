package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

public final class SpecialSymbol extends Token {
    public SpecialSymbol(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public SpecialSymbol(final Object value) {
        super(value);
    }
}
