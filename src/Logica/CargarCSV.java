/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Graphik.Resultado;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Kristhal
 */
public class CargarCSV {
    public static CargarCSV cargar = null;
    public static JTable tablaDatos;
    public static DefaultTableModel modelo;
    TableRowSorter<TableModel> sorter;
    
    public CargarCSV()
    {
        modelo = new DefaultTableModel();
        sorter = new TableRowSorter<TableModel>(modelo);
        tablaDatos = new JTable(modelo);
        tablaDatos.setRowSorter(sorter);
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
    
    public Vector consultaDonde(Resultado indice, Resultado filtro)
    {
        if(indice.tipogk.equals("entero")){
            for(int i=0; i<modelo.getRowCount(); i++)
            {
                Vector algo = (Vector)modelo.getDataVector().elementAt(i);
                Resultado resultado = (Resultado) algo.get(indice.valgk);
                if(resultado.tipogk.equalsIgnoreCase("cadena") && filtro.tipogk.equalsIgnoreCase("cadena"))
                {
                    if(resultado.valorgk.equalsIgnoreCase(filtro.valorgk))
                    {
                        return algo;
                    }
                }
                else if(resultado.tipogk.equalsIgnoreCase("entero") && filtro.tipogk.equalsIgnoreCase("entero"))
                {
                    if(resultado.valgk==filtro.valgk)
                    {
                        return algo;
                    }
                }
                else if(resultado.tipogk.equalsIgnoreCase("decimal") && filtro.tipogk.equalsIgnoreCase("decimal"))
                {
                    if(resultado.valDoble==filtro.valDoble)
                    {
                        return algo;
                    }
                }
            }
        }
        return null;
    }
    
    public Vector consultaDondeCada(Resultado fila)
    {
        if(fila.tipogk.equalsIgnoreCase("entero"))
        {
            Vector algo = (Vector) modelo.getDataVector().elementAt(fila.valgk);
            return algo;
        }
        return null;
    }
}
