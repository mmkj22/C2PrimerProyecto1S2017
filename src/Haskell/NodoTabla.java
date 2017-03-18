/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;

/**
 *
 * @author Kristhal
 */
public class NodoTabla {
    String nombre;
    String tipo;
    String rol;
    String ambito;
    String dimension;
    Value val;
    NodoHK exp;
    int globaLocal;
    String parametros;
    int retorna;
    NodoHK valRetorno;
    int bandera;
    
    public NodoTabla( String nombre, String tipo, String rol, String ambito, 
                      String dimension, String parametros, int globaLocal, 
                      int retorna, Value val, NodoHK exp, NodoHK valRetorno, int bandera)
    
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.rol = rol;
        this.ambito = ambito;
        this.dimension = dimension;
        this.parametros = parametros;
        this.globaLocal = globaLocal;
        this.retorna = retorna;
        this.val = val;
        this.exp = exp;
        this.valRetorno = valRetorno;  
        this.bandera=bandera;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
    
    public NodoTabla()
    {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Value getVal() {
        return val;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    public NodoHK getExp() {
        return exp;
    }

    public void setExp(NodoHK exp) {
        this.exp = exp;
    }

    public int getGlobaLocal() {
        return globaLocal;
    }

    public void setGlobaLocal(int globaLocal) {
        this.globaLocal = globaLocal;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public int getRetorna() {
        return retorna;
    }

    public void setRetorna(int retorna) {
        this.retorna = retorna;
    }

    public NodoHK getValRetorno() {
        return valRetorno;
    }

    public void setValRetorno(NodoHK valRetorno) {
        this.valRetorno = valRetorno;
    }
    
}
