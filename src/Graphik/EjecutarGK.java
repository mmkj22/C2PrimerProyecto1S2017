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

/**
 *
 * @author Kristhal
 */
public class EjecutarGK {
    NodoGK root;
    Stack ambito = new Stack();
    TablaSimbolosGK tabla;
    public static Map<String, ClaseGK> listaClases; 
    LinkedList<String> ambitos;
    private boolean romper;
    private boolean vieneBreak=true;
    
    public EjecutarGK(NodoGK root)
    {
        this.root=root;
        this.tabla = TablaSimbolosGK.getInstance();
        this.listaClases=this.tabla.getHash();
        this.asignarGlobales();
    }
    
    
    private void asignarGlobales()
    {
        Iterator it = listaClases.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            ClaseGK clase = listaClases.get(key);
            this.asignacionGlobales(clase.getVarGlobales(), clase.getNodo());
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
    
    private Resultado evaluarExpresion(String ambito, NodoGK nodo)
    {
        Resultado temp1, temp2;
        Resultado content= new Resultado();
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
                content = new Resultado("decimal", temp1.valgk/temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valDoble/temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("decimal", temp1.valDoble/temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valChar/temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valgk/temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("decimal", temp1.valDoble/temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
                content = new Resultado("decimal", temp1.valDoble/temp2.valDoble);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                content = new Resultado("decimal", temp1.valgk/temp2.valChar);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valChar/temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valgk/temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                content = new Resultado("decimal", temp1.valgk/temp2.valgk);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                content = new Resultado("decimal", temp1.valgk/temp2.valgk);
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
                
            }
            else if((temp1.tipogk.equalsIgnoreCase("cadena") || temp1.tipogk.equalsIgnoreCase("caracter") )
                    && (temp2.tipogk.equalsIgnoreCase("cadena") || temp2.tipogk.equalsIgnoreCase("caracter")))
            {
                
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("caracter"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("decimal") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("decimal"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
            
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
            
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
}
