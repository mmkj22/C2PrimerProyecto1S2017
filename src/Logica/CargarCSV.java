/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kristhal
 */
public class CargarCSV {
    public static CargarCSV cargar = null;
    public static JTable tablaDatos;
    public static DefaultTableModel modelo;
    
    public CargarCSV()
    {
        modelo = new DefaultTableModel();
        tablaDatos = new JTable(modelo);
        
    }
    
    public static CargarCSV resetInstance()
    {
        cargar = new CargarCSV();
        return cargar;
    }
    
    public static CargarCSV getInstance()
    {
        if(cargar == null)
        {
            cargar = new CargarCSV();
        }
        return cargar;
    }
    
    public void printTable() throws PrinterException
    {
        MessageFormat header = new MessageFormat("Page Header");
        MessageFormat footer = new MessageFormat("Page Footer");
        tablaDatos.print(JTable.PrintMode.FIT_WIDTH, header, footer);
    }
}
