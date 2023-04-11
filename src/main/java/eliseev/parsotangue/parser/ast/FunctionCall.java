package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class FunctionCall extends AbstractNamedChildrenASTNode {
    public FunctionCall(final String name, final ArgumentList arguments) {
        super("Function call", Map.of("Arguments", arguments), name);
    }
}
