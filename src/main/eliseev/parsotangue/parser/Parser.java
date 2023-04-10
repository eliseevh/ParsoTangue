package eliseev.parsotangue.parser;

import eliseev.parsotangue.lexer.token.*;
import eliseev.parsotangue.parser.ast.*;
import eliseev.parsotangue.parser.ast.BooleanLiteral;
import eliseev.parsotangue.parser.ast.IntegerLiteral;
import eliseev.parsotangue.parser.ast.StringLiteral;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Parser {
    private static final Token LEFT_PAR = new SpecialSymbol("(");
    private static final Token RIGHT_PAR = new SpecialSymbol(")");
    private static final Token LEFT_CURLY_BRACKET = new SpecialSymbol("{");
    private static final Token RIGHT_CURLY_BRACKET = new SpecialSymbol("}");
    private static final Token ASSIGN = new SpecialSymbol(":=");
    private static final Token COMMA = new SpecialSymbol(",");
    private static final Token SEMICOLON = new SpecialSymbol(";");
    private static final Token LET = new Keyword("let");
    private static final Token RETURN = new Keyword("return");
    private static final Token IF = new Keyword("if");
    private static final Token ELSE = new Keyword("else");
    private static final Set<Token> PLUS_MINUS_OPERATIONS = Set.of(new Operation("+"), new Operation("-"));
    private static final Set<Token> MUL_DIV_OPERATIONS =
            Set.of(new Operation("*"), new Operation("/"), new Operation("%"));
    private static final Set<Token> COMPARISON_OPERATIONS =
            Set.of(new Operation("<="), new Operation(">="), new Operation("<"), new Operation(">"),
                   new Operation("=="), new Operation("!="));

    private final List<Token> tokens;
    private int position;

    public Parser(final List<Token> tokens) {
        position = 0;
        this.tokens = tokens;
    }

    public Program parse() throws ParserException {
        return parseProgram();
    }

    private Program parseProgram() throws ParserException {
        final List<Function> functions = new ArrayList<>();
        while (position < this.tokens.size()) {
            functions.add(parseFunction());
        }
        return new Program(functions);
    }

    private Function parseFunction() throws ParserException {
        expect(LET);
        final FunctionType type = parseFunctionType();
        final Token token = next();
        if (!(token instanceof Ident)) {
            throw unexpectedTokenError("function name", token);
        }
        final String name = ((Ident) token).name();
        final FunctionParameters parameters = parseFunctionParameters();
        final CodeBlock body = parseCodeBlock();
        return new Function(name, type, parameters, body);
    }

    private FunctionType parseFunctionType() throws ParserException {
        final Token token = next();
        if (token instanceof final Typename type) {
            return new FunctionType(type.value());
        } else {
            throw unexpectedTokenError("function type", token);
        }
    }

    private FunctionParameters parseFunctionParameters() throws ParserException {
        expect(LEFT_PAR);
        final List<FunctionParameter> parameters = new ArrayList<>();
        boolean first = true;
        while (!skip(RIGHT_PAR)) {
            if (first) {
                first = false;
            } else {
                expect(COMMA);
            }
            parameters.add(parseFunctionParameter());
        }
        return new FunctionParameters(parameters);
    }

    private FunctionParameter parseFunctionParameter() throws ParserException {
        final ValueType type = parseValueType();
        final String name = parseIdent();
        return new FunctionParameter(type, name);
    }

    private String parseIdent() throws ParserException {
        final Token token = next();
        if (!(token instanceof Ident)) {
            throw unexpectedTokenError("parameter name", token);
        }
        return ((Ident) token).name();
    }

    private ValueType parseValueType() throws ParserException {
        final Token token = next();
        if (!(token instanceof final Typename type && type.isValueType())) {
            throw unexpectedTokenError("parameter type", token);
        }
        final String name = ((Typename) token).value();
        return new ValueType(name);
    }

    private CodeBlock parseCodeBlock() throws ParserException {
        expect(LEFT_CURLY_BRACKET);
        final List<LineExpression> lines = new ArrayList<>();
        while (!skip(RIGHT_CURLY_BRACKET)) {
            lines.add(parseLineExpression());
        }
        return new CodeBlock(lines);
    }

    private LineExpression parseLineExpression() throws ParserException {
        Token token = next();
        if (token instanceof Typename) {
            position--;
            return new LineExpression(parseVariableCreation());
        } else if (token instanceof Ident) {
            token = next();
            position -= 2;
            if (token.equals(LEFT_PAR)) {
                final FunctionCall functionCall = parseFunctionCall();
                expect(SEMICOLON);
                return new LineExpression(functionCall);
            } else {
                return new LineExpression(parseVariableAssignment());
            }
        } else if (token instanceof final Keyword keyword) {
            position--;
            if (keyword.equals(IF)) {
                return new LineExpression(parseConditionalExpression());
            } else if (keyword.equals(RETURN)) {
                return new LineExpression(parseReturnStatement());
            } else {
                throw unexpectedTokenError("line expression", token);
            }
        } else if (token.equals(LEFT_CURLY_BRACKET)) {
            position--;
            return new LineExpression(parseCodeBlock());
        } else {
            position--;
            throw unexpectedTokenError("line expression", token);
        }
    }

    private VariableCreation parseVariableCreation() throws ParserException {
        final ValueType type = parseValueType();
        final String name = parseIdent();
        expect(ASSIGN);
        final Value value = parseValue();
        expect(SEMICOLON);
        return new VariableCreation(type, name, value);
    }

    private FunctionCall parseFunctionCall() throws ParserException {
        final String name = parseIdent();
        final ArgumentList arguments = parseArgumentList();
        return new FunctionCall(name, arguments);
    }

    private ArgumentList parseArgumentList() throws ParserException {
        expect(LEFT_PAR);
        final List<Value> arguments = new ArrayList<>();
        boolean first = true;
        while (!skip(RIGHT_PAR)) {
            if (first) {
                first = false;
            } else {
                expect(COMMA);
            }
            arguments.add(parseValue());
        }
        return new ArgumentList(arguments);
    }

    private VariableAssignment parseVariableAssignment() throws ParserException {
        final String name = parseIdent();
        expect(ASSIGN);
        final Value value = parseValue();
        expect(SEMICOLON);
        return new VariableAssignment(name, value);
    }

    private ConditionalExpression parseConditionalExpression() throws ParserException {
        expect(IF);
        expect(LEFT_PAR);
        final Value condition = parseValue();
        expect(RIGHT_PAR);
        final LineExpression ifBlock = parseLineExpression();
        if (skip(ELSE)) {
            final LineExpression elseBlock = parseLineExpression();
            return new ConditionalExpression(condition, ifBlock, elseBlock);
        } else {
            return new ConditionalExpression(condition, ifBlock);
        }
    }

    private ReturnStatement parseReturnStatement() throws ParserException {
        expect(RETURN);
        final Value value = parseValue();
        expect(SEMICOLON);
        return new ReturnStatement(value);
    }

    private Value parseValue() throws ParserException {
        final ArithmeticValue left = parseArithmeticValue();
        final Token token = next();
        if (COMPARISON_OPERATIONS.contains(token)) {
            final String operation = ((Operation) token).value();
            final ArithmeticValue right = parseArithmeticValue();
            return new Value(new OrderOperation(left, right, operation));
        }
        position--;
        return new Value(left);
    }

    private ArithmeticValue parseArithmeticValue() throws ParserException {
        final Term left = parseTerm();
        for (final Token operation : PLUS_MINUS_OPERATIONS) {
            if (skip(operation)) {
                final String operationName = ((Operation) operation).value();
                final ArithmeticValue right = parseArithmeticValue();
                return new ArithmeticValue(new PlusMinusOperation(left, right, operationName));
            }
        }
        return new ArithmeticValue(left);
    }

    private Term parseTerm() throws ParserException {
        final Atom left = parseAtom();
        for (final Token operation : MUL_DIV_OPERATIONS) {
            if (skip(operation)) {
                final String operationName = ((Operation) operation).value();
                final Term right = parseTerm();
                return new Term(new MulDivOperation(left, right, operationName));
            }
        }
        return new Term(left);
    }

    private Atom parseAtom() throws ParserException {
        Token token = next();
        if (token instanceof final eliseev.parsotangue.lexer.token.IntegerLiteral integerLiteral) {
            return new Atom(new IntegerLiteral(integerLiteral.value()));
        } else if (token instanceof final eliseev.parsotangue.lexer.token.StringLiteral stringLiteral) {
            return new Atom(new StringLiteral(stringLiteral.value()));
        } else if (token instanceof final eliseev.parsotangue.lexer.token.BooleanLiteral booleanLiteral) {
            return new Atom(new BooleanLiteral(booleanLiteral.value()));
        } else if (token instanceof Ident) {
            token = next();
            position -= 2;
            if (token.equals(LEFT_PAR)) {
                return new Atom(parseFunctionCall());
            } else {
                return new Atom(parseVariable());
            }
        } else if (token.equals(LEFT_PAR)) {
            final Value value = parseValue();
            expect(RIGHT_PAR);
            return new Atom(value);
        } else {
            throw unexpectedTokenError("value", token);
        }
    }

    private Variable parseVariable() throws ParserException {
        final String name = parseIdent();
        return new Variable(name);
    }

    private Token next() throws ParserException {
        if (position >= tokens.size()) {
            throw unexpectedTokenError("token", "eof");
        }
        return tokens.get(position++);
    }

    private boolean skip(final Token skip) throws ParserException {
        if (test(skip)) {
            next();
            return true;
        }
        return false;
    }

    private boolean test(final Token next) {
        return tokens.get(position).equals(next);
    }


    private void expect(final Token expected) throws ParserException {
        final Token token = next();
        if (!token.equals(expected)) {
            throw unexpectedTokenError(expected, token);
        }
    }

    private ParserException unexpectedTokenError(final Object expected, final Object got) {
        return new ParserException(
                "Expected " + wrapTokenInErrorMessage(expected) + ", got " + wrapTokenInErrorMessage(got));
    }

    private String wrapTokenInErrorMessage(final Object token) {
        if (token instanceof Token) {
            return "'" + token + "'";
        } else {
            return token.toString();
        }
    }
}
