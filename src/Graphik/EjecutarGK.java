/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;


import GUI.TablaResultados;
import Haskell.Value;
import Logica.CargarCSV;
import Logica.Graficar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.jfree.ui.RefineryUtilities;

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
    Stack bandera = new Stack();
    Stack variables = new Stack();
    Resultado valorRetorno;
    List<String> lista_ids;
    List<String> lista_vis;
    List<Integer> linea;
    List<Integer> columna;
    List<Resultado> arregloInit = new ArrayList();
    Vector vectorActual;
    List<Integer> columnas;
    private static int keySeries = 0;
    
    public EjecutarGK(NodoGK root, JTextArea txtResultados)
    {
        this.root=root;
        this.tabla = TablaSimbolosGK.getInstance();
        EjecutarGK.listaClases=this.tabla.getHash();
        this.txtResultados=txtResultados;
        this.bandera.push(false);
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
                    this.decArray(claseActual, n);
                    this.decAsigArray(claseActual, n);
                }
                else if(n.valor.equals("DECLARA_ASIG_OBJ"))
                {
                    System.out.println("Globales: DECLARA_ASIG_OBJ");
                    this.decAsigObj(claseActual, n);
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
        else if(tipo.equalsIgnoreCase(valor.tipogk))
        {
            content = new Resultado(valor.tipogk, valor.valObj);
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
        else if(nodo.valor.equalsIgnoreCase("ARREGLO"))
        {
            System.out.println("EX: Entro a Arreglo");
            content=this.generarArreglo(ambito, nodo);
            if(content==null)
            {
                return null;
            }
            return content;
        }
        else if(nodo.valor.equals("LLAMAR_MET"))
        {
            System.out.println("EX: Entro a llamar Metodo");
            content = hacerLlamada(nodo, ambito);
            return content;
            
        }
        else if(nodo.valor.equals("LLAMARHK"))
        {
            System.out.println("EX: Entro a Llamar Haskell");
            if(existeHKGuardado(ambito, nodo.hijos.get(0).valor)){
                List<Resultado> lst_parametros = new ArrayList();
                for(NodoGK n : nodo.hijos.get(1).hijos)
                {
                    temp1=this.evaluarExpresion(ambito, n);
                    if(temp1==null)
                    {
                        return null;
                    }
                    lst_parametros.add(temp1);
                }
                LlamadasHK llamadas = new LlamadasHK(txtResultados);
                content = llamadas.generarLlamadaHK(nodo.hijos.get(0), lst_parametros);
                if(content==null)
                {
                    return null;
                }
                return content;
            }
            else
            {
                //ERROR
                return null;
            }
        }
        else if(nodo.valor.equalsIgnoreCase("LLAMADA_HK_DATOS"))
        {
            if(existeHKGuardado(ambito, nodo.hijos.get(0).valor)){
                List<Resultado> lst_parametros = new ArrayList();
                for(NodoGK n : nodo.hijos.get(1).hijos)
                {
                    temp1=this.evaluarExpresion(ambito, n);
                    if(temp1==null)
                    {
                        return null;
                    }
                    lst_parametros.add(temp1);
                }
                LlamadasHK llamadas = new LlamadasHK(txtResultados);
                content = llamadas.generarLlamadaHK(nodo.hijos.get(0), lst_parametros);
                if(content==null)
                {
                    return null;
                }
                return content;
            }
            else
            {
                //ERROR
                return null;
            }
        }
        else if(nodo.valor.equalsIgnoreCase("LLAMADA_MET_DATOS"))
        {
            System.out.println("EX: Entro a llamar Metodo");
            content = hacerLlamada(nodo, ambito);
            return content;
        }
        else if(nodo.valor.equals("ACCESOBJ"))
        {
            
        }
        else if(nodo.valor.equalsIgnoreCase("AccesoArreglo"))
        {
            System.out.println("EX: Entro Acceso a Arreglo");
            content = this.getArray(ambito, nodo);
            if(content==null)
            {
                return null;
            }
            return content;
        }
        else if(nodo.valor.equalsIgnoreCase("columna"))
        {
            temp1=this.evaluarExpresion(ambito, nodo.hijos.get(0));
            if(temp1==null)
            {
                return null;
            }
            if(!temp1.tipogk.equalsIgnoreCase("entero"))
            {
                return null;
            }
            columnas.add(temp1.valgk);
            content = (Resultado)vectorActual.get(temp1.valgk);
            if(content==null)
            {
                return null;
            }
            return content;
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
                    content.elementosArreglo=result.elementosArreglo;
                    content.lstdimensiones=result.lstdimensiones;
                    content.totalgk = result.totalgk;
                    content.arreglo=result.arreglo;
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
                    content.elementosArreglo=result.elementosArreglo;
                    content.lstdimensiones=result.lstdimensiones;
                    content.totalgk = result.totalgk;
                    content.arreglo=result.arreglo;
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
                    content.elementosArreglo=result.elementosArreglo;
                    content.lstdimensiones=result.lstdimensiones;
                    content.totalgk = result.totalgk;
                    content.arreglo=result.arreglo;
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
                    content.elementosArreglo=result.elementosArreglo;
                    content.lstdimensiones=result.lstdimensiones;
                    content.totalgk = result.totalgk;
                    content.arreglo=result.arreglo;
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
                    content.elementosArreglo=result.elementosArreglo;
                    content.lstdimensiones=result.lstdimensiones;
                    content.totalgk = result.totalgk;
                    content.arreglo=result.arreglo;
                }
                content.value="esId";
                content.id_result=var.getId();
                return content;
            default:
                clase = (ClaseGK)var.getValor();
                content = new Resultado(var.getTipoVariable(), clase);
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
        if(aux1==null && clase.getHereda()!=null && ubicacion.length>1)
        {
            aux1=this.buscarVariablesLocalesHereda(ubicacion[1], clase.getHereda(), nodo);
        }
        if(aux1==null && clase.getHereda()!=null)
        {
            aux1=this.buscarVariablesGlobalesHereda(clase.getHereda(), nodo);
        }
        return aux1;
    }
    
    SimboloGK getIdsLocal(MetodoGK metodo, NodoGK nodo)
    {
        SimboloGK registro=null;
        if(metodo.varLocales.containsKey(nodo.valor))
        {
            if(!(boolean)this.bandera.peek())
            {
                if(metodo.varLocales.get(nodo.valor).getKey()!=22)
                {
                    registro=metodo.varLocales.get(nodo.valor);
                    if(registro == null)
                    {
                        return null;
                    }
                    return registro;
                }
                else
                {
                    return null;
                }
            }
            else{
                registro=metodo.varLocales.get(nodo.valor);
                if(registro == null)
                {
                    return null;
                }
                return registro;
            }
        }
        else if (metodo.parametros.containsKey(nodo.valor))
        {
            registro = metodo.parametros.get(nodo.valor);
            if(registro==null)
            {
                return null;
            }
            return registro;
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
                    if(romper==false /*|| retorna==false*/)
                    {
                        if(x.valor.equalsIgnoreCase("DECLARA_VAR"))
                        {
                            if((boolean)bandera.peek())
                            {
                                this.agregarVariable(ambito, x, 0);
                            }
                            else
                            {
                            
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ARR"))
                        {
                            if((boolean)bandera.peek())
                            {
                                this.agregarArreglo(ambito, x);
                            }
                            else
                            {
                                this.decArray(ambito, x);
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_VAR"))
                        {
                            System.out.println("EJ: Entro a Declaracion y Asignacion de variable");
                            if((boolean)bandera.peek())
                            {
                                this.agregarVariable(ambito,x, 1);
                            }
                            else
                            {
                                bien = this.DeclaraAsignaVariable(ambito, x);
                                if(!bien)
                                {
                                    return;
                                }
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_OBJ"))
                        {
                            System.out.println("EJ: Entro a Declaracion y Asignacion de Objeto");
                            if((boolean)bandera.peek())
                            {
                                
                            }
                            else
                            {
                                this.decAsigObj(ambito, x);
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("DECLARA_ASIG_ARR"))
                        {
                            System.out.println("EJ: Entro a Declara Asigna Arreglo");
                            if((boolean)bandera.peek())
                            {
                                
                            }
                            else
                            {
                                this.decArray(ambito, x);
                                this.decAsigArray(ambito, x);
                            }
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
                            System.out.println("Entro a Asignacion Arreglo");
                            this.asigArray(ambito, x);
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
                                aux3 = this.evaluarExpresion(ambito, x.hijos.get(0));
                                if(aux3==null)
                                {
                                    return;
                                }
                                valorRetorno = aux3;
                                return;
                            }
                            else
                            {
                                //retorna=true;
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMAR_MET"))
                        {
                            System.out.println("EJ: Entro a Llamar Metodo");
                            this.hacerLlamada(x, ambito);
                        }
                        else if(x.valor.equalsIgnoreCase("LLAMARHK"))
                        {
                            System.out.println("EJ: Entro a Llamar Haskell");
                            if(existeHKGuardado(ambito, x.hijos.get(0).valor)){
                                List<Resultado> lst_parametros = new ArrayList();
                                for(NodoGK n : x.hijos.get(1).hijos)
                                {
                                    aux3=this.evaluarExpresion(ambito, n);
                                    if(aux3==null)
                                    {
                                        return;
                                    }
                                    lst_parametros.add(aux3);
                                }
                                LlamadasHK llamadas = new LlamadasHK(txtResultados);
                                aux2 = llamadas.generarLlamadaHK(x.hijos.get(0), lst_parametros);
                                if(aux2==null)
                                {
                                    return;
                                }
                                System.out.println("Parece que funciono la Llamada a HK");
                            }
                            else
                            {
                                //ERROR
                                return;
                            }
                        }
                        else if(x.valor.equalsIgnoreCase("ACCESOBJ"))
                        {
                        
                        }
                        else if(x.valor.equalsIgnoreCase("GRAFICAR"))
                        {
                            System.out.println("Entro a Graficar");
                            aux2 = this.evaluarExpresion(ambito, x.hijos.get(0));
                            if(aux2==null)
                            {
                                return;
                            }
                            aux3 = this.evaluarExpresion(ambito, x.hijos.get(1));
                            if(aux3==null)
                            {
                                return;
                            }
                            if(aux2.arreglo && aux3.arreglo)
                            {
                                if(aux2.tipogk.equalsIgnoreCase(aux3.tipogk))
                                {
                                    if(aux2.totalgk==aux3.totalgk)
                                    {
                                        Graficar grafica = Graficar.getInstance();
                                        grafica.addSerie(aux2.elementosArreglo, aux3.elementosArreglo, String.valueOf(keySeries));
                                        grafica.personalizarGrafica();
                                        grafica.mostrarGrafica();
                                        grafica.pack();
                                        RefineryUtilities.centerFrameOnScreen(grafica);// TODO add your handling code here:
                                        grafica.setVisible(true);
                                    }
                                }
                            }
                            keySeries++;
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
                                aux2= this.evaluarExpresion(ambito, x.hijos.get(1).hijos.get(0));
                                aux3= this.evaluarExpresion(ambito, x.hijos.get(1).hijos.get(1));
                                Vector prueba = CargarCSV.getInstance().consultaDonde(aux2, aux3);
                                if(prueba==null)
                                {
                                    return;
                                }
                                vectorActual=prueba;
                                columnas = new ArrayList();
                                aux2 = this.evaluarExpresion(ambito, x.hijos.get(0));
                                if(aux2==null)
                                {
                                    return;
                                }
                                TablaResultados tableres = new TablaResultados();
                                tableres.cargarModeloDonde(x, aux3, aux2, columnas);
                                tableres.setVisible(true);
                            }
                            else if(x.hijos.get(1).valor.equalsIgnoreCase("DONDECADA"))
                            {
                                System.out.println("EJ: Entro a DondeCada");
                                aux3=this.evaluarExpresion(ambito, x.hijos.get(1).hijos.get(0));
                                if(aux3==null)
                                {
                                    return;
                                }
                                if(!aux3.tipogk.equalsIgnoreCase("entero")){ return ;}
                                int columna = aux3.valgk;
                                TablaResultados tablares = new TablaResultados();
                                for(int i=0; i<CargarCSV.modelo.getRowCount(); i++)
                                {
                                    Vector prueba = CargarCSV.getInstance().consultaDondeCada(new Resultado("entero", i));
                                    if(prueba==null)
                                    {
                                        return;
                                    }
                                    aux3=(Resultado)prueba.get(columna);
                                    vectorActual=prueba;
                                    columnas=new ArrayList();
                                    aux2=this.evaluarExpresion(ambito, x.hijos.get(0));
                                    if(aux2==null)
                                    {
                                        return;
                                    }
                                    if(i==0)
                                    {
                                        tablares.cargarModeloDondeCada(x,columnas);
                                    }
                                    tablares.insertarFilas(aux3, aux2);
                                }
                                tablares.setVisible(true);
                            }
                            else if(x.hijos.get(1).valor.equalsIgnoreCase("DONDETODO"))
                            {
                                System.out.println("EJ: Entro a DondeTodo");
                                List<Resultado> valores = new ArrayList();
                                aux3=this.evaluarExpresion(ambito, x.hijos.get(1).hijos.get(0));
                                if(aux3==null)
                                {
                                    return;
                                }
                                if(!aux3.tipogk.equalsIgnoreCase("entero")){ return ;}
                                TablaResultados tablares = new TablaResultados();
                                for(int i=0; i<CargarCSV.modelo.getRowCount(); i++)
                                {
                                    Vector prueba = CargarCSV.getInstance().consultaDondeCada(new Resultado("entero", i));
                                    if(prueba==null)
                                    {
                                        return;
                                    }
                                    vectorActual=prueba;
                                    columnas=new ArrayList();
                                    aux2=this.evaluarExpresion(ambito, x.hijos.get(0));
                                    if(aux2==null)
                                    {
                                        return;
                                    }
                                    valores.add(aux2);
                                }
                                aux3= this.sumarTodo(valores);
                                if(aux3==null){ return; }
                                tablares.cargarModeloDondeTodo(x, columnas, aux3);
                                tablares.setVisible(true);
                            }
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
    
    private Resultado sumarTodo(List<Resultado> valores)
    {
        double resultado=0; 
        int bandera = 0;
        for(Resultado res : valores)
        {
            if(res.tipogk.equalsIgnoreCase("entero"))
            {
                resultado+=res.valgk;
            }
            else if(res.tipogk.equalsIgnoreCase("decimal"))
            {
                resultado+=res.valDoble;
                bandera =1;
            }
            else
            {
                return null;
            }
        }
        if(bandera==0)
        {
            Resultado content = new Resultado("entero",(int)resultado);
            return content;
        }
        else
        {
            Resultado content = new Resultado("decimal",resultado);
            return content;
        }
    }
    
    private boolean existeHKGuardado(String ambito, String nombreHK)
    {
        String [] ubicacion = ambito.split("-");
        if(ubicacion.length>0)
        {
            if(this.listaClases.containsKey(ubicacion[0]))
            {
                ClaseGK clase = this.listaClases.get(ubicacion[0]);
                if(clase.getLlamadasHK().contains(nombreHK))
                {
                    return true;
                }
            }
        }
        return false;
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
        this.bandera.push(true);
        List<String> lista = new ArrayList();
        this.variables.push(lista);
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
        this.borrarVariables(ambito);
        this.bandera.pop();
        this.variables.pop();
    }
    
    private void hacerIfElse(String ambito, NodoGK nodo)
    {
        Resultado aux3;
        this.bandera.push(true);
        List<String> lista = new ArrayList();
        this.variables.push(lista);
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
        this.borrarVariables(ambito);
        this.variables.pop();
        this.bandera.pop();
    }
    
    private void hacerMientras(String ambito, NodoGK nodo)
    {
        if(nodo!=null)
        {
            while(this.evaluarExpresion(ambito, nodo.hijos.get(0)).valBool)
            {
                this.bandera.push(true);
                List<String> lista = new ArrayList();
                this.variables.push(lista);
                this.recorrido(ambito, nodo.hijos.get(1));
                this.borrarVariables(ambito);
                this.variables.pop();
                this.bandera.pop();
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
            this.bandera.push(true);
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
            this.bandera.pop();
        }
    }
    
    private void hacerSwitch(String ambito, NodoGK nodo)
    {
        Resultado aux1, aux2;
        boolean alguno = false;
        this.bandera.push(true);
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
        this.bandera.pop();
    }
    
    private void hacerPara(String ambito, NodoGK nodo)
    {
        Resultado aux1, aux2;
        boolean bien;
        this.bandera.push(true);
        List<String> lista = new ArrayList();
        this.variables.push(lista);
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
        this.borrarVariables(ambito);
        this.variables.pop();
        this.bandera.pop();
    }
    
    private boolean DeclaraAsignaPara(String ambito, NodoGK nodo)
    {
        Resultado aux2, aux3;
        SimboloGK aux1;
        boolean bien = false;
        if(nodo.valor.equalsIgnoreCase("ASIGNACION"))
        {
            //CUANDO SOLO VIENE ASIGNACION
            bien=this.AsignaVariable(ambito, nodo);
            if(!bien)
            {
                return false;
            }
        }
        else
        {
            //CUANDO VIENE DECLARACION Y ASIGNACION SE CREA TEMPORALMENTE
            try{
                this.agregarVariable(ambito, nodo, 1);
                return true;
            }
            catch(Exception ex)
            {
                return false;
            }
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
            bien=this.AsignaVariable(ambito, nodo);
            if(!bien)
            {
                return false;
            }
            return true;
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
        MetodoGK metodo=null;
        List<Resultado> parametros = new ArrayList();
        String cadena = sacarParametros(ambito, parametros, algo);
        String idmetodo=algo.hijos.get(0).valor;
        if(!cadena.equals(""))
        {
            idmetodo+=cadena;
        }
        Resultado parametro = null;
        try {
                metodo = existeMetodo(ambito, idmetodo).clone();
                if(metodo==null)
                {
                    return null;
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EjecutarGK.class.getName()).log(Level.SEVERE, null, ex);
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
                            s.setValor(new Resultado (parametro.tipogk, parametro.valObj));
                            break;
                    }
                }
            }
            this.ambito.push(metodo.getAmbito()+"-"+metodo.getId());
            this.bandera.push(false);
            this.recorrido((String)this.ambito.peek(), metodo.getSentencias());
            this.ambito.pop();
            this.bandera.pop();
            ambito=(String)this.ambito.peek();
            if(valorRetorno==null)
            {
                return null;
            }
            switch(valorRetorno.tipogk)
            {
                case "entero":
                    metodo.setRetorno(new Resultado("entero",valorRetorno.valgk));
                    break;
                case "decimal":
                    metodo.setRetorno(new Resultado("decimal", valorRetorno.valDoble));
                    break;
                case "bool":
                    metodo.setRetorno(new Resultado("bool", valorRetorno.valBool));
                    break;
                case "cadena":
                    metodo.setRetorno(new Resultado("cadena", valorRetorno.valorgk));
                    break;
                case "caracter":
                    metodo.setRetorno(new Resultado("caracter", valorRetorno.valChar));
                    break;
                default:
                    metodo.setRetorno(valorRetorno);
                    break;
            }
            return valorRetorno;
        }
        else
        {
        
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
        else if(clase.getHereda().metodos.containsKey(nombre))
        {
            metodo = clase.getHereda().metodos.get(nombre);
        }
        return metodo;
    }
    
    
    private MetodoGK traerMetodo(String ambito, String nombre)
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
    
    //METODOS PARA DECLARAR VARIABLES EN AMBITOS DE CICLOS---------------------------------------------------------------------------------------------
    private void agregarVariable(String ambito, NodoGK nodo, int opc)
    {
        MetodoGK metodo;
        metodo=this.retornarMetodo(ambito);
        if(metodo==null)
        {
            return;
        }
        if(opc==0)
        {
            //SOLO HACER DECLARACION
            this.hacerDeclaracion(nodo, metodo, ambito);
        }
        else if(opc==1)
        {
            //HACER DECLARACION
            this.hacerDeclaracionAsignacion(nodo, metodo, ambito);
            
        }
    }
    
    private void agregarArreglo(String ambito, NodoGK nodo)
    {
        MetodoGK metodo;
        metodo=this.retornarMetodo(ambito);
        if(metodo==null)
        {
            return;
        }
        this.hacerDeclaracionArr(metodo, nodo, ambito);
    }
    
    private void hacerDeclaracion(NodoGK raiz, MetodoGK metodo, String ambito_variable) {
        if (raiz != null) {
            lista_ids.clear();
            lista_vis.clear();
            linea.clear();
            columna.clear();
            sacarIds(lista_ids, lista_vis, raiz.hijos.get(1), linea, columna);
            SimboloGK nueva_variable;
            int contador = 0;
            for (String s : lista_ids) {
                switch (raiz.hijos.get(0).valor) {
                    case "entero":
                        nueva_variable = new SimboloGK("entero", s, 0, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                        }
                        else
                        {
                        }
                        break;
                    case "cadena":
                        nueva_variable = new SimboloGK("cadena", s, "", ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                        }
                        else
                        {
                        }
                        break;
                    case "caracter":
                        nueva_variable = new SimboloGK("caracter", s, (char) 32, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                        }
                        else
                        {
                        }
                        break;
                    case "bool":
                        nueva_variable = new SimboloGK("bool", s, false, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                                
                        }
                        else
                        {
                        }
                        break;
                    case "decimal":
                        nueva_variable = new SimboloGK("decimal", s, 0.0, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                        }
                        else
                        {
                        }
                        break;
                    default:
                        nueva_variable = new SimboloGK(raiz.hijos.get(0).valor, s, new ClaseGK(), ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("obj");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setKey(22);
                        if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                                List<String> lista = (List<String>)this.variables.peek();
                                lista.add(nueva_variable.getId());
                        }
                        else
                        {
                        }
                        break;
                }
                contador++;
            }
        }
    }
    
    private void hacerDeclaracionAsignacion(NodoGK raiz, MetodoGK metodo, String ambito_variable) {
        if (raiz != null) {
            SimboloGK nueva_variable;
            Resultado aux3;
            switch (raiz.hijos.get(0).valor) {
                case "entero":
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK("entero", raiz.hijos.get(1).valor, aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
                case "cadena":
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK("cadena", raiz.hijos.get(1).valor, aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
                case "caracter":
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK("caracter", raiz.hijos.get(1).valor, aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
                case "bool":
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK("bool", raiz.hijos.get(1).valor, aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
                case "decimal":
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK("decimal", raiz.hijos.get(1).valor,aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
                default:
                    aux3=this.evaluarExpresion(ambito_variable, raiz.hijos.get(3));
                    if(aux3==null)
                    {
                        return;
                    }
                    nueva_variable = new SimboloGK(raiz.hijos.get(0).valor, raiz.hijos.get(1).valor, aux3, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("obj");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    nueva_variable.setKey(22);
                    if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                            metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
                            List<String> lista = (List<String>)this.variables.peek();
                            lista.add(nueva_variable.getId());
                    } 
                    else {
                            //ERROR YA EXISTE
                    }
                    break;
            }
        }
    }
    
    private void sacarIds(List<String> ids, List<String> vis, NodoGK raiz, List<Integer> linea, List<Integer> columna) {
        if (raiz != null) {
            for (NodoGK n : raiz.hijos) {
                ids.add(n.hijos.get(0).valor);
                vis.add(n.hijos.get(1).valor);
                linea.add(n.hijos.get(0).getLinea());
                columna.add(n.hijos.get(0).getColumna());
            }
        }
    }
    
    private MetodoGK retornarMetodo(String ambito)
    {
        MetodoGK metodo=null;
        String[] ubicacion = ambito.split("-");
        ClaseGK clase = EjecutarGK.listaClases.get(ubicacion[0]);
        if(ubicacion.length>1)
        {
            if(clase.metodos.containsKey(ubicacion[1]))
            {
                metodo = clase.metodos.get(ubicacion[1]);
                return metodo;
            }
        }
        return metodo;
    }

    private void borrarVariables(String ambito) {
        MetodoGK metodo;
        metodo=this.retornarMetodo(ambito);
        if(metodo==null)
        {
            return;
        }
        List<String> lista = (List<String>)this.variables.peek();
        for(String var : lista)
        {
            if(metodo.varLocales.containsKey(var))
            {
                if(metodo.varLocales.get(var).getKey()==22)
                {
                    metodo.varLocales.remove(var);
                }
            }
        }
    }

    private SimboloGK buscarVariablesLocalesHereda(String nombre, ClaseGK clase , NodoGK nodo) {
        SimboloGK registro; 
        if(clase.metodos.containsKey(nombre))
        {
            MetodoGK metodo = clase.metodos.get(nombre);
            if(metodo.varLocales.containsKey(nodo.valor))
            {
                if(!(boolean)this.bandera.peek())
                {
                    if(metodo.varLocales.get(nodo.valor).getKey()!=22)
                    {
                        registro=metodo.varLocales.get(nodo.valor);
                        if(registro.getVisibilidad().equalsIgnoreCase("publico")||registro.getVisibilidad().equalsIgnoreCase("protegido"))
                        {
                            return registro;
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                else{
                    registro=metodo.varLocales.get(nodo.valor);
                    
                    if(registro.getVisibilidad().equalsIgnoreCase("publico") || registro.getVisibilidad().equalsIgnoreCase("protegido"))
                    {
                        return registro;
                    }
                    else
                    {
                        return null;
                    }
                }
            }
            else if (metodo.parametros.containsKey(nodo.valor))
            {
                registro = metodo.parametros.get(nodo.valor);
                if(registro.getVisibilidad().equalsIgnoreCase("publico") || registro.getVisibilidad().equalsIgnoreCase("protegido"))
                {
                    return registro;
                }
                else
                {
                    return null;
                }
            }
        }
        return null;
    }
    
    private SimboloGK buscarVariablesGlobalesHereda(ClaseGK clase, NodoGK nodo)
    {
        SimboloGK registro;
        if(clase.varGlobales.containsKey(nodo.valor))
        {
            registro= clase.varGlobales.get(nodo.valor);
            if(registro.getVisibilidad().equalsIgnoreCase("publico") || registro.getVisibilidad().equalsIgnoreCase("protegido"))
            {
                return registro;
            }
        }
        return null;
    }
    
    private void decArray(String ambito, NodoGK nodo)
    {
        SimboloGK variable;
        String[] aux;
        String arreglo = lDimCad(ambito, nodo.hijos.get(2));
        aux = arreglo.split("-");
        int locGlob= Integer.parseInt(aux[1]);
        arreglo = aux[0];
        variable = this.existeVariable(ambito, nodo.hijos.get(1));
        if(variable==null)
        {
            return;
        }
        if(variable.getRol().equals("arr"))
        {
            variable.setTotal(locGlob);
            variable.setLstDimensiones(arreglo);
            Resultado content = new Resultado();
            for(int i= 0; i<locGlob; i++)
            {
                content.elementosArreglo.add(new Resultado());
            }
            content.tipogk=variable.getTipoVariable();
            content.totalgk = variable.getTotal();
            content.lstdimensiones=variable.getLstDimensiones();
            content.setIsArreglo(true);
            content.arreglo=true;
            variable.setValor(content);
        }
    }
    
    private String lDimCad(String ambito, NodoGK nodo)
    {
        String retorno="";
        Resultado aux3;
        int valDim=1;
        int i=1;
        int size=nodo.hijos.size();
        
        for(NodoGK n : nodo.hijos)
        {
            aux3=this.evaluarExpresion(ambito, n);
            if(aux3==null)
            {
                return null;
            }
            if(aux3.tipogk.equals("entero"))
            {
                retorno+=aux3.valorgk;
                if(i<size)
                {
                    retorno+="_";
                }
                valDim*=aux3.valgk;
                i++;
            }
            else
            {
                //ERROR SOLO TIENE QUE SER DIMENSIONES ENTERAS
                return null;
            }
        }
        retorno+="-"+String.valueOf(valDim);
        return retorno;
    }

    private SimboloGK decAsigArray(String ambito, NodoGK nodo) {
        SimboloGK variable=null;
        NodoGK inita;
        int loc, size;
        Resultado aux2;
        List<Resultado> arreglo = new  ArrayList();
        if(!nodo.hijos.get(4).valor.equalsIgnoreCase("ARREGLO")){
            variable = this.existeVariable(ambito, nodo.hijos.get(1));
            if(variable == null)
            {
                return null;
            }
            if(!variable.getIsArreglo())
            {
                return null;
            }
            aux2=this.evaluarExpresion(ambito, nodo.hijos.get(4));
            if(aux2==null)
            {
                return null;
            }
            if(variable.getTipoVariable().equalsIgnoreCase(aux2.tipogk))
            {
                if(variable.getTotal()<=aux2.totalgk)
                {
                    variable.setValor(aux2);
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        else
        {
            inita=nodo.hijos.get(4);
            variable = this.existeVariable(ambito, nodo.hijos.get(1));
            if(variable == null)
            {
                return null;
            }
            if(!variable.getRol().equalsIgnoreCase("arr"))
            {
                return null;
            }
            this.arregloInit.clear();
            if(inita!=null)
            {
                this.inita(ambito, inita);
            }
            size=this.arregloInit.size();
            if(size>0 && size==variable.getTotal())
            {
                for(int z=0; z<size; z++)
                {
                    aux2=this.arregloInit.get(z);
                    if(!aux2.tipogk.equalsIgnoreCase(variable.getTipoVariable()))
                    {
                        break;
                    }
                    arreglo.add(aux2);
                }
            }
            String [] dimensiones = variable.getLstDimensiones().split("_");
            Resultado content = new Resultado(variable.getTipoVariable(), arreglo);
            content.totalgk=variable.getTotal();
            content.lstdimensiones=variable.getLstDimensiones();
            content.setDimensiones(variable.getN_dimensiones());
            content.setIsArreglo(true);
            content.arreglo=true;
            variable.setValor(content);
        }
        return variable;
    }
    
    private void inita(String ambito, NodoGK nodo)
    {
        Resultado aux1;
        for(NodoGK n : nodo.hijos)
        {
            if(n.valor.equals("ARREGLO"))
            {
                this.inita(ambito, n);
            }
            else
            {
                aux1=this.evaluarExpresion(ambito, n);
                if(aux1==null)
                {
                    return;
                }
                this.arregloInit.add(aux1);
            }
        }
    }
    
    private int mapeoLexicografico(List<Integer> i, List<Integer>m)
    {
        int sumatoria = 0;
        int productoria = 1;
        int k=m.size();
        for( int x = 0; x<k; x++)
        {
            productoria = 1;
            for(int y = x + 1; y<k; y++)
            {
                productoria = productoria * (m.get(y)-1+1);
            }
            sumatoria = sumatoria + (i.get(x)-1)*productoria;
        }
        return sumatoria;
    }
    
    private void hacerDeclaracionArr(MetodoGK metodo, NodoGK raiz, String ambito) {
        if (raiz != null) {
            SimboloGK nueva_variable;
            int dim; 
            int total = 1;
            nueva_variable = new SimboloGK();
            nueva_variable.setId(raiz.hijos.get(1).valor);
            nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
            nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
            nueva_variable.setTipoVariable(raiz.hijos.get(0).valor);
            nueva_variable.setRol("arr");
            nueva_variable.setVisibilidad(raiz.hijos.get(3).valor);
            nueva_variable.setAmbito(ambito);
            nueva_variable.setIsArreglo(true);
            nueva_variable.setKey(22);
            nueva_variable.setN_dimensiones(raiz.hijos.get(2).hijos.size());
            if(!metodo.existeVar(nueva_variable.getId()) && !metodo.existePar(nueva_variable.getId())){
                metodo.varLocales.put(nueva_variable.getId(), nueva_variable);
            } else {
                    //ERROR YA EXISTE
            }
            this.decArray(ambito, raiz);
        }
    }
    
    private SimboloGK asigArray(String ambito, NodoGK nodo)
    {
        SimboloGK reg1;
        Resultado aux1;
        int i, j, mapeo;
        String total;
        String[] lNum, lDim;
        List<Integer> coordenadas = new ArrayList();
        List<Integer> dimensiones = new ArrayList();
        reg1=this.existeVariable(ambito, nodo.hijos.get(0));
        if(reg1==null)
        {
            return null;
        }
        if(!reg1.getRol().equalsIgnoreCase("arr"))
        {
            return null;
        }
        total=this.getCadDimArray(ambito, nodo.hijos.get(1));
        if(total.equalsIgnoreCase(""))
        {
            return null;
        }
        lNum=total.split("_");
        lDim=reg1.getLstDimensiones().split("_");
        j=lNum.length;
        i=lDim.length;
        if(i!=j)
        {
            return null;
        }
        for(int k=0; k<i; k++)
        {
            coordenadas.add(Integer.parseInt(lNum[k]));
            dimensiones.add(Integer.parseInt(lDim[k]));
        }
        mapeo=this.mapeoLexicografico(coordenadas, dimensiones);
        if(mapeo>=reg1.getTotal() || mapeo<0)
        {
            return null;
        }
        aux1=this.evaluarExpresion(ambito, nodo.hijos.get(2));
        if(aux1==null)
        {
            return null;
        }
        Resultado content = (Resultado)reg1.getValor();
        content.setPosition(mapeo, aux1);
        reg1.setValor(content);
        return reg1;
    }
    
    private String getCadDimArray(String ambito, NodoGK n)
    {
        Resultado aux1;
        String retorno="";
        int size=0;
        int i=1;
        if(n!=null)
        {
            size=n.hijos.size();
            for(NodoGK nodo: n.hijos)
            {
                aux1=this.evaluarExpresion(ambito, nodo);
                if(aux1==null)
                {
                    return null;
                }
                if(aux1.tipogk.equalsIgnoreCase("entero")){
                    int val = aux1.valgk+1;
                    retorno+=String.valueOf(val);
                    if(i<size)
                    {
                        retorno+="_";
                    }
                    i++;
                }
                else
                {
                    return null;
                }
            }
        }
        return retorno;
    }
    
    private Resultado getArray(String ambito, NodoGK nodo)
    {
        SimboloGK reg1;
        Resultado content;
        int i, j, mapeo;
        String dim;
        String [] lSc, lSm;
        List<Integer> lNi = new ArrayList();
        List<Integer> lNm = new ArrayList();
        reg1=this.existeVariable(ambito, nodo.hijos.get(0));
        if(reg1==null)
        {
            return null;
        }
        dim=this.getCadDimArray(ambito, nodo.hijos.get(1));
        if(dim=="")
        {
            return null;
        }
        lSc=dim.split("_");
        lSm=reg1.getLstDimensiones().split("_");
        i=lSc.length;
        j=lSm.length;
        if(i!=j)
        {
            return null;
        }
        for(int k=0; k<i; k++)
        {
            lNi.add(Integer.parseInt(lSc[k]));
            lNm.add(Integer.parseInt(lSm[k]));
        }
        mapeo = this.mapeoLexicografico(lNi, lNm);
        if(mapeo>=reg1.getTotal() || mapeo<0)
        {
            return null;
        }
        Resultado aux2 = (Resultado) reg1.getValor();
        content= aux2.getPosition(mapeo);
        return content;
    }
    
    private Resultado generarArreglo(String ambito, NodoGK nodo)
    {
        Resultado content;
        String tipo="";
        if(nodo!=null)
        {
            this.arregloInit.clear();
            this.inita(ambito, nodo);
            for(int i=0; i<this.arregloInit.size(); i++)
            {
                if(i==0)
                {
                    tipo=this.arregloInit.get(i).tipogk;
                }
                else
                {
                    if(tipo.equalsIgnoreCase(this.arregloInit.get(i).tipogk))
                    {
                        content = new Resultado(tipo, this.arregloInit);
                        content.totalgk=this.arregloInit.size();
                        return content;
                    }
                }
            }
        }
        return null;
    }
    
    //METODOS PARA TODO LO QUE TIENE QUE VER CON OBJETOS-----------------------------------------------------------------------------------------

    private void decAsigObj(String ambito, NodoGK nodo) {
        SimboloGK variable;
        variable = this.existeVariable(ambito, nodo.hijos.get(1));
        if(variable==null)
        {
            return;
        }
        if(!variable.getTipoVariable().equals(nodo.hijos.get(0).valor))
        {
            return;
        }
        if(nodo.hijos.get(0).valor.equals(nodo.hijos.get(3).valor))
        {
            if(existeImportacion(ambito, nodo.hijos.get(3).valor))
            {
                if(this.listaClases.containsKey(nodo.hijos.get(3).valor))
                {
                    ClaseGK clase = this.listaClases.get(nodo.hijos.get(3).valor);
                    if(clase.getVisibilidad().equalsIgnoreCase("publico") || clase.getVisibilidad().equalsIgnoreCase("protegido"))
                    {
                        try{
                        ClaseGK asignacion;
                        ClonarCosas clonar = new ClonarCosas();
                        asignacion = clonar.clonarClase(clase.clone());
                        if(asignacion==null)
                        {
                            return;
                        }
                        this.asignacionGlobales(asignacion.getVarGlobales(), asignacion.getNodo());
                        variable.setValor(asignacion);
                        }catch(CloneNotSupportedException ex)
                        {
                            Logger.getLogger(EjecutarGK.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }    
    
    private boolean existeImportacion(String ambito, String name)
    {
        ClaseGK clase;
        String[] ubicacion = ambito.split("-");
        if(this.listaClases.containsKey(ubicacion[0]))
        {
            clase=this.listaClases.get(ubicacion[0]);
            if(clase.getImports().contains(name))
            {
                return true;
            }
        }
        return false;
    }
    
}