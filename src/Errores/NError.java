/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

import Logica.Constantes;

/**
 *
 * @author Kristhal
 */
public class NError {
    int linea;
    int columna;
    String tipo;
    String descripcion;
    
    public NError(int linea, int columna, int tipo, String descripcion)
    {
        this.linea=linea;
        this.columna=columna;
        this.tipo=Constantes.ERRORES[tipo];
        this.descripcion=descripcion;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
