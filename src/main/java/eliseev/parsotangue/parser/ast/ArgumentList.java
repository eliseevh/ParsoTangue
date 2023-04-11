package eliseev.parsotangue.parser.ast;

import java.util.List;

public class ArgumentList extends AbstractListChildrenASTNode<Value> {
    public ArgumentList(final List<Value> values) {
        super("Argument list", values);
    }
}
