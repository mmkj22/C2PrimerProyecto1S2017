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
            if(par.arreglo)
            {
                NodoHK lista = new NodoHK("lista");
                String[] dimensiones = par.lstdimensiones.split("_");
                if(dimensiones.length>2 || dimensiones.length==0)
                {
                    return null;
                }
                else if(dimensiones.length==1)
                {
                    NodoHK dim = new NodoHK("DIMENSIONES");
                    for(Resultado res : par.elementosArreglo)
                    {
                        NodoHK val;
                        if(res.tipogk.equalsIgnoreCase("caracter"))
                        {
                            NodoHK tipo = new NodoHK(res.tipogk);
                            val = new NodoHK("\'"+res.valorgk+"\'");
                            tipo.hijos.add(val);
                            dim.hijos.add(tipo);
                        }
                        else if(res.tipogk.equalsIgnoreCase("cadena"))
                        {
                            NodoHK tipo = new NodoHK(res.tipogk);
                            val = new NodoHK("\""+res.valorgk+"\"");
                            tipo.hijos.add(val);
                            dim.hijos.add(tipo);
                        }
                        else
                        {   
                            NodoHK cal = new NodoHK("CALCULAR");
                            NodoHK tipo = new NodoHK(res.tipogk);
                            val = new NodoHK(res.valorgk);
                            tipo.hijos.add(val);
                            cal.hijos.add(tipo);
                            dim.hijos.add(cal);
                        }
                        
                    }
                    lista.hijos.add(dim);
                    parametros.hijos.add(lista);
                }
                else
                {
                    NodoHK dims = new NodoHK("DIMENSIONES");
                    NodoHK dim = new NodoHK("DIMENSIONES");
                    for(int i=0; i<Integer.parseInt(dimensiones[0]); i++)
                    {
                        if(par.elementosArreglo.get(i).tipogk.equalsIgnoreCase("caracter"))
                        {
                            NodoHK tipo = new NodoHK(par.elementosArreglo.get(i).tipogk);
                            NodoHK val = new NodoHK("\'"+par.elementosArreglo.get(i).valorgk+"\'");
                            tipo.hijos.add(val);
                            dim.hijos.add(tipo);
                        }
                        else if(par.elementosArreglo.get(i).tipogk.equalsIgnoreCase("cadena"))
                        {
                            NodoHK tipo = new NodoHK(par.elementosArreglo.get(i).tipogk);
                            NodoHK val = new NodoHK("\""+par.elementosArreglo.get(i).valorgk+"\"");
                            tipo.hijos.add(val);
                            dim.hijos.add(tipo);
                        } 
                        else
                        {
                            NodoHK cal = new NodoHK("CALCULAR");
                            NodoHK tipo = new NodoHK(par.elementosArreglo.get(i).tipogk);
                            NodoHK val = new NodoHK(par.elementosArreglo.get(i).valorgk);
                            tipo.hijos.add(val);
                            cal.hijos.add(tipo);
                            dim.hijos.add(cal);
                        }
                        
                    }
                    int a = Integer.parseInt(dimensiones[0]);
                    NodoHK otradim = new NodoHK("DIMENSIONES");
                    for(int i=0; i<Integer.parseInt(dimensiones[1]); i++)
                    {
                        NodoHK cal = new NodoHK("CALCULAR");
                        NodoHK tipo = new NodoHK(par.elementosArreglo.get(i+a).tipogk);
                        if(par.elementosArreglo.get(i).tipogk.equalsIgnoreCase("caracter"))
                        {
                            NodoHK val = new NodoHK("\'"+par.elementosArreglo.get(i+a).valorgk+"\'");
                            tipo.hijos.add(val);
                        }
                        else if(par.elementosArreglo.get(i).tipogk.equalsIgnoreCase("cadena"))
                        {
                            NodoHK val = new NodoHK("\""+par.elementosArreglo.get(i+a).valorgk+"\"");
                            tipo.hijos.add(val);
                        } 
                        else
                        {
                            NodoHK val = new NodoHK(par.elementosArreglo.get(i+a).valorgk);
                            tipo.hijos.add(val);
                        }
                        cal.hijos.add(tipo);
                        otradim.hijos.add(cal);
                    }
                    dims.hijos.add(dim);
                    dims.hijos.add(otradim);
                    lista.hijos.add(dims);
                    parametros.hijos.add(lista);
                }
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
            NodoTabla ultimo = ejecutar.getUltimo();
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
        Resultado elemento;
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
            System.out.println("Me quiere devolver un arreglo");
            if(valor.getDimensiones()>1)
            {
               String lstdim="";
               int total = 1;
               content = new Resultado();
               content.arreglo=true;
               content.setIsArreglo(true);
               content.tipogk=valor.getTipo();
               for(int i=0; i<valor.getElementosArreglo().size(); i++)
               {
                   if(i==0)
                   {
                       lstdim+=valor.getElementosArreglo().get(i).getElementosArreglo().size();
                       total*=valor.getElementosArreglo().get(i).getElementosArreglo().size();
                   }
                   else
                   {
                       lstdim+="_"+valor.getElementosArreglo().get(i).getElementosArreglo().size();
                       total*=valor.getElementosArreglo().get(i).getElementosArreglo().size();
                    }
                    for(int j=0; j <valor.getElementosArreglo().get(i).getElementosArreglo().size(); j++)
                    {
                        switch(valor.getElementosArreglo().get(i).getElementosArreglo().get(j).getTipo())
                        {
                            case "numero":
                                elemento = new Resultado("decimal", (double)valor.getElementosArreglo().get(i).getElementosArreglo().get(j).getVal());
                                content.elementosArreglo.add(elemento);
                                break;
                            case "caracter":
                                elemento = new Resultado("caracter", (char)valor.getElementosArreglo().get(i).getElementosArreglo().get(j).getVal());
                                content.elementosArreglo.add(elemento);
                                break;
                            default: 
                                break;
                                //ERROR
                        }
                    }
                }
                content.totalgk=total;
                content.lstdimensiones=lstdim;
                return content;
            }
            else
            {
                content = new Resultado();
                content.arreglo=true;
                content.setIsArreglo(true);
                content.lstdimensiones=String.valueOf(valor.getElementosArreglo().size());
                content.totalgk=valor.getElementosArreglo().size();
                content.tipogk=valor.getTipo();
                for(int i=0; i <valor.getElementosArreglo().size(); i++)
                {
                    switch(valor.getElementosArreglo().get(i).getTipo())
                    {
                        case "numero":
                            elemento = new Resultado("decimal", (double)valor.getElementosArreglo().get(i).getVal());
                            content.elementosArreglo.add(elemento);
                            break;
                        case "caracter":
                            elemento = new Resultado("caracter", (char)valor.getElementosArreglo().get(i).getVal());
                            content.elementosArreglo.add(elemento);
                            break;
                        default: 
                            break;
                            //ERROR
                    }
                }
                return content;
            }
        }
        return null;
    }
}
