package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class MulDivOperation extends AbstractNamedChildrenASTNode {
    public MulDivOperation(final Atom left, final Term right, final String operation) {
        super("MulDiv operation", Map.of("Left argument", left, "Right argument", right), operation);
    }
}
