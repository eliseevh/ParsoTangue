package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class Function extends AbstractNamedChildrenASTNode {
    public Function(final String name, final FunctionType type, final FunctionParameters parameters, final CodeBlock body) {
        super("Function", Map.of("Type", type, "Parameters", parameters, "Body", body), name);
    }
}
