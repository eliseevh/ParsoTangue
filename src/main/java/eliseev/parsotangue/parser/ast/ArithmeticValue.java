package eliseev.parsotangue.parser.ast;

public class ArithmeticValue extends AbstractWrapperASTNode {
    public ArithmeticValue(final Term value) {
        super(value);
    }

    public ArithmeticValue(final PlusMinusOperation operation) {
        super(operation);
    }
}
