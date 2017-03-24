/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;
import java.sql.*;
import org.relique.jdbc.csv.CsvDriver;

/**
 *
 * @author Kristhal
 */
public class CargarDatos {
    
    public static CargarDatos cargar= null;
    private Connection conn;
    public static String ruta;
    
    public CargarDatos()
    {
        ruta="";
    }
    
    public void Donde() throws Exception
    {
        System.out.println(ruta);
        Class.forName("org.relique.jdbc.csv.CsvDriver");
        conn = DriverManager.getConnection("jdbc:relique:csv:"+ruta);
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM Prueba");
        boolean append = true;
        CsvDriver.writeToCsv(results, System.out, append);
        conn.close();
        System.out.println(results.toString());
    }
    
    public void DondeCada() throws Exception
    {
        System.out.println(ruta);
        Class.forName("org.relique.jdbc.csv.CsvDriver");
        conn = DriverManager.getConnection("jdbc:relique:csv:"+ruta);
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT NOMBRE, APELLIDO FROM Prueba");
        boolean append = true;
        CsvDriver.writeToCsv(results, System.out, append);
        conn.close();
    }
    
    public void DondeTodo() throws Exception
    {
        System.out.println(ruta);
        Class.forName("org.relique.jdbc.csv.CsvDriver");
        conn = DriverManager.getConnection("jdbc:relique:csv:"+ruta);
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT NOMBRE, APELLIDO FROM Prueba");
        boolean append = true;
        CsvDriver.writeToCsv(results, System.out, append);
        conn.close();
    }
    
    public static CargarDatos resetInstance()
    {
        cargar = new CargarDatos();
        return cargar;
    }
    
    public static CargarDatos getInstance()
    {
        if(cargar == null)
        {
            cargar = new CargarDatos();
        }
        return cargar;
    }
}
