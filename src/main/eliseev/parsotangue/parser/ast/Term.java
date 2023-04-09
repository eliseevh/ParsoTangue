package eliseev.parsotangue.parser.ast;

public class Term extends AbstractWrapperASTNode {
    public Term(final Atom value) {
        super(value);
    }
    public Term(final MulDivOperation value) {
        super(value);
    }
}
