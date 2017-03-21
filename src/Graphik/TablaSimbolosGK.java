/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Kristhal
 */
public class TablaSimbolosGK {
    public static Map<String, ClaseGK> listaClases = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    public static String claseCompilar;
    private static TablaSimbolosGK tablaGK = null;
    
    public TablaSimbolosGK()
    {
        listaClases=new TreeMap(String.CASE_INSENSITIVE_ORDER);
        claseCompilar="";
    }
    
    public static TablaSimbolosGK resetInstance()
    {
        tablaGK = new TablaSimbolosGK();
        return tablaGK;
    }
    
    public static TablaSimbolosGK getInstance()
    {
        if(tablaGK == null)
        {
            tablaGK = new TablaSimbolosGK();
        }
        return tablaGK;
    }
    
    public void declarar(ClaseGK nodo)
    {
        this.listaClases.put(nodo.getId(), nodo);
    }
    
    public boolean existe(String nombre)
    {
        if(this.listaClases.containsKey(nombre))
        {
            return true;
        }   
        else
        {
            return false;
        }
    }
    
    public Map getHash()
    {
        return this.listaClases;
    }
    
    public void setHash(Map valor)
    {
        listaClases=valor;
    }
}
