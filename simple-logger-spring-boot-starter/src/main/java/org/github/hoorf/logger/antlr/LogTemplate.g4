grammar LogTemplate;

content: template+;

template: STR |  method |  param  ;
method : FO methodName MO param MC FC ;
methodName : ID ;
param : FO paramName FC;
paramName : ROOT ID;
ID : [a-zA-Z0-9.]+ ;
FO : '{';
FC : '}';
MO : '(';
MC : ')';
ROOT : '#';
STR : ~[\t\t\n'{''}''('')''#']*;
WS: [ \t\n\r]+ -> skip ;