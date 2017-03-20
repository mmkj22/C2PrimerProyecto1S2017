/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristhal
 */
public class NodoGK {
    public String valor;
    public List<NodoGK> hijos;
    public int relativo;
    public int linea;
    public int columna;
    
    public NodoGK(String valor) {
        this.valor = valor;
        hijos = new ArrayList<NodoGK>();
    }
    
    public NodoGK(String valor, int linea, int columna) {
        this.valor = valor;
        this.linea=linea;
        this.columna=columna;
        hijos = new ArrayList<NodoGK>();
    }
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
       
}
