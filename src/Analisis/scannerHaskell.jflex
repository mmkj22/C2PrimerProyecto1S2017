package Analisis;
import Analisis.sym.*;
import java_cup.runtime.Symbol;
import Errores.*;

%%
//EXPRESIONES REGULARES A UTILIZAR EN EL ARCHIVO DE CONFIGURACION
texto       = "\""([^\"]*)"\""
numero      = [0-9]+
letras      = [a-zA-ZñÑ]+
id          ={letras}({numero}|"_"|{letras})*
decimal     ={numero}"."{numero}
caracter    = "\'"([a-zA-Z0-9]|"!"|"\""|"·"|"$"|"%"|"="|"?"|"¿"|"Ç"|"_"|"-"|"."|","|"<"|">"|"@"|"#"|"~"|"½"|"¬"|";"|"["|"]"|"º"|"ª"|""|"!"|"+"|"-"|"*"|"^"|"&"|"\\"|" ")"\'"


%cupsym symH
%class lexicoHanskell
%cup
%public
%line
%char
%ignorecase

%{
//VARIABLES Y METODOS DEL SCANNER EN JAVA
    private Errores err = Errores.getInstance();
    private void reportarError(int fila, int columna, String descError)
    {
        err.nuevoErrorLexico(fila, columna, descError);
    }
%}

%%

//PALABRAS RESERVADAS

"let"           {   System.out.println("Entro let");
                    return new Symbol(symH.tkn_let, yyline, yycolumn, new String(yytext()));
                }
"Calcular"      {   System.out.println("Entro Calcular");
                    return new Symbol(symH.tkn_calcular, yyline, yycolumn, new String(yytext()));
                }     
"Succ"          {   System.out.println("Entro Succ");
                    return new Symbol(symH.tkn_succ, yyline, yycolumn, new String(yytext()));
                }
"Decc"          {   System.out.println("Entro Decc");
                    return new Symbol(symH.tkn_decc, yyline, yycolumn, new String(yytext()));
                }
"Min"           {   System.out.println("Entro Min");
                    return new Symbol(symH.tkn_min, yyline, yycolumn, new String(yytext()));
                }
"Max"           {   System.out.println("Entro Max");
                    return new Symbol(symH.tkn_max, yyline, yycolumn, new String(yytext()));
                }
"sum"           {   System.out.println("Entro sum");
                    return new Symbol(symH.tkn_sum, yyline, yycolumn, new String(yytext()));
                }
"product"       {   System.out.println("Entro product");
                    return new Symbol(symH.tkn_product, yyline, yycolumn, new String(yytext()));
                }
"revers"        {   System.out.println("Entro revers");
                    return new Symbol(symH.tkn_revers, yyline, yycolumn, new String(yytext()));
                }
"impr"          {   System.out.println("Entro impr");
                    return new Symbol(symH.tkn_impr, yyline, yycolumn, new String(yytext()));
                }
"par"           {   System.out.println("Entro par");
                    return new Symbol(symH.tkn_par, yyline, yycolumn, new String(yytext()));
                }
"asc"           {   System.out.println("Entro asc");
                    return new Symbol(symH.tkn_asc, yyline, yycolumn, new String(yytext()));
                }
"desc"          {   System.out.println("Entro desc");
                    return new Symbol(symH.tkn_desc, yyline, yycolumn, new String(yytext()));
                }
"length"        {   System.out.println("Entro length");
                    return new Symbol(symH.tkn_length, yyline, yycolumn, new String(yytext()));
                }
"end"           {   System.out.println("Entro end");
                    return new Symbol(symH.tkn_end, yyline, yycolumn, new String(yytext()));
                }
"if"            {   System.out.println("Entro if");
                    return new Symbol(symH.tkn_if, yyline, yycolumn, new String(yytext()));
                }
"else"          {   System.out.println("Entro else");
                    return new Symbol(symH.tkn_else, yyline, yycolumn, new String(yytext()));
                }
"then"          {   System.out.println("Entro then");
                    return new Symbol(symH.tkn_then, yyline, yycolumn, new String(yytext()));
                }
"case"          {   System.out.println("Entro case");
                    return new Symbol(symH.tkn_case, yyline, yycolumn, new String(yytext()));
                }
"'pot'"           {   System.out.println("Entro pot");
                    return new Symbol(symH.potencia, yyline, yycolumn, new String(yytext()));
                }
"'mod'"           {   System.out.println("Entro mod");
                    return new Symbol(symH.mod, yyline, yycolumn, new String(yytext()));
                }
"'sqrt'"          {   System.out.println("Entro sqrt");
                    return new Symbol(symH.sqrt, yyline, yycolumn, new String(yytext()));
                }


//BASICA
{texto}         {   System.out.println("Entro texto");
                    return new Symbol(symH.texto, yyline, yycolumn, new String(yytext()));
                }
{numero}        {   System.out.println("Entro numero");
                    return new Symbol(symH.numero, yyline, yycolumn, new String(yytext()));
                }
{id}            {   System.out.println("Entro id");
                    return new Symbol(symH.identificador, yyline, yycolumn, new String(yytext()));
                }
{decimal}       {   System.out.println("Entro decimal");
                    return new Symbol(symH.decimal, yyline, yycolumn, new String(yytext()));
                }
{caracter}      {   System.out.println("Entro caracter");
                    return new Symbol(symH.caracter, yyline, yycolumn, new String(yytext()));
                }

//OPERADORES VALIDOS
"("             {   System.out.println("Entro (");
                    return new Symbol(symH.par_izq, yyline, yycolumn, new String(yytext()));
                }
")"             {   System.out.println("Entro )");
                    return new Symbol(symH.par_der, yyline, yycolumn, new String(yytext()));
                }
"["             {   System.out.println("Entro [");
                    return new Symbol(symH.cor_izq, yyline, yycolumn, new String(yytext()));
                }
"]"             {   System.out.println("Entro ]");
                    return new Symbol(symH.cor_der, yyline, yycolumn, new String(yytext()));
                }
"{"             {   System.out.println("Entro {");
                    return new Symbol(symH.llave_izq, yyline, yycolumn, new String(yytext()));
                }
"}"             {   System.out.println("Entro }");
                    return new Symbol(symH.llave_der, yyline, yycolumn, new String(yytext()));
                }
"+"             {   System.out.println("Entro +");
                    return new Symbol(symH.mas, yyline, yycolumn, new String(yytext()));
                }
"-"             {   System.out.println("Entro -");
                    return new Symbol(symH.menos, yyline, yycolumn, new String(yytext()));
                }
"/"             {   System.out.println("Entro /");
                    return new Symbol(symH.div, yyline, yycolumn, new String(yytext()));
                }
"*"             {   System.out.println("Entro *");
                    return new Symbol(symH.por, yyline, yycolumn, new String(yytext()));
                }
","             {   System.out.println("Entro ,");
                    return new Symbol(symH.coma, yyline, yycolumn, new String(yytext()));
                }
"$"             {   System.out.println("Entro $");
                    return new Symbol(symH.dolar, yyline, yycolumn, new String(yytext()));
                }
"||"            {   System.out.println("Entro ||");
                    return new Symbol(symH.tkn_or, yyline, yycolumn, new String(yytext()));
                }
"&&"            {   System.out.println("Entro &&");
                    return new Symbol(symH.tkn_and, yyline, yycolumn, new String(yytext()));
                }
">"             {   System.out.println("Entro >");
                    return new Symbol(symH.mayor, yyline, yycolumn, new String(yytext()));
                }
"<"             {   System.out.println("Entro <");
                    return new Symbol(symH.menor, yyline, yycolumn, new String(yytext()));
                }
">="            {   System.out.println("Entro >=");
                    return new Symbol(symH.mayor_igual, yyline, yycolumn, new String(yytext()));
                }
"<="            {   System.out.println("Entro <=");
                    return new Symbol(symH.menor_igual, yyline, yycolumn, new String(yytext()));
                }
"=="            {   System.out.println("Entro ==");
                    return new Symbol(symH.igualacion, yyline, yycolumn, new String(yytext()));
                }
"!="            {   System.out.println("Entro !=");
                    return new Symbol(symH.diferente, yyline, yycolumn, new String(yytext()));
                }
"++"            {   System.out.println("Entro ++");
                    return new Symbol(symH.aumento, yyline, yycolumn, new String(yytext()));
                }
"!!"            {   System.out.println("Entro !!");
                    return new Symbol(symH.dadmiracion, yyline, yycolumn, new String(yytext()));
                }
"="             {   System.out.println("Entro =");
                    return new Symbol(symH.igual, yyline, yycolumn, new String(yytext()));
                }
":"             {   System.out.println("Entro :");
                    return new Symbol(symH.dosp, yyline, yycolumn, new String(yytext()));
                }
";"             {   System.out.println("Entro ;");
                    return new Symbol(symH.pyc, yyline, yycolumn, new String(yytext()));
                }

//      ESPACIO EN BLANCO
[ \t\r\f\n]+  { /* Se ignoran */} 

//ERRORES
.                    { 
                        System.out.println("Error en el token: " + yytext() + "linea: "+ yyline);
                        reportarError((yyline+1), (yycolumn+1), "El token "+ yytext()+" no forma parte del lenguaje");
                     }

