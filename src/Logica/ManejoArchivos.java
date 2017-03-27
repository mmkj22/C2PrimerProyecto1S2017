/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kristhal
 */
public class ManejoArchivos {
    public java.io.File fichero;
    private java.io.FileWriter escribir;
    private java.io.PrintWriter pintar;
    private java.io.FileReader leerFichero;
    private java.io.BufferedReader leer;
    private String ruta;
    
    public boolean Guardar(String path, String texto){
        try {            
            fichero = new java.io.File(path);
            escribir = new java.io.FileWriter(fichero, false);
            pintar = new java.io.PrintWriter(escribir);          
            pintar.write(texto);
            ruta=fichero.getAbsolutePath();
            return true;            
        } catch (IOException ex) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
           if (escribir != null){               
                try {
                    escribir.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        }
        return false;
    }

    public boolean Guardar(String texto){
        try {
            pintar = new java.io.PrintWriter(fichero);          
            pintar.write(texto);
            return true;            
        } catch (IOException ex) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
           if (pintar != null){      
                pintar.close();
           }
        }
        return false;
    }
    
    public String Open(){
        try {
            String archivo = "", linea;
            leerFichero = new java.io.FileReader(fichero);
            leer = new java.io.BufferedReader(leerFichero);
            ruta=fichero.getAbsolutePath();
            linea = leer.readLine();
            while (linea != null){
                archivo += linea;
                archivo += System.getProperty("line.separator");
                linea = leer.readLine();
            }
            
            return archivo;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(leerFichero != null){
                try {
                    leerFichero.close();
                } catch (IOException ex) {
                    Logger.getLogger(ManejoArchivos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    public String getRuta()
    {
        return ruta;
    }
}
