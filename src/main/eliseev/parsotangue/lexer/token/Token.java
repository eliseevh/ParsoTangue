package eliseev.parsotangue.lexer.token;

public sealed interface Token permits BooleanLiteral, Ident, IntegerLiteral, Keyword, Operation, SpecialSymbol,
                                      StringLiteral, Typename {
}
