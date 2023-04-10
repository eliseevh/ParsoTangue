package eliseev.parsotangue.lexer;

import java.util.*;

public abstract class AbstractCharSource implements CharSource {
    private static final int CONTEXT_RADIUS = 15;
    private static final char[] LINE_SEPARATOR;

    static {
        final String lineSeparator = System.lineSeparator();
        LINE_SEPARATOR = new char[lineSeparator.length()];
        for (int i = 0; i < LINE_SEPARATOR.length; i++) {
            LINE_SEPARATOR[i] = lineSeparator.charAt(i);
        }
    }

    private final char[] buffer = new char[System.lineSeparator().length()];

    private final Queue<Character> contextBuffer = new ArrayDeque<>();

    protected abstract char getNext();

    private int lineCount;
    private int linePos;

    protected AbstractCharSource() {
        lineCount = 0;
        linePos = 0;
    }

    @Override
    public char next() {
        final char next = getNext();
        contextBuffer.add(next);
        if (contextBuffer.size() > CONTEXT_RADIUS) {
            contextBuffer.poll();
        }
        linePos++;
        shiftBuffer();
        buffer[buffer.length - 1] = next;
        if (Arrays.equals(buffer, LINE_SEPARATOR)) {
            lineCount++;
            linePos = 0;
        }
        return next;
    }

    @Override
    public LexerException error(final String message) {
        return new LexerException(String.format("%s at %d:%d (\"%s\")", message, lineCount, linePos, getContext()));
    }

    private String getContext() {
        final StringBuilder result = new StringBuilder();
        contextBuffer.forEach(result::append);
        for (int i = 0; i < CONTEXT_RADIUS; i++) {
            if (!hasNext()) {
                break;
            }
            result.append(getNext());
        }
        return result.toString();
    }

    @Override
    public Position getPosition() {
        return new Position(lineCount, linePos);
    }

    private void shiftBuffer() {
        for (int i = 0; i < buffer.length - 1; i++) {
            buffer[i] = buffer[i + 1];
        }
    }
}
