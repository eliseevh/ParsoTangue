package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class OrderOperation extends AbstractNamedChildrenASTNode {
    public OrderOperation(final ArithmeticValue left, final ArithmeticValue right, final String operation) {
        super("Order operation", Map.of("Left argument", left, "Right argument", right), operation);
    }
}
