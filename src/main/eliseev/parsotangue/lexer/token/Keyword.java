package eliseev.parsotangue.lexer.token;

import java.util.Set;

public record Keyword(eliseev.parsotangue.lexer.token.Keyword.KeywordType type) implements Token {
    public static final Set<String> POSSIBLE_VALUES = Set.of("let", "return", "if", "else");

    public static KeywordType parseType(final String string) {
        return switch (string) {
            case "let" -> KeywordType.LET;
            case "return" -> KeywordType.RETURN;
            case "if" -> KeywordType.IF;
            case "else" -> KeywordType.ELSE;
            default -> throw new AssertionError("Unreachable");
        };
    }

    @Override
    public String toString() {
        return switch (type) {
            case LET -> "let ";
            case RETURN -> "return ";
            case IF -> "if ";
            case ELSE -> "else ";
        };
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof final Keyword keyword) {
            return type == keyword.type;
        }
        return false;
    }

    public enum KeywordType {
        LET, RETURN, IF, ELSE
    }
}
