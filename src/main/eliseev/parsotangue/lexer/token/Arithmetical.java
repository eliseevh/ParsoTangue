package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record Arithmetical(String value) implements Token {
    @Override
    public String toString() {
        return " " + value + " ";
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Arithmetical arithmetical) {
            return Objects.equals(value, arithmetical.value);
        }
        return false;
    }
}
