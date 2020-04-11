package model.logic;

import model.data_structures.*;
import org.jetbrains.annotations.NotNull;


import java.awt.geom.Area;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public Modelo(List<Features> listaFeatures, int lp, int sc) {
        cargarComparendos(listaFeatures,lp,sc);
        objConMayorOBJID();
        MiniMax();
    }

    public void cargarComparendos(List<Features> listaFeatures,int lp,int sc) {
        try {
            long startTime = System.nanoTime();
            comparableF = new Features[listaFeatures.size()];
            for (int i = 0; i<listaFeatures.size();i++){
                comparableF[i] = listaFeatures.get(i);
            }
            Quick.sort(comparableF,"normal");
            this.lp = new LinearProbingHashST<>(lp);
            this.sc = new SeparateChainingHashST<>(sc);
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
        Features[] aux;
        int k=0;
        for(int j = 0 ; j< list.length ;j++){
            String date = list[j].getProperties().getFECHA_HORA();
            Boolean comp = false;
            if (!(j == list.length - 1)) comp = list[j].equalsK(list[j + 1]);
            if (!comp) {
                aux = new Features[j+1];
                for(;k<=j;k++){
                    aux[k] = list[k];
                }
                Quick.sort(aux,"vehiculo");
                
            }
            if (comp) {

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

