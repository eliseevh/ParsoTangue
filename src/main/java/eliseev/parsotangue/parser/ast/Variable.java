package eliseev.parsotangue.parser.ast;

public class Variable extends AbstractLeafASTNode {
    public Variable(final String value) {
        super("Variable", value);
    }
}
