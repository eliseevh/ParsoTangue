package eliseev.parsotangue.parser.ast;

import java.util.List;

public class CodeBlock extends AbstractListChildrenASTNode<LineExpression> {
    public CodeBlock(final List<LineExpression> expressions) {
        super("Code block", expressions);
    }
}
