package eliseev.parsotangue.lexer.token;

import java.util.Objects;
import java.util.Set;

public record Typename(String value) implements Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("void", "Integer", "String", "Boolean");
    private static final Set<String> VALUE_TYPES = Set.of("Integer", "String", "Boolean");

    @Override
    public String toString() {
        return "Typename : " + value;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Typename typename) {
            return Objects.equals(value, typename.value);
        }
        return false;
    }

    public boolean isValueType() {
        return VALUE_TYPES.contains(value);
    }
}
