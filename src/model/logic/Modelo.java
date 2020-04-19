package model.logic;

import model.data_structures.*;


import java.util.Iterator;
import java.util.List;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private RedBlackBST<Integer,Features> rbt;
    private LinearProbingHashST<String,Features[]> lp;
    private SeparateChainingHashST<String,Features[]> sc;
    private Features[] comparableF;
    private int tamanio;
    private Integer mayorObj;
    private Integer minObj;
    private Double[] MinMax;

    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorObj = null;
    }

    /*public Modelo(List<Features> listaFeatures, int lp, int sc,Primos primos) {
        cargarComparendos(listaFeatures,lp,sc,primos);
    }
*/
    public Modelo(List<Features> listaFeatures) {
        cargarComparendos(listaFeatures);
    }
    public void cargarComparendos(List<Features> listaFeatures) {
        try {
            long startTime = System.nanoTime();
            comparableF = new Features[listaFeatures.size()];
            for (int i = 0; i<listaFeatures.size();i++){
                comparableF[i] = listaFeatures.get(i);
            }
            Quick.sort(comparableF,"OBJECTID");
            this.rbt = new RedBlackBST<>();
            KeysRedBlack(comparableF,this.rbt);
            getMayorOBJ();
            getMinOBJ();
            /*Quick.sort(comparableF,"Key");
            this.lp = new LinearProbingHashST<>(lp,primos);
            this.sc = new SeparateChainingHashST<>(sc,primos);
            Keys(comparableF,this.lp,this.sc);*/
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            System.out.println("Red-Black Binary Search Tree size: \n\t N:" + this.rbt.size());
           // System.out.println("Linear Probing hash table size: \n\tM: " + this.lp.sizeM() + "\n\tN: "+this.lp.sizeN());
           // System.out.println("Separate chaining hash table size: \n\tM: " + this.sc.sizeM() + "\n\tN: "+this.sc.sizeN());
            System.out.println(
                    "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void KeysRedBlack(Features[] list,RedBlackBST rbt){
            Llave dataPair = new Llave();
            for (int i =0 ; i< list.length;i++){
                Integer key = dataPair.keyRB(list[i]);
                Features value = list[i];
                rbt.put(key,value);
            }

    }
    /*public void Keys(Features[] list,LinearProbingHashST lp, SeparateChainingHashST sc){
        Llave dataPair = new Llave();
        int l=0;
        for(int j=0;j<list.length;j++){
            int comp = 0;
            if (!(j == list.length-1)) comp = list[j].compareKey(list[j+1]);
            if (comp != 0) {
                String key = dataPair.keyHash(list[j]);
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
                String key = dataPair.keyHash(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values,"normal");
                lp.put(key,values);
                sc.put(key,values);
            }

        }

    }*/
    /*public Features[] Requerimiento1(String key) {
        Features[] values = lp.get(key);
        return values;
    }

    public Features[] Requerimiento2(String key) {
        Features[] values = sc.get(key);
        return values;
    }

    public double[] Rendimiento() {
        String[] keys = new String[10000];
        double[] performance;
        int k = 0;
        for (int a = 0; a < 100000 && k < 8000; a++) {
            int comp = 0;
            if (!(a == 99999)) comp = comparableF[a].compareKey(comparableF[a + 1]);
            if (comp != 0) {
                Llave dataPair = new Llave();
                String key = dataPair.keyHash(comparableF[a]);
                keys[k] = key;
                k++;
            }
            if (a == 99999) {
                Llave dataPair = new Llave();
                String key = dataPair.keyHash(comparableF[a]);
                keys[k] = key;
                k++;
            }
        }
        for (int i = 0; i < 2000; i++)
            keys[i + k] = RandomString.getAlphaNumericString(20);
        double mayorlp = 0;
        double menorlp = 0;
        double totallp = 0;
        double mayorsc = 0;
        double menorsc = 0;
        double totalsc = 0;
        for (int l = 0; l < keys.length; l++) {
            long startTime = System.nanoTime();
            lp.get(keys[l]);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            long startTimesc = System.nanoTime();
            sc.get(keys[l]);
            long endTimesc = System.nanoTime();
            long elapsedTimesc = endTimesc - startTimesc;
            double convertETsc = (double) elapsedTimesc / 1000000000;
            totallp = totallp + convertET;
            totalsc = totalsc + convertETsc;
            if (convertET > mayorlp) mayorlp = convertET;
            else if (convertET < menorlp) menorlp = convertET;
            if (convertETsc > mayorsc) mayorsc = convertETsc;
            else if (convertETsc < menorsc) menorsc = convertETsc;
        }
        totallp = totallp / 10000;
        totalsc = totalsc / 10000;
        performance = new double[]{mayorlp, menorlp, totallp, mayorsc, menorsc, totalsc};
        return performance;
    }*/
    public Features Req1(Integer key){
        return this.rbt.get(key);
    }
    public ArregloDinamico<Integer> Req2(Integer keyMin,Integer keyMax){
        ArregloDinamico<Integer> keys = new ArregloDinamico<>(20);
        Iterator iter = this.rbt.iteratorInRange(keyMin,keyMax);
        while (iter.hasNext()){
            keys.agregar((Integer)iter.next());
        }
        return keys;
    }

    public Integer getMayorOBJ() {
        mayorObj = (Integer) this.rbt.max();
        return mayorObj;
    }
    public Integer getMinOBJ(){
        minObj = (Integer) this.rbt.min();
        return minObj;
    }
    public Double[] getMinMax() {
        return MinMax;
    }

    public int getTamanio() {
        return tamanio;
    }

}

