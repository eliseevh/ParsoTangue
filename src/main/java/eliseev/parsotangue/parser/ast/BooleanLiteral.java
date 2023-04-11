package eliseev.parsotangue.parser.ast;

public class BooleanLiteral extends AbstractLeafASTNode {
    public BooleanLiteral(final String value) {
        super("Boolean literal", value);
    }
}
