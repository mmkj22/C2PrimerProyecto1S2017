/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Kristhal
 */
public class ClaseGK implements Cloneable{
    
    private String id;
    private String visibilidad;
    public Map<String, MetodoGK> metodos;
    public Map<String, SimboloGK> varGlobales;
    private ClaseGK hereda;
    private List<String> llamadasHK;
    private List<String> imports;
    private NodoGK nodo;

    @Override
    public ClaseGK clone() throws CloneNotSupportedException
    {
        return (ClaseGK)super.clone();
    }
    
    public ClaseGK()
    {
        id="";
        visibilidad="";
        metodos=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varGlobales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        
    }
    
    public ClaseGK(String id, String visibilidad)
    {
        this.id=id;
        this.visibilidad=visibilidad;
        metodos=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varGlobales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public ClaseGK getHereda() {
        return hereda;
    }

    public void setHereda(ClaseGK hereda) {
        this.hereda = hereda;
    }
    
    public void addHerencia(ClaseGK clase)
    {
        this.hereda = clase;
    }

    public List<String> getLlamadasHK() {
        return llamadasHK;
    }

    public void setLlamadasHK(List<String> llamadasHK) {
        this.llamadasHK = llamadasHK;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }
    

    public Map<String, MetodoGK> getMetodos() {
        return metodos;
    }

    public void setMetodos(Map<String, MetodoGK> metodos) {
        this.metodos = metodos;
    }

    public Map<String, SimboloGK> getVarGlobales() {
        return varGlobales;
    }

    public void setVarGlobales(Map<String, SimboloGK> varGlobales) {
        this.varGlobales = varGlobales;
    }

    public NodoGK getNodo() {
        return nodo;
    }

    public void setNodo(NodoGK nodo) {
        this.nodo = nodo;
    }
    
    public void declararMet(MetodoGK nodo)
    {
        this.metodos.put(nodo.getId(), nodo);
    }
    
    public boolean existeMet(String nombre)
    {
        if(this.metodos.containsKey(nombre))
        {
            return true;
        }   
        else
        {
            return false;
        }
    }
    
    public void declararVar(SimboloGK nodo)
    {
        this.varGlobales.put(nodo.getId(), nodo);
    }
    
    public boolean existeVar(String nombre)
    {
        if(this.varGlobales.containsKey(nombre))
        {
            return true;
        }   
        else
        {
            return false;
        }
    }
    
}
