/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Graphik.Resultado;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author Kristhal
 */
public class Graficar extends JFrame {
    public static Graficar graficar = null;
    public static JFreeChart graficaGK;
    public static XYSeriesCollection dataset;
    
    
    public Graficar(String applicationTitle)
    {
        super(applicationTitle);
        dataset = new XYSeriesCollection();
    }
    
    public static Graficar resetInstance()
    {
        graficar = new Graficar("GraphiK");
        return graficar;
    }
    
    public static Graficar getInstance()
    {
        if(graficar == null)
        {
            graficar = new Graficar("GraphiK");
        }
        return graficar;
    }
    
    public void personalizarGrafica()
    {
        graficaGK = ChartFactory.createXYLineChart(
                    "Proyecto 1 Compiladores 2", "Eje X", "Eje Y", 
                    dataset, PlotOrientation.VERTICAL, true, true, false);
    }
    
    public void addSerie(List<Resultado> ejex, List<Resultado> ejey, String key)
    {
        final XYSeries serie = new XYSeries(key);
        if(ejex.size()==ejey.size())
        {
            for(int i=0; i<ejex.size(); i++)
            {
                if(ejex.get(i).tipogk.equalsIgnoreCase("entero") && ejey.get(i).tipogk.equalsIgnoreCase("decimal"))
                {
                    serie.add(ejex.get(i).valgk, ejey.get(i).valDoble );
                }
                else if(ejex.get(i).tipogk.equalsIgnoreCase("decimal") && ejey.get(i).tipogk.equalsIgnoreCase("entero"))
                {
                    serie.add(ejex.get(i).valDoble, ejey.get(i).valgk );
                }
                else if (ejex.get(i).tipogk.equalsIgnoreCase("entero") && ejey.get(i).tipogk.equalsIgnoreCase("entero"))
                {
                    serie.add(ejex.get(i).valgk, ejey.get(i).valgk );
                }
                else if (ejex.get(i).tipogk.equalsIgnoreCase("decimal") && ejey.get(i).tipogk.equalsIgnoreCase("decimal"))
                {
                    serie.add(ejex.get(i).valDoble, ejey.get(i).valDoble );
                }
            }
            dataset.addSeries(serie);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Los Arreglos no son del mismo size, no se puede graficar. ","Graphikar",JOptionPane.ERROR);
        }
        
    }
    
    public void mostrarGrafica()
    {
        ChartPanel chartPanel = new ChartPanel(graficaGK);
        chartPanel.setPreferredSize(new java.awt.Dimension(560,367));
        final XYPlot plot = graficaGK.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.BLACK);
        setContentPane(chartPanel);
    } 
}
