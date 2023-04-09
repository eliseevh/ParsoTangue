package eliseev.parsotangue.lexer.token;

import java.util.Set;

public record Typename(eliseev.parsotangue.lexer.token.Typename.Type value) implements Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("void", "Integer", "String", "Boolean");

    public static Type parseType(final String string) {
        return switch (string) {
            case "void" -> Type.VOID;
            case "Integer" -> Type.INTEGER;
            case "String" -> Type.STRING;
            case "Boolean" -> Type.BOOLEAN;
            default -> throw new AssertionError("Unreachable");
        };
    }

    public String getTypename() {
        return switch (value) {
            case VOID -> "void";
            case INTEGER -> "Integer";
            case STRING -> "String";
            case BOOLEAN -> "Boolean";
        };
    }

    public boolean isValueType() {
        return value != Type.VOID;
    }

    @Override
    public String toString() {
        return getTypename() + " ";
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Typename typename) {
            return value == typename.value;
        }
        return false;
    }

    public enum Type {
        VOID, INTEGER, STRING, BOOLEAN
    }
}
