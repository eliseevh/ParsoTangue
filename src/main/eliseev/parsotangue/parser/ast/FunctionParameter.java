package eliseev.parsotangue.parser.ast;

import java.util.List;
import java.util.Map;

public class FunctionParameter extends AbstractNamedChildrenASTNode {
    public FunctionParameter(final ValueType type, final String name) {
        super("Function parameter", Map.of("Type", type), name);
    }
}
