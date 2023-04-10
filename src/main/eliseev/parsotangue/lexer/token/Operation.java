package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record Operation(String value) implements Token {
    @Override
    public String toString() {
        return "Operation : " + value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Operation operation) {
            return Objects.equals(value, operation.value);
        }
        return false;
    }
}
