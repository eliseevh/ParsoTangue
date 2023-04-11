package eliseev.parsotangue.parser.ast;

public abstract class AbstractWrapperASTNode extends AbstractASTNode {
    private final AbstractASTNode inner;
    public AbstractWrapperASTNode(final AbstractASTNode inner) {
        super(inner.name);
        this.inner = inner;
    }

    @Override
    public String toString(final int indentLevel) {
        return inner.toString(indentLevel);
    }

    @Override
    public int countNodesInSubtree() {
        return inner.countNodesInSubtree();
    }
}
