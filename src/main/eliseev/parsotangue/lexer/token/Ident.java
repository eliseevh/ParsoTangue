package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record Ident(String name) implements Token {
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Ident ident) {
            return Objects.equals(name, ident.name);
        }
        return false;
    }
}
