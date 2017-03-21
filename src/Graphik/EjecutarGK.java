/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author Kristhal
 */
public class EjecutarGK {
    NodoGK root;
    Stack ambito = new Stack();
    String claseActual;
    TablaSimbolosGK tabla;
    public static Map<String, ClaseGK> listaClases; 
    LinkedList<String> ambitos;
    private boolean romper;
    private boolean retorna;
    private boolean vieneBreak=true;
    private boolean continuar = false;
    JTextArea txtResultados;
    
    public EjecutarGK(NodoGK root, JTextArea txtResultados)
    {
        this.root=root;
        this.tabla = TablaSimbolosGK.getInstance();
        EjecutarGK.listaClases=this.tabla.getHash();
        this.txtResultados=txtResultados;
        this.asignarGlobales();
        this.buscarPrincipal();
    }
    
    
    private void asignarGlobales()
    {
        Iterator it = listaClases.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            ClaseGK clase = listaClases.get(key);
            claseActual = clase.getId();
            this.asignacionGlobales(clase.getVarGlobales(), clase.getNodo());
        }
    }
    
    private void buscarPrincipal()
    {
        if(EjecutarGK.listaClases.containsKey(TablaSimbolosGK.claseCompilar))
        {
            ClaseGK clase = listaClases.get(TablaSimbolosGK.claseCompilar);
            if(clase.metodos.containsKey("Inicio"))
            {
                MetodoGK principal = clase.metodos.get("Inicio");
                this.ambito.push(TablaSimbolosGK.claseCompilar+"_Inicio");
                System.out.println("Se encontro Principal");
                this.recorrido((String)ambito.peek(), principal.getSentencias());
            }
            else
            {
                //ERROR NO EXISTE PRINCIPAL EN LA CLASE QUE SE COMPILO
            }
        }
        else
        {
            //ERROR NO SE ENCUENTRA LA CLASE QUE SE COMPILO
        }
    }
    
    private void asignacionGlobales(Map<String, SimboloGK> globales, NodoGK nodo)
    {
        String nombre, nombreReg, tipo, val;
        
        for(NodoGK n: nodo.hijos)
        {
            nombre=nombreReg=tipo=val="";
            if(n!=null)
            {
                if(n.valor.equals("DECLARA_ASIG_VAR"))
                {
                    System.out.println("Globales: DECLARA_ASIG_VAR");
                    Resultado res = this.evaluarExpresion(claseActual, n.hijos.get(3));
                    System.out.println(res.tipogk);
                }
                else if(n.valor.equals("DECLARA_ASIG_ARR"))
                {
                    System.out.println("Globales: DECLARA_ASIG_ARR");
                }
                else if(n.valor.equals("DECLARA_ASIG_OBJ"))
                {
                    System.out.println("Globales: DECLARA_ASIG_OBJ");
                }
            }
        }
    }
    
    private Resultado evaluarAsignacion(String tipo, Resultado valor)
    {
        Resultado content;
        if(tipo.equalsIgnoreCase("cadena") && valor.tipogk.equalsIgnoreCase("cadena"))
        {
            content = new Resultado("cadena", valor.valorgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("cadena") && valor.tipogk.equalsIgnoreCase("caracter"))
        {
            content = new Resultado("cadena", valor.valorgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("cadena") && valor.tipogk.equalsIgnoreCase("entero"))
        {
            content = new Resultado("cadena", valor.valorgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("cadena") && valor.tipogk.equalsIgnoreCase("decimal"))
        {
            content = new Resultado("cadena", valor.valorgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("cadena") && valor.tipogk.equalsIgnoreCase("bool"))
        {
            content = new Resultado("cadena", String.valueOf(valor.valgk));
            return content;
        }
        else if(tipo.equalsIgnoreCase("caracter") && valor.tipogk.equalsIgnoreCase("caracter"))
        {
            content = new Resultado("caracter", valor.valChar);
            return content;
        }
        else if(tipo.equalsIgnoreCase("caracter") && valor.tipogk.equalsIgnoreCase("entero"))
        {
            char val = (char)valor.valgk;
            content = new Resultado("caracter", val);
            return content;
        }
        else if(tipo.equalsIgnoreCase("decimal") && valor.tipogk.equalsIgnoreCase("decimal"))
        {
            content = new Resultado("decimal", valor.valDoble);
            return content;
        }
        else if(tipo.equalsIgnoreCase("decimal") && valor.tipogk.equalsIgnoreCase("entero"))
        {
            double val = valor.valgk;
            content = new Resultado("decimal", val);
            return content;
        }
        else if(tipo.equalsIgnoreCase("decimal") && valor.tipogk.equalsIgnoreCase("caracter"))
        {
            double val = valor.valChar*1;
            content = new Resultado("decimal", val);
            return content;
        }
        else if(tipo.equalsIgnoreCase("entero") && valor.tipogk.equalsIgnoreCase("entero"))
        {
            content = new Resultado("entero", valor.valgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("entero") && valor.tipogk.equalsIgnoreCase("decimal"))
        {
            int val = (int)valor.valDoble;
            content = new Resultado("entero", val);
            return content;
        }
        else if(tipo.equalsIgnoreCase("entero") && valor.tipogk.equalsIgnoreCase("caracter"))
        {
            int val = valor.valChar*1;
            content = new Resultado("entero", val);
            return content;
        }
        else if(tipo.equalsIgnoreCase("entero") && valor.tipogk.equalsIgnoreCase("bool"))
        {
            content = new Resultado("entero", valor.valgk);
            return content;
        }
        else if(tipo.equalsIgnoreCase("bool") && valor.tipogk.equalsIgnoreCase("bool"))
        {
            content = new Resultado("bool", valor.valBool);
            return content;
        }
        else
        {
            return null;
        }
    }
    
    private Resultado evaluarExpresion(String ambito, NodoGK nodo)
    {
        Resultado temp1, temp2;
        Resultado content;
        SimboloGK simGeneral;
        MetodoGK metGeneral;
        ClaseGK claseGeneral;
        if(nodo.valor.equals("mas"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            //RETORNA TIPO DECIMAL------------------------------------------------------------------------------
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valgk+temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valDoble+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("decimal", temp1.valDoble+temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valChar+temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valgk+temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("decimal", temp1.valDoble+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valDoble+temp2.valDoble);
                return content;
            }
            //RETORNA TIPO ENTERO----------------------------------------------------------------------------------
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("entero", temp1.valgk+temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valChar+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valgk+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("entero", temp1.valgk+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valgk+temp2.valgk);
                return content;
            }
            //RETORNA TIPO CADENA-----------------------------------------------------------------------------------------
            else if(temp1.tipogk.equalsIgnoreCase("cadena") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("cadena", temp1.valorgk+temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("cadena"))
            {
                content = new Resultado("cadena", temp1.valgk+temp2.valorgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("cadena") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("cadena", temp1.valorgk+temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("cadena"))
            {
                content = new Resultado("cadena", temp1.valDoble+temp2.valorgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("cadena") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("cadena", temp1.valorgk+temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("cadena"))
            {
                content = new Resultado("cadena", temp1.valChar+temp2.valorgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("cadena") && temp2.tipogk.equalsIgnoreCase("cadena"))
            {
                content = new Resultado("cadena", temp1.valorgk+temp2.valorgk);
                return content;
            }
            //RETORNA TIPO BOOL
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool || temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk=1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("menos"))
        {
            if(nodo.hijos.size()==1)
            {
                temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
                if(temp1==null)
                {
                    return null;
                }
                if(temp1.tipogk.equalsIgnoreCase("entero"))
                {
                    content = new Resultado("entero", temp1.valgk*-1);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("decimal"))
                {
                    content = new Resultado("decimal", temp1.valDoble*-1);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("caracter"))
                {
                    content = new Resultado("entero", temp1.valChar*-1);
                    return content;
                }
                else 
                {
                    return null;
                }
            }
            else 
            {
                temp1=this.evaluarExpresion(ambito,nodo.hijos.get(0));
                temp2=this.evaluarExpresion(ambito,nodo.hijos.get(1));
                if(temp1==null || temp2==null)
                {
                    return null;
                }
                //RETORNA TIPO DECIMAL--------------------------------------------------------------------------
                if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
                {
                    content = new Resultado("decimal", temp1.valgk-temp2.valDoble);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
                {
                    content = new Resultado("decimal", temp1.valDoble-temp2.valgk);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
                {
                    content = new Resultado("decimal", temp1.valDoble-temp2.valChar);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
                {
                    content = new Resultado("decimal", temp1.valChar-temp2.valDoble);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
                {
                    content = new Resultado("decimal", temp1.valgk-temp2.valDoble);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
                {
                    content = new Resultado("decimal", temp1.valDoble-temp2.valgk);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
                {
                    content = new Resultado("decimal", temp1.valDoble-temp2.valDoble);
                    return content;
                }
                //RETORNA TIPO ENTERO
                else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
                {
                    content = new Resultado("entero", temp1.valgk-temp2.valChar);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
                {
                    content = new Resultado("entero", temp1.valChar-temp2.valgk);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
                {
                    content = new Resultado("entero", temp1.valgk-temp2.valgk);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
                {
                    content = new Resultado("entero", temp1.valgk-temp2.valgk);
                    return content;
                }
                else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
                {
                    content = new Resultado("entero", temp1.valgk-temp2.valgk);
                    return content;
                }
                else
                {
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
                return null;
            }
            //RETORNA TIPO DECIMAL
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valgk*temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valDoble*temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("decimal", temp1.valDoble*temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valChar*temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valgk*temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("decimal", temp1.valDoble*temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valDoble*temp2.valDoble);
                return content;
            }
            //RETORNA TIPO ENTERO
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("entero", temp1.valgk*temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valChar*temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valgk*temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("entero", temp1.valgk*temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", temp1.valgk*temp2.valgk);
                return content;
            }
            //RETORNA TIPO BOOL
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool && temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk=1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("div"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            //RETORNA TIPO DECIMAL
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                double resultado = temp1.valgk/temp2.valDoble;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double resultado = temp1.valDoble/temp2.valgk;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                double resultado = temp1.valDoble/temp2.valChar;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                double resultado = temp1.valChar/temp2.valDoble;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                double resultado = temp1.valgk/temp2.valDoble;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                double resultado = temp1.valDoble/temp2.valgk;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                double resultado = temp1.valDoble/temp2.valDoble;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                double resultado = temp1.valgk/temp2.valChar;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double resultado = temp1.valChar / temp2.valgk;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double resultado = temp1.valgk/temp2.valgk;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                double resultado = temp1.valgk/temp2.valgk; 
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double resultado = temp1.valgk/temp2.valgk;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("pot"))
        {
            temp1= this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            //RETORNA TIPO DECIMAL
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valgk, temp2.valDoble));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valDoble, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valDoble, temp2.valChar));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valChar, temp2.valDoble));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valgk, temp2.valDoble));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valDoble, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", Math.pow(temp1.valDoble, temp2.valDoble));
                return content;
            }
            //RETORNA TIPO ENTERO
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("entero", Math.pow(temp1.valgk, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", Math.pow(temp1.valChar, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", Math.pow(temp1.valgk, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("entero", Math.pow(temp1.valgk, temp2.valgk));
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("entero", Math.pow(temp1.valgk, temp2.valgk));
                return content;
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("aumento"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            //RETORNA TIPO DECIMAL
            if(temp1.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            //RETORNA TIPO ENTERO
            else if(temp1.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("decremento"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            //RETORNA TIPO DECIMAL
            if(temp1.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            //RETORNA TIPO ENTERO
            else if(temp1.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("igualacion"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble==temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool==temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk==temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar==temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble==temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk==temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)==valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk==temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk==temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk==temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }
        }
        else if(nodo.valor.equals("diferente"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble!=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool!=temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk!=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar!=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble!=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk!=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)!=valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk!=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk!=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk!=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }
        }
        else if(nodo.valor.equals("menor"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble<temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk<temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar<temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble<temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk<temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)<valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk<temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk<temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk<temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }

        }
        else if(nodo.valor.equals("mayor"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble>temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk>temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar>temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble>temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk>temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)>valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk>temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk>temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk>temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }

        }
        else if(nodo.valor.equals("menorI"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble<=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk<=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar<=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble<=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk<=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)<=valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk<=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk<=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk<=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }

        }
        else if(nodo.valor.equals("mayorI"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valDoble>=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk >= temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk>=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valDoble>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valChar>=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valDoble>=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valChar>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk>=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                if(valorCadena(temp1.valorgk)>=valorCadena(temp2.valorgk))
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valChar>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                if(temp1.valgk>=temp2.valChar)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valDoble>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                if(temp1.valgk>=temp2.valDoble)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valgk>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                if(temp1.valgk>=temp2.valgk)
                {
                    content = new Resultado("bool", true);
                    content.valgk = 1;
                    return content;
                }
                else 
                {
                    content = new Resultado("bool", false);
                    content.valgk = 0;
                    return content;
                }
            }
            else 
            {
                return null;
            }

        }
        else if(nodo.valor.equals("or"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool || temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk=1;
                    return content;
                }
                else
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("xor"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if((temp1.valBool && !temp2.valBool) || (temp2.valBool && !temp1.valBool))
                {
                    content = new Resultado("bool", true);
                    content.valgk=1;
                    return content;
                }
                else
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("and"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            temp2=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(temp1==null || temp2==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                if(temp1.valBool && temp2.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk=1;
                    return content;
                }
                else
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("not"))
        {
            temp1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            if(temp1.tipogk.equalsIgnoreCase("bool"))
            {
                if(!temp1.valBool)
                {
                    content = new Resultado("bool", true);
                    content.valgk=0;
                    return content;
                }
                else
                {
                    content = new Resultado("bool", false);
                    content.valgk=0;
                    return content;
                }
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("entero"))
        {
            int valor = Integer.parseInt(nodo.hijos.get(0).valor);
            content = new Resultado("entero", valor);
            return content; 
        }
        else if(nodo.valor.equals("cadena"))
        {
            String valor = nodo.hijos.get(0).valor;
            valor = valor.replace("\"", "");
            content = new Resultado("cadena", valor);
            return content; 
        }
        else if(nodo.valor.equals("identificador"))
        {
            simGeneral = this.existeVariable(ambito, nodo);
            if(simGeneral==null)
            {
                return null;
            }
            content = this.tipoIdentificador(simGeneral);
            if(content==null)
            {
                return null;
            }
            return content;
        }
        else if(nodo.valor.equals("arreglo"))
        {
        
        }
        else if(nodo.valor.equals("decimal"))
        {
            double valor = Double.parseDouble(nodo.hijos.get(0).valor);
            content = new Resultado("decimal", valor);
            return content; 
        }
        else if(nodo.valor.equals("bool"))
        {
            if(nodo.hijos.get(0).valor.equalsIgnoreCase("verdadero"))
            {
                content = new Resultado("bool", true);
                content.valgk=1;
                return content; 
            }
            else if(nodo.hijos.get(0).valor.equalsIgnoreCase("falso"))
            {
                content = new Resultado("bool", false);
                content.valgk=0;
                return content; 
            }
            else
            {
                return null;
            }
        }
        else if(nodo.valor.equals("caracter"))
        {
            String preval = nodo.hijos.get(0).valor.replace("\'", "");
            char valor = preval.charAt(0);
            content = new Resultado("caracter", valor);
            return content; 
        }
        else if(nodo.valor.equals("LLAMAR_MET"))
        {
        
        }
        else if(nodo.valor.equals("LLAMARHK"))
        {
        
        }
        else if(nodo.valor.equals("ACCESOBJ"))
        {
        
        }
        else 
        {
            return null;
        }
        return null;
    }
    
    private int valorCadena(String cadena)
    {
        int valor = 0;
        char c;
        for(int i=0; i<cadena.length(); i++)
        {
            c=cadena.charAt(i);
            valor+=c;
        }
        return valor;
    }
    
    private Resultado tipoIdentificador(SimboloGK var)
    {
        Resultado content;
        switch(var.getTipoVariable())
        {
            case "entero":
                content = new Resultado(var.getTipoVariable(), (int)var.getValor());
                content.value="esId";
                return content;
            case "decimal":
                content = new Resultado(var.getTipoVariable(), (double)var.getValor());
                content.value="esId";
                return content;
            case "bool":
                content = new Resultado(var.getTipoVariable(), (boolean)var.getValor());
                content.value="esId";
                return content;
            case "cadena":
                content = new Resultado(var.getTipoVariable(), (String)var.getValor());
                content.value="esId";
                return content;
            case "caracter":
                content = new Resultado(var.getTipoVariable(), (char)var.getValor());
                content.value="esId";
                return content;
            default:
                content = new Resultado(var.getTipoVariable(), var.getValor());
                content.value="esId";
                return content;
        }
    }
    
    private SimboloGK existeVariable(String ambito, NodoGK nodo)
    {
        SimboloGK aux1=null;
        String[] ubicacion = ambito.split("_");
        ClaseGK clase = EjecutarGK.listaClases.get(ubicacion[0]);
        if(ubicacion.length>1)
        {
            MetodoGK metodo = clase.metodos.get(ubicacion[1]);
            aux1= this.getIdsLocal(metodo,nodo);
        }
        if(aux1==null)
        {
            aux1=this.getIdsGlobal(clase, nodo);
        }
        return aux1;
    }
    
    SimboloGK getIdsLocal(MetodoGK metodo, NodoGK nodo)
    {
        SimboloGK registro;
        if(metodo.varLocales.containsKey(nodo.hijos.get(0).valor))
        {
            registro=metodo.varLocales.get(nodo.hijos.get(0).valor);
            if(registro!=null)
            {
                return registro;
            }
        }
        return null;
    }
    
    SimboloGK getIdsGlobal(ClaseGK clase, NodoGK nodo)
    {
        SimboloGK registro;
        if(clase.varGlobales.containsKey(nodo.hijos.get(0).valor))
        {
            registro=clase.varGlobales.get(nodo.hijos.get(0).valor);
            if(registro!=null)
            {
                return registro;
            }
        }
        return null;
    }
    
    private void recorrido(String ambito, NodoGK nodo)
    {
        Resultado aux2, aux3;
        SimboloGK aux1;
        MetodoGK metodo;
        ClaseGK clase;
        if(nodo!=null){
            for(NodoGK x : nodo.hijos)
            {
                if(x!=null)
                {
                    if(romper==false || retorna==false)
                    {
                        if(x.valor.equalsIgnoreCase("DECLARA_ASIG_VAR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_OBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_ARR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ASIGNACION"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ASIG_OBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ASIG_ACCESOBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ASIGNA_ARR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("SI"))
                        {
                            System.out.println("EJ: Entro Si");
                            this.hacerIf(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("SINO"))
                        {
                            System.out.println("EJ: Entro a Sino");
                            this.hacerIfElse(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("SELECCIONA"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("PARA"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("MIENTRAS"))
                        {
                            System.out.println("EJ: Entro a Mientras");
                            this.hacerMientras(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("HACERMIENTRAS"))
                        {
                            System.out.println("EJ: Entro a Hacer Mientras");
                            this.hacerDo(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("CONTINUAR"))
                        {
                            System.out.println("EJ: Entro a Continuar");
                            romper=true;
                            continuar=true;
                        }
                        else if(x.valor.equalsIgnoreCase("TERMINAR"))
                        {
                            System.out.println("EJ: Entro a Terminar");
                            romper=true;
                        }
                        else if(x.valor.equalsIgnoreCase("RETORNO"))
                        {
                            System.out.println("EJ: Entro a Retorno");
                            if(x.hijos.size()>0)
                            {
                                
                            }
                            else
                            {
                                retorna=true;
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMAR_MET"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMARHK"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ACCESOBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("GRAFICAR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("IMPRIMIR"))
                        {
                            System.out.println("EJ: Entro a Imprimir");
                            imprimirPantalla(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("PROCESAR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMADA_HK_DATOS"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMADA_MET_DATOS"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DONDE"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DONDECADA"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DONDETODO"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("aumento"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("decremento"))
                        {
                        
                        }
                    }
                }
            }
        }
    }
    
    private void imprimirPantalla(String ambito, NodoGK nodo)
    {
        Resultado aux3;
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(0));
        if(aux3==null)
        {
            return;
        }
        imprimirConsola(aux3.valorgk);
    }
    
    private void imprimirConsola(String cadena)
    {
        cadena = cadena.replace("\\n", "\r \n");
        cadena = cadena.replace("\\t", "     ");
        txtResultados.append("-> "+cadena+"\n");
    }
    
    private void hacerIf(String ambito, NodoGK nodo)
    {
        Resultado aux3;
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(0));
        if(aux3==null || !aux3.tipogk.equalsIgnoreCase("bool"))
        {
            //ERROR DE TIPO BOOL
            return;
        }
        if(aux3.tipogk.equalsIgnoreCase("bool") && aux3.valBool)
        {
            this.recorrido(ambito, nodo.hijos.get(1));
        }
    }
    
    private void hacerIfElse(String ambito, NodoGK nodo)
    {
        Resultado aux3;
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(0).hijos.get(0));
        if(aux3==null || !aux3.tipogk.equalsIgnoreCase("bool"))
        {
            return;
        }
        if(aux3.tipogk.equalsIgnoreCase("bool") && aux3.valBool)
        {
            this.recorrido(ambito, nodo.hijos.get(0).hijos.get(1));
        }
        else
        {
            this.recorrido(ambito, nodo.hijos.get(1));
        }
    }
    
    private void hacerMientras(String ambito, NodoGK nodo)
    {
        if(nodo!=null)
        {
            while(this.evaluarExpresion(ambito, nodo.hijos.get(0)).valBool)
            {
                this.recorrido(ambito, nodo.hijos.get(1));
                if(romper)
                {
                    romper=false;
                    if(continuar)
                    {
                        continuar=false;
                        continue;
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
    }
    
    private void hacerDo(String ambito, NodoGK nodo)
    {
        if(nodo!=null)
        {
            do
            {
                this.recorrido(ambito, nodo.hijos.get(1));
                romper=false;
                    if(continuar)
                    {
                        continuar=false;
                        continue;
                    }
                    else
                    {
                        break;
                    }
            }while(this.evaluarExpresion(ambito, nodo.hijos.get(0)).valBool);
        }
    }
}
