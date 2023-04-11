package eliseev.parsotangue.parser.ast;

import java.util.List;

public class Program extends AbstractListChildrenASTNode<Function> {
    public Program(final List<Function> functions) {
        super("Program", functions);
    }
}
