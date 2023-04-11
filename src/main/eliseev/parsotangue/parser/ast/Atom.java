package eliseev.parsotangue.parser.ast;

public class Atom extends AbstractWrapperASTNode {
    public Atom(final IntegerLiteral integerLiteral) {
        super(integerLiteral);
    }
    public Atom(final StringLiteral stringLiteral) {
        super(stringLiteral);
    }
    public Atom(final BooleanLiteral booleanLiteral) {
        super(booleanLiteral);
    }
    public Atom(final FunctionCall functionCall) {
        super(functionCall);
    }
    public Atom(final Variable variable) {
        super(variable);
    }
    public Atom(final Value value) {
        super(value);
    }
    public Atom(final UnaryOperation unaryOperation) {
        super(unaryOperation);
    }
}
