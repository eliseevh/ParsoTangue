package eliseev.parsotangue.parser.ast;

public class ValueType extends AbstractLeafASTNode {
    public ValueType(final String typeName) {
        super("Value type", typeName);
    }
}
