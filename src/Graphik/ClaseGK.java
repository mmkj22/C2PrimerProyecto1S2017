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
public class ClaseGK {
    
    private String id;
    private String visibilidad;
    public Map<String, MetodoGK> metodos;
    public Map<String, SimboloGK> varGlobales;
    private List<ClaseGK> hereda;
    private List<String> llamadasHK;

    public ClaseGK()
    {
        id="";
        visibilidad="";
        metodos=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varGlobales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        hereda=new ArrayList();
    }
    
    public ClaseGK(String id, String visibilidad)
    {
        this.id=id;
        this.visibilidad=visibilidad;
        metodos=new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varGlobales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        hereda=new ArrayList();
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

    public List<ClaseGK> getHereda() {
        return hereda;
    }

    public void setHereda(List<ClaseGK> hereda) {
        this.hereda = hereda;
    }
    
    public void addHerencia(ClaseGK clase)
    {
        this.hereda.add(clase);
    }

    public List<String> getLlamadasHK() {
        return llamadasHK;
    }

    public void setLlamadasHK(List<String> llamadasHK) {
        this.llamadasHK = llamadasHK;
    }
    
}
