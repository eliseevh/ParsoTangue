package eliseev.parsotangue.parser.ast;

public class LineExpression extends AbstractWrapperASTNode {
    public LineExpression(final VariableCreation variableCreation) {
        super(variableCreation);
    }
    public LineExpression(final VariableAssignment variableAssignment) {
        super(variableAssignment);
    }
    public LineExpression(final ConditionalExpression conditionalExpression) {
        super(conditionalExpression);
    }
    public LineExpression(final FunctionCall functionCall) {
        super(functionCall);
    }

    public LineExpression(final ReturnStatement returnStatement) {
        super(returnStatement);
    }
    public LineExpression(final CodeBlock codeBlock) {
        super(codeBlock);
    }
}
