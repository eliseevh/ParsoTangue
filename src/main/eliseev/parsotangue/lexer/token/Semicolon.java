package eliseev.parsotangue.lexer.token;

public final class Semicolon implements Token {
    @Override
    public String toString() {
        return ";" + System.lineSeparator();
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof Semicolon;
    }
}
