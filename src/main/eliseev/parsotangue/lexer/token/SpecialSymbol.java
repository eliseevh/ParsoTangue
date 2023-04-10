package eliseev.parsotangue.lexer.token;

import java.util.Objects;

public record SpecialSymbol(String value) implements Token {
    @Override
    public String toString() {
        return "Special symbol : " + value;
    }
    @Override
    public boolean equals(final Object other) {
        if (other instanceof final SpecialSymbol specialSymbol) {
            return Objects.equals(value, specialSymbol.value);
        }
        return false;
    }
}
