package eliseev.parsotangue.lexer.token;

public final class Assign implements Token {
    @Override
    public String toString() {
        return " := ";
    }
    @Override
    public boolean equals(final Object other) {
        return other instanceof Assign;
    }
}
