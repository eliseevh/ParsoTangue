package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class PlusMinusOperation extends AbstractNamedChildrenASTNode {
    public PlusMinusOperation(final Term left, final ArithmeticValue right, final String operation) {
        super("PlusMinus operation", Map.of("Left argument", left, "Right argument", right), operation);
    }
}
