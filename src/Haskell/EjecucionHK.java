/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;

import Errores.Errores;
import java.util.Hashtable;
import java.util.Stack;
import java.math.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**;

 *
 * @author Kristhal
 */
public class EjecucionHK {
    
    TablaSimbolos tabla;
    Hashtable<String, NodoTabla> hash = new Hashtable<String, NodoTabla>();
    NodoHK root;
    Stack ambito = new Stack();
    Stack actual = new Stack();
    JTextArea txtConsola;
    Value valRetorno;
    Value arregloInit= new Value();
    int dimensiones=0;
    int total=0;
    private Errores err = Errores.getInstance();
    
            
    public EjecucionHK(NodoHK root, JTextArea txtConsola)
    {
        this.root=root;
        this.txtConsola=txtConsola;
        this.tabla=TablaSimbolos.getInstance();
        this.hash=tabla.getHash();
        this.actual.push(this.hash.clone());
    }
    
    public void Ejecutar()
    {
        this.ambito.push("");
        this.recorrido("", root);
    }

    
    private Value evaluarExpresion(String ambito, NodoHK nodo)
    {
        Value temp1, temp2;
        Value content = new Value();
        NodoTabla nodeTable;
        //OPERACIONES ARITMETICAS--------------------------------------------------------------------------------------------
        if(nodo.valor.equals("mas"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal() + (Double)temp2.getVal());
                return content;
            }
            else
            {
                err.nuevoError("No se pueden sumar los tipos "+temp1.tipo+" y "+ temp2.tipo);
                //ERROR
                return null;
            }
        }
        else if(nodo.valor.equals("menos"))
        {
            if(nodo.hijos.size()==2){
                temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
                temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
                if(temp1==null || temp2==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return null;
                }
                if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
                {
                    content.setTipo("numero");
                    content.setVal((Double)temp1.getVal() - (Double)temp2.getVal());
                    return content;
                }
                else
                {
                    //ERROR
                    err.nuevoError("No se pueden sumar los tipos "+temp1.tipo+" y "+ temp2.tipo);
                    return null;
                }
            }
            else
            {
                temp1=this.evaluarExpresion(ambito,nodo.hijos.get(0));
                if(temp1==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return null;
                }
                if(temp1.getTipo().equals("numero"))
                {
                    content.setTipo(temp1.getTipo());
                    content.setVal(Double.parseDouble("-"+temp1.getVal()));
                    return content;
                }
                else
                {
                    //Error
                    err.nuevoError("No se pueden poner negativos los tipos "+temp1.tipo);
                    return null;
                }
            }
        }
        else if(nodo.valor.equals("por"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal() * (Double)temp2.getVal());
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se pueden multiplicar los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("div"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal() / (Double)temp2.getVal());
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se pueden dividir los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("mod"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal() % (Double)temp2.getVal());
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se puede hacer modularidad en los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            } 
        }
        else if(nodo.valor.equals("potencia"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal(Math.pow((Double)temp1.getVal(),(Double)temp2.getVal()));
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se puede hacer potencia en  los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }        
        }
        else if(nodo.valor.equals("sqrt"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null || temp2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal(Math.pow((Double)temp1.getVal(),1/(Double)temp2.getVal()));
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se puede sacar raiz a los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }              
        }
        //OPERACIONES RELACIONALES--------------------------------------------------------------------------------------------
        else if(nodo.valor.equals("menorI"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if((Double)temp1.getVal()<=(Double)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado<=0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else
            {
                err.nuevoError("No se pueden comparar menor igual los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        
        else if(nodo.valor.equals("mayorI"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if((Double)temp1.getVal()>=(Double)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado>=0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }   
            else
            {
                err.nuevoError("No se pueden comparar mayor igual los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("menor"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if((Double)temp1.getVal()<(Double)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado<0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }  
            else
            {
                err.nuevoError("No se pueden comparar menor los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("mayor"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if((Double)temp1.getVal()>(Double)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado>0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }  
            else
            {
                err.nuevoError("No se pueden comparar mayor los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("diferente"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if(!Objects.equals((Double)temp1.getVal(), (Double)temp2.getVal()))
                {
                    content.setTipo("bool");
                    
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado!=0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }  
            else
            {
                err.nuevoError("No se pueden comparar diferente los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("igualacion"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if (temp1 == null || temp2 == null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero") && temp2.getTipo().equals("numero"))
            {
                if(Objects.equals((Double)temp1.getVal(), (Double)temp2.getVal()))
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else if(temp1.getTipo().equals("cadena") && temp2.getTipo().equals("cadena"))
            {
                int Resultado = temp1.getVal().toString().compareTo((String)temp2.getVal());
                if(Resultado==0)
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else 
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            } 
            else
            {
                err.nuevoError("No se pueden comparar igual los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        //OPERACIONES LOGICAS----------------------------------------------------------------------------------------------
        else if(nodo.valor.equals("and"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                //Error
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("bool") && temp2.getTipo().equals("bool"))
            {
                if((Boolean)temp1.getVal() && (Boolean)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else
            {
                //ERROR
                err.nuevoError("No se pueden comparar en expresiones logicas los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        else if(nodo.valor.equals("or"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                //Error
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("bool") && temp2.getTipo().equals("bool"))
            {
                if((Boolean)temp1.getVal() || (Boolean)temp2.getVal())
                {
                    content.setTipo("bool");
                    content.setVal(true);
                    return content;
                }
                else
                {
                    content.setTipo("bool");
                    content.setVal(false);
                    return content;
                }
            }
            else
            {
                //ERROR
                err.nuevoError("No se pueden comparar en expresiones logicas los tipos "+temp1.tipo+" y "+ temp2.tipo);
                return null;
            }
        }
        //OTRAS--------------------------------------------------------------------------------------------------------------
        else if(nodo.valor.equals("entero"))
        {
            content.setTipo("numero");
            content.setVal(Double.parseDouble(nodo.hijos.get(0).valor));
            return content;
        }
        else if(nodo.valor.equals("decimal"))
        {
            content.setTipo("numero");
            content.setVal(Double.parseDouble(nodo.hijos.get(0).valor));
            return content;
        }
        else if(nodo.valor.equals("caracter"))
        {
            char[] car = nodo.hijos.get(0).valor.replace("\'", "").toCharArray();
            content.setTipo("caracter");
            content.setVal(car[0]);
            return content;
        }
        else if(nodo.valor.equals("cadena"))
        {
            String cad=nodo.hijos.get(0).valor.replace("\"", "");
            content.setIsArreglo(true);
            content.setTipo("cadena");
            content.setVal(cad);
            content.setDimensiones(1);
            content.setElementosArreglo(this.ingresarArreglo(cad));
            content.setImprimirPantalla(content.imprimirArreglo());
            return content;
        }
        else if(nodo.valor.equals("LLAMADA_MET"))
        {
            nodeTable = this.llamadaMetodo(ambito, nodo);
            if(nodeTable==null)
            {
                err.nuevoError("No se encontro el metodo");
                return null;
            }
            this.recorrido((String)this.ambito.peek(), nodeTable.getExp());
            content=this.getUltimo().getVal();
            this.ambito.pop();
            this.actual.pop();
            ambito=(String)this.ambito.peek();
            return content;
        }
        else if(nodo.valor.equals("lista"))
        {
            //AQUI ENTRA CUANDO EN EXP SE TRAE UNA LISTA DECLARADA [1,2] O [[1,2][3,2]]
            //Enviar a llamar al init a para obtener el arreglo
            temp1=this.inita(ambito, nodo);
            if(temp1==null)
            {
                err.nuevoError("No se pudo asignar la lista");
                return null;
            }
            nodeTable=this.getUltimo();
            nodeTable.setVal(temp1);
            content=temp1;
            return content;
        }
        else if(nodo.valor.equals("identificador"))
        {
            nodeTable=this.existeVariable(ambito, nodo.hijos.get(0));
            if(nodeTable==null)
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "La variable "+ nodo.hijos.get(0).valor + " no ha sido declarada");
                return null;
            }
            content=nodeTable.getVal();
            return content;
        }
        //METODOS NATIVOS---------------------------------------------------------------------------------------------------------------
        else if(nodo.valor.equals("SUCC"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal()+1);
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se puede realizar succ en un tipo que no sea numerico");
                return null;
            }
        }
        else if(nodo.valor.equals("DECC"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.getTipo().equals("numero"))
            {
                content.setTipo("numero");
                content.setVal((Double)temp1.getVal()-1);
                return content;
            }
            else
            {
                //ERROR
                err.nuevoError("No se puede realizar succ en un tipo que no sea numerico");
                return null;
            }
        }
        else if(nodo.valor.equals("MIN"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.minArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar MIN si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("MAX"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.maxArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar MAX si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("SUM"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.sumArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar SUM si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("PRODUCT"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.productArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar PRODUCT si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("REVERS"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                int sePudo = temp1.reversArreglo();
                if(sePudo==1){
                    content = temp1;
                    nodeTable=this.getUltimo();
                    nodeTable.setVal(content);
                    return content;
                }
            }
            else
            {
                err.nuevoError("No se puede realizar REVERS si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("IMPR"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.imparArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar IMPR si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("PAR"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.parArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar PAR si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("ASC"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                int sePudo = temp1.ordenarArregloAscendente();
                if(sePudo==1){
                    content = temp1;
                    nodeTable=this.getUltimo();
                    nodeTable.setVal(content);
                    return content;
                }
            }
            else
            {
                err.nuevoError("No se puede realizar ASC si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("DESC"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                int sePudo = temp1.ordenarArregloDescendente();
                if(sePudo==1){
                    content = temp1;
                    nodeTable=this.getUltimo();
                    nodeTable.setVal(content);
                    return content;
                }
            }
            else
            {
                err.nuevoError("No se puede realizar DESC si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("LENGTH"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                content = temp1.lengthArreglo();
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
            else
            {
                err.nuevoError("No se puede realizar LENGTH si no es arreglo");
                return null;
            }
        }
        else if(nodo.valor.equals("CONCAT"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            for(NodoHK n : nodo.hijos.get(1).hijos)
            {
                temp2=this.evaluarExpresion(ambito, n);
                if(temp2==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return null;
                }
                temp1=this.concatArreglos(temp1, temp2);
            }
            content=temp1;
            nodeTable = this.getUltimo();
            nodeTable.setVal(content);
            return content;
        }
        else if(nodo.valor.equals("INDICE"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(temp1.isIsArreglo())
            {
                if(nodo.hijos.get(1).hijos.size()>2)
                {
                    return null;
                }
                List<Value> valores = new ArrayList();
                for(NodoHK n : nodo.hijos.get(1).hijos)
                {
                    temp2=this.evaluarExpresion(ambito, n);
                    if(temp2==null)
                    {
                        return null;
                    }
                    valores.add(temp2);
                }
                content=temp1.buscarIndice(valores);
                if(content==null)
                {
                    return null;
                }
                nodeTable=this.getUltimo();
                nodeTable.setVal(content);
                return content;
            }
        }
        else if(nodo.valor.equals("CALCULAR"))
        {
            content = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            return content;
        }
        else if(nodo.valor.equals("PORCENTAJE"))
        {
            nodeTable=this.getUltimo();
            if(nodeTable==null)
            {
                err.nuevoError("No se encontro el valor del ultimo");
                return null;
            }
            nodeTable.setTipo(nodeTable.getVal().getTipo());
            content=nodeTable.getVal();
            return content;
        }
        return null;
    }
    
    NodoTabla llamadaMetodo(String ambito, NodoHK nodo)
    {
        String s2, codb="";
        Value aux1, aux2;
        NodoTabla reg, reg2;
        String [] lista;
        boolean bien;
        int i=1;
        reg=this.buscarMetodo(nodo.hijos.get(0).valor);
        if(reg!=null)
        {
            if(reg.getRol().equals("func")){
                i=0;
                if(nodo.hijos.size()==2)
                {
                    lista=reg.getDimension().split("_");
                    for(NodoHK n : nodo.hijos.get(1).hijos)
                    {
                        s2=reg.getNombre()+"_"+lista[i];
                        reg2=(NodoTabla)this.hash.get(s2);
                        aux1=this.evaluarExpresion(ambito, n);
                        if(reg2!=null && aux1!=null)
                        {
                            if(reg2.getRol().equals("param"))
                            {
                                aux2=this.evaluarAsignacion(reg2.getTipo(), aux1);
                                if(aux2!=null)
                                {
                                    bien=this.modificarValor(reg2.getNombre(), aux2);
                                }
                            }
                        }
                        i++;
                    }
                }
            }
        }
        return reg;
    }
    
    Value evaluarAsignacion(String tipo, Value valor)
    {
        Value content;
        if(tipo.equals("") && valor.getTipo().equals("numero"))
        {
            content=valor;
            content.setTipo("numero");
            return content;
        }
        else if(tipo.equals("numero") && valor.getTipo().equals(""))
        {
            content=valor;
            content.setTipo("numero");
            return content;
        }
        else if(tipo.equals("") && valor.getTipo().equals("cadena"))
        {
            content=valor;
            content.setTipo("cadena");
            return content;
        }
        else if (tipo.equals("cadena") && valor.getTipo().equals(""))
        {
            content=valor;
            content.setTipo("cadena");
            return content;
        }
        else if(tipo.equals("") && valor.getTipo().equals("caracter"))
        {
            content=valor;
            content.setTipo("caracter");
            return content;
        }
        else if (tipo.equals("caracter") && valor.getTipo().equals(""))
        {
            content=valor;
            content.setTipo("caracter");
            return content;
        }
        else
        {
            err.nuevoError("No se puede asignar un tipo "+tipo+ " a un tipo "+valor.getTipo());
            return null;
        }
    }
    
    NodoTabla buscarMetodo(String nombre)
    {
        NodoTabla content;
        NodoTabla registro;
        registro=(NodoTabla)this.hash.get(nombre);
        if(registro!=null)
        {
            content=registro;
            this.ambito.push(content.getNombre());
            Hashtable<String, NodoTabla> tope = (Hashtable)actual.peek();
            this.actual.push(this.clonarHash(tope));
            return content;
        }
        err.nuevoError("No se encontro el metodo "+ nombre);
        return null;
    }
    
    List<Value> ingresarArreglo(String cadena)
    {
        List<Value> array=new ArrayList();
        char[] caracteres = cadena.toCharArray();
        for(int i=0; i<caracteres.length; i++)
        {
            Value valor = new Value();
            valor.setTipo("cadena");
            valor.setVal(caracteres[i]);
            array.add(valor);
        }
        return array;
    }
    
    NodoTabla existeVariable(String ambito, NodoHK nodo)
    {
        NodoTabla aux1;
        aux1=this.getIdsLocal(ambito, nodo, 2);
        if(aux1==null)
        {
            aux1=this.getIdsGlobal("",nodo,2);
        }
        return aux1;
    }
    
    NodoTabla getIdsLocal(String ambito, NodoHK nodo, int que)
    {
        NodoTabla registro;
        Hashtable<String,NodoTabla> tablaHash;
        tablaHash=(Hashtable)actual.peek();
        registro=(NodoTabla) tablaHash.get(ambito+"_"+nodo.valor);
        if(registro!=null)
        {
            return registro;
        }
        err.nuevoErrorSemantico(nodo.linea, nodo.columna, "La variable "+ nodo.valor+ " no ha sido declarada" );
        return null;
    }
    
    NodoTabla getIdsGlobal(String ambito, NodoHK nodo, int que)
    {
        NodoTabla registro;
        registro=(NodoTabla)this.hash.get(nodo.valor);
        if(registro!=null)
        {
            return registro;
        }
        err.nuevoErrorSemantico(nodo.linea, nodo.columna, "La variable "+ nodo.valor+ " no ha sido declarada" );
        return null;
    }
    
    public NodoTabla getUltimo()
    {
        if(ambito.peek().equals("")){
            NodoTabla registro;
            registro=(NodoTabla)this.hash.get("%");
            if(registro!=null)
            {
                if(registro.getVal()==null)
                {
                    JOptionPane.showMessageDialog(null,"No se tiene ningun ultimo valor almacenado","Porcentaje",JOptionPane.ERROR);
                }
                else
                {
                    return registro;
                }
            }
        }
        else
        {
            NodoTabla registro;
            Hashtable table= (Hashtable)this.actual.peek();
            registro=(NodoTabla)table.get("%");
            if(registro!=null)
            {
                if(registro.getVal()==null)
                {
                    JOptionPane.showMessageDialog(null,"No se tiene ningun ultimo valor almacenado","Porcentaje",JOptionPane.ERROR);
                }
                else
                {
                    return registro;
                }
            }
        }
        return null;
    }
    
    void recorrido(String ambito, NodoHK nodo)
    {
        NodoTabla aux1, aux4;
        Value aux2, aux3;
        boolean bien;
        for(NodoHK x : nodo.hijos)
        {
            if(x.valor.equals("DECLARA_ASIGNA_LISTA"))
            {
                System.out.println("EJ: Entro a Declara Asigna Lista");
                if(x.hijos.get(1).valor.equals("cadena"))
                {
                    aux3=this.evaluarExpresion(ambito, x.hijos.get(1));
                    if(aux3==null)
                    {
                        err.nuevoErrorSemantico(x.hijos.get(0).linea, x.hijos.get(0).columna, "Expresion Invalida");
                        //Error
                    }
                    aux1=this.existeVariable(ambito, x.hijos.get(0));
                    if(aux1==null)
                    {
                        err.nuevoErrorSemantico(x.hijos.get(0).linea, x.hijos.get(0).columna, "La variable "+ x.hijos.get(0).valor+" no ha sido declarada");
                        return;
                    }
                    bien=modificarValor(aux1.getNombre(), aux3);
                    if(bien==false)
                    {
                        modificarValorGlobales(aux1.getNombre(), aux3);
                    }
                    if(bien==false)
                    {
                        return;
                    }
                    aux1=this.getUltimo();
                    aux1.setVal(aux3);
                }
                else
                {
                    aux1=this.decArreglo(ambito, x);
                    aux4=this.getUltimo();
                    aux4.setVal(aux1.getVal());
                }
            }
            else if(x.valor.equals("SINO"))
            {
                System.out.println("EJ: Entro a Sino");
                this.hacerIfElse(ambito, x);
            }
            else if(x.valor.equals("NCASE"))
            {
                System.out.println("EJ: Entro a Case");
                this.hacerSwitch(ambito, x);
            }
            else if(x.valor.equals("LLAMADA_MET"))
            {
                System.out.println("EJ: Entro a Llamada Metodo");
                aux1=this.llamadaMetodo(ambito,x);
                if(aux1==null)
                {
                    err.nuevoError("No se pudo realizar la llamada al metodo");
                    return;
                }
                this.recorrido((String)this.ambito.peek(), aux1.getExp());
                NodoTabla temp = this.getUltimo();
                this.ambito.pop();
                this.actual.pop();
                NodoTabla ultimo = this.getUltimo();
                ultimo.setVal(temp.getVal());
                ambito=(String)this.ambito.peek();
            }
            else if(x.valor.equals("CALCULAR"))
            {
                System.out.println("EJ: Entro a Calcular");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                aux3.setImprimirPantalla(String.valueOf(aux3.getVal()));
                aux1=this.getUltimo();
                aux1.setVal(aux3);
            }
            else if(x.valor.equals("SUCC"))
            {
                System.out.println("EJ: Entro a Succ");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(aux3.isIsArreglo())
                {
                    err.nuevoError("Para una operacion SUCC se necesita que la variable sea un arreglo");
                    return;
                }
                aux3.setVal((Double)aux3.getVal()+1);
                aux3.setImprimirPantalla(String.valueOf(aux3.getVal()));
                aux1=this.getUltimo();
                aux1.setVal(aux3);
                
            }
            else if(x.valor.equals("DECC"))
            {
                System.out.println("EJ: Entro a Decc");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(aux3.isIsArreglo())
                {
                    err.nuevoError("Para una operacion DECC se necesita que la variable sea un arreglo");
                    return;
                }
                aux3.setVal((Double)aux3.getVal()-1);
                aux3.setImprimirPantalla(String.valueOf(aux3.getVal()));
                aux1=this.getUltimo();
                aux1.setVal(aux3);
            }
            else if(x.valor.equals("MIN"))
            {
               System.out.println("EJ: Entro a Min");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion MIN se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.minArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("MAX"))
            {
                System.out.println("EJ: Entro a Max");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion MAX se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.maxArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("SUM"))
            {
                System.out.println("EJ: Entro a Sum");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion SUM se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.sumArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("PRODUCT"))
            {
                System.out.println("EJ: Entro a Product");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion PRODUCT se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.productArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("REVERS"))
            {
                System.out.println("EJ: Entro a Product");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion REVERS se necesita que la variable sea un arreglo");
                    return;
                }
                int res = aux3.reversArreglo();
                if(res==0)
                {
                    err.nuevoError("No se pudo realizar la operacion de Reversa en el arreglo");
                    return;
                }
                aux1=this.getUltimo();
                aux1.setVal(aux3);
            }
            else if(x.valor.equals("IMPR"))
            {
                System.out.println("EJ: Entro a IMPR");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion IMPR se necesita que la variable sea un arreglo");
                    return;
                    
                }
                Value ret = aux3.imparArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("PAR"))
            {
                System.out.println("EJ: Entro a PAR");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion PAR se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.parArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("ASC"))
            {
                System.out.println("EJ: Entro a Ascendente");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion ASC se necesita que la variable sea un arreglo");
                    return;
                }
                int res = aux3.ordenarArregloAscendente();
                if(res==0)
                {
                    err.nuevoError("No se pudo realizar el orden asc en el arreglo");
                    return;
                }
                aux1=this.getUltimo();
                aux1.setVal(aux3);
            }
            else if(x.valor.equals("DESC"))
            {
               System.out.println("EJ: Entro a Descendente");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion DESC se necesita que la variable sea un arreglo");
                    return;
                }
                int res = aux3.ordenarArregloDescendente();
                if(res==0)
                {
                    err.nuevoError("No se pudo realizar la operacion DESC en el arreglo");
                    return;
                }
                aux1=this.getUltimo();
                aux1.setVal(aux3);
            }
            else if(x.valor.equals("INDICE"))
            {
                System.out.println("EJ: Entro Indice");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    err.nuevoError("Para una operacion INDICE se necesita que la variable sea un arreglo");
                    return;
                }
                if(x.hijos.get(1).hijos.size()>2)
                {
                    return;
                }
                List<Value> valores = new ArrayList();
                for(NodoHK n : x.hijos.get(1).hijos)
                {
                    aux2=this.evaluarExpresion(ambito, n);
                    if(aux2==null)
                    {
                        err.nuevoError("Expresion Invalida");
                        return;
                    }
                    valores.add(aux2);
                }
                Value ret = aux3.buscarIndice(valores);
                if(ret==null)
                {
                    err.nuevoError("No se pudo encontrar el INDICE en el arreglo");
                    return;
                }
                aux1=this.getUltimo();
                aux1.setVal(ret);
                
            }
            else if(x.valor.equals("LENGTH"))
            {
                System.out.println("EJ: Entro a Length");
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    //Error
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                if(!aux3.isIsArreglo())
                {
                    //ERROR
                    err.nuevoError("Para una operacion LENGTH se necesita que la variable sea un arreglo");
                    return;
                }
                Value ret = aux3.lengthArreglo();
                aux1=this.getUltimo();
                aux1.setVal(ret);
            }
            else if(x.valor.equals("CONCAT"))
            {
                aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return;
                }
                for(NodoHK n : x.hijos.get(1).hijos)
                {
                    aux2=this.evaluarExpresion(ambito, n);
                    if(aux2==null)
                    {
                        err.nuevoError("Expresion Invalida");
                        return;
                    }
                    aux3=this.concatArreglos(aux3, aux2);
                }
                aux1 = this.getUltimo();
                aux1.setVal(aux3);
            }
        }
    }
    
    void hacerIfElse(String ambito, NodoHK nodo)
    {
        Value aux3;
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(0).hijos.get(0));
        if(aux3==null || !aux3.getTipo().equals("bool"))
        {
            //ERROR
            err.nuevoError("IF: No se puede realizar una expresion que no sea de tipo booleana");
            return;
        }
        if(aux3.getTipo().equals("bool") && (Boolean)aux3.getVal())
        {
            this.recorrido(ambito, nodo.hijos.get(0).hijos.get(1));
        }
        else
        {
            this.recorrido(ambito, nodo.hijos.get(1));
        }
    }
    
    void hacerSwitch(String ambito, NodoHK nodo)
    {
        Value aux1, aux2;
        Value ret=new Value();
        NodoTabla nodeTable;
        boolean alguno=false;
        aux1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
        if(aux1==null)
        {
            //Error
            err.nuevoError("SWITCH : No se puede realizar una expresion que no sea de tipo booleana");
            return;
        }
        if(aux1.isIsArreglo())
        {
            err.nuevoError("No se permiten valores de tipo arreglo en las expresiones del switch");
            return;
        }
        for(int i=0; i<nodo.hijos.get(1).hijos.size(); i++)
        {
            aux2=this.evaluarExpresion(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(0));
            if(!aux2.getTipo().equals(aux1.getTipo()))
            {
                //ERROR
                return;
            }
            if(aux1.getVal().equals(aux2.getVal()))
            {   
                alguno=true;
                this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                
            }
        }
        if(alguno==false)
        {
            //ESTE ES EL DEFAULT
            if(aux1.getTipo().equals("numero"))
            {
                nodeTable=this.getUltimo();
                ret.setTipo("numero");
                ret.setVal(0.0);
                ret.setImprimirPantalla("0");
                nodeTable.setVal(ret);
            }
            else if(aux1.getTipo().equals("cadena"))
            {
                nodeTable=this.getUltimo();
                ret.setTipo("caracter");
                ret.setVal(' ');
                ret.setImprimirPantalla("''");
                nodeTable.setVal(ret);
            }
        }
    }
    
    NodoTabla decArreglo(String ambito, NodoHK nodo)
    {
        NodoHK inita;
        NodoTabla reg1;
        String id;
        int loc, size;
        Value aux2;
        Value arreglo=null;
        dimensiones=0;
        if(nodo.hijos.get(1).valor.equals("LLAMADA_MET") || nodo.hijos.get(1).valor.equals("identificador") || 
                nodo.hijos.get(1).valor.equals("REVERS") || nodo.hijos.get(1).valor.equals("IMPR") ||
                nodo.hijos.get(1).valor.equals("PAR") || nodo.hijos.get(1).valor.equals("ASC") ||
                nodo.hijos.get(1).valor.equals("INDICE") ||
                nodo.hijos.get(1).valor.equals("DESC")|| nodo.hijos.get(1).valor.equals("CONCAT"))
        {
            id=nodo.hijos.get(0).valor;
            reg1=this.existeVariable(ambito, nodo.hijos.get(0));
            if(reg1==null)
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "La variable "+nodo.hijos.get(0).valor+" no ha sido declarada");
                return null;
            }
            if(!reg1.getRol().equals("arr"))
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "La variable "+nodo.hijos.get(0).valor+" debe ser un arreglo");
                return null;
            }
            aux2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(aux2==null)
            {
                err.nuevoError("Expresion Invalida");
                return null;
            }
            if(aux2.isIsArreglo())
            {
                aux2.setImprimirPantalla(aux2.imprimirArreglo());
                reg1.setVal(aux2);
                reg1.setTipo(aux2.getTipo());
            }
        }
        else
        {
            inita=nodo.hijos.get(1);
            id=nodo.hijos.get(0).valor;
            reg1=this.existeVariable(ambito, nodo.hijos.get(0));
            if(reg1==null)
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "La variable "+nodo.hijos.get(0).valor+" no ha sido declarada");
                return null;
            }
            if(!reg1.getRol().equals("arr"))
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "La variable "+nodo.hijos.get(0).valor+" debe ser un arreglo");
                return null;
            }
            if(inita!=null)
            {
                arreglo=this.inita(ambito, inita);
            }
            if(arreglo==null)
            {
                err.nuevoErrorSemantico(nodo.hijos.get(0).linea, nodo.hijos.get(0).columna, "No se pudo asignar el arreglo");
                return null;
            }
            size=arreglo.getElementosArreglo().size();
            reg1.setVal(arreglo);
            reg1.setTipo(arreglo.getTipo());
        }
        return reg1;
    }
    
    Value inita(String ambito, NodoHK nodo)
    {
        Value aux1;
        Value arreglo = new Value();
        int tipo=tipoArreglo(ambito, nodo.hijos.get(0));
        switch (tipo) {
        //LISTA DE LISTAS
            case 0:
                System.out.println("LISTA DE LISTAS");
                for(int i=0; i<dimensiones; i++)
                {
                    List<Value> sublista = llenarDimension(ambito, nodo.hijos.get(0).hijos.get(i));
                    if(sublista==null)
                    {
                        err.nuevoError("No se pudo llenar la lista de dimensiones del arreglo");
                        return null;
                    }
                    Value dim = new Value();
                    dim.setIsArreglo(true);
                    dim.setTotal(total);
                    dim.setElementosArreglo(sublista);
                    dim.setTipo(sublista.get(0).getTipo());
                    arreglo.addElemento(dim);
                    arreglo.setTipo(dim.getTipo());
                    
                }
                arreglo.setIsArreglo(true);
                arreglo.setDimensiones(dimensiones);
                arreglo.setImprimirPantalla(arreglo.imprimirArreglo());
                break;
        //CARACTERES
            case 1:
                System.out.println("LISTA DE CARACTERES");
                arreglo.setDimensiones(1);
                List<Value> sublista = llenarDimension(ambito, nodo.hijos.get(0));
                if(sublista==null)
                {
                    err.nuevoError("No se pudo llenar la lista de dimensiones del arreglo");
                    return null;
                }
                arreglo.setTipo(sublista.get(0).getTipo());
                arreglo.setElementosArreglo(sublista);
                arreglo.setIsArreglo(true);
                arreglo.setTotal(total);
                arreglo.setImprimirPantalla(arreglo.imprimirArreglo());
                break;
        //NUMERICO
            default:
                System.out.println("LISTA DE NUMEROS");
                arreglo.setDimensiones(1);
                List<Value> lista = llenarDimension(ambito, nodo.hijos.get(0));
                if(lista==null)
                {
                    err.nuevoError("No se pudo llenar la lista de dimensiones del arreglo");
                    return null;
                }
                arreglo.setTipo(lista.get(0).getTipo());
                arreglo.setElementosArreglo(lista);
                arreglo.setIsArreglo(true);
                arreglo.setTotal(total);
                arreglo.setImprimirPantalla(arreglo.imprimirArreglo());
                break;
        }
        return arreglo;
    }
    
    List<Value> llenarDimension(String ambito, NodoHK nodo)
    {
        List<Value> sublista = new ArrayList();
        Value aux3;
        String tipo="";
        int contador=0;
        total=0;
        for(NodoHK n : nodo.hijos)
        {
            if(contador==0)
            {
                aux3=this.evaluarExpresion(ambito, n);
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return null;
                }
                total++;
                tipo=aux3.getTipo();
                sublista.add(aux3);
            }
            else
            {
                aux3=this.evaluarExpresion(ambito, n);
                if(aux3==null)
                {
                    err.nuevoError("Expresion Invalida");
                    return null;
                }
                if(!aux3.getTipo().equals(tipo))
                {
                    err.nuevoError("Los tipos de asignacion del arreglo no son iguales");
                    return null;
                }
                total++;
                sublista.add(aux3);
            }
        }
        return sublista;
    }
    
    int tipoArreglo(String ambito, NodoHK nodo)
    {
        boolean bandera=true;
        dimensiones=0;
        int tipo=2;
        for(NodoHK n: nodo.hijos)
        {
            if(n.valor.equals("DIMENSIONES") && bandera==true)
            {
                dimensiones++;
                bandera=true;
            }
            else
            {
                bandera=false;
                break;
            }
        }
        if(bandera)
        {
            tipo=0;                    
            return tipo;
        }
        bandera=true;
        for(NodoHK n: nodo.hijos)
        {
            if(n.valor.equals("caracter") && bandera==true)
            {
                bandera=true;
            }
            else
            {
                bandera=false;
            }
        }
        if(bandera)
        {
            tipo=1;
            return tipo;
        }
        return tipo;
    }
    
    boolean modificarValor(String nombre, Value val)
    {
        Hashtable tablaHash;
        tablaHash = (Hashtable)actual.peek();
        if (tablaHash.containsKey(nombre))
        {
            NodoTabla aux;
            aux = (NodoTabla)tablaHash.get(nombre);
            aux.setVal(val);
            tablaHash.put(nombre, aux);
            return true;
        }
        else
        {
            err.nuevoError("No se pudo modificar el valor de la variable "+nombre);
            return false; 
        }
    }
    
    boolean modificarValorGlobales(String nombre, Value val)
    {
        if (this.hash.containsKey(nombre))
        {
            NodoTabla aux;
            aux = (NodoTabla)this.hash.get(nombre);
            aux.setVal(val);
            aux.setGlobaLocal(0);
            this.hash.put(nombre, aux);
            return true;
        }
        else
        {
            err.nuevoError("No se pudo modificar el valor de la variable global "+nombre);
            return false;
        }
    }
    
    Value concatArreglos(Value aux1, Value aux3)
    {
        Value content=new Value();
        content.setIsArreglo(true);
        
        if(aux1.isIsArreglo() && aux3.isIsArreglo())
        {
            if(aux1.getDimensiones()==aux3.getDimensiones() && aux1.getTipo().equals(aux3.getTipo()))
            {
                content.setIsArreglo(true);
                content.setDimensiones(aux1.getDimensiones());
                content.setTipo(aux1.getTipo());
                if(aux1.getDimensiones()>1)
                {
                    for(int i=0; i<aux1.getDimensiones(); i++)
                    {
                        List<Value> valores=new ArrayList();
                        Value valor = new Value();
                        for(int k=0; k<aux1.getElementosArreglo().get(i).getElementosArreglo().size(); k++)
                        {
                            valores.add(aux1.getElementosArreglo().get(i).getElementosArreglo().get(k));
                        }
                        for(int j=0; j<aux3.getElementosArreglo().get(i).getElementosArreglo().size(); j++)
                        {
                            valores.add(aux3.getElementosArreglo().get(i).getElementosArreglo().get(j));
                        }
                        valor.setElementosArreglo(valores);
                        valor.setTipo(aux1.getTipo());
                        valor.setIsArreglo(true);
                        content.addElemento(valor);
                    }
                    content.setImprimirPantalla(content.imprimirArreglo());
                }
                else
                {
                    
                    for(int i=0; i<aux1.getElementosArreglo().size(); i++)
                    {
                        content.addElemento(aux1.getElementosArreglo().get(i));
                    }
                    for(int i=0; i<aux3.getElementosArreglo().size(); i++)
                    {
                        content.addElemento(aux3.getElementosArreglo().get(i));
                    }
                    content.setImprimirPantalla(content.imprimirArreglo());
                }
            }
        }
        return content;
    }
    
    Hashtable clonarHash(Hashtable<String, NodoTabla> actual) 
    {
        Hashtable<String, NodoTabla> nueva = new Hashtable();
        Enumeration<String> keys = actual.keys();
        while(keys.hasMoreElements())
        {
            String val = keys.nextElement();
            NodoTabla value = (NodoTabla)actual.get(val);
            try {
                value = value.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EjecucionHK.class.getName()).log(Level.SEVERE, null, ex);
            }
            Value otroval = value.getVal();
            try {
                if(otroval==null)
                {
                    value.setVal(null);
                }
                else
                {
                    value.setVal(otroval.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EjecucionHK.class.getName()).log(Level.SEVERE, null, ex);
            }
            nueva.put(val, value);
        }
        return nueva;
    }
}