package eliseev.parsotangue.parser.ast;

public class FunctionType extends AbstractLeafASTNode {
    public FunctionType(final String typeName) {
        super("Function type", typeName);
    }
}
