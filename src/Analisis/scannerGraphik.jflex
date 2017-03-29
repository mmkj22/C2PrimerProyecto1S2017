package Analisis;
import Analisis.sym.*;
import java_cup.runtime.Symbol;
import Errores.*;

%%
//EXPRESIONES REGULARES A UTILIZAR EN EL ARCHIVO DE CONFIGURACION
//cualquier texto entre comillas
texto       = "\""([^\"]*)"\""
caracter    = "\'"([a-zA-Z0-9]|"!"|"\""|"·"|"$"|"%"|"="|"?"|"¿"|"Ç"|"_"|"-"|"."|","|"<"|">"|"@"|"#"|"~"|"½"|"¬"|";"|"["|"]"|"º"|"ª"|""|"!"|"+"|"-"|"*"|"^"|"&"|"\\"|" ")"\'"
numero      = [0-9]+
letras      = [a-zA-ZñÑ]+
id          ={letras}({numero}|"_"|{letras})*
decimal     ={numero}"."{numero}
nombreArchivo = {id}".gk"
Comment = {TraditionalComment} | {EndOfLineComment} 
TraditionalComment   = "#/" ([^/]*)"/#"
EndOfLineComment     = "#"([^\n]*)


%cupsym symG
%class lexicoGraphik
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

"verdadero"     {   System.out.println("Entro verdadero");
                    return new Symbol(symG.tkn_verdadero, yyline, yycolumn, new String(yytext()));
                }
"falso"         {   System.out.println("Entro falso");
                    return new Symbol(symG.tkn_falso, yyline, yycolumn, new String(yytext()));
                }
"entero"        {   System.out.println("Entro entero");
                    return new Symbol(symG.tkn_entero, yyline, yycolumn, new String(yytext()));
                }
"decimal"       {   System.out.println("Entro decimal");
                    return new Symbol(symG.tkn_decimal, yyline, yycolumn, new String(yytext()));
                }
"caracter"      {   System.out.println("Entro caracter");
                    return new Symbol(symG.tkn_caracter, yyline, yycolumn, new String(yytext()));
                }
"cadena"        {   System.out.println("Entro cadena");
                    return new Symbol(symG.tkn_cadena, yyline, yycolumn, new String(yytext()));
                }
"bool"          {   System.out.println("Entro bool");
                    return new Symbol(symG.tkn_bool, yyline, yycolumn, new String(yytext()));
                }
"vacio"         {   System.out.println("Entro vacio");
                    return new Symbol(symG.tkn_vacio, yyline, yycolumn, new String(yytext()));
                }
"publico"       {   System.out.println("Entro publico");
                    return new Symbol(symG.tkn_publico, yyline, yycolumn, new String(yytext()));
                }
"protegido"     {   System.out.println("Entro protegido");
                    return new Symbol(symG.tkn_protegido, yyline, yycolumn, new String(yytext()));
                }
"privado"       {   System.out.println("Entro privado");
                    return new Symbol(symG.tkn_privado, yyline, yycolumn, new String(yytext()));
                }
"importar"      {   System.out.println("Entro importar");
                    return new Symbol(symG.tkn_importar, yyline, yycolumn, new String(yytext()));
                }
"ALS"           {   System.out.println("Entro ALS");
                    return new Symbol(symG.tkn_als, yyline, yycolumn, new String(yytext()));
                }
"hereda"        {   System.out.println("Entro hereda");
                    return new Symbol(symG.tkn_hereda, yyline, yycolumn, new String(yytext()));
                }
"var"           {   System.out.println("Entro var");
                    return new Symbol(symG.tkn_var, yyline, yycolumn, new String(yytext()));
                }
"nuevo"         {   System.out.println("Entro nuevo");
                    return new Symbol(symG.tkn_nuevo, yyline, yycolumn, new String(yytext()));
                }
"retornar"      {   System.out.println("Entro retornar");
                    return new Symbol(symG.tkn_retornar, yyline, yycolumn, new String(yytext()));
                }
"llamar"        {   System.out.println("Entro llamar");
                    return new Symbol(symG.tkn_llamar, yyline, yycolumn, new String(yytext()));
                }
"inicio"        {   System.out.println("Entro inicio");
                    return new Symbol(symG.tkn_inicio, yyline, yycolumn, new String(yytext()));
                }
"incluir_HK"    {   System.out.println("Entro incluir_HK");
                    return new Symbol(symG.tkn_incluirHK, yyline, yycolumn, new String(yytext()));
                }
"llamarHK"      {   System.out.println("Entro llamarHK");
                    return new Symbol(symG.tkn_llamarHK, yyline, yycolumn, new String(yytext()));
                }
"Si"            {   System.out.println("Entro Si");
                    return new Symbol(symG.tkn_si, yyline, yycolumn, new String(yytext()));
                }
"Sino"          {   System.out.println("Entro Sino");
                    return new Symbol(symG.tkn_sino, yyline, yycolumn, new String(yytext()));
                }
"Seleccion"     {   System.out.println("Entro seleccion");
                    return new Symbol(symG.tkn_seleccion, yyline, yycolumn, new String(yytext()));
                }
"Caso"          {   System.out.println("Entro caso");
                    return new Symbol(symG.tkn_caso, yyline, yycolumn, new String(yytext()));
                }
"Defecto"       {   System.out.println("Entro Defecto");
                    return new Symbol(symG.tkn_defecto, yyline, yycolumn, new String(yytext()));
                }
"Para"          {   System.out.println("Entro Para");
                    return new Symbol(symG.tkn_para, yyline, yycolumn, new String(yytext()));
                }
"Mientras"      {   System.out.println("Entro Mientras");
                    return new Symbol(symG.tkn_mientras, yyline, yycolumn, new String(yytext()));
                }
"Hacer"         {   System.out.println("Entro Hacer");
                    return new Symbol(symG.tkn_hacer, yyline, yycolumn, new String(yytext()));
                }
"Continuar"     {   System.out.println("Entro Continuar");
                    return new Symbol(symG.tkn_continuar, yyline, yycolumn, new String(yytext()));
                }
"Terminar"      {   System.out.println("Entro Terminar");
                    return new Symbol(symG.tkn_terminar, yyline, yycolumn, new String(yytext()));
                }
"graphikar_funcion" {   System.out.println("Entro graphikar_funcion");
                        return new Symbol(symG.tkn_graphikar, yyline, yycolumn, new String(yytext()));
                    }
"datos"         {   System.out.println("Entro Datos");
                    return new Symbol(symG.tkn_datos, yyline, yycolumn, new String(yytext()));
                }
"columna"       {   System.out.println("Entro Columna");
                    return new Symbol(symG.tkn_columna, yyline, yycolumn, new String(yytext()));
                }
"procesar"      {   System.out.println("Entro Procesar");
                    return new Symbol(symG.tkn_procesar, yyline, yycolumn, new String(yytext()));
                }
"donde"         {   System.out.println("Entro Donde");
                    return new Symbol(symG.tkn_donde, yyline, yycolumn, new String(yytext()));
                }
"dondecada"     {   System.out.println("Entro DondeCada");
                    return new Symbol(symG.tkn_dondecada, yyline, yycolumn, new String(yytext()));
                }
"DondeTodo"     {   System.out.println("Entro DondeTodo");
                    return new Symbol(symG.tkn_dondetodo, yyline, yycolumn, new String(yytext()));
                }
"imprimir"      {   System.out.println("Entro imprimir");
                    return new Symbol(symG.tkn_imprimir, yyline, yycolumn, new String(yytext()));
                }
"imprimirK"      {   System.out.println("Entro imprimir");
                    return new Symbol(symG.tkn_imprimirK, yyline, yycolumn, new String(yytext()));
                }

//BASICA
{texto}         {   System.out.println("Entro texto");
                    return new Symbol(symG.texto, yyline, yycolumn, new String(yytext()));
                }
{numero}        {   System.out.println("Entro numero");
                    return new Symbol(symG.numero, yyline, yycolumn, new String(yytext()));
                }
{id}            {   System.out.println("Entro id");
                    return new Symbol(symG.identificador, yyline, yycolumn, new String(yytext()));
                }
{decimal}       {   System.out.println("Entro decimal");
                    return new Symbol(symG.decimal, yyline, yycolumn, new String(yytext()));
                }
{nombreArchivo} {   System.out.println("Entro nombreArchivo");
                    return new Symbol(symG.nombreArchivo, yyline, yycolumn, new String(yytext()));
                }
{caracter}      {
                    System.out.println("Entro Caracter");
                    return new Symbol(symG.caracter, yyline, yycolumn, new String(yytext()));
                }

//OPERADORES VALIDOS
"("             {   System.out.println("Entro (");
                    return new Symbol(symG.par_izq, yyline, yycolumn, new String(yytext()));
                }
")"             {   System.out.println("Entro )");
                    return new Symbol(symG.par_der, yyline, yycolumn, new String(yytext()));
                }
"["             {   System.out.println("Entro [");
                    return new Symbol(symG.cor_izq, yyline, yycolumn, new String(yytext()));
                }
"]"             {   System.out.println("Entro ]");
                    return new Symbol(symG.cor_der, yyline, yycolumn, new String(yytext()));
                }
"{"             {   System.out.println("Entro {");
                    return new Symbol(symG.llave_izq, yyline, yycolumn, new String(yytext()));
                }
"}"             {   System.out.println("Entro }");
                    return new Symbol(symG.llave_der, yyline, yycolumn, new String(yytext()));
                }
"+"             {   System.out.println("Entro +");
                    return new Symbol(symG.mas, yyline, yycolumn, new String(yytext()));
                }
"-"             {   System.out.println("Entro -");
                    return new Symbol(symG.menos, yyline, yycolumn, new String(yytext()));
                }
"/"             {   System.out.println("Entro /");
                    return new Symbol(symG.div, yyline, yycolumn, new String(yytext()));
                }
"^"             {   System.out.println("Entro *");
                    return new Symbol(symG.potencia, yyline, yycolumn, new String(yytext()));
                }
"*"             {   System.out.println("Entro *");
                    return new Symbol(symG.por, yyline, yycolumn, new String(yytext()));
                }
","             {   System.out.println("Entro ,");
                    return new Symbol(symG.coma, yyline, yycolumn, new String(yytext()));
                }
"?"             {   System.out.println("Entro ?");
                    return new Symbol(symG.int_der, yyline, yycolumn, new String(yytext()));
                }
"||"            {   System.out.println("Entro ||");
                    return new Symbol(symG.tkn_or, yyline, yycolumn, new String(yytext()));
                }
"&&"            {   System.out.println("Entro &&");
                    return new Symbol(symG.tkn_and, yyline, yycolumn, new String(yytext()));
                }
"&|"            {   System.out.println("Entro &|");
                    return new Symbol(symG.tkn_xor, yyline, yycolumn, new String(yytext()));
                }
"!"             {   System.out.println("Entro !");
                    return new Symbol(symG.tkn_not, yyline, yycolumn, new String(yytext()));
                }
">"             {   System.out.println("Entro >");
                    return new Symbol(symG.mayor, yyline, yycolumn, new String(yytext()));
                }
"<"             {   System.out.println("Entro <");
                    return new Symbol(symG.menor, yyline, yycolumn, new String(yytext()));
                }
">="            {   System.out.println("Entro >=");
                    return new Symbol(symG.mayor_igual, yyline, yycolumn, new String(yytext()));
                }
"<="            {   System.out.println("Entro <=");
                    return new Symbol(symG.menor_igual, yyline, yycolumn, new String(yytext()));
                }
"=="            {   System.out.println("Entro ==");
                    return new Symbol(symG.igualacion, yyline, yycolumn, new String(yytext()));
                }
"!="            {   System.out.println("Entro !=");
                    return new Symbol(symG.diferente, yyline, yycolumn, new String(yytext()));
                }
"++"            {   System.out.println("Entro ++");
                    return new Symbol(symG.aumento, yyline, yycolumn, new String(yytext()));
                }
"--"            {   System.out.println("Entro --");
                    return new Symbol(symG.decremento, yyline, yycolumn, new String(yytext()));
                }
"="             {   System.out.println("Entro =");
                    return new Symbol(symG.igual, yyline, yycolumn, new String(yytext()));
                }
":"             {   System.out.println("Entro :");
                    return new Symbol(symG.dosp, yyline, yycolumn, new String(yytext()));
                }
"."             {   System.out.println("Entro .");
                    return new Symbol(symG.punto, yyline, yycolumn, new String(yytext()));
                }
{Comment}       { }

//      ESPACIO EN BLANCO
[ \t\r\f\n]+  { /* Se ignoran */} 


//ERRORES
.                    {  System.out.println("Error en el token: " + yytext() + "linea: "+ yyline);
                        reportarError((yyline+1), (yycolumn+1), "El token "+ yytext()+" no forma parte del lenguaje");
                     }


