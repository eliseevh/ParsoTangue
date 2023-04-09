package eliseev.parsotangue.parser.ast;

public abstract class AbstractLeafASTNode extends AbstractASTNode {
    private final String value;

    protected AbstractLeafASTNode(final String name, final String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String toString(final int indentLevel) {
        return "(" + name + " (" + value + "))";
    }

    @Override
    public int countNodesInSubtree() {
        return 1;
    }
}
