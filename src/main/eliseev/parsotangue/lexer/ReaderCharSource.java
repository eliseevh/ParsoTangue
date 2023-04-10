package eliseev.parsotangue.lexer;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class ReaderCharSource extends AbstractCharSource {
    private final Reader reader;
    private int nextChar;

    public ReaderCharSource(final Reader reader) {
        this.reader = reader;
        try {
            nextChar = reader.read();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected char getNext() {
        final char result = (char) nextChar;
        try {
            nextChar = reader.read();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
        return result;
    }

    @Override
    public boolean hasNext() {
        return nextChar != -1;
    }
}
