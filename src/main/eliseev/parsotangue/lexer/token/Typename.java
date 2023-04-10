package eliseev.parsotangue.lexer.token;

import eliseev.parsotangue.lexer.Position;

import java.util.Set;

public final class Typename extends Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("void", "Integer", "String", "Boolean");
    private static final Set<String> VALUE_TYPES = Set.of("Integer", "String", "Boolean");

    public Typename(final String value, final Position startPos, final Position endPos) {
        super(value, startPos, endPos);
    }

    public Typename(final Object value) {
        super(value);
    }

    public boolean isValueType() {
        return VALUE_TYPES.contains(value);
    }
}
