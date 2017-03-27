/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphik;

import Haskell.*;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author Kristhal
 */
public class LlamadasHK {
    TablaSimbolos tabla = TablaSimbolos.getInstance();
    JTextArea txtResultados;
    
    public LlamadasHK(JTextArea txtResultados)
    {
        this.txtResultados=txtResultados;
    }
    
    public Resultado generarLlamadaHK(NodoGK id, List<Resultado> lst)
    {
        Resultado content;
        NodoHK sentencias = new NodoHK("SENTENCIAS");
        NodoHK nodo = new NodoHK("LLAMADA_MET");
        nodo.hijos.add(new NodoHK(id.valor, id.linea, id.columna));
        NodoHK parametros = new NodoHK("PARAMETROS");
        for(Resultado par : lst)
        {
            if(par.isIsArreglo())
            {
                
            }
            else
            {
                NodoHK tipo;
                switch(par.tipogk)
                {
                    case "entero":
                        tipo = new NodoHK("entero");
                        tipo.hijos.add(new NodoHK(String.valueOf(par.valgk)));
                        parametros.hijos.add(tipo);
                        break;
                    case "decimal":
                        tipo = new NodoHK("decimal");
                        tipo.hijos.add(new NodoHK(String.valueOf(par.valDoble)));
                        parametros.hijos.add(tipo);
                        break;
                    case "caracter":
                        tipo = new NodoHK("caracter");
                        tipo.hijos.add(new NodoHK("'"+String.valueOf(par.valChar)+"'"));
                        parametros.hijos.add(tipo);
                        break;
                    case "cadena":
                        tipo = new NodoHK("cadena");
                        tipo.hijos.add(new NodoHK("\""+String.valueOf(par.valorgk)+"\""));
                        parametros.hijos.add(tipo);
                        break;
                    default:
                        //ERROR
                        return null; 
                }
            }
        }
        nodo.hijos.add(parametros);
        sentencias.hijos.add(nodo);
        if(tabla.getHash().containsKey(id.valor))
        {
            EjecucionHK ejecutar = new EjecucionHK(sentencias, txtResultados);
            ejecutar.Ejecutar();
            if(ejecutar.getUltimo()!=null)
            {
                content=this.transformarAGraphik(ejecutar.getUltimo());
                if(content==null)
                {
                    return null;
                }
                return content;
            }
        }
        return null;
    }
    
    public Resultado transformarAGraphik(NodoTabla ultimo)
    {
        Resultado content;
        Value valor = ultimo.getVal();
        if(!valor.isIsArreglo())
        {
            switch(valor.getTipo())
            {
                case "numero":
                    content = new Resultado("decimal", (double)valor.getVal());
                    content.setVal(valor.getVal());
                    content.setTipo(valor.getTipo());
                    return content;
                case "caracter":
                    content = new Resultado("caracter", (char)valor.getVal());
                    content.setVal(valor.getVal());
                    content.setTipo(valor.getTipo());
                    return content;
                default: 
                    //ERROR
            }
        }
        else if(ultimo.getRol().equalsIgnoreCase("arr") || valor.isIsArreglo())
        {
        
        }
        return null;
    }
}
