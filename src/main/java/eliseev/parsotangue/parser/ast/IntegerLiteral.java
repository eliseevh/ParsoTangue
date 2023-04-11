package eliseev.parsotangue.parser.ast;

public class IntegerLiteral extends AbstractLeafASTNode {
    public IntegerLiteral(final String value) {
        super("Integer literal", value);
    }
}
