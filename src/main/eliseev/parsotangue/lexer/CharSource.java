package eliseev.parsotangue.lexer;

public interface CharSource {
    char next();
    boolean hasNext();
    LexerException error(String message);
}
