package eliseev.parsotangue.parser.ast;

public class StringLiteral extends AbstractLeafASTNode {
    public StringLiteral(final String value) {
        super("String literal", value);
    }
}
