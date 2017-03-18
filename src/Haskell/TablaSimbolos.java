/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;

import java.util.Hashtable;

/**
 *
 * @author Kristhal
 */
public class TablaSimbolos {
    
    public static Hashtable<String,NodoTabla> hash;
    private static TablaSimbolos tablaHK = null;
    
    public TablaSimbolos()
    {
        hash=new Hashtable<String,NodoTabla>();
        
    }
    
    public static TablaSimbolos resetInstance()
    {
        tablaHK = new TablaSimbolos();
        return tablaHK;
    }
    
    public static TablaSimbolos getInstance()
    {
        if(tablaHK == null)
        {
            tablaHK = new TablaSimbolos();
        }
        return tablaHK;
    }
    
    public void declarar(NodoTabla nodo)
    {
        this.hash.put(nodo.getNombre(), nodo);
    }
    
    public boolean existe(String nombre)
    {
        if(this.hash.containsKey(nombre))
        {
            return true;
        }   
        else
        {
            return false;
        }
    }
    
    public NodoTabla getNodo(String nombre)
    {
        if(this.hash.containsKey(nombre))
        {
            NodoTabla aux = this.hash.get(nombre);
            return aux;
        }
        else
        {
            return null;
        }
    }
    
    public Hashtable getHash()
    {
        return this.hash;
    }
    
    public void setHash(Hashtable valor)
    {
        hash=valor;
    }
}
