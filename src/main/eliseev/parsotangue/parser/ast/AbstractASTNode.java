package eliseev.parsotangue.parser.ast;

public abstract class AbstractASTNode {
    protected final String name;

    protected AbstractASTNode(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    protected abstract String toString(int indentLevel);

    public abstract int countNodesInSubtree();
}
