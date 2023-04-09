package eliseev.parsotangue.parser.ast;

import java.util.List;

public class FunctionParameters extends AbstractListChildrenASTNode<FunctionParameter> {
    public FunctionParameters(final List<FunctionParameter> parameters) {
        super("Function parameters", parameters);
    }
}
