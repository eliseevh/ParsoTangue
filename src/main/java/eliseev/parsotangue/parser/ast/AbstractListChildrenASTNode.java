package eliseev.parsotangue.parser.ast;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractListChildrenASTNode<T extends AbstractASTNode> extends AbstractASTNode {
    private final List<T> children;

    public AbstractListChildrenASTNode(final String name, final List<T> children) {
        super(name);
        this.children = children;
    }


    @Override
    public String toString(final int indentLevel) {
        return "(" + name + (
                children.isEmpty() ? "[]" : children.stream().map(node -> " ".repeat(4 * (indentLevel + 1)) +
                                                                          node.toString(indentLevel + 1)).collect(
                        Collectors.joining(",\n", " [\n", "\n" + " ".repeat(4 * indentLevel) + "]"))) + ")";
    }

    @Override
    public int countNodesInSubtree() {
        return 1 + children.stream().mapToInt(AbstractASTNode::countNodesInSubtree).sum();
    }
}
