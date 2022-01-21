grammar LogTemplate;

content: template+;

template: STR |  method |  param  ;
method : FO methodName FO param FC FC ;
methodName : ID ;
param : FO paramName FC;
paramName : ROOT ID;
ID : [a-zA-Z0-9.]+ ;
FO : '{';
FC : '}';
ROOT : '#';
STR : ~[\t\r\n'{''}''#']*;
WS: [ \t\n\r]+ -> skip ;