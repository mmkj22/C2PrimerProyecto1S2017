/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author Kristhal
 */
public class GraphvizJava {
    public static void main( String []arg ){
	new GraphvizJava( arg[0], arg[1] );
    }
    public GraphvizJava( String direccionDot, String direccionPng ){
	dibujar( direccionDot, direccionPng );
    }
    
    public void dibujar( String direccionDot, String direccionPng ){
	try
	{       
            ProcessBuilder pbuilder;
            /*
            * Realiza la construccion del comando    
            * en la linea de comandos esto es: 
            * dot -Tpng -o archivo.png archivo.dot
            */
            pbuilder = new ProcessBuilder( "dot", "-Tpng", "-o", direccionPng, direccionDot );
            pbuilder.redirectErrorStream( true );
            //Ejecuta el proceso
            pbuilder.start();
		    
        } catch (Exception e) { e.printStackTrace(); }
    }
}
