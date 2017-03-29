/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import Haskell.Value;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristhal
 */
public class Resultado extends Value implements Cloneable{
    protected int id;
    public String op="";
    public int valgk;
    public String valorgk;
    public String tipogk;
    public double valDoble;
    public boolean valBool;
    public char valChar;
    public String id_result="";
    protected Object value;
    public List<Resultado> elementosArreglo = new ArrayList();
    public int totalgk;
    public String lstdimensiones;
    public boolean arreglo = false;
    public ClaseGK valObj;
            
    @Override
    public Resultado clone() throws CloneNotSupportedException
    {
        return (Resultado)super.clone();
    }
    
    public Resultado()
    {
        totalgk=0;
        lstdimensiones="";
    }
    
    public Resultado(int i)
    {
        id=i;
    }
    
    public Resultado(String tipo, char val)
    {
        this.tipogk=tipo;
        this.valChar = val;
        this.valorgk=""+val;
    }
    
    public Resultado(String tipo, int val)
    {
        this.tipogk=tipo;
        this.valgk = val;
        this.valorgk=""+val;
    }
    
    public Resultado(String tipo, boolean val)
    {
        this.tipogk=tipo;
        this.valBool=val;
        this.valorgk =""+val;
    }
    
    public Resultado(String tipo, double val)
    {
        this.tipogk=tipo;
        this.valDoble = val;
        this.valorgk=""+val;
    }
    
    public Resultado(String tipo, String val)
    {
        this.tipogk=tipo;
        this.valorgk = val;
        
    }
    
    public Resultado(String tipo, Object val)
    {
        this.tipogk=tipo;
        this.value = val;
        this.valorgk=val.toString();
    }
    
    public Resultado(String tipo, ClaseGK val)
    {
        this.tipogk=tipo;
        this.valObj=val;
        
    }
    
    public Resultado(String tipo, List<Resultado> lista)
    {
        this.tipogk = tipo;
        this.elementosArreglo=lista;
        this.setIsArreglo(true);
    }
    
    public void setPosition(int mapeo, Resultado val)
    {
        this.elementosArreglo.add(mapeo, val);
    }
    
    public Resultado getPosition(int mapeo)
    {
        return this.elementosArreglo.get(mapeo);
    }
}
