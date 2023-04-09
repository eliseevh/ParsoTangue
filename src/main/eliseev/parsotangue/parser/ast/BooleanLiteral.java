package eliseev.parsotangue.parser.ast;

public class BooleanLiteral extends AbstractLeafASTNode {
    public BooleanLiteral(final Boolean value) {
        super("Boolean literal", value.toString());
    }
}
