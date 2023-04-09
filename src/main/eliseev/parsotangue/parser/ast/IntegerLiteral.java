package eliseev.parsotangue.parser.ast;

public class IntegerLiteral extends AbstractLeafASTNode {
    public IntegerLiteral(final Integer value) {
        super("Integer literal", value.toString());
    }
}
