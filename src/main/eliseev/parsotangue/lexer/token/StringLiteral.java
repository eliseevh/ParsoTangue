package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record StringLiteral(String value) implements Token {
    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final StringLiteral stringLiteral) {
            return Objects.equals(value, stringLiteral.value);
        }
        return false;
    }
}
