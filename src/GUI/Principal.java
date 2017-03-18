/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Haskell.NodoHK;
import Graphik.RecorridoAST;
import Graphik.NodoGK;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import Logica.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import Errores.*;
import Haskell.*;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Kristhal
 */
public class Principal extends javax.swing.JFrame implements KeyListener{

    public static int contTabs=0;
    JTabbedPane TabControl = new JTabbedPane();
    javax.swing.filechooser.FileNameExtensionFilter filtro;
    javax.swing.undo.UndoManager manejador;
    ManejoArchivos accEditor;
    boolean login=false;
    boolean logout=false;
    GregorianCalendar gcalendar = new GregorianCalendar();
    JTextArea txtConsola;
    JTextField txtComandos;
    JPanel pnlConsola;
    JSplitPane splitPane;
    JScrollPane scroll;
    String comandoAnt="";
    
    
    /**
     * Creates new form Principal
     */
    
    public Principal() {
        initComponents();
        masComponentes();
        componentesAuxiliares();
        crearPorcentaje();
        //System.out.println(Fibonacci(5));
    }
   
//    int Fibonacci (int n)
//    {
//        if(n==0||n==1)
//        {
//            return 1;
//        }
//        else
//        {
//            return Fibonacci(n-1)+Fibonacci(n-2);
//        }
//    }
    
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
        getImage(ClassLoader.getSystemResource("Image/otroIcono.png"));
        return retValue;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileCh = new javax.swing.JFileChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mnuCrear = new javax.swing.JMenuItem();
        mnuNuevo = new javax.swing.JMenu();
        mnuHaskell = new javax.swing.JMenuItem();
        mnuGraphik = new javax.swing.JMenuItem();
        mnuAbrir = new javax.swing.JMenuItem();
        mnuGuardar = new javax.swing.JMenuItem();
        mnuGuardarComo = new javax.swing.JMenuItem();
        mnuCerrarP = new javax.swing.JMenuItem();
        mnuSalir = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btnEjecutar = new javax.swing.JMenuItem();
        mnuCompilarGK = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuLogin = new javax.swing.JMenuItem();
        mnuLogout = new javax.swing.JMenuItem();
        mnuCrearProyecto = new javax.swing.JMenuItem();
        mnuImportarProyecto = new javax.swing.JMenuItem();
        mnuExportarProyecto = new javax.swing.JMenuItem();
        mnuErrores = new javax.swing.JMenu();
        mnuErrorHaskell = new javax.swing.JMenuItem();
        mnuErrorGraphik = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GraphiK & Haskell++");
        setIconImage(getIconImage());
        setName("Principal"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1550, 850));
        setSize(new java.awt.Dimension(1550, 850));

        mnuArchivo.setBackground(new java.awt.Color(255, 255, 255));
        mnuArchivo.setText("Archivo");
        mnuArchivo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        mnuCrear.setBackground(new java.awt.Color(255, 255, 255));
        mnuCrear.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuCrear.setText("Crear");
        mnuCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCrearActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuCrear);

        mnuNuevo.setBackground(new java.awt.Color(255, 255, 255));
        mnuNuevo.setText("Nuevo");
        mnuNuevo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        mnuHaskell.setBackground(new java.awt.Color(255, 255, 255));
        mnuHaskell.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuHaskell.setText("Haskell++");
        mnuNuevo.add(mnuHaskell);

        mnuGraphik.setBackground(new java.awt.Color(255, 255, 255));
        mnuGraphik.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuGraphik.setText("GraphiK");
        mnuNuevo.add(mnuGraphik);

        mnuArchivo.add(mnuNuevo);

        mnuAbrir.setBackground(new java.awt.Color(255, 255, 255));
        mnuAbrir.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuAbrir.setText("Abrir");
        mnuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAbrirActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuAbrir);

        mnuGuardar.setBackground(new java.awt.Color(255, 255, 255));
        mnuGuardar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuGuardar.setText("Guardar");
        mnuArchivo.add(mnuGuardar);

        mnuGuardarComo.setBackground(new java.awt.Color(255, 255, 255));
        mnuGuardarComo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuGuardarComo.setText("Guardar Como...");
        mnuGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGuardarComoActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuGuardarComo);

        mnuCerrarP.setBackground(new java.awt.Color(255, 255, 255));
        mnuCerrarP.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuCerrarP.setText("Cerrar Pestaña");
        mnuCerrarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCerrarPActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuCerrarP);

        mnuSalir.setBackground(new java.awt.Color(255, 255, 255));
        mnuSalir.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        mnuSalir.setText("Salir");
        mnuArchivo.add(mnuSalir);

        jMenuBar1.add(mnuArchivo);

        jMenu1.setText("Ejecucion");

        btnEjecutar.setText("Compilar Haskell");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });
        jMenu1.add(btnEjecutar);

        mnuCompilarGK.setText("Compilar Graphik");
        mnuCompilarGK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCompilarGKActionPerformed(evt);
            }
        });
        jMenu1.add(mnuCompilarGK);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Usuario");

        mnuLogin.setText("Login");
        mnuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuLoginActionPerformed(evt);
            }
        });
        jMenu2.add(mnuLogin);

        mnuLogout.setText("Logout");
        jMenu2.add(mnuLogout);

        mnuCrearProyecto.setText("Crear Proyecto");
        jMenu2.add(mnuCrearProyecto);

        mnuImportarProyecto.setText("Importar Proyecto");
        jMenu2.add(mnuImportarProyecto);

        mnuExportarProyecto.setText("Exportar Proyecto");
        jMenu2.add(mnuExportarProyecto);

        jMenuBar1.add(jMenu2);

        mnuErrores.setText("Errores");

        mnuErrorHaskell.setText("Reporte Haskell++");
        mnuErrorHaskell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuErrorHaskellActionPerformed(evt);
            }
        });
        mnuErrores.add(mnuErrorHaskell);

        mnuErrorGraphik.setText("Reporte Graphik");
        mnuErrorGraphik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuErrorGraphikActionPerformed(evt);
            }
        });
        mnuErrores.add(mnuErrorGraphik);

        jMenuBar1.add(mnuErrores);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(1566, 849));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnuCerrarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCerrarPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuCerrarPActionPerformed

    private void mnuCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCrearActionPerformed
        crear();    // TODO add your handling code here:
    }//GEN-LAST:event_mnuCrearActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        ejecutarHK();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void mnuCompilarGKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCompilarGKActionPerformed
        ejecutarGK();    // TODO add your handling code here:
    }//GEN-LAST:event_mnuCompilarGKActionPerformed

    private void mnuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuLoginActionPerformed
        Login log = new Login();    // TODO add your handling code here:
        log.setVisible(true);
    }//GEN-LAST:event_mnuLoginActionPerformed

    private void mnuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAbrirActionPerformed
        abrir();    // TODO add your handling code here:
    }//GEN-LAST:event_mnuAbrirActionPerformed

    private void mnuGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGuardarComoActionPerformed
        guardarComo(); // TODO add your handling code here:
    }//GEN-LAST:event_mnuGuardarComoActionPerformed

    private void mnuErrorHaskellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuErrorHaskellActionPerformed
        abrirErrorHaskell();    // TODO add your handling code here:
    }//GEN-LAST:event_mnuErrorHaskellActionPerformed

    private void mnuErrorGraphikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuErrorGraphikActionPerformed
        abrirErrorGraphik();        // TODO add your handling code here:
    }//GEN-LAST:event_mnuErrorGraphikActionPerformed

    private void crear()
    {
        Tabs tab = new Tabs();
        contTabs++;
        TabControl.addTab("New "+contTabs, null, tab);
    }
    
    public void ejecutarHK()
    {
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        if(actual!=null){
            try { 
                reiniciarAnalisis();
                String input=actual.txtEntrada.getText();
                Reader reader = new StringReader(input);
                Analisis.lexicoHanskell scan = new Analisis.lexicoHanskell(reader);
                Analisis.sintacticoHaskell pars = new Analisis.sintacticoHaskell(scan);
                pars.parse();
                RELHK(pars.nodo);
                dibujarHK(pintarHK(pars.nodo,""));
                RecorridoHK recorre = new RecorridoHK(pars.nodo);
                recorre.primeraPasada();
                String tabla = recorre.imprimirTabla();
                escribirArchivoTabla(tabla);
                mostrarErrores("Haskell");
            } 
            catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                Errores.getInstance().nuevoError(ex.getMessage());
            }
        }
    }
    
    public void ejecutarGK()
    {
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        if(actual!=null){
            try { 
                reiniciarAnalisis();
                String input=actual.txtEntrada.getText();
                Reader reader = new StringReader(input);
                Analisis.lexicoGraphik scan = new Analisis.lexicoGraphik(reader);
                Analisis.sintacticoGraphik pars = new Analisis.sintacticoGraphik(scan);
                pars.parse();
                RELGK(pars.nodo);
                dibujarGK(pintarGK(pars.nodo,""));
                RecorridoAST recorre = new RecorridoAST(pars.nodo);
                recorre.primeraPasada(actual.getRuta(), actual.getNombre());
                mostrarErrores("Graphik");
            } 
            catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                Errores.getInstance().nuevoError(ex.getMessage());
            }
        }
    }
    
    public void ejecutarConsole(String cadena)
    {
        if(!cadena.equals("")){
            try { 
                reiniciarAnalisis();
                Reader reader = new StringReader(cadena);
                Analisis.lexicoConsole scan = new Analisis.lexicoConsole(reader);
                Analisis.sintacticoConsole pars = new Analisis.sintacticoConsole(scan);
                pars.parse();
                RELHK(pars.nodo);
                dibujarHK(pintarHK(pars.nodo,""));
                RecorridoHK recorre = new RecorridoHK(pars.nodo);
                recorre.primeraPasada();
                String tabla = recorre.imprimirTabla();
                escribirArchivoTabla(tabla);
                EjecucionHK ejecutar = new EjecucionHK(pars.nodo, txtConsola);
                ejecutar.Ejecutar();
                if(ejecutar.getUltimo()!=null)
                {
                    txtConsola.append("->"+ejecutar.getUltimo().getVal().getImprimirPantalla()+"\n");
                }
                mostrarErrores("Haskell");
            } 
            catch (Exception ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                txtConsola.append(">"+"Se encontraron errores en la cadena de entrada\n");
                mostrarErrores("Haskell");
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnEjecutar;
    private javax.swing.JFileChooser fileCh;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem mnuAbrir;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenuItem mnuCerrarP;
    private javax.swing.JMenuItem mnuCompilarGK;
    private javax.swing.JMenuItem mnuCrear;
    private javax.swing.JMenuItem mnuCrearProyecto;
    private javax.swing.JMenuItem mnuErrorGraphik;
    private javax.swing.JMenuItem mnuErrorHaskell;
    private javax.swing.JMenu mnuErrores;
    private javax.swing.JMenuItem mnuExportarProyecto;
    private javax.swing.JMenuItem mnuGraphik;
    private javax.swing.JMenuItem mnuGuardar;
    private javax.swing.JMenuItem mnuGuardarComo;
    private javax.swing.JMenuItem mnuHaskell;
    private javax.swing.JMenuItem mnuImportarProyecto;
    private javax.swing.JMenuItem mnuLogin;
    private javax.swing.JMenuItem mnuLogout;
    private javax.swing.JMenu mnuNuevo;
    private javax.swing.JMenuItem mnuSalir;
    // End of variables declaration//GEN-END:variables

    private void masComponentes() {
        txtConsola = new JTextArea();
        txtConsola.setForeground(Color.WHITE);
        txtConsola.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtConsola.setBackground(Color.BLACK);
        txtConsola.setAutoscrolls(true);
        txtConsola.addKeyListener(this);
        txtConsola.setEnabled(false);
        
        txtComandos = new JTextField();
        txtComandos.setForeground(Color.WHITE);
        txtComandos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtComandos.setBackground(Color.BLACK);
        txtComandos.addKeyListener(this);
        
        pnlConsola = new JPanel(new BorderLayout());
        scroll = new JScrollPane(txtConsola);
        pnlConsola.add(scroll, BorderLayout.CENTER);
        pnlConsola.add(txtComandos, BorderLayout.SOUTH);
        
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(TabControl);
        splitPane.setRightComponent(pnlConsola);
        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
        splitPane.setResizeWeight(0.7);
        
        
        
    }

    private void componentesAuxiliares() {
        accEditor = new ManejoArchivos();
        manejador = new javax.swing.undo.UndoManager();
        fileCh = new javax.swing.JFileChooser();        
        filtro = new javax.swing.filechooser.FileNameExtensionFilter("GraphiK", "graphik");             
        fileCh.setFileFilter(filtro);   
    }
    
    //METODOS PARA EL ANALISIS
    private void reiniciarAnalisis()
    {
        Errores.resetInstance();
    }
    
    //METODOS PARA GRAFICAR
    
    static int relativo = 0; 
    public void RELGK(NodoGK nd){
        try{
            nd.relativo=relativo++;
            for(int i =0; i<nd.hijos.size();i++){
            nd.relativo=relativo++; 
            RELGK(nd.hijos.get(i));    
            }
        }catch(Exception e){
        }
    }
    
    private String pintarGK(NodoGK nd, String cad){
        try{
            for(int i =0; i<nd.hijos.size();i++){
                cad=cad+"\""+nd.valor.replace("\"", "")+nd.relativo+"\"[label =\""+nd.valor.replace("\"", "")+"\"];\n";
                cad=cad+"\""+nd.hijos.get(i).valor.replace("\"", "")+nd.hijos.get(i).relativo+"\"[label =\""+nd.hijos.get(i).valor.replace("\"", "")+"\"];\n";
                cad=cad+"\""+nd.valor.replace("\"", "") +nd.relativo+"\"--\""+nd.hijos.get(i).valor.replace("\"", "")+nd.hijos.get(i).relativo+"\";\n";
                cad=pintarGK(nd.hijos.get(i), cad); 
            }
            return cad;
        }catch(Exception e){
            return "";
        }
    }
    
    static int relativoHK = 0; 
    public void RELHK(NodoHK nd){
        try{
            nd.relativo=relativoHK++;
            for(int i =0; i<nd.hijos.size();i++){
            nd.relativo=relativoHK++; 
            RELHK(nd.hijos.get(i));    
            }
        }catch(Exception e){
        }
    }
    
    private String pintarHK(NodoHK nd, String cad){
        try{
            for(int i =0; i<nd.hijos.size();i++){
                cad=cad+"\""+nd.valor.replace("\"", "")+nd.relativo+"\"[label =\""+nd.valor.replace("\"", "")+"\"];\n";
                cad=cad+"\""+nd.hijos.get(i).valor.replace("\"", "")+nd.hijos.get(i).relativo+"\"[label =\""+nd.hijos.get(i).valor.replace("\"", "")+"\"];\n";
                cad=cad+"\""+nd.valor.replace("\"", "") +nd.relativo+"\"--\""+nd.hijos.get(i).valor.replace("\"", "")+nd.hijos.get(i).relativo+"\";\n";
                cad=pintarHK(nd.hijos.get(i), cad); 
            }
            return cad;
        }catch(Exception e){
            return "";
        }
    }
    
    private void dibujarGK(String nd){

        try{
            String cad="";
            cad=cad+"graph G{ rankir=TB; node [shape = box, fontsize=11, fontname=\"Arial\", style=filled, fillcolor=grey88];"+nd+"}}}";
            String type = "png";
            File out = new File("Graphik." + type);
            File out2 = new File("Graphik." + "txt");
            FileWriter w = new FileWriter(out2);
            PrintWriter wr = new PrintWriter(w); 
            wr.write(cad);
            w.close();
            wr.close();
            GraphvizJava gj = new GraphvizJava(out2+"",out+"");
        }catch(Exception e){}
    }
    
    private void dibujarHK(String nd){

        try{
            String cad="";
            cad=cad+"graph G{ rankir=TB; node [shape = box, fontsize=11, fontname=\"Arial\", style=filled, fillcolor=grey88];"+nd+"}}}";
            String type = "png";
            File out = new File("Haskell." + type);
            File out2 = new File("Haskell." + "txt");
            FileWriter w = new FileWriter(out2);
            PrintWriter wr = new PrintWriter(w); 
            wr.write(cad);
            w.close();
            wr.close();
            GraphvizJava gj = new GraphvizJava(out2+"",out+"");
        }catch(Exception e){}
    }
    
    //METODOS PARA EL MANEJO DE ARCHIVOS
        private int askGuardar(){
        int selec = 0;
        if(mnuGuardar.isEnabled()){
            selec = JOptionPane.showConfirmDialog(this, "¿Desea guardar el archivo?", "Guardar ...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selec == 0){
                guardar();
            }
        }
        return selec;
    }
     
    private void onEditor(boolean b){
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        mnuGuardar.setEnabled(b);
        actual.enable(b);      
    }
    
    private void onGuardar(boolean b){
        mnuGuardar.setEnabled(b);
        
    }
//    
    private boolean abrirDialog(){              
        int seleccion = fileCh.showDialog(this, "Seleecionar archivo ...");
        
        if(seleccion == javax.swing.JFileChooser.APPROVE_OPTION){
            accEditor.fichero = fileCh.getSelectedFile();
            return true;
        }
        return false;
    }    
//    
    private boolean guardarComoDialog(){
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        int seleccion = fileCh.showSaveDialog(this);
        
        if(seleccion == javax.swing.JFileChooser.APPROVE_OPTION){
            return accEditor.GuardarGK(fileCh.getSelectedFile().getPath(), actual.txtEntrada.getText());
        }
        return false;
    }
    
    private void nuevo(){
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        if(askGuardar() != -1){
            accEditor.fichero = null;
            actual.txtEntrada.setText(null);
            if(!actual.txtEntrada.isEnabled()){
                actual.txtEntrada.setBackground(new java.awt.Color(255,255,255,255));
                actual.txtEntrada.requestFocus();
                onEditor(true);
            }
            onGuardar(false);
        }
    }
    
    private void abrir(){   
        Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
        if(askGuardar() != -1){
            if(abrirDialog()){
                String texto = accEditor.Open();
                if(texto != null){
                    actual.txtEntrada.setText(texto);
                    actual.setRuta(accEditor.getRuta());
                    onGuardar(false);
                    JOptionPane.showMessageDialog(null,"EL ARCHIVO SE ABRIO CON EXITO","Abrir",JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null,"NO SE PUDO ABRIR EL ARCHIVO","Abrir",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    
       private boolean guardar(){
           Tabs actual = (Tabs)this.TabControl.getSelectedComponent();
           if(mnuGuardar.isEnabled()){
            if(accEditor.fichero == null){
                if(guardarComoDialog()){
                    onGuardar(false);
                    return true;
                }else{
                    return false;
                }
            }else{
                if(accEditor.Guardar(actual.txtEntrada.getText())){
                    onGuardar(false);
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    
    private void guardarComo(){
        if(guardarComoDialog()){
            onGuardar(false);
            JOptionPane.showMessageDialog(null,"EL ARCHIVO SE GUARDO CON EXITO","Guardar",JOptionPane.INFORMATION_MESSAGE);
        }
        
        else {
             JOptionPane.showMessageDialog(null,"NO SE PUDO GUARDAR EL ARCHIVO","Guardar",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //METODOS PARA EL CONTROL DE ERRORES
    private void mostrarErrores(String reporte)
    {
        Errores errs = Errores.getInstance();
        int cuentaErrores = errs.cuentaErrores();
        List<NError> lista = new ArrayList<>();
        if(cuentaErrores>0)
        {
            JOptionPane.showMessageDialog(this, "Existen algunos errores en la entrada.", "Errores", JOptionPane.ERROR_MESSAGE);
            lista=errs.getReporteErrores();
            String cadena=impirmirErrores(lista,reporte);
            escribirArchivoErrores(cadena, reporte);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No se encontro ningun error, Excelente!! xD", "Errores", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //IMPRIMIR ERRORES EN HTML
    private String impirmirErrores(List<NError> errores, String reporte)
    {
        String path="<html>";
        path += "<head><title>Errores "+reporte+"</title></head>";
        path += "<body><h1>Reporte de Errores: "+reporte+"</h1>";
        path += "<h3>Fecha de Ejecucion: "+obtenerDate()+"</h3>";
        path += "<h3>Hora de Ejecucion: "+obtenerTime()+"</h3>";
        path += "<table border=\"1\" align=\"left\" bordercolor=\"gray\" cellspacing=\"0\">";
        path += "<tr>";
        path += "<td><strong>Linea</strong></td>";
        path += "<td><strong>Columna</strong></td>";
        path += "<td><strong>Tipo Error</strong></td>";
        path += "<td><strong>Descripcion</strong></td>";
        for(NError value : errores)
        {
            path += "<tr>";
            path += "<td>" + value.getLinea() + "</td>";
            path += "<td>" + value.getColumna() + "</td>";
            path += "<td>" + value.getTipo() + "</td>";
            path += "<td>" + value.getDescripcion() + "</td>";
            path += "</tr>";
        }
        path += "</table></body></html>";
        return path;
    }
    
    private String obtenerDate()
    {
        String months[]={"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                         "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String cadena="";
        int year;
        cadena+=gcalendar.get(Calendar.DATE)+" de ";
        cadena+=months[gcalendar.get(Calendar.MONTH)];
        year=gcalendar.get(Calendar.YEAR);
        cadena+=" del "+year;
        return cadena;
    }
    
    private String obtenerTime()
    {
        String cadena="";
        cadena+=gcalendar.get(Calendar.HOUR)+ ":";
        cadena+=gcalendar.get(Calendar.MINUTE)+":";
        cadena+=gcalendar.get(Calendar.SECOND)+" ";
        if(gcalendar.get(Calendar.AM_PM)==0)
        {
            cadena+="AM";
        }
        else
        {
            cadena+="PM";
        }
        return cadena;
    }
    
    private void escribirArchivoErrores(String cadena, String reporte)
    {
        try {            
            File archivo = new File("Errores"+reporte+".html");
            FileWriter escribir = new FileWriter(archivo, false);
            PrintWriter pintar = new PrintWriter(escribir);          
            pintar.write(cadena);    
            escribir.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirErrorHaskell(){
        Desktop dt = Desktop.getDesktop();
        File f = new File("ErroresHaskell.html");
        try {
            dt.open(f);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirErrorGraphik() {
        Desktop dt = Desktop.getDesktop();
        File f = new File("ErroresGraphik.html");
        try {
            dt.open(f);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void escribirArchivoTabla(String cadena)
    {
        try {            
            File archivo = new File("TablaSimbolos"+".html");
            FileWriter escribir = new FileWriter(archivo, false);
            PrintWriter pintar = new PrintWriter(escribir);          
            pintar.write(cadena);    
            escribir.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void abrirTablaSimbolos() {
        Desktop dt = Desktop.getDesktop();
        File f = new File("TablaSimbolos.html");
        try {
            dt.open(f);
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        if(key==KeyEvent.VK_ENTER)
        {
            if(!txtComandos.getText().equals("")){
                comandoAnt=txtComandos.getText();
                txtConsola.append(">"+txtComandos.getText()+"\n");
                ejecutarConsole(txtComandos.getText());
                txtComandos.setText("");
            }
        }
        else if(key==KeyEvent.VK_UP)
        {
            txtComandos.setText(comandoAnt);
        }
        if(key==KeyEvent.VK_F5)
        {
            txtConsola.setText("");
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    private void crearPorcentaje()
    {
        TablaSimbolos tabla = TablaSimbolos.getInstance();
        NodoTabla registro;
        registro=new NodoTabla("%","","ultimo","","","",0,0,new Value(), null,null,0);
        tabla.declarar(registro);
        
    }
            
}
