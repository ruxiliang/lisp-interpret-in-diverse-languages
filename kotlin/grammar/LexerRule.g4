lexer grammar LexerRule;


// ID : [a-zA-Z]+ ;
// INT : [0-9]+ ;
// NEWLINE : '\r'? '\n' ;
// WS : [ \t]+ -> skip ;
// MUL : '*' ;
// DIV : '/' ;
// ADD : '+' ;
// SUB : '-' ;

ADJ: 'wow' | 'many' | 'so' | 'such' ;
NOUN: 'lisp' | 'language' | 'book' | 'build' | 'c' ;
WS: [ \t]+ -> skip ;