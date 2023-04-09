package eliseev.parsotangue.lexer.token;

public record CurlyBracket(boolean isLeft) implements Token {
    @Override
    public String toString() {
        return (isLeft ? " {" : "}") + System.lineSeparator();
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final CurlyBracket curlyBracket) {
            return isLeft == curlyBracket.isLeft;
        }
        return false;
    }
}
