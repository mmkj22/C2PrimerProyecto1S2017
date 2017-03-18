/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;

import Errores.NError;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Kristhal
 */
public class RecorridoHK {
    NodoHK raiz;
    TablaSimbolos tabla;
    GregorianCalendar gcalendar = new GregorianCalendar();
    
    public RecorridoHK()
    {
    
    }
    
    public RecorridoHK(NodoHK root)
    {
        this.raiz=root;
        this.tabla= TablaSimbolos.getInstance();
    }
    
    public void primeraPasada()
    {
        if(raiz!=null)
        {
            listaMetodos(raiz);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"La raiz del arbol esta vacia","Raiz Arbol",JOptionPane.ERROR);
        }
    }
    
    public void listaMetodos(NodoHK nodo)
    {
        this.pasada1("", nodo);
    }
    
    void addReg( String nombreReg, String tipo, String rol, String ambito, String dimension,
                 String parametros, int globaLocal, int retorna, Value val, NodoHK body, NodoHK valRetorno)
    {
        NodoTabla registro;
        registro = new NodoTabla(nombreReg, tipo, rol, ambito, dimension, parametros, globaLocal, retorna, val, body, valRetorno, 0);
        this.tabla.declarar(registro);
    }
    
    void addProc( String nombreReg, String tipo, String rol, String ambito, String dimension,
                  String parametros, int globaLocal, int retorna, NodoHK n, Value val, NodoHK body, NodoHK valRetorno)
    {
        if(!n.hijos.get(1).valor.equals("sinparametros"))
        {
            dimension=this.listaParametros(nombreReg, n.hijos.get(1),2);
        }
        this.pasada1(nombreReg, body);
        this.addReg(nombreReg, tipo, rol, ambito, dimension, parametros, globaLocal, retorna, val, body, valRetorno);
    }
    
    String listaParametros(String ambito, NodoHK nodo, int tipo)
    {
        int i = 1;
        int j=nodo.hijos.size();
        String retorno = "";
        String nombre;
        for(NodoHK c : nodo.hijos)
        {
            if(tipo==1)
            {
                retorno += c.valor;
                if(i<j)
                {
                    retorno +="_";
                }
            }
            else
            {
                nombre=c.valor;
                if(!this.tabla.existe(ambito+"_"+nombre))
                {
                    this.addReg(ambito+"_"+nombre,"", "param", ambito, "","",0,-1,null,null,null);
                    retorno+=nombre;
                    if(i<j)
                    {
                        retorno+="_";
                    }
                }
                else
                {
                
                }
            }
            i++;
        }
        return retorno;
    }
    
    
    void pasada1(String ambito, NodoHK nodo)
    {
        String nombre;
        String nombreReg;
        String tipo;
        String lDim;
        NodoHK x;
        NodoHK otro;
        int locGlob, ret=0;
        
        for(NodoHK n : nodo.hijos)
        {
            nombre=nombreReg=tipo=lDim="";
            locGlob=-1;
            if(n!=null)
            {
                if(n.valor.equals("DECLARA_ASIGNA_LISTA"))
                {
                    System.out.println("TS: Entro a Declara Lista");
                    nombre=n.hijos.get(0).valor;
                    if(ambito.equals(""))
                    {
                        nombreReg=nombre;
                    }
                    else
                    {
                        nombreReg=ambito+"_"+nombre;
                    }
                    if(n.hijos.get(1).valor.equals("cadena"))
                    {
                        tipo=n.hijos.get(1).valor;
                    }
                    else
                    {
                        tipo="";
                    }
                    String rol="arr";
                    x=n.hijos.get(1);
                    if(!this.tabla.existe(nombreReg))
                    {
                        this.addReg(nombreReg, tipo, rol, ambito, lDim, "", locGlob, -1, null, x, null);
                    }
                    else
                    {
                        //ERROR
                    }
                }
                else if(n.valor.equals("FUNCION"))
                {
                    System.out.println("TS: Entro a funcion");
                    tipo="";
                    ret=1;
                    String rol = "func";
                    nombre=n.hijos.get(0).valor;
                    nombreReg=nombre;
                    if(!n.hijos.get(1).valor.equals("sinparametros"))
                    {
                        lDim=listaParametros(nombreReg, n.hijos.get(1), 1);
                    }
                    x=n.hijos.get(2);
                    if(this.tabla.existe(nombreReg))
                    {
                        borrarVariables(nombreReg);
                        addProc(nombreReg, tipo, rol, ambito, "", lDim, locGlob, 1, n, null, x, null);
                    }
                    else
                    {
                        addProc(nombreReg, tipo, rol, ambito, "", lDim, locGlob, 1, n, null, x, null);
                    }
                }
                else if(n.valor.equals("SINO"))
                {
                    System.out.println("TS: Entro a SINO");
                    this.pasada1(ambito, n.hijos.get(0).hijos.get(1));
                    this.pasada1(ambito, n.hijos.get(1));
                }
                else if(n.valor.equals("NCASE"))
                {
                    System.out.println("TS: Entro a CASE");
                    n.hijos.get(1).hijos.forEach((c) -> {
                        this.pasada1(ambito, c.hijos.get(1));
                    });
                }
            }
        }
    }
    
    //-----------------------------------------IMPRIME TABLA DE SIMBOLOS --------------------------------------------
        private String obtenerDate()
    {
        String months[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String cadena="";
        int year;
        cadena+=gcalendar.get(Calendar.DATE)+" de ";
        cadena+=months[gcalendar.get(Calendar.MONTH)];
        year=gcalendar.get(Calendar.YEAR);
        cadena+=" del "+year;
        return cadena;
    }
    
    private String obtenerTime()
    {
        String cadena="";
        cadena+=gcalendar.get(Calendar.HOUR)+ ":";
        cadena+=gcalendar.get(Calendar.MINUTE)+":";
        cadena+=gcalendar.get(Calendar.SECOND)+" ";
        if(gcalendar.get(Calendar.AM_PM)==0)
        {
            cadena+="AM";
        }
        else
        {
            cadena+="PM";
        }
        return cadena;
    }
    
    public String imprimirTabla()
    {
        Enumeration<String> keys = this.tabla.getHash().keys();
        String path="<html>";
        path += "<head><title>Tabla de Simbolos</title></head>";
        path += "<body><h1>Reporte de Tabla de Simbolos:</h1>";
        path += "<h3>Fecha de Ejecucion: "+obtenerDate()+"</h3>";
        path += "<h3>Hora de Ejecucion: "+obtenerTime()+"</h3>";
        path += "<table border=\"1\" align=\"left\" bordercolor=\"gray\" cellspacing=\"0\">";
        path += "<tr>";
        path += "<td><strong>Nombre</strong></td>";
        path += "<td><strong>Tipo</strong></td>";
        path += "<td><strong>Rol</strong></td>";
        path += "<td><strong>Ambito</strong></td>";
        path += "<td><strong>Dim</strong></td>";
        path += "<td><strong>Parametros</strong></td>";
        path += "<td><strong>Local/Global</strong></td>";
        path += "<td><strong>SeUso</strong></td>";
        path += "<td><strong>Return</strong></td></tr>";
        while(keys.hasMoreElements())
        {
            String val = keys.nextElement();
            NodoTabla value = (NodoTabla)this.tabla.getHash().get(val);
            path += "<tr>";
            path += "<td>" + value.getNombre() + "</td>";
            path += "<td>" + value.getTipo() + "</td>";
            path += "<td>" + value.getRol() + "</td>";
            path += "<td>" + value.getAmbito() + "</td>";
            path += "<td>" + value.getDimension() + "</td>";
            path += "<td>" + value.getParametros() + "</td>";
            path += "<td>" + value.getGlobaLocal() + "</td>";
            path += "<td>" + value.getBandera() + "</td>";
            path += "<td>" + value.getRetorna() + "</td>";
            path += "</tr>";
        }
        path += "</table></body></html>";
        return path;
    }
    
    private void borrarVariables(String ambito)
    {
        Enumeration<String> keys = this.tabla.getHash().keys();
        while(keys.hasMoreElements())
        {
            String val = keys.nextElement();
            NodoTabla value = (NodoTabla)this.tabla.getHash().get(val);
            if(value.getAmbito().equals(ambito) && (value.getRol().equals("param")|| value.getRol().equals("arr")))
            {
                this.tabla.getHash().remove(val);
            }
        }
    }
    
}
