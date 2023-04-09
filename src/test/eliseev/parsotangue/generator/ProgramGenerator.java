package eliseev.parsotangue.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProgramGenerator {
    private static final List<Character> STRING_LITERAL_POSSIBLE_CHARS;
    private static final List<Character> IDENT_POSSIBLE_CHARS;
    private static final List<Character> WHITESPACES;
    private static final List<String> TYPES = List.of("Integer", "String", "Boolean", "void");
    private static final List<String> VARIABLE_TYPES = TYPES.subList(0, 3);
    private static final List<String> ORDER_OPERATIONS = List.of("<=", ">=", "<", ">", "==", "!=");
    private static final List<String> PLUS_MINUS_OPERATIONS = List.of("+", "-");
    private static final List<String> MUL_DIV_OPERATIONS = List.of("*", "/", "%");

    static {
        STRING_LITERAL_POSSIBLE_CHARS = new ArrayList<>();
        IDENT_POSSIBLE_CHARS = new ArrayList<>();
        WHITESPACES = new ArrayList<>();
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            if (ch != '"') {
                STRING_LITERAL_POSSIBLE_CHARS.add(ch);
            }
            if (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ch == '_') {
                IDENT_POSSIBLE_CHARS.add(ch);
            }
            if (Character.isWhitespace(ch)) {
                WHITESPACES.add(ch);
            }
        }
    }

    private final Random random;
    private final int maxStringLength;
    private final int maxIdentLength;
    private final int maxParametersCount;
    private final int minWhitespaces;
    private final int maxWhitespaces;
    private final int maxLinesInCodeBlock;
    private final int maxCodeBlockDepth;
    private final int maxValueDepth;

    public ProgramGenerator(
            final int maxStringLength, final int maxIdentLength, final int maxParametersCount, final int minWhitespaces,
            final int maxWhitespaces, final int maxLinesInCodeBlock, final int maxCodeBlockDepth, final int maxValueDepth) {
        this.maxStringLength = maxStringLength;
        this.maxIdentLength = maxIdentLength;
        this.maxParametersCount = maxParametersCount;
        this.minWhitespaces = minWhitespaces;
        this.maxWhitespaces = maxWhitespaces;
        this.maxLinesInCodeBlock = maxLinesInCodeBlock;
        this.maxCodeBlockDepth = maxCodeBlockDepth;
        this.maxValueDepth = maxValueDepth;
        this.random = new Random(42);
    }

    public String generateProgram(final int minFunctions, final int maxFunctions) {
        final StringBuilder result = new StringBuilder();
        final int functions = random.nextInt(minFunctions, maxFunctions + 1);
        for (int i = 0; i < functions; i++) {
            result.append(generateWhitespaces()).append(generateFunction());
        }
        result.append(generateWhitespaces());
        return result.toString();
    }

    private String generateFunction() {
        return generateFunctionDeclarator() + generateCodeBlock(0);
    }

    private String generateFunctionDeclarator() {
        return "let" + generateDelimiter() + generateFunctionType() + generateDelimiter() + generateIdent() + generateFunctionParameterList();
    }

    private String generateFunctionParameterList() {
        final StringBuilder result = new StringBuilder();
        result.append(generateLeftPar());
        final int parametersCount = random.nextInt(maxParametersCount);
        for (int i = 0; i < parametersCount; i++) {
            if (i != 0) {
                result.append(generateWhitespaces()).append(",").append(generateWhitespaces());
            }
            result.append(generateVariableType()).append(generateDelimiter()).append(generateIdent());
        }
        result.append(generateWhitespaces()).append(")").append(generateWhitespaces());
        return result.toString();
    }

    private String generateCodeBlock(final int depth) {
        if (depth >= maxCodeBlockDepth) {
            return "{}";
        }
        final StringBuilder result = new StringBuilder().append("{");
        final int lines = random.nextInt(maxLinesInCodeBlock + 1);
        for (int i = 0; i < lines; i++) {
            result.append(generateWhitespaces()).append(generateLineExpression(depth));
        }
        result.append(generateWhitespaces()).append("}");
        return result.toString();
    }
    private String generateLineExpression(final int depth) {
        final int variantNumber = random.nextInt(6);
        return switch (variantNumber) {
            case 0 -> generateVariableCreation();
            case 1 -> generateVariableAssignment();
            case 2 -> generateConditionalExpression(depth);
            case 3 -> generateFunctionCall() + ";";
            case 4 -> generateReturnStatement();
            case 5 -> generateCodeBlock(depth + 1);
            default -> throw new AssertionError("Unreachable");
        };
    }

    private String generateVariableCreation() {
        return generateVariableType() + generateDelimiter() + generateVariableAssignment();
    }

    private String generateVariableAssignment() {
        return generateIdent() + generateWhitespaces() + ":=" + generateWhitespaces() + generateValue(0) +
               generateWhitespaces() + ";";
    }
    private String generateConditionalExpression(final int depth) {
        final StringBuilder result = new StringBuilder();
        result.append("if").append(generateLeftPar()).append(generateValue(0)).append(generateRightPar()).append(generateLineExpression(depth + 1));
        if (random.nextBoolean()) {
            result.append(generateWhitespaces()).append("else").append(generateDelimiter()).append(generateLineExpression(depth + 1));
        }
        return result.toString();
    }
    private String generateReturnStatement() {
        return "return" + generateDelimiter() + generateValue(0) + generateWhitespaces() + ";";
    }

    private String generateValue(final int depth) {
        final StringBuilder result = new StringBuilder();
        result.append(generateArithmeticValue(depth));
        if (random.nextBoolean() && depth < maxValueDepth) {
            result.append(generateWhitespaces()).append(getRandom(ORDER_OPERATIONS)).append(generateWhitespaces())
                  .append(generateArithmeticValue(depth + 1));
        }
        return result.toString();
    }

    private String generateArithmeticValue(final int depth) {
        final StringBuilder result = new StringBuilder();
        result.append(generateTerm(depth + 1));
        if (random.nextBoolean() && depth < maxValueDepth) {
            result.append(generateWhitespaces()).append(getRandom(PLUS_MINUS_OPERATIONS))
                  .append(generateWhitespaces()).append(generateArithmeticValue(depth + 1));
        }
        return result.toString();
    }

    private String generateTerm(final int depth) {
        final StringBuilder result = new StringBuilder();
        result.append(generateAtom(depth));
        if (random.nextBoolean() && depth < maxValueDepth) {
            result.append(generateWhitespaces()).append(getRandom(MUL_DIV_OPERATIONS)).append(generateWhitespaces())
                  .append(generateTerm(depth + 1));

        }
        return result.toString();
    }

    private String generateAtom(final int depth) {
        int numberOfVariants = 6;
        if (depth >= maxValueDepth) {
            numberOfVariants--;
        }
        final int variantNumber = random.nextInt(numberOfVariants);

        return switch (variantNumber) {
            case 0 -> generateIntegerLiteral();
            case 1 -> generateStringLiteral();
            case 2 -> generateBoolLiteral();
            case 3 -> generateFunctionCall();
            case 4 -> generateIdent();
            case 5 -> "(" + generateWhitespaces() + generateValue(depth + 1) + generateWhitespaces() + ")";
            default -> throw new AssertionError("Unreachable");
        };
    }

    private String generateFunctionCall() {
        final StringBuilder result = new StringBuilder();
        result.append(generateIdent()).append(generateLeftPar());
        final int argumentsCount = random.nextInt(maxParametersCount);
        for (int i = 0; i < argumentsCount; i++) {
            if (i != 0) {
                result.append(generateWhitespaces()).append(",").append(generateWhitespaces());
            }
            result.append(generateIdent());
        }
        result.append(generateWhitespaces()).append(")").append(generateWhitespaces());
        return result.toString();
    }

    private String generateIntegerLiteral() {
        return Integer.toString(random.nextInt(Integer.MAX_VALUE));
    }

    private String generateStringLiteral() {
        final int length = random.nextInt(maxStringLength + 1);
        final StringBuilder result = new StringBuilder().append('"');
        for (int i = 0; i < length; i++) {
            result.append(getRandom(STRING_LITERAL_POSSIBLE_CHARS));
        }
        return result.append('"').toString();
    }

    private String generateBoolLiteral() {
        return Boolean.toString(random.nextBoolean());
    }

    private String generateIdent() {
        final int length = random.nextInt(1, maxIdentLength + 1);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandom(IDENT_POSSIBLE_CHARS));
        }
        return result.toString();
    }

    private String generateVariableType() {
        return getRandom(VARIABLE_TYPES);
    }

    private String generateFunctionType() {
        return getRandom(TYPES);
    }

    private String generateLeftPar() {
        return generateWhitespaces() + "(" + generateWhitespaces();
    }

    private String generateRightPar() {
        return generateWhitespaces() + ")" + generateWhitespaces();
    }

    private String generateWhitespaces() {
        final int length = random.nextInt(maxWhitespaces + 1);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandom(WHITESPACES));
        }
        return result.toString();
    }

    private String generateDelimiter() {
        final int length = random.nextInt(minWhitespaces, maxWhitespaces + 1);
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(getRandom(WHITESPACES));
        }
        return result.toString();
    }

    private <T> T getRandom(final List<T> values) {
        return values.get(random.nextInt(values.size()));
    }
}
