package model.logic;

import model.data_structures.*;


import java.util.List;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private LinearProbingHashST<String,ArregloDinamico> lp;
    private SeparateChainingHashST<String,ArregloDinamico> sc;
    private Features[] comparableF;
    private int tamanio;
    private Features mayorObj;
    private Double[] MinMax;

    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorObj = null;
    }

    public Modelo(List<Features> listaFeatures, int lp, int sc,Primos primos) {
        cargarComparendos(listaFeatures,lp,sc,primos);
        objConMayorOBJID();
        MiniMax();
    }

    public void cargarComparendos(List<Features> listaFeatures,int lp,int sc,Primos primos) {
        try {
            long startTime = System.nanoTime();
            comparableF = new Features[listaFeatures.size()];
            for (int i = 0; i<listaFeatures.size();i++){
                comparableF[i] = listaFeatures.get(i);
            }
            Quick.sort(comparableF,"Key");
            this.lp = new LinearProbingHashST<>(lp,primos);
            this.sc = new SeparateChainingHashST<>(sc,primos);
            Keys(comparableF,this.lp,this.sc);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            System.out.println("Linear Probing hash table size: \n\tM: " + this.lp.sizeM() + "\n\tN: "+this.lp.sizeN());
            System.out.println("Separate chaining hash table size: \n\tM: " + this.sc.sizeM() + "\n\tN: "+this.sc.sizeN());
            System.out.println(
                    "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Keys(Features[] list,LinearProbingHashST lp, SeparateChainingHashST sc){
        Llave dataPair = new Llave();
        int l=0;
        for(int j=0;j<list.length;j++){
            int comp = 0;
            if (!(j == list.length-1)) comp = list[j].compareKey(list[j+1]);
            if (comp != 0) {
                String key = dataPair.key(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                   valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values,"normal");
                lp.put(key,values);
                sc.put(key,values);
                dataPair.getArr().clear();
            }
            if (j == list.length-1){
                String key = dataPair.key(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values,"normal");
                lp.put(key,values);
                sc.put(key,values);
            }

        }

    }
    public void objConMayorOBJID() {
    }

    public void MiniMax() {
    }

    public Features getMayorOBJ() {
        return mayorObj;
    }

    public Double[] getMinMax() {
        return MinMax;
    }

    public int getTamanio() {
        return tamanio;
    }

}

