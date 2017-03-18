/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Kristhal
 */
public class NodoHK {
    
    public String valor;
    public List<NodoHK> hijos;
    public int relativo;
    public int linea;
    public int columna;
    
    public NodoHK(String valor) {
        this.valor = valor;
        hijos = new ArrayList<NodoHK>();
    }
    
    public NodoHK(String valor, int linea, int columna) {
        this.valor = valor;
        hijos = new ArrayList<NodoHK>();
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
