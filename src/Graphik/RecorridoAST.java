/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Kristhal
 */
public class RecorridoAST {
    
    NodoGK raiz;
    String rutaOficial;
    File f = null;
    public static List<String> llamadasHK;
    public static Map<String,ClaseGK> listaClases = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    public static Map<String,SimboloGK> tabSimbolos = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    LinkedList<String> ambitos;
    String ambito_variable;
    String aux_ambito;
    List<String> lista_ids;
    int contBloques;
    int contParametros=0;
    private SimboloGK simGeneral;
    public boolean romper;
    ClaseGK clase;
    MetodoGK nuevoMetodo;
    
    public RecorridoAST(NodoGK raiz)
    {
        this.raiz=raiz;
    }
    
    public void primeraPasada(String ruta, String name)
    {
        this.rutaOficial = ruta;
        if(raiz!=null)
        {
            this.listaClase(raiz.hijos.get(2), name);
            this.listaImports(raiz.hijos.get(0), name);
            this.llamadasHK(raiz.hijos.get(1), name);
        }
    }
    
    private void listaImports(NodoGK n, String nombre)
    {
        String aux="";
        String path;
        if(n!=null)
        {
            if(n.hijos.size()>0)
            {
                for(NodoGK t :n.hijos)
                {
                    if(this.rutaOficial!=null)
                    {
                        aux=t.valor;
                        path=parsearRuta(this.rutaOficial);
                        path+=aux;
                        System.out.println(path);
                        f = new File(path);
                        if(f.exists())
                        {
                            System.out.println("Se encontro el archivo");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"El Archivo "+aux+" no se encuentra en la carpeta","Imports",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Guarde el archivo antes de compilarlo debido a que no se encuentra una ruta para buscar los Imports","Imports",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
    
    private void listaClase(NodoGK n, String nombre)
    {
        for(NodoGK nodo : n.hijos)
        {
            clase = new ClaseGK(nodo.hijos.get(0).valor, nodo.hijos.get(1).valor);
            pasada1(nodo.hijos.get(0).valor, nodo.hijos.get(2));
        }
    }
    
    private void llamadasHK(NodoGK n, String nombre)
    {
        if(n!=null)
        {
            if(n.hijos.size()>0)
            {
                llamadasHK= new ArrayList();
                for(NodoGK t : n.hijos)
                {
                    llamadasHK.add(t.valor);
                }
            }
        }
    }
    
    private String parsearRuta(String cadena)
    {
        String[] parRuta = cadena.split("\\\\");
        String path="";
        for(int i =0; i<parRuta.length-1; i++)
        {
            path+=parRuta[i] + "\\";
        }
        return path;
    }
    
    
    void pasada1(String ambito, NodoGK nodo)
    {
        String rol="";
        String nombre="";
        String nombreReg="";
        for(NodoGK n : nodo.hijos)
        {
            rol=nombre=nombreReg="";
            if(n!=null)
            {
                if(nodo.valor.equals("DECLARA_VAR"))
                {
                
                }
                else if(nodo.valor.equals("METODO"))
                {
                    rol="met";
                    contParametros=0;
                    SimboloGK metodo = new SimboloGK();
                    nuevoMetodo = new MetodoGK();
                    ambito_variable = "_"+nodo.hijos.get(0).valor;
                    nuevoMetodo.setId(nodo.hijos.get(0).valor);
                    metodo.setId(nodo.hijos.get(0).valor);
                    nuevoMetodo.setVisibilidad(nodo.hijos.get(2).valor);
                    metodo.setVisibilidad(-1);
                    if(nodo.hijos.get(1).valor!="sinparametros")
                    {
                        recorrerParametros(ambito, nodo.hijos.get(1));
                    }
                    
                    
                    
                }
                else if(nodo.valor.equals("FUNCION"))
                {
                
                }
                else if(nodo.valor.equals("MAIN"))
                {
                
                }
                else if(nodo.valor.equals("DECLARA_ASIG_VAR"))
                {
                
                }
                else if(nodo.valor.equals("DECLARA_ASIG_OBJ"))
                {
                
                }
                else if(nodo.valor.equals("DECLARA_ARR"))
                {
                }
                else if(nodo.valor.equals("DECLARA_ASIG_ARR"))
                {
                }
            }
        }
    }
    
    boolean recorrerParametros(String ambito, NodoGK nodo)
    {
        boolean bandera=true;
        for(NodoGK n : nodo.hijos)
        {
            SimboloGK sim = new SimboloGK();
            String id = n.hijos.get(0).valor;
            String visibilidad = n.hijos.get(1).valor;
        }
        return bandera;
    }
}
