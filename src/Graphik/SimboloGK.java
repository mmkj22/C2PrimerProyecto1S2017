/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import Haskell.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristhal
 */
public class SimboloGK implements Cloneable {
    private String id;
    private String tipo_variable;
    private Object valor;
    private String ambito;
    private int numero_parametros;
    private int tipogk;
    private int orden=0;
    public List<SimboloGK> elementosArreglo;
    private int n_dimensiones;
    private List<Integer> tamDimensiones;
    public int linea;
    public int columna;
    private String visibilidad;
    private String rol;
    private int key;
    private boolean isarreglo=false;
    
    public SimboloGK()
    {
        this.id="";
        this.tipo_variable="";
        this.valor=new Object();
        this.tipogk=-1;
        this.numero_parametros=0;
        this.elementosArreglo=new ArrayList();
        this.key=0;
    }
    
    @Override
    public SimboloGK clone() throws CloneNotSupportedException
    {
        return (SimboloGK)super.clone();
    }
    
    public SimboloGK(String tipo_variable, String id, Object valor, String ambito)
    {
        this.id=id;
        this.tipo_variable=tipo_variable;
        this.valor=valor;
        this.ambito=ambito;
        this.elementosArreglo=new ArrayList();
        this.key=0;
    }
    
    public SimboloGK(String tipo_variable, String id, Object valor, String ambito, int tipo)
    {
        this.id=id;
        this.tipo_variable=tipo_variable;
        this.valor=valor;
        this.ambito=ambito;
        this.tipogk=tipo;
        this.elementosArreglo=new ArrayList();
        this.key=0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoVariable() {
        return tipo_variable;
    }

    public void setTipoVariable(String tipo_variable) {
        this.tipo_variable = tipo_variable;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public int getNumeroParametros() {
        return numero_parametros;
    }

    public void setNumeroParametros(int numero_parametros) {
        this.numero_parametros = numero_parametros;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public List<SimboloGK> getElementosArregloGK() {
        return elementosArreglo;
    }

    public void setElementosArregloGK(List<SimboloGK> elementosArreglo) {
        this.elementosArreglo = elementosArreglo;
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

    public int getNumero_parametros() {
        return numero_parametros;
    }

    public void setNumero_parametros(int numero_parametros) {
        this.numero_parametros = numero_parametros;
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

    public int getN_dimensiones() {
        return n_dimensiones;
    }

    public void setN_dimensiones(int n_dimensiones) {
        this.n_dimensiones = n_dimensiones;
    }

    public int getTipogk() {
        return tipogk;
    }

    public void setTipogk(int tipogk) {
        this.tipogk = tipogk;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean getIsArreglo() {
        return isarreglo;
    }

    public void setIsArreglo(boolean isarreglo) {
        this.isarreglo = isarreglo;
    }
    
    
    
}
