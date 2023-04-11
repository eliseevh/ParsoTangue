package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class UnaryOperation extends AbstractNamedChildrenASTNode {
    public UnaryOperation(final Atom inner, final String value) {
        super("Unary operation", Map.of("Inner", inner), value);
    }
}
