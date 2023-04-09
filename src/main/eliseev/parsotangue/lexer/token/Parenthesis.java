package eliseev.parsotangue.lexer.token;

public record Parenthesis(boolean isLeft) implements Token {
    @Override
    public String toString() {
        return isLeft ? "(" : ")";
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Parenthesis parenthesis) {
            return isLeft == parenthesis.isLeft;
        }
        return false;
    }
}
