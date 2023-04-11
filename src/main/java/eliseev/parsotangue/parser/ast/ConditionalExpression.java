package eliseev.parsotangue.parser.ast;

import java.util.Map;

public class ConditionalExpression extends AbstractNamedChildrenASTNode {
    public ConditionalExpression(final Value condition, final LineExpression ifBlock, final LineExpression elseBlock) {
        super("Conditional expression",
              Map.of("Condition", condition, "\"if\" block", ifBlock, "\"else\" block", elseBlock));

    }

    public ConditionalExpression(final Value condition, final LineExpression ifBlock) {
        super("Conditional expression", Map.of("Condition", condition, "\"if\" block", ifBlock));
    }
}
