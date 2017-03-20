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
    public static Map<String, ClaseGK> listaClases = TablaSimbolosGK.getInstance().getHash();
    String ambito_variable;
    List<String> lista_ids;
    List<String> lista_vis;
    List<Integer> linea;
    List<Integer> columna;
    int contParametros = 0;
    ClaseGK clase;
    MetodoGK nuevoMetodo;
    String claseActual;

    public RecorridoAST(NodoGK raiz) {
        this.raiz = raiz;
        lista_ids = new ArrayList();
        lista_vis = new ArrayList();
        linea=new ArrayList();
        columna=new ArrayList();
    }

    public void primeraPasada(String ruta, String name) {
        this.rutaOficial = ruta;
        if (raiz != null) {
            this.listaClase(raiz.hijos.get(2), name);
            this.listaImports(raiz.hijos.get(0), name);
            this.llamadasHK(raiz.hijos.get(1), name);
        }
    }

    private void listaImports(NodoGK n, String nombre) {
        String aux = "";
        String path;
        if (n != null) {
            if (n.hijos.size() > 0) {
                for (NodoGK t : n.hijos) {
                    if (this.rutaOficial != null) {
                        aux = t.valor;
                        path = parsearRuta(this.rutaOficial);
                        path += aux;
                        System.out.println(path);
                        f = new File(path);
                        if (f.exists()) {
                            System.out.println("Se encontro el archivo");
                        } else {
                            JOptionPane.showMessageDialog(null, "El Archivo " + aux + " no se encuentra en la carpeta", "Imports", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Guarde el archivo antes de compilarlo debido a que no se encuentra una ruta para buscar los Imports", "Imports", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }

    private void listaClase(NodoGK n, String nombre) {
        for (NodoGK nodo : n.hijos) {
            clase = new ClaseGK(nodo.hijos.get(0).valor, nodo.hijos.get(1).valor);
            claseActual = nodo.hijos.get(0).valor;
            clase.setNodo(nodo.hijos.get(2));
            pasada1("", nodo.hijos.get(2));
            RecorridoAST.listaClases.put(clase.getId(), clase);
        }
    }

    private void llamadasHK(NodoGK n, String nombre) {
        if (n != null) {
            if (n.hijos.size() > 0) {
                llamadasHK = new ArrayList();
                for (NodoGK t : n.hijos) {
                    llamadasHK.add(t.valor);
                }
            }
        }
    }

    private String parsearRuta(String cadena) {
        String[] parRuta = cadena.split("\\\\");
        String path = "";
        for (int i = 0; i < parRuta.length - 1; i++) {
            path += parRuta[i] + "\\";
        }
        return path;
    }

    void pasada1(String ambito, NodoGK n) {
        SimboloGK nueva_variable;
        String rol = "";
        String nombre = "";
        String nombreReg = "";
        ambito_variable = ambito;
        for (NodoGK nodo : n.hijos) {
            rol = nombre = nombreReg = "";
            if (nodo != null) {
                if (nodo.valor.equals("METODO")) {
                    rol = "met";
                    contParametros = 0;
                    nuevoMetodo = new MetodoGK();
                    ambito_variable = "_" + nodo.hijos.get(0).valor;
                    nuevoMetodo.setId(nodo.hijos.get(0).valor);
                    nuevoMetodo.setLinea(nodo.hijos.get(0).getLinea());
                    nuevoMetodo.setColumna(nodo.hijos.get(0).getColumna());
                    nuevoMetodo.setVisibilidad(nodo.hijos.get(2).valor);
                    nuevoMetodo.setRol(rol);
                    nuevoMetodo.setSentencias(nodo.hijos.get(3));
                    nuevoMetodo.setAmbito(claseActual);
                    if (!nodo.hijos.get(1).valor.equals("sinparametros")) {
                        recorrerParametros(ambito, nodo.hijos.get(1));
                    }
                    this.pasada1(ambito_variable, nodo.hijos.get(3));
                    clase.metodos.put(nodo.hijos.get(0).valor, nuevoMetodo);
                } else if (nodo.valor.equals("FUNCION")) {
                    rol = "func";
                    contParametros = 0;
                    nuevoMetodo = new MetodoGK();
                    ambito_variable = nodo.hijos.get(1).valor;
                    nuevoMetodo.setId(nodo.hijos.get(1).valor);
                    nuevoMetodo.setLinea(nodo.hijos.get(1).getLinea());
                    nuevoMetodo.setColumna(nodo.hijos.get(1).getColumna());
                    nuevoMetodo.setVisibilidad(nodo.hijos.get(3).valor);
                    nuevoMetodo.setRol(rol);
                    nuevoMetodo.setTipo(nodo.hijos.get(0).valor);
                    nuevoMetodo.setSentencias(nodo.hijos.get(4));
                    nuevoMetodo.setAmbito(claseActual);
                    if (!nodo.hijos.get(2).valor.equals("sinparametros")) {
                        recorrerParametros(ambito, nodo.hijos.get(2));
                    }
                    this.pasada1(ambito_variable, nodo.hijos.get(4));
                    clase.metodos.put(nodo.hijos.get(1).valor, nuevoMetodo);
                } else if (nodo.valor.equals("MAIN")) {
                    rol = "main";
                    nuevoMetodo = new MetodoGK();
                    ambito_variable = "_Inicio";
                    nuevoMetodo.setId("Inicio");
                    nuevoMetodo.setLinea(0);
                    nuevoMetodo.setColumna(0);
                    nuevoMetodo.setVisibilidad("publico");
                    nuevoMetodo.setRol(rol);
                    nuevoMetodo.setSentencias(nodo.hijos.get(0));
                    nuevoMetodo.setAmbito(claseActual);
                    this.pasada1(ambito_variable, nodo.hijos.get(0));
                    clase.metodos.put("Inicio", nuevoMetodo);
                } else if (nodo.valor.equals("DECLARA_ASIG_VAR")) {
                    ambito_variable = ambito;
                    this.hacerDeclaracionAsignacion(nodo);
                } else if (nodo.valor.equals("DECLARA_ASIG_OBJ")) {
                    ambito_variable = ambito;
                    this.hacerDeclaracionObj(nodo);
                } else if (nodo.valor.equals("DECLARA_ARR")) {
                    ambito_variable = ambito;
                    this.hacerDeclaracionArr(nodo);
                } else if (nodo.valor.equals("DECLARA_ASIG_ARR")) {
                    ambito_variable = ambito;
                    this.hacerDeclaracionArr(nodo);
                } else if (nodo.valor.equals("DECLARA_VAR")) {
                    ambito_variable = ambito;
                    this.hacerDeclaracion(nodo);
                }
            }
        }
    }

    void recorrerParametros(String ambito, NodoGK nodo) {
        SimboloGK nueva_variable;
        for (NodoGK n : nodo.hijos) {
            contParametros++;
            if (n.hijos.get(0).valor.equals("entero")) {
                nueva_variable = new SimboloGK("entero", n.hijos.get(1).valor, 0, ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            } else if (n.hijos.get(0).valor.equals("cadena")) {
                nueva_variable = new SimboloGK("cadena", n.hijos.get(1).valor, "", ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            } else if (n.hijos.get(0).valor.equals("caracter")) {
                nueva_variable = new SimboloGK("caracter", n.hijos.get(1).valor, (char) 32, ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            } else if (n.hijos.get(0).valor.equals("decimal")) {
                nueva_variable = new SimboloGK("decimal", n.hijos.get(1).valor, 0.00, ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            } else if (n.hijos.get(0).valor.equals("bool")) {
                nueva_variable = new SimboloGK("bool", n.hijos.get(1).valor, false, ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            } else {
                nueva_variable = new SimboloGK(n.hijos.get(0).valor, n.hijos.get(1).valor, new ClaseGK(), ambito_variable, 0);
                nueva_variable.setOrden(contParametros);
                nueva_variable.setLinea(n.hijos.get(1).getLinea());
                nueva_variable.setColumna(n.hijos.get(1).getColumna());
                nueva_variable.setRol("param");
                nuevoMetodo.parametros.put(nueva_variable.getId() + ambito_variable, nueva_variable);
            }
        }
    }

    private void hacerDeclaracion(NodoGK raiz) {
        if (raiz != null) {
            lista_ids.clear();
            lista_vis.clear();
            linea.clear();
            columna.clear();
            sacarIds(lista_ids, lista_vis, raiz.hijos.get(1), linea, columna);
            SimboloGK nueva_variable;
            int contador = 0;
            for (String s : lista_ids) {
                NodoGK parametro;

                switch (raiz.hijos.get(0).valor) {
                    case "entero":
                        nueva_variable = new SimboloGK("entero", s, 0, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        nueva_variable.setRol("var");
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                    case "cadena":
                        nueva_variable = new SimboloGK("cadena", s, "", ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                    case "caracter":
                        nueva_variable = new SimboloGK("caracter", s, (char) 32, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                    case "bool":
                        nueva_variable = new SimboloGK("bool", s, false, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                    case "decimal":
                        nueva_variable = new SimboloGK("decimal", s, 0.0, ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("var");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                    default:
                        nueva_variable = new SimboloGK(raiz.hijos.get(0).valor, s, new ClaseGK(), ambito_variable, 0);
                        nueva_variable.setVisibilidad(lista_vis.get(contador));
                        nueva_variable.setRol("obj");
                        nueva_variable.setLinea(linea.get(contador));
                        nueva_variable.setColumna(columna.get(contador));
                        if (!ambito_variable.equals("")) {
                            nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                        } else {
                            nueva_variable.setAmbito("global");
                            clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                        }
                        break;
                }
                contador++;
            }
        }
    }

    private void hacerDeclaracionAsignacion(NodoGK raiz) {
        if (raiz != null) {
            SimboloGK nueva_variable;
            switch (raiz.hijos.get(0).valor) {
                case "entero":
                    nueva_variable = new SimboloGK("entero", raiz.hijos.get(1).valor, 0, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
                case "cadena":
                    nueva_variable = new SimboloGK("cadena", raiz.hijos.get(1).valor, "", ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
                case "caracter":
                    nueva_variable = new SimboloGK("caracter", raiz.hijos.get(1).valor, (char) 32, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
                case "bool":
                    nueva_variable = new SimboloGK("bool", raiz.hijos.get(1).valor, false, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
                case "decimal":
                    nueva_variable = new SimboloGK("decimal", raiz.hijos.get(1).valor, 0.0, ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("var");
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
                default:
                    nueva_variable = new SimboloGK(raiz.hijos.get(0).valor, raiz.hijos.get(1).valor, new ClaseGK(), ambito_variable, 0);
                    nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
                    nueva_variable.setRol("obj");
                    nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
                    nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
                    if (!ambito_variable.equals("")) {
                        nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
                    } else {
                        nueva_variable.setAmbito("global");
                        clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
                    }
                    break;
            }
        }
    }

    private void hacerDeclaracionArr(NodoGK raiz) {
        if (raiz != null) {
            SimboloGK nueva_variable;
            nueva_variable = new SimboloGK();
            nueva_variable.setId(raiz.hijos.get(1).valor);
            nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
            nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
            nueva_variable.setTipoVariable(raiz.hijos.get(0).valor);
            nueva_variable.setRol("arr");
            nueva_variable.setVisibilidad(raiz.hijos.get(3).valor);
            nueva_variable.setAmbito(ambito_variable);
            nueva_variable.setN_dimensiones(raiz.hijos.get(2).hijos.size());
            if (!ambito_variable.equals("")) {
                nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
            } else {
                nueva_variable.setAmbito("global");
                clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
            }
        }
    }

    private void hacerDeclaracionObj(NodoGK raiz) {
        if (raiz != null) {
            SimboloGK nueva_variable;
            nueva_variable = new SimboloGK();
            nueva_variable.setId(raiz.hijos.get(1).valor);
            nueva_variable.setLinea(raiz.hijos.get(1).getLinea());
            nueva_variable.setColumna(raiz.hijos.get(1).getColumna());
            nueva_variable.setTipoVariable(raiz.hijos.get(0).valor);
            nueva_variable.setRol("obj");
            nueva_variable.setAmbito(ambito_variable);
            nueva_variable.setVisibilidad(raiz.hijos.get(2).valor);
            if (!ambito_variable.equals("")) {
                nuevoMetodo.varLocales.put(nueva_variable.getId() + "_" + ambito_variable, nueva_variable);
            } else {
                nueva_variable.setAmbito("global");
                clase.varGlobales.put(nueva_variable.getId() + "_global", nueva_variable);
            }
        }
    }

    private void sacarIds(List<String> ids, List<String> vis, NodoGK raiz, List<Integer> linea, List<Integer> columna) {
        if (raiz != null) {
            for (NodoGK n : raiz.hijos) {
                ids.add(n.hijos.get(0).valor);
                vis.add(n.hijos.get(1).valor);
                linea.add(n.hijos.get(0).getLinea());
                columna.add(n.hijos.get(0).getColumna());
            }
        }
    }
}
