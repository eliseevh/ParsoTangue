package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class VariableCreation extends AbstractNamedChildrenASTNode {
    public VariableCreation(final ValueType type, final String name, final Value value) {
        super("Variable creation", Map.of("Type", type, "Value", value), name);
    }
}
