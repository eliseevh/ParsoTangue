package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class VariableAssignment extends AbstractNamedChildrenASTNode {
    public VariableAssignment(final String name, final Value value) {
        super("Variable assignment", Map.of("Value", value), name);
    }
}
