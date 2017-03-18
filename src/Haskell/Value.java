/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Haskell;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kristhal
 */
public class Value {
    public List<Value> elementosArreglo= new ArrayList();
    String tipo;
    Object val;
    boolean isArreglo=false;
    int dimensiones;
    int total;
    String auxArr;
    String imprimirPantalla;
    
    public Value()
    {
        
    }
    
    public Value(String tipo, Object val)
    {
        this.tipo=tipo;
        this.val=val;
    }
    
    public Value(String tipo, int dim, int total, List<Value> elementosArreglo)
    {
        this.tipo=tipo;
        this.dimensiones=dim;
        this.total=total;
        this.elementosArreglo=elementosArreglo;
    }

    public List<Value> getElementosArreglo() {
        return elementosArreglo;
    }

    public void setElementosArreglo(List<Value> elementosArreglo) {
        this.elementosArreglo = elementosArreglo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
    
    public void addElemento(Value val)
    {
        this.elementosArreglo.add(val);
    }

    public boolean isIsArreglo() {
        return isArreglo;
    }

    public void setIsArreglo(boolean isArreglo) {
        this.isArreglo = isArreglo;
    }

    public int getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(int dimensiones) {
        this.dimensiones = dimensiones;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
    public Value getPosicion(int mapeo)
    {
        return this.elementosArreglo.get(mapeo);
    }
    
    public void setPosicion(int mapeo, Value val)
    {
        this.elementosArreglo.add(mapeo, val);
    }

    public String getAuxArr() {
        return auxArr;
    }

    public void setAuxArr(String auxArr) {
        this.auxArr = auxArr;
    }

    public String getImprimirPantalla() {
        return imprimirPantalla;
    }

    public void setImprimirPantalla(String imprimirPantalla) {
        this.imprimirPantalla = imprimirPantalla;
    }
    
    public int ordenarArregloAscendente()
    {
        Value temp;
        if(this.dimensiones>1)
        {
            for(int k=0; k<dimensiones; k++)
            {
                this.elementosArreglo.get(k).ordenarArregloAscendente();
            }
            this.setImprimirPantalla(this.imprimirArreglo());
            return 1;
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        if((Double)elementosArreglo.get(i).getVal()>(Double)elementosArreglo.get(j).getVal()){
                            temp=elementosArreglo.get(i);
                            elementosArreglo.set(i,elementosArreglo.get(j));
                            elementosArreglo.set(j, temp);
                        }
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        double primero = (Character)elementosArreglo.get(i).getVal()*1;
                        double segundo = (Character) elementosArreglo.get(j).getVal()*1;
                        if(primero>segundo){
                            temp=elementosArreglo.get(i);
                            elementosArreglo.set(i,elementosArreglo.get(j));
                            elementosArreglo.set(j, temp);
                        }
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
        }
        return 0;
    }
    
    public int ordenarArregloDescendente()
    {
        Value temp;
        if(this.dimensiones>1)
        {
            for(int k=0; k<dimensiones; k++)
            {
                this.elementosArreglo.get(k).ordenarArregloDescendente();
            }
            this.setImprimirPantalla(this.imprimirArreglo());
            return 1;
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        if((Double)elementosArreglo.get(i).getVal()<(Double)elementosArreglo.get(j).getVal()){
                            temp=elementosArreglo.get(i);
                            elementosArreglo.set(i,elementosArreglo.get(j));
                            elementosArreglo.set(j, temp);
                        }
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        double primero = (Character)elementosArreglo.get(i).getVal()*1;
                        double segundo = (Character) elementosArreglo.get(j).getVal()*1;
                        if(primero<segundo){
                            temp=elementosArreglo.get(i);
                            elementosArreglo.set(i,elementosArreglo.get(j));
                            elementosArreglo.set(j, temp);
                        }
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
        }
        return 0;
    }
    
    public Value parArreglo()
    {
        Value valor=new Value();
        int contador=1;
        boolean par;
        if(this.dimensiones>1)
        {
            for(int k=0; k<dimensiones; k=k+2)
            {
                valor.addElemento(this.elementosArreglo.get(k));
                contador++;
            }
            valor.setIsArreglo(true);
            valor.setDimensiones(contador);
            valor.setTipo(tipo);
            valor.setImprimirPantalla(valor.imprimirArreglo());
            return valor;
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i=i+2)
                {
                    valor.addElemento(elementosArreglo.get(i));
                }
                valor.setIsArreglo(true);
                valor.setDimensiones(1);
                valor.setTipo("numero");
                valor.setImprimirPantalla(valor.imprimirArreglo());
                return valor;
                
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i=i+2)
                {
                    valor.addElemento(elementosArreglo.get(i));
                }
                valor.setIsArreglo(true);
                valor.setDimensiones(1);
                valor.setTipo(tipo);
                valor.setImprimirPantalla(valor.imprimirArreglo());
                return valor;
            }
        }
        return null;
    }
    
    public Value imparArreglo()
    {
        Value valor=new Value();
        boolean impr;
        int contador=1;
        if(this.dimensiones>1)
        {
            for(int k=1; k<dimensiones; k=k+2)
            {
                valor.addElemento(this.elementosArreglo.get(k));
                contador++;
            }
            valor.setIsArreglo(true);
            valor.setDimensiones(contador);
            valor.setTipo(tipo);
            valor.setImprimirPantalla(valor.imprimirArreglo());
            return valor;
           
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=1; i<this.elementosArreglo.size(); i=i+2)
                {
                    valor.addElemento(elementosArreglo.get(i));
                }
                valor.setIsArreglo(true);
                valor.setDimensiones(1);
                valor.setImprimirPantalla(valor.imprimirArreglo());
                return valor;
                
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=1; i<this.elementosArreglo.size(); i=i+2)
                {
                    valor.addElemento(elementosArreglo.get(i));
                }
                valor.setIsArreglo(true);
                valor.setDimensiones(1);
                valor.setImprimirPantalla(valor.imprimirArreglo());
                return valor;
            }
        }
        return null;
    }
    
    boolean esPar(double num)
    {
        if(num%2==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    boolean esImpar(double num)
    {
        if(num%2==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    Value sumArreglo()
    {
        Value valor = new Value();
        double sum=0;
        if(this.dimensiones>1)
        {
            List<Value> elementos = new ArrayList();
            for(int k=0; k<dimensiones; k++)
            {
                Value val = this.elementosArreglo.get(k).sumArreglo();
                elementos.add(val);
            }
            for(int i=0; i<elementos.size();i++)
            {
                if(i==0)
                {
                    sum=(Double)elementos.get(i).getVal();
                }
                else
                {
                    sum=sum+(Double)elementos.get(i).getVal();
                }
            }
            valor.setVal(sum);
            valor.setTipo("numero");
            valor.setImprimirPantalla(String.valueOf(sum));
            return valor;
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size();i++)
                {
                    if(i==0)
                    {
                        sum=(Double)elementosArreglo.get(i).getVal();
                    }
                    else
                    {
                        sum=sum+(Double)elementosArreglo.get(i).getVal();
                    }
                }
                valor.setVal(sum);
                valor.setTipo("numero");
                valor.setImprimirPantalla(String.valueOf(sum));
                return valor;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i++)
                {
                    if(i==0)
                    {
                        sum=(Character)elementosArreglo.get(i).getVal()*1;
                    }
                    else
                    {
                        sum+=(Character)elementosArreglo.get(i).getVal()*1;
                    }
                }
                valor.setVal(sum);
                valor.setTipo("numero");
                valor.setImprimirPantalla(String.valueOf(sum));
                return valor;
            }
        }
        return null;
    }
    
    Value productArreglo()
    {
        Value valor = new Value();
        double product=1;
        if(this.dimensiones>1)
        {
            List<Value> elementos = new ArrayList();
            for(int k=0; k<dimensiones; k++)
            {
                Value val = this.elementosArreglo.get(k).productArreglo();
                elementos.add(val);
            }
            for(int i=0; i<elementos.size();i++)
            {
                if(i==0)
                {
                    product=(Double)elementos.get(i).getVal();
                }
                else
                {
                    product=product*(Double)elementos.get(i).getVal();
                }
            }
            valor.setTipo("numero");
            valor.setVal(product);
            valor.setImprimirPantalla(String.valueOf(product));
            return valor;
        }
        else {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size();i++)
                {
                    if(i==0)
                    {
                        product=(Double)elementosArreglo.get(i).getVal();
                    }
                    else
                    {
                        product=product*(Double)elementosArreglo.get(i).getVal();
                    }
                }
                valor.setTipo("numero");
                valor.setVal(product);
                valor.setImprimirPantalla(String.valueOf(product));
                return valor;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size();i++)
                {
                    if(i==0)
                    {
                        product=(Character)elementosArreglo.get(i).getVal()*1;
                    }
                    else
                    {
                        product=product*((Character)elementosArreglo.get(i).getVal()*1);
                    }
                }
                valor.setTipo("numero");
                valor.setVal(product);
                valor.setImprimirPantalla(String.valueOf(product));
                return valor;
            }
        }
        return null;
    }
    
    public int reversArreglo()
    {
        Value temp;
        if(this.dimensiones>1)
        {
            for(int k=0; k<dimensiones; k++)
            {
                this.elementosArreglo.get(k).reversArreglo();
            }
            this.setImprimirPantalla(this.imprimirArreglo());
            return 1;
        }
        else
        {
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        temp=elementosArreglo.get(i);
                        elementosArreglo.set(i,elementosArreglo.get(j));
                        elementosArreglo.set(j, temp);
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size()-1; i++)
                {
                    for(int j=i+1; j<this.elementosArreglo.size(); j++)
                    {
                        temp=elementosArreglo.get(i);
                        elementosArreglo.set(i,elementosArreglo.get(j));
                        elementosArreglo.set(j, temp);
                    }
                }
                this.setImprimirPantalla(this.imprimirArreglo());
                return 1;
            }
        }
        return 0;
    }
    
    public Value lengthArreglo()
    {
        Double size=Double.valueOf(String.valueOf(this.elementosArreglo.size()));
        Value val = new Value("numero", size);
        val.setImprimirPantalla(String.valueOf(size));
        return val;
    }
    
    public Value buscarIndice(List<Value> valores)
    {
        
        if(this.dimensiones>1)
        {
            Value valor;
            Value oval;
            double algo = (double) valores.get(0).getVal();
            int pos = (int) algo;
            valor = this.elementosArreglo.get(pos);
            if(valor==null)
            {
                return null;
            }
            if(valor.isIsArreglo() && valores.size()==1)
            {
                valor.setImprimirPantalla(valor.imprimirArreglo());
                return valor;
            }
            algo=(double)valores.get(1).getVal();
            pos=(int)algo;
            oval = valor.elementosArreglo.get(pos);
            if(oval==null)
            {
                return null;
            }
            oval.setImprimirPantalla(String.valueOf(oval.getVal()));
            return oval;
        }
        else
        {
            if(this.dimensiones==valores.size())
            {
                Value valor;
                double algo = (double)valores.get(0).getVal();
                int pos = (int) algo;
                valor = this.elementosArreglo.get(pos);
                if(valor==null)
                {
                    return null;
                }
                valor.setImprimirPantalla(String.valueOf(valor.getVal()));
                return valor;
            }
        }
        return null;
    }
    
    public Value maxArreglo()
    {
        double num=0;
        Value max=new Value();
        if(dimensiones>1)
        {
            List<Value> elementos = new ArrayList();
            for(int k=0; k<dimensiones; k++)
            {
                Value val = this.elementosArreglo.get(k).maxArreglo();
                elementos.add(val);
            }
            for(int i=0; i<elementos.size(); i++)
            {
                if((Double)elementos.get(i).getVal() > num)
                {
                    num=(Double)elementos.get(i).getVal();
                }
            }
            max.setTipo("numero");
            max.setVal(num);
            max.setImprimirPantalla(String.valueOf(num));
            return max;
        }
        else{
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i++)
                {
                    if((Double)elementosArreglo.get(i).getVal() > num)
                    {
                        num=(Double)elementosArreglo.get(i).getVal();
                    }
                }
                max.setTipo("numero");
                max.setVal(num);
                max.setImprimirPantalla(String.valueOf(num));
                return max;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i++)
                {
                    int actual = (Character)elementosArreglo.get(i).getVal() *1;
                    if(actual > num)
                    {
                        num=actual;
                    }
                }
                max.setTipo("numero");
                max.setVal(num);
                max.setImprimirPantalla(String.valueOf(num));
                return max;
            }
        }
        return null;
    }
    
    public Value minArreglo()
    {
        double num=Double.MAX_VALUE;
        Value min=new Value();
        if(this.dimensiones>1)
        {
            List<Value> elementos = new ArrayList();
            for(int k=0; k<dimensiones; k++)
            {
                Value val = this.elementosArreglo.get(k).minArreglo();
                elementos.add(val);
            }
            for(int i=0; i<elementos.size(); i++)
                {
                if((Double)elementos.get(i).getVal() < num)
                {
                        num=(Double)elementos.get(i).getVal();
                }
            }
            min.setTipo("numero");
            min.setVal(num);
            min.setImprimirPantalla(String.valueOf(num));
            return min;
        }
        else
        {
        
            if(this.tipo.equals("numero"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i++)
                {
                    if((Double)elementosArreglo.get(i).getVal() < num)
                    {
                        num=(Double)elementosArreglo.get(i).getVal();
                    }
                }
                min.setTipo("numero");
                min.setVal(num);
                min.setImprimirPantalla(String.valueOf(num));
                return min;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                for(int i=0; i<this.elementosArreglo.size(); i++)
                {
                    int actual = (Character)elementosArreglo.get(i).getVal() *1;
                    if(actual < num)
                    {
                        num=actual;
                    }
                }
                min.setTipo("numero");
                min.setVal(num);
                min.setImprimirPantalla(String.valueOf(num));
                return min;
            }
        }
        return null;
    }
    
    String imprimirArreglo()
    {
        if(this.dimensiones>1)
        {
            if(this.tipo.equals("numero")){
                String cadena="[";
                for(int i=0; i<elementosArreglo.size(); i++)
                {
                    cadena+="[";
                    for(int k=0; k<elementosArreglo.get(i).getElementosArreglo().size(); k++)
                    {
                        if(k==0)
                        {
                            cadena+=elementosArreglo.get(i).getElementosArreglo().get(k).getVal().toString();
                        }
                        else
                        {
                            cadena+=","+elementosArreglo.get(i).getElementosArreglo().get(k).getVal().toString();
                        }
                    }
                    cadena+="]";
                }
                cadena+="]";
                return cadena;
            }
            else if(this.tipo.equals("cadena")|| this.tipo.equals("caracter"))
            {
                    String cadena="[";
                for(int i=0; i<elementosArreglo.size(); i++)
                {
                    cadena+="[";
                    for(int k=0; k<elementosArreglo.get(i).getElementosArreglo().size(); k++)
                    {
                        if(k==0)
                        {
                            cadena+="\'"+elementosArreglo.get(i).getElementosArreglo().get(k).getVal().toString()+"\'";
                        }
                        else
                        {
                            cadena+=",\'"+elementosArreglo.get(i).getElementosArreglo().get(k).getVal().toString()+"\'";
                        }
                    }
                    cadena+="]";
                }
                cadena+="]";
                return cadena;
            }
        }
        else{
            if(this.tipo.equals("numero"))
            {
                String cadena="[";
                for(int i=0; i<elementosArreglo.size(); i++)
                {
                    if(i==0)
                    {
                        cadena+=elementosArreglo.get(i).getVal().toString();
                    }
                    else
                    {
                        cadena+=", "+elementosArreglo.get(i).getVal().toString();
                    }
                }
                cadena+="]";
                return cadena;
            }
            else if(this.tipo.equals("cadena") || this.tipo.equals("caracter"))
            {
                String cadena="[";
                for(int i=0; i<elementosArreglo.size(); i++)
                {
                    if(i==0)
                    {
                        cadena+="\'"+elementosArreglo.get(i).getVal().toString()+"\'";
                    }
                    else
                    {
                        cadena+=", \'"+elementosArreglo.get(i).getVal().toString()+"\'";
                    }
                }
                cadena+="]";
                return cadena;
            }
        }
        return "";
    }
}
