package eliseev.parsotangue.lexer.token;

import java.util.Objects;
import java.util.Set;

public record Keyword(String value) implements Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("let", "return", "if", "else");

    @Override
    public String toString() {
        return "Keyword : " + value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Keyword keyword) {
            return Objects.equals(value, keyword.value);
        }
        return false;
    }
}
