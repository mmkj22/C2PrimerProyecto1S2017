/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;


import Logica.CargarCSV;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
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
    private boolean bandera = false;
    
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
                this.ambito.push(TablaSimbolosGK.claseCompilar+"-Inicio");
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
        Resultado aux1, aux2;
        for(NodoGK n: nodo.hijos)
        {
            nombre=nombreReg=tipo=val="";
            if(n!=null)
            {
                if(n.valor.equals("DECLARA_ASIG_VAR"))
                {
                    System.out.println("Globales: DECLARA_ASIG_VAR");
                    aux1 = this.evaluarExpresion(claseActual, n.hijos.get(3));
                    tipo=n.hijos.get(0).valor;
                    aux2=this.evaluarAsignacion(tipo, aux1);
                    if(aux2==null)
                    {
                        return;
                    }
                    if(!this.modificarValorGlobales(claseActual, n.hijos.get(1), aux2))
                    {
                        return;
                    }
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
    
    private boolean modificarValorGlobales(String ambito, NodoGK variable, Resultado val)
    {
        SimboloGK aux1=null;
        String[] ubicacion = ambito.split("-");
        ClaseGK clase = EjecutarGK.listaClases.get(ubicacion[0]);
        if(clase==null)
        {
            return false;
        }
        if(clase.varGlobales.containsKey(variable.valor))
        {
            aux1=clase.varGlobales.get(variable.valor);
            aux1.setValor(val);
            return true;
        }
        return false;
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
                double primero = (double) temp1.valgk;
                double segundo = temp2.valChar*1;
                double resultado = primero/segundo;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double primero = temp1.valChar*1;
                double segundo = (double) temp2.valgk;
                double resultado = primero/segundo;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double primero = (double) temp1.valgk;
                double segundo = (double) temp2.valgk;
                double resultado = primero/segundo;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                double primero = (double) temp1.valgk;
                double segundo = (double) temp2.valgk;
                double resultado = primero/segundo;
                content = new Resultado("decimal", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                double primero = (double) temp1.valgk;
                double segundo = (double) temp2.valgk;
                double resultado = primero/segundo;
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
            temp2= this.evaluarExpresion(ambito, nodo.hijos.get(1));
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
                int resultado = (int)Math.pow(temp1.valgk, temp2.valChar);
                content = new Resultado("entero", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("caracter") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                int resultado = (int)Math.pow(temp1.valChar, temp2.valgk);
                content = new Resultado("entero", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("bool") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                int resultado = (int) Math.pow(temp1.valgk, temp2.valgk);
                content = new Resultado("entero", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("bool"))
            {
                int resultado = (int) Math.pow(temp1.valgk, temp2.valgk);
                content = new Resultado("entero", resultado);
                return content;
            }
            else if(temp1.tipogk.equalsIgnoreCase("entero") && temp2.tipogk.equalsIgnoreCase("entero"))
            {
                int resultado = (int) Math.pow(temp1.valgk, temp2.valgk);
                content = new Resultado("entero", resultado);
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
            temp2=this.evaluarAumento(ambito, temp1);
            if(temp2==null)
            {
                return null;
            }
            content=temp2;
            return content;
        }
        else if(nodo.valor.equals("decremento"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            temp2=this.evaluarDecremento(ambito, temp1);
            if(temp2==null)
            {
                return null;
            }
            content=temp2;
            return content;
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
            simGeneral = this.existeVariable(ambito, nodo.hijos.get(0));
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
        else if(nodo.valor.equalsIgnoreCase("AccesoArreglo"))
        {
            
        }
        else if(nodo.valor.equalsIgnoreCase("ARREGLO"))
        {
            
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
        else if(nodo.valor.equalsIgnoreCase("columna"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            Vector algo = (Vector) CargarCSV.modelo.getDataVector().elementAt(1);
            
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
        Resultado result;
        ClaseGK clase;
        String tipo;
        switch(var.getTipoVariable())
        {
            case "entero":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Integer"))
                {
                    content = new Resultado(var.getTipoVariable(), (int)var.getValor());
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), result.valgk);
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            case "decimal":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Double"))
                {
                    content = new Resultado(var.getTipoVariable(), var.getValor());
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), result.valDoble);
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            case "bool":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Boolean"))
                {
                    content = new Resultado(var.getTipoVariable(), (boolean)var.getValor());
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), result.valBool);
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            case "cadena":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("String"))
                {
                    content = new Resultado(var.getTipoVariable(), (String)var.getValor());
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), result.valorgk);
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            case "caracter":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Character"))
                {
                    content = new Resultado(var.getTipoVariable(), (char)var.getValor());
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), result.valChar);
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            default:
                clase = (ClaseGK)var.getValor();
                content = new Resultado(var.getTipoVariable(), var.getValor());
                content.value="esId";
                content.id_result=var.getId();
                return content;
        }
    }
    
    private SimboloGK existeVariable(String ambito, NodoGK nodo)
    {
        SimboloGK aux1=null;
        String[] ubicacion = ambito.split("-");
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
        if(metodo.varLocales.containsKey(nodo.valor))
        {
            registro=metodo.varLocales.get(nodo.valor);
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
        if(clase.varGlobales.containsKey(nodo.valor))
        {
            registro=clase.varGlobales.get(nodo.valor);
            if(registro!=null)
            {
                return registro;
            }
        }
        return null;
    }
    
    private void recorrido(String ambito, NodoGK nodo)
    {
        boolean bien; 
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
                        if(x.valor.equalsIgnoreCase("DECLARA_VAR"))
                        {
                            if(bandera)
                            {
                            
                            }
                            else
                            {
                            
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ARR"))
                        {
                            if(bandera)
                            {
                            
                            }
                            else
                            {
                            
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_VAR"))
                        {
                            System.out.println("EJ: Entro a Declaracion y Asignacion de variable");
                            bien = this.DeclaraAsignaVariable(ambito, x);
                            if(!bien)
                            {
                                return;
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_OBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_ARR"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("ASIGNACION"))
                        {
                            System.out.println("EJ: Entro a Asignacion");
                            bien=this.AsignaVariable(ambito, x);
                            if(!bien)
                            {
                                return;
                            }
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
                            System.out.println("EJ: Entro a Selecciona");
                            this.hacerSwitch(ambito, x);
                        }
                        else if(x.valor.equalsIgnoreCase("PARA"))
                        {
                            System.out.println("EJ: Entro a Para");
                            this.hacerPara(ambito, x);
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
                            this.hacerLlamada(x, ambito);
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
                            System.out.println("EJ: Entro a Procesar");
                            if(x.hijos.get(1).valor.equalsIgnoreCase("DONDE"))
                            {
                                //PRIMERO VOY A TRAER LA FILA DEL FILTRO
                                System.out.println("EJ: Entro a Donde");
                                
                            }
                            else if(x.hijos.get(1).valor.equalsIgnoreCase("DONDECADA"))
                            {
                                System.out.println("EJ: Entro a DondeCada");
                            }
                            else if(x.hijos.get(1).valor.equalsIgnoreCase("DONDETODO"))
                            {
                                System.out.println("EJ: Entro a DondeTodo");
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMADA_HK_DATOS"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMADA_MET_DATOS"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("aumento"))
                        {
                            System.out.println("EJ: Entro a aumento");
                            aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                            if(aux3==null)
                            {
                                return; 
                            }
                            aux2=this.evaluarAumento(ambito, aux3);
                            if(aux2==null)
                            {
                                return;
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("decremento"))
                        {
                            System.out.println("EJ: Entro a decremento");
                            aux3=this.evaluarExpresion(ambito, x.hijos.get(0));
                            if(aux3==null)
                            {
                                return; 
                            }
                            aux2=this.evaluarDecremento(ambito, aux3);
                            if(aux2==null)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    
    private Resultado evaluarAumento(String ambito, Resultado valor)
    {
        SimboloGK simGeneral;
        Resultado content, variable;
        if(valor.value==null)
        {
            switch(valor.tipogk)
            {
                case "entero":
                    content=new Resultado("entero", valor.valgk+1);
                    return content;
                case "decimal":
                    content=new Resultado("decimal", valor.valDoble+1);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val++;
                    content= new Resultado("caracter", (char)val);
                    return content;

                default:
                    //Error
                    break;
            }
        }
        else if(valor.value.toString().equalsIgnoreCase("esId") && !valor.id_result.isEmpty())
        {
            NodoGK nodo = new NodoGK(valor.id_result);
            simGeneral = this.existeVariable(ambito, nodo);
            if(simGeneral==null)
            {
                return null;
            }
            variable = this.tipoIdentificador(simGeneral);
            switch(variable.tipogk)
            {
                case "entero":
                    int resint = valor.valgk+1;
                    content=new Resultado("entero", resint);
                    simGeneral.setValor(content);
                    return content;
                case "decimal":
                    double resdouble = valor.valDoble+1;
                    content=new Resultado("decimal", resdouble);
                    simGeneral.setValor(content);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val++;
                    content= new Resultado("caracter", (char)val);
                    simGeneral.setValor(content);
                    return content;

                default:
                    return null;
            }
        }
        else
        {
            switch(valor.tipogk)
            {
                case "entero":
                    content=new Resultado("entero", valor.valgk+1);
                    return content;
                case "decimal":
                    content=new Resultado("decimal", valor.valDoble+1);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val++;
                    content= new Resultado("caracter", (char)val);
                    return content;

                default:
                    //Error
                    break;
            }
        }
        return null;
    }
    
    private Resultado evaluarDecremento(String ambito, Resultado valor)
    {
        SimboloGK simGeneral;
        Resultado content, variable;
        if(valor.value==null)
        {
            switch(valor.tipogk)
            {
                case "entero":
                    content=new Resultado("entero", valor.valgk-1);
                    return content;
                case "decimal":
                    content=new Resultado("decimal", valor.valDoble-1);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val--;
                    content= new Resultado("caracter", (char)val);
                    return content;

                default:
                    //Error
                    break;
            }
        }
        else if(valor.value.toString().equalsIgnoreCase("esId") && !valor.id_result.isEmpty())
        {
            NodoGK nodo = new NodoGK(valor.id_result);
            simGeneral = this.existeVariable(ambito, nodo);
            if(simGeneral==null)
            {
                return null;
            }
            variable = this.tipoIdentificador(simGeneral);
            switch(variable.tipogk)
            {
                case "entero":
                    int resint = valor.valgk-1;
                    content=new Resultado("entero", resint);
                    simGeneral.setValor(content);
                    return content;
                case "decimal":
                    double resdouble = valor.valDoble-1;
                    content=new Resultado("decimal", resdouble);
                    simGeneral.setValor(content);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val--;
                    content= new Resultado("caracter", (char)val);
                    simGeneral.setValor(content);
                    return content;

                default:
                    return null;
            }
        }
        else
        {
            switch(valor.tipogk)
            {
                case "entero":
                    content=new Resultado("entero", valor.valgk-1);
                    return content;
                case "decimal":
                    content=new Resultado("decimal", valor.valDoble-1);
                    return content;
                case "caracter":
                    int val = valor.valChar*1;
                    val--;
                    content= new Resultado("caracter", (char)val);
                    return content;

                default:
                    //Error
                    break;
            }
        }
        return null;
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
        bandera=true;
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
        bandera=true;
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
            bandera=true;
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
            bandera=true;
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
    
    private void hacerSwitch(String ambito, NodoGK nodo)
    {
        Resultado aux1, aux2;
        boolean alguno = false;
        aux1 = this.evaluarExpresion(ambito, nodo.hijos.get(0));
        if(aux1==null)
        {
        
        }
        for(int i=0; i<nodo.hijos.get(1).hijos.size(); i++)
        {
            if(nodo.hijos.get(1).hijos.get(i).valor.equals("CASE")){
                aux2 = this.evaluarExpresion(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(0));
                if(aux2.tipogk.equalsIgnoreCase(aux1.tipogk))
                {
                    if(aux1.tipogk.equalsIgnoreCase("entero"))
                    {
                        if(aux1.valgk==aux2.valgk)
                        {
                            alguno=true;
                            this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                            if (romper)
                            {
                                romper = false;
                                break;
                            }

                        }
                    }
                    else if(aux1.tipogk.equalsIgnoreCase("decimal"))
                    {
                        if(aux1.valDoble==aux2.valDoble)
                        {
                            alguno=true;
                            this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                            if (romper)
                            {
                                romper = false;
                                break;
                            }

                        }
                    }
                    else if(aux1.tipogk.equalsIgnoreCase("caracter"))
                    {
                        if(aux1.valChar==aux2.valChar)
                        {
                            alguno=true;
                            this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                            if (romper)
                            {
                                romper = false;
                                break;
                            }
                            break;
                        }
                    }
                    else if(aux1.tipogk.equalsIgnoreCase("cadena"))
                    {
                        if(aux1.valorgk.equals(aux2.valorgk))
                        {
                            alguno=true;
                            this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                            if (romper)
                            {
                                romper = false;
                                break;
                            }

                        }
                    }
                    else if(aux1.tipogk.equalsIgnoreCase("bool"))
                    {
                        if(aux1.valBool==aux2.valBool)
                        {
                            alguno=true;
                            this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                            if (romper)
                            {
                                romper = false;
                                break;
                            }

                        }
                    }
                }
                else if(alguno==true)
                {
                    this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(1));
                    if(romper)
                    {
                        romper = false;
                        break;
                    }
                }
            }
        }
        if(!alguno)
        {
            for(int i=0; i<nodo.hijos.get(1).hijos.size(); i++)
            {
                if(nodo.hijos.get(1).hijos.get(i).valor.equals("DEFECTO")){
                    this.recorrido(ambito, nodo.hijos.get(1).hijos.get(i).hijos.get(0));
                    if(romper)
                    {
                        romper = false;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void hacerPara(String ambito, NodoGK nodo)
    {
        Resultado aux1, aux2;
        boolean bien;
        bien = this.DeclaraAsignaPara(ambito, nodo.hijos.get(0));
        if(!bien)
        {
            return;
        }
        aux1 = this.evaluarExpresion(ambito, nodo.hijos.get(1));
        if(aux1==null || !aux1.tipogk.equalsIgnoreCase("bool"))
        {
            return;
        }
        while(aux1.valBool)
        {
            this.recorrido(ambito, nodo.hijos.get(3));
            if(romper)
            {
                romper=false;
                if(continuar)
                {
                    continuar=false;
                    bien = operacionPara(ambito, nodo.hijos.get(2));
                    if(!bien)
                    {
                        return;
                    }
                    aux1=this.evaluarExpresion(ambito, nodo.hijos.get(1));
                    if(aux1==null || aux1.tipogk.equalsIgnoreCase("bool"))
                    {
                        return;
                    }
                    continue;
                }
                else
                {
                    break;
                }
            }
            bien = operacionPara(ambito, nodo.hijos.get(2));
            if(!bien)
            {
                return;
            }
            aux1=this.evaluarExpresion(ambito, nodo.hijos.get(1));
            if(aux1==null || !aux1.tipogk.equalsIgnoreCase("bool"))
            {
                return;
            }
        }
    }
    
    private boolean DeclaraAsignaPara(String ambito, NodoGK nodo)
    {
        Resultado aux2, aux3;
        SimboloGK aux1;
        boolean bien = false;
        if(nodo.valor.equalsIgnoreCase("ASIGNACION"))
        {
            //CUANDO SOLO VIENE ASIGNACION
        }
        else
        {
            //CUANDO VIENE DECLARACION Y ASIGNACION SE CREA TEMPORALMENTE
            
        }
        return bien;
    }
    
    private boolean operacionPara(String ambito, NodoGK nodo)
    {
        Resultado aux3;
        boolean bien = false;
        if(nodo.valor.equalsIgnoreCase("aumento") || nodo.valor.equals("decremento"))
        {
            //MANDO A LLAMAR A LA EXPRESION
            aux3 = this.evaluarExpresion(ambito, nodo);
            if(aux3==null)
            {
                return false;
            }
            return true;
        }
        else
        {
            //AQUI VIENE UNA ASIGNACION
            
            return bien;
        }
    }
    
    boolean DeclaraAsignaVariable(String ambito, NodoGK nodo)
    {
        Resultado aux2, aux3, aux4;
        SimboloGK aux1;
        boolean bien=false;
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(3));
        if(aux3==null)
        {
            return false;
        }
        aux1=this.existeVariable(ambito, nodo.hijos.get(1));
        if(aux1==null)
        {
            return false;
        }
        aux4=this.tipoIdentificador(aux1);
        if(aux4==null)
        {
            return false;
        }
        aux2=this.evaluarAsignacion(aux4.tipogk, aux3);
        if(aux2==null)
        {
            return false;
        }
        bien=this.modificarValor(ambito, nodo.hijos.get(1), aux2);
        if(!bien)
        {
            return false;
        }
        return bien;
    }
    
    private boolean AsignaVariable(String ambito, NodoGK nodo)
    {
        Resultado aux2, aux3, aux4;
        SimboloGK aux1;
        boolean bien=false;
        aux1=this.existeVariable(ambito, nodo.hijos.get(0));
        if(aux1==null)
        {
            return false;
        }
        aux3=this.evaluarExpresion(ambito, nodo.hijos.get(1));
        if(aux3==null)
        {
            return false;
        }
        aux4=this.tipoIdentificador(aux1);
        if(aux4==null)
        {
            return false;
        }
        aux2=this.evaluarAsignacion(aux4.tipogk, aux3);
        if(aux2==null)
        {
            return false;
        }
        bien=this.modificarValor(ambito, nodo.hijos.get(0),aux2);
        if(!bien)
        {
            return false;
        }
        return bien;
    }
    
    boolean modificarValor(String ambito, NodoGK nombre, Resultado val)
    {
        SimboloGK variable;
        variable = this.existeVariable(ambito, nombre);
        if(variable==null)
        {
            return false;
        }
        variable.setValor(val);
        return true;
    }
    
    public Resultado hacerLlamada(NodoGK algo, String ambito)
    {
        List<Resultado> parametros = new ArrayList();
        String cadena = sacarParametros(ambito, parametros, algo);
        String idmetodo=algo.hijos.get(0).valor;
        if(!cadena.equals(""))
        {
            idmetodo+=cadena;
        }
        Resultado parametro = null;
        MetodoGK metodo = existeMetodo(ambito, idmetodo);
        if(metodo==null)
        {
            return null;
        }
        if(metodo.parametros.size()==parametros.size())
        {
            Set set = metodo.parametros.entrySet();
            Iterator iterador = set.iterator();
            while(iterador.hasNext())
            {
                Map.Entry<String, SimboloGK> entry = (Map.Entry<String, SimboloGK>)iterador.next();
                SimboloGK s = entry.getValue();
                parametro=parametros.get(s.getOrden()-1);
                if(s.getTipoVariable().equalsIgnoreCase(parametro.tipogk))
                {
                    switch(parametro.tipogk)
                    {
                        case "entero":
                            s.setValor(new Resultado("entero",parametro.valgk));
                            break;
                        case "doble":
                            s.setValor(new Resultado("decimal",parametro.valDoble));
                            break;
                        case "cadena":
                            s.setValor(new Resultado("cadena", parametro.valorgk));
                            break;
                        case "bool":
                            s.setValor(new Resultado("bool",parametro.valBool));
                            break;
                        case "caracter":
                            s.setValor(new Resultado("caracter",parametro.valChar));
                            break;
                        default:
                            s.setValor(new Resultado (parametro.tipogk, parametro.value));
                            break;
                    }
                }
            }
        }
        return null;
    }
    
    
    private MetodoGK existeMetodo(String ambito, String nombre)
    {
        MetodoGK metodo=null;
        String[] ubicacion = ambito.split("-");
        ClaseGK clase = EjecutarGK.listaClases.get(ubicacion[0]);
        if(clase.metodos.containsKey(nombre))
        {
            metodo = clase.metodos.get(nombre);
            return metodo;
        }
        return metodo;
    }
    
    public String sacarParametros(String ambito, List<Resultado> list, NodoGK nodo)
    {
        String tipos="";
        if(nodo!=null)
        {
            Resultado aux3;
            for(int i=0; i<nodo.hijos.get(1).hijos.size(); i++)
            {
                aux3=this.evaluarExpresion(ambito, nodo.hijos.get(1).hijos.get(i));
                if(aux3==null)
                {
                    return null;
                }
                tipos+="_"+aux3.tipogk;
                list.add(aux3);
            }
        }
        return tipos;
    }
}
