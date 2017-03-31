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
import java.awt.Color;
import java.awt.Font;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;



/**
 *
 * @author Kristhal
 */
public class Tabs extends JPanel {
    
    private String nombre=null;
    private String ruta =null;
    private String tipo = null;
    public RSyntaxTextArea rsta;
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public JTextPane txtEntrada = new JTextPane();
    
    public Tabs(String tipo)
    {
        this.setLayout(new BorderLayout());
        this.tipo=tipo;
        if(tipo.equalsIgnoreCase(".gk"))
        {
            AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
            atmf.putMapping("text/Graphik", "GUI.GraphikSyntax");
            rsta = new RSyntaxTextArea(20, 60);
            rsta.setSyntaxEditingStyle("text/Graphik");
            rsta.setCodeFoldingEnabled(true);
            rsta.setCurrentLineHighlightColor(new Color(227, 242, 253, 200));
            rsta.setFadeCurrentLineHighlight(true);
            rsta.setBorder(BorderFactory.createEmptyBorder());
            rsta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            RTextScrollPane rtsp = new RTextScrollPane(rsta);
            rtsp.setFont(new Font("Segoe UI", Font.BOLD, 14));
            rtsp.setViewportBorder(BorderFactory.createEmptyBorder());
            this.add(rtsp, BorderLayout.CENTER);
            SyntaxScheme scheme = rsta.getSyntaxScheme();
            scheme.getStyle(Token.RESERVED_WORD).foreground = Color.BLUE;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.decode("#54C161");
            scheme.getStyle(Token.IDENTIFIER).foreground = Color.decode("#FFC50A");
            scheme.getStyle(Token.COMMENT_EOL).foreground = Color.GRAY;
            scheme.getStyle(Token.COMMENT_MULTILINE).foreground = Color.GRAY;
            scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.BLACK;
            scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground= Color.BLACK;
            scheme.getStyle(Token.LITERAL_CHAR).foreground= Color.decode("#54C161");
            scheme.getStyle(Token.RESERVED_WORD_2).foreground= Color.BLACK;
            
        }
        else
        {
            AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
            atmf.putMapping("text/Haskell", "GUI.HaskellSyntax");
            rsta = new RSyntaxTextArea(20, 60);
            rsta.setSyntaxEditingStyle("text/Haskell");
            rsta.setCodeFoldingEnabled(true);
            rsta.setCurrentLineHighlightColor(new Color(227, 242, 253, 200));
            rsta.setFadeCurrentLineHighlight(true);
            rsta.setBorder(BorderFactory.createEmptyBorder());
            rsta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            RTextScrollPane rtsp = new RTextScrollPane(rsta);
            rtsp.setFont(new Font("Segoe UI", Font.BOLD, 14));
            rtsp.setViewportBorder(BorderFactory.createEmptyBorder());
            this.add(rtsp, BorderLayout.CENTER);
            SyntaxScheme scheme = rsta.getSyntaxScheme();
            scheme.getStyle(Token.RESERVED_WORD).foreground = Color.BLUE;
            scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.PINK;
            scheme.getStyle(Token.IDENTIFIER).foreground = Color.BLACK;
            scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.MAGENTA;
            scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground= Color.MAGENTA;
            scheme.getStyle(Token.LITERAL_CHAR).foreground= Color.PINK;
            scheme.getStyle(Token.RESERVED_WORD_2).foreground= Color.RED;

        }
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
        this.setName(this.parsearRuta(ruta));
    }
    
    private String parsearRuta(String cadena) {
        String[] parRuta = cadena.split("\\\\");
        String path = parRuta[parRuta.length-1];
        return path;
    }
}
