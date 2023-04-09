package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record Comparison(String value) implements Token {
    @Override
    public String toString() {
        return " " + value + " ";
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Comparison comparison) {
            return Objects.equals(value, comparison.value);
        }
        return false;
    }
}
