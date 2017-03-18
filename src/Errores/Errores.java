/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

import java.util.ArrayList;
import java.util.List;
import Logica.*;
/**
 *
 * @author Kristhal
 */
public class Errores {
    private static List<NError> listaErrores = null;
    private static Errores errores = null;
    
    public static Errores resetInstance()
    {
        errores = new Errores();
        return errores;
    }
    
    public static Errores getInstance()
    {
        if(errores == null)
        {
            errores = new Errores();
        }
        return errores;
    }
    
    private Errores()
    {
        listaErrores = new ArrayList<>();
    }
    
    public List<NError> getReporteErrores()
    {
        return listaErrores;
    }
    
    public void nuevoErrorLexico(int linea, int columna, String descripcion)
    {
        NError err = new NError(linea, columna, Constantes.ERR_LEXICO, descripcion);
        listaErrores.add(err);
    }
    
    public void nuevoErrorSintactico(int linea, int columna, String descripcion)
    {
        NError err = new NError(linea, columna, Constantes.ERR_SINTACTICO, descripcion);
        listaErrores.add(err);
    }
    
    public void nuevoErrorSemantico(int linea, int columna, String descripcion)
    {
        NError err = new NError(linea, columna, Constantes.ERR_SEMANTICO, descripcion);
        listaErrores.add(err);
    }
    
    public void nuevoError(String descripcion)
    {
        NError err = new NError(-1, -1, Constantes.ERR_GENERAL, descripcion);
        listaErrores.add(err);
    }
    
    public int cuentaErrores()
    {
        return listaErrores.size();
    }
}
