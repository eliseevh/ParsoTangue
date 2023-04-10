package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

import java.util.Objects;

public abstract sealed class Token permits BooleanLiteral, Ident, IntegerLiteral, Keyword, Operation,
                                           SpecialSymbol,
                                           StringLiteral, Typename {
    protected final String value;
    public final Position startPos;
    public final Position endPos;

    protected Token(final Object value, final Position startPos, final Position endPos) {
        this.value = value.toString();
        this.startPos = startPos;
        this.endPos = endPos;
    }
    protected Token(final Object value) {
        this(value, null, null);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Token token) {
            return getClass() == token.getClass() && Objects.equals(value, token.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
