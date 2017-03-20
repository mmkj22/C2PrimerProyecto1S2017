/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Kristhal
 */
public class MetodoGK {
    private NodoGK sentencias;
    private NodoGK retorno;
    public Map<String, SimboloGK> parametros;
    public Map<String, SimboloGK> varLocales;
    private String id;
    private String visibilidad;
    private String rol;
    private String ambito;
    private String tipo;
    private int linea;
    private int columna;

    public MetodoGK()
    {
        sentencias=null;
        retorno=null;
        parametros = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varLocales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        id="";
        rol="";
        visibilidad="";
        tipo="";
        linea=0;
        columna=0;
    }
    
    public MetodoGK(NodoGK sentencias, String id, String visibilidad)
    {
        this.sentencias = sentencias;
        this.id=id;
        parametros = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        varLocales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.visibilidad=visibilidad;
    }
    
        public NodoGK getSentencias() {
        return sentencias;
    }

    public void setSentencias(NodoGK sentencias) {
        this.sentencias = sentencias;
    }

    public NodoGK getRetorno() {
        return retorno;
    }

    public void setRetorno(NodoGK retorno) {
        this.retorno = retorno;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    
    
    public void imprimirParametros()
    {
        Set set = parametros.entrySet();
        Iterator iterador = set.iterator();
        while(iterador.hasNext())
        {
            Map.Entry<String,SimboloGK> entry =(Map.Entry<String,SimboloGK>)iterador.next();
            SimboloGK s = entry.getValue();
            System.out.println("id metodo: "+this.id+" id: "+s.getId()+" ambito "+s.getAmbito()+" valor:"+s.getTipoVariable());
        }
    }
}
