package eliseev.parsotangue.parser.ast;

public class Value extends AbstractWrapperASTNode {
    public Value(final ArithmeticValue value) {
        super(value);
    }

    public Value(final OrderOperation value) {
        super(value);
    }
}
