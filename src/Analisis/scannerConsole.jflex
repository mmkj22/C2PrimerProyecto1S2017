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
caracter    = "\'"([a-zA-Z0-9]|"!"|"\""|"·"|"%"|"="|"?"|"¿"|"Ç"|"_"|"-"|"."|","|"<"|">"|"@"|"#"|"~"|"½"|"¬"|";"|"["|"]"|"º"|"ª"|""|"!"|"+"|"-"|"*"|"^"|"&"|"\\"|" ")"\'"


%cupsym sym
%class lexicoConsole
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
                    return new Symbol(sym.tkn_let, yyline, yycolumn, new String(yytext()));
                }
"Calcular"      {   System.out.println("Entro Calcular");
                    return new Symbol(sym.tkn_calcular, yyline, yycolumn, new String(yytext()));
                }     
"Succ"          {   System.out.println("Entro Succ");
                    return new Symbol(sym.tkn_succ, yyline, yycolumn, new String(yytext()));
                }
"Decc"          {   System.out.println("Entro Decc");
                    return new Symbol(sym.tkn_decc, yyline, yycolumn, new String(yytext()));
                }
"Min"           {   System.out.println("Entro Min");
                    return new Symbol(sym.tkn_min, yyline, yycolumn, new String(yytext()));
                }
"Max"           {   System.out.println("Entro Max");
                    return new Symbol(sym.tkn_max, yyline, yycolumn, new String(yytext()));
                }
"sum"           {   System.out.println("Entro sum");
                    return new Symbol(sym.tkn_sum, yyline, yycolumn, new String(yytext()));
                }
"product"       {   System.out.println("Entro product");
                    return new Symbol(sym.tkn_product, yyline, yycolumn, new String(yytext()));
                }
"revers"        {   System.out.println("Entro revers");
                    return new Symbol(sym.tkn_revers, yyline, yycolumn, new String(yytext()));
                }
"impr"          {   System.out.println("Entro impr");
                    return new Symbol(sym.tkn_impr, yyline, yycolumn, new String(yytext()));
                }
"par"           {   System.out.println("Entro par");
                    return new Symbol(sym.tkn_par, yyline, yycolumn, new String(yytext()));
                }
"asc"           {   System.out.println("Entro asc");
                    return new Symbol(sym.tkn_asc, yyline, yycolumn, new String(yytext()));
                }
"desc"          {   System.out.println("Entro desc");
                    return new Symbol(sym.tkn_desc, yyline, yycolumn, new String(yytext()));
                }
"length"        {   System.out.println("Entro length");
                    return new Symbol(sym.tkn_length, yyline, yycolumn, new String(yytext()));
                }
"'pot'"         {   System.out.println("Entro pot");
                    return new Symbol(sym.potencia, yyline, yycolumn, new String(yytext()));
                }
"'mod'"         {   System.out.println("Entro mod");
                    return new Symbol(sym.mod, yyline, yycolumn, new String(yytext()));
                }
"'sqrt'"        {   System.out.println("Entro sqrt");
                    return new Symbol(sym.sqrt, yyline, yycolumn, new String(yytext()));
                }


//BASICA
{texto}         {   System.out.println("Entro texto");
                    return new Symbol(sym.texto, yyline, yycolumn, new String(yytext()));
                }
{numero}        {   System.out.println("Entro numero");
                    return new Symbol(sym.numero, yyline, yycolumn, new String(yytext()));
                }
{id}            {   System.out.println("Entro id");
                    return new Symbol(sym.identificador, yyline, yycolumn, new String(yytext()));
                }
{decimal}       {   System.out.println("Entro decimal");
                    return new Symbol(sym.decimal, yyline, yycolumn, new String(yytext()));
                }
{caracter}      {   System.out.println("Entro caracter");
                    return new Symbol(sym.caracter, yyline, yycolumn, new String(yytext()));
                }

//OPERADORES VALIDOS
"("             {   System.out.println("Entro (");
                    return new Symbol(sym.par_izq, yyline, yycolumn, new String(yytext()));
                }
")"             {   System.out.println("Entro )");
                    return new Symbol(sym.par_der, yyline, yycolumn, new String(yytext()));
                }
"["             {   System.out.println("Entro [");
                    return new Symbol(sym.cor_izq, yyline, yycolumn, new String(yytext()));
                }
"]"             {   System.out.println("Entro ]");
                    return new Symbol(sym.cor_der, yyline, yycolumn, new String(yytext()));
                }
"{"             {   System.out.println("Entro {");
                    return new Symbol(sym.llave_izq, yyline, yycolumn, new String(yytext()));
                }
"}"             {   System.out.println("Entro }");
                    return new Symbol(sym.llave_der, yyline, yycolumn, new String(yytext()));
                }
"+"             {   System.out.println("Entro +");
                    return new Symbol(sym.mas, yyline, yycolumn, new String(yytext()));
                }
"-"             {   System.out.println("Entro -");
                    return new Symbol(sym.menos, yyline, yycolumn, new String(yytext()));
                }
"/"             {   System.out.println("Entro /");
                    return new Symbol(sym.div, yyline, yycolumn, new String(yytext()));
                }
"*"             {   System.out.println("Entro *");
                    return new Symbol(sym.por, yyline, yycolumn, new String(yytext()));
                }
","             {   System.out.println("Entro ,");
                    return new Symbol(sym.coma, yyline, yycolumn, new String(yytext()));
                }
"$"             {   System.out.println("Entro $");
                    return new Symbol(sym.dolar, yyline, yycolumn, new String(yytext()));
                }
"++"            {   System.out.println("Entro ++");
                    return new Symbol(sym.aumento, yyline, yycolumn, new String(yytext()));
                }
"!!"            {   System.out.println("Entro !!");
                    return new Symbol(sym.dadmiracion, yyline, yycolumn, new String(yytext()));
                }
"="             {   System.out.println("Entro =");
                    return new Symbol(sym.igual, yyline, yycolumn, new String(yytext()));
                }
"%"             {   System.out.println("Entro %");
                    return new Symbol(sym.porcentaje, yyline, yycolumn, new String(yytext()));
                }

//      ESPACIO EN BLANCO
[ \t\r\f\n]+  { /* Se ignoran */} 

//ERRORES
.                    { 
                        System.out.println("Error en el token: " + yytext() + "linea: "+ yyline);
                        reportarError((yyline+1), (yycolumn+1), "El token "+ yytext()+" no forma parte del lenguaje");
                     }

