package eliseev.parsotangue.lexer;

public class StringCharSource extends AbstractCharSource {
    private final String source;
    private int pos;

    public StringCharSource(final String source) {
        this.source = source;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < source.length();
    }

    @Override
    protected char getNext() {
        return source.charAt(pos++);
    }
}
