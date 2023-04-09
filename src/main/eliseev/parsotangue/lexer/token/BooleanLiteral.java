package eliseev.parsotangue.lexer.token;

import java.util.Set;

public record BooleanLiteral(boolean value) implements Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("true", "false");

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final BooleanLiteral booleanLiteral) {
            return value == booleanLiteral.value;
        }
        return false;
    }
}
