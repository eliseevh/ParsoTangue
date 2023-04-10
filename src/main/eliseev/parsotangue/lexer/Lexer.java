package eliseev.parsotangue.lexer;

import eliseev.parsotangue.lexer.token.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Lexer {
    private static final Predicate<Character> IS_IDENT_CHARACTER =
            character -> ('a' <= character && character <= 'z') || ('A' <= character && character <= 'Z') ||
                         character == '_';
    private final CharSource source;
    private char nextChar;
    private boolean eof;

    public Lexer(final CharSource source) {
        this.source = source;
        try {
            take();
        } catch (final LexerException ignored) {
            // Unreachable
        }
    }


    public Optional<Token> nextToken() throws LexerException {
        final Token result;
        skipWhitespaces();
        final Position startPos = source.getPosition();
        if (eof) {
            // No token
            result = null;
        } else if (test('+') || test('-') || test('*') || test('/') || test('%')) {
            // Arithmetical
            result = new Operation(Character.toString(take()), startPos, source.getPosition());
        } else if (skip(':')) {
            // Assign
            expect('=');
            result = new SpecialSymbol(":=", startPos, source.getPosition());
        } else if (skip(',')) {
            // Comma
            result = new SpecialSymbol(",", startPos, source.getPosition());
        } else if (test('<') || test('>')) {
            // Comparison
            final StringBuilder token = new StringBuilder().append(take());
            if (skip('=')) {
                token.append('=');
            }
            result = new Operation(token.toString(), startPos, source.getPosition());
        } else if (test('=') || test('!')) {
            // Comparison
            final String token = Character.toString(take()) + '=';
            expect('=');
            result = new Operation(token, startPos, source.getPosition());
        } else if (skip('{')) {
            // Left curly bracket
            result = new SpecialSymbol("{", startPos, source.getPosition());
        } else if (skip('}')) {
            // Right curly bracket
            result = new SpecialSymbol("}", startPos, source.getPosition());
        } else if (skip('(')) {
            // Left parenthesis
            result = new SpecialSymbol("(", startPos, source.getPosition());
        } else if (skip(')')) {
            // Right parenthesis
            result = new SpecialSymbol(")", startPos, source.getPosition());
        } else if (skip(';')) {
            // Semicolon
            result = new SpecialSymbol(";", startPos, source.getPosition());
        } else if (skip('"')) {
            // String literal
            final StringBuilder value = new StringBuilder();
            while (!test('"')) {
                value.append(take());
            }
            expect('"');
            result = new StringLiteral(value.toString(), startPos, source.getPosition());
        } else if (test(Character::isDigit)) {
            // Integer literal
            final StringBuilder number = new StringBuilder().append(take());
            while (test(Character::isDigit)) {
                number.append(take());
            }
            result = new IntegerLiteral(Integer.parseInt(number.toString()), startPos, source.getPosition());
        } else {
            // Boolean literal, ident, keyword or typename
            final StringBuilder tokenBuilder = new StringBuilder();
            while (test(IS_IDENT_CHARACTER)) {
                tokenBuilder.append(take());
            }
            if (tokenBuilder.isEmpty()) {
                throw source.error("Expected identifier, got '" + nextChar + "'");
            }
            final String token = tokenBuilder.toString();
            if (BooleanLiteral.POSSIBLE_VALUES.contains(token)) {
                result = new BooleanLiteral(Boolean.parseBoolean(token), startPos, source.getPosition());
            } else if (Typename.POSSIBLE_VALUES.contains(token)) {
                result = new Typename(token, startPos, source.getPosition());
            } else if (Keyword.POSSIBLE_VALUES.contains(token)) {
                result = new Keyword(token, startPos, source.getPosition());
            } else {
                result = new Ident(token, startPos, source.getPosition());
            }
        }

        return Optional.ofNullable(result);
    }

    public List<Token> tokenize() throws LexerException {
        final List<Token> result = new ArrayList<>();
        Optional<Token> next;
        while ((next = nextToken()).isPresent()) {
            result.add(next.get());
        }
        return result;
    }


    private char take() throws LexerException {
        if (eof) {
            throw source.error("Unexpected eof");
        }
        final char result = nextChar;
        eof = !source.hasNext();
        if (!eof) {
            nextChar = source.next();
        }
        return result;
    }

    private boolean test(final char expected) {
        return nextChar == expected;
    }

    private boolean skip(final char expected) throws LexerException {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(nextChar) && !eof) {
            try {
                take();
            } catch (final LexerException ignored) {
                // Unreachable
            }
        }
    }

    private void expect(final char expected) throws LexerException {
        if (!skip(expected)) {
            throw source.error("Expected '" + expected + "', found '" + nextChar + "'");
        }
    }

    private boolean test(final Predicate<Character> predicate) {
        return predicate.test(nextChar);
    }
}
