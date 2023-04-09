package eliseev.parsotangue.lexer.token;

public record IntegerLiteral(int value) implements Token {
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final IntegerLiteral integerLiteral) {
            return value == integerLiteral.value;
        }
        return false;
    }
}
