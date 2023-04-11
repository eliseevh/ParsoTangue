package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class ReturnStatement extends AbstractNamedChildrenASTNode {
    public ReturnStatement() {
        super("Void return", Map.of());
    }
    public ReturnStatement(final Value value) {
        super("Return", Map.of("Value", value));
    }
}
