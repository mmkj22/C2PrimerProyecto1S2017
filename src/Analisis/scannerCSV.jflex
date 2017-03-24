package Analisis;
import Analisis.sym.*;
import java_cup.runtime.Symbol;
import Errores.*;

%%
//EXPRESIONES REGULARES A UTILIZAR EN EL ARCHIVO DE CONFIGURACION
texto       = "\""([^\"]*)"\""
numero      = [0-9]+
decimal     ={numero}"."{numero}
salto       =[\n]+


%cupsym symCSV
%class lexicoCSV
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

//BASICA
{texto}         {   System.out.println("Entro texto");
                    return new Symbol(symCSV.texto, yyline, yycolumn, new String(yytext()));
                }
{numero}        {   System.out.println("Entro numero");
                    return new Symbol(symCSV.numero, yyline, yycolumn, new String(yytext()));
                }
{decimal}       {   System.out.println("Entro decimal");
                    return new Symbol(symCSV.decimal, yyline, yycolumn, new String(yytext()));
                }
{salto}         {
                    System.out.println("Entro salto");
                    return new Symbol(symCSV.salto, yyline, yycolumn, new String(yytext()));
                }

//OPERADORES VALIDOS
"["             {   System.out.println("Entro [");
                    return new Symbol(symCSV.cor_izq, yyline, yycolumn, new String(yytext()));
                }
"]"             {   System.out.println("Entro ]");
                    return new Symbol(symCSV.cor_der, yyline, yycolumn, new String(yytext()));
                }
"{"             {   System.out.println("Entro {");
                    return new Symbol(symCSV.llave_izq, yyline, yycolumn, new String(yytext()));
                }
"}"             {   System.out.println("Entro }");
                    return new Symbol(symCSV.llave_der, yyline, yycolumn, new String(yytext()));
                }
","             {   System.out.println("Entro ,");
                    return new Symbol(symCSV.coma, yyline, yycolumn, new String(yytext()));
                }
"%"             {
                    System.out.println("Entro !");
                    return new Symbol(symCSV.finCadena, yyline, yycolumn, new String(yytext()));
                }



//      ESPACIO EN BLANCO
[ \t\r\f]+  { /* Se ignoran */} 

//ERRORES
.                    { 
                        System.out.println("Error en el token: " + yytext() + "linea: "+ yyline);
                        reportarError((yyline+1), (yycolumn+1), "El token "+ yytext()+" no forma parte del lenguaje");
                     }


