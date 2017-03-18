/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import com.mxrck.autocompleter.TextAutoCompleter;

/**
 *
 * @author Kristhal
 */
public class Tabs extends JPanel {
    
    private String nombre=null;
    private String ruta =null;
    private String tipo = null;
    TextAutoCompleter textAutoCompleter;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public JTextPane txtEntrada = new JTextPane();
    
    public Tabs()
    {
        this.setLayout(new BorderLayout());
        JScrollPane panel = new JScrollPane(txtEntrada);
        TextLineNumber tln = new TextLineNumber(txtEntrada);
        panel.setRowHeaderView(tln);
        this.add(panel,BorderLayout.CENTER);
        
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
}
