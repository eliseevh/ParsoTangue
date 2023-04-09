package eliseev.parsotangue.lexer;

import java.util.Arrays;

public class StringCharSource implements CharSource {
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
    private final String source;
    private int pos;
    private int lineCount;
    private int linePos;

    public StringCharSource(final String source) {
        this.source = source;
        pos = 0;
        lineCount = 0;
        linePos = 0;
    }

    @Override
    public char next() {
        final char next = source.charAt(pos++);
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
    public boolean hasNext() {
        return pos < source.length();
    }

    @Override
    public LexerException error(final String message) {
        return new LexerException(String.format("%s at %d:%d (\"%s\")", message, lineCount, linePos, getContext()));
    }

    private String getContext() {
        final int left = Math.max(0, pos - CONTEXT_RADIUS);
        final int right = Math.min(source.length(), pos + CONTEXT_RADIUS);
        return source.substring(left, right);
    }
    private void shiftBuffer() {
        for (int i = 0; i < buffer.length - 1; i++) {
            buffer[i] = buffer[i + 1];
        }
    }
}
