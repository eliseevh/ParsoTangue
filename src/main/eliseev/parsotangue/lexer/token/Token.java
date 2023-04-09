package eliseev.parsotangue.lexer.token;

public sealed interface Token permits Arithmetical, Assign, BooleanLiteral, Comma, Comparison, CurlyBracket, Ident,
                                      IntegerLiteral, Keyword, Parenthesis, Semicolon, StringLiteral, Typename {
}
