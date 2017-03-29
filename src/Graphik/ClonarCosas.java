/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristhal
 */
public class ClonarCosas {
    
    public ClonarCosas()
    {
    }
    
    public ClaseGK clonarClase(ClaseGK clase)
    {
        ClaseGK retorno = clase;
        //CLONAR EL MAP DE VARIABLES GLOBALES--------------------------------------------------------------------------------------------------------------
        Map<String, SimboloGK> varGlobales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Iterator it = clase.varGlobales.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            System.out.println("Esto trae la key: "+key);
            try {
                SimboloGK simGeneral = clase.varGlobales.get(key).clone();
                Resultado res = reiniciarResultado(simGeneral);
                simGeneral.setValor(res);
                varGlobales.put(key, simGeneral);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ClonarCosas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        retorno.setVarGlobales(varGlobales);
        //-------------------------------------------------------------------------------------------------------------------------------------------------
        //CLONAR EL MAP DE METODOS-------------------------------------------------------------------------------------------------------------------------
        Map<String, MetodoGK> metodos = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        it = clase.metodos.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            System.out.println("Esto trae la key: "+key);
            try {
                MetodoGK metGeneral = clase.metodos.get(key).clone();
                //CLONAR LOS PARAMETROS DEL METODO-----------------------------------------------------------------------------------------------------------
                Map<String, SimboloGK> parametros = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                parametros = clonarParametros(metGeneral.parametros);
                Map<String, SimboloGK> varLocales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                varLocales = clonarLocales(metGeneral.varLocales);
                metGeneral.parametros=parametros;
                metGeneral.varLocales=varLocales;
                //-------------------------------------------------------------------------------------------------------------------------------------------
                metodos.put(key, metGeneral);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ClonarCosas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        retorno.setMetodos(metodos);
        //---------------------------------------------------------------------------------------------------------------------------------------------------
        return retorno;
    }
    
    public Resultado reiniciarResultado(SimboloGK var)
    {
        Resultado content;
        Resultado result;
        ClaseGK clase;
        String tipo;
        switch(var.getTipoVariable())
        {
            case "entero":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Integer"))
                {
                    content = new Resultado(var.getTipoVariable(), 0);
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), 0);
                    content.arreglo=result.arreglo;
                }
                return content;
            case "decimal":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Double"))
                {
                    content = new Resultado(var.getTipoVariable(), 0.0);
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), 0.0);
                    content.arreglo=result.arreglo;
                }
                return content;
            case "bool":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Boolean"))
                {
                    content = new Resultado(var.getTipoVariable(), false);
                    
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), false);
                    content.arreglo=result.arreglo;
                }
                return content;
            case "cadena":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("String"))
                {
                    content = new Resultado(var.getTipoVariable(), "");
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), "");
                    content.arreglo=result.arreglo;
                }
                return content;
            case "caracter":
                tipo = var.getValor().getClass().getSimpleName();
                if(tipo.equalsIgnoreCase("Character"))
                {
                    content = new Resultado(var.getTipoVariable(), (char)32);
                }
                else
                {
                    result = (Resultado)var.getValor();
                    content = new Resultado(var.getTipoVariable(), (char)32);
                    content.arreglo=result.arreglo;
                }
                return content;
            default:
                clase = (ClaseGK)var.getValor();
                content = new Resultado(var.getTipoVariable(), new ClaseGK());
                content.value="esId";
                content.id_result=var.getId();
                return content;
        }
    }
    
    public Map<String, SimboloGK> clonarParametros(Map<String, SimboloGK> lstparametros)
    {
        Map<String, SimboloGK> parametros = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Iterator it = lstparametros.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            System.out.println("Esto trae la key: "+key);
            try {
                SimboloGK simGeneral = lstparametros.get(key).clone();
                Resultado res = reiniciarResultado(simGeneral);
                simGeneral.setValor(res);
                parametros.put(key, simGeneral);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ClonarCosas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parametros;
    }
    
    public Map<String, SimboloGK> clonarLocales(Map<String, SimboloGK> locales)
    {
        Map<String, SimboloGK> varLocales = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        Iterator it = locales.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            System.out.println("Esto trae la key: "+key);
            try {
                SimboloGK simGeneral = locales.get(key).clone();
                Resultado res = reiniciarResultado(simGeneral);
                simGeneral.setValor(res);
                varLocales.put(key, simGeneral);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ClonarCosas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return varLocales;
    }
}
