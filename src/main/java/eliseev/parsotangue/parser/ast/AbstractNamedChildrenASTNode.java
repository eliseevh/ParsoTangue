package eliseev.parsotangue.parser.ast;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractNamedChildrenASTNode extends AbstractASTNode {
    private final Map<String, ? extends AbstractASTNode> children;
    private final String value;

    public AbstractNamedChildrenASTNode(final String name, final Map<String, ? extends AbstractASTNode> children, final String value) {
        super(name);
        this.children = children;
        this.value = value;
    }

    public AbstractNamedChildrenASTNode(final String name, final Map<String, ? extends AbstractASTNode> children) {
        this(name, children, null);
    }

    @Override
    public String toString(final int indentLevel) {
        return "(" + name + (value == null ? "" : " (" + value + ")") +
               children.entrySet().stream().sorted(Map.Entry.comparingByKey())
                       .map(kv -> " ".repeat(4 * (indentLevel + 1)) + kv.getKey() + " : " +
                                  kv.getValue().toString(indentLevel + 1))
                       .collect(Collectors.joining(",\n", " {\n", "\n" + " ".repeat(4 * indentLevel) + "}")) + ")";
    }

    @Override
    public int countNodesInSubtree() {
        return 1 + children.values().stream().mapToInt(AbstractASTNode::countNodesInSubtree).sum();
    }
}
