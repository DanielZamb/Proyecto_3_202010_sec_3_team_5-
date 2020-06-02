package model.logic;

import me.tongfei.progressbar.*;
import model.data_structures.*;
import model.logic.Vertices.GraphWriter;
import model.logic.Vertices.VertexParser;

import java.util.Iterator;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private LinearProbingHashST<Integer,Vertex<Integer,Integer>> NWG = null;
    private LinearProbingHashST<Integer,Vertex<Integer,WeightedEdge<Integer>>> WG;
    private int tamanio;
    private String mayorObj;
    private String minObj;
    private Double[] MinMax;

    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorObj = null;
    }

    public Modelo(String file1,String file2, int lp,Primos primos) {
        cargarComparendosTxt(file1,file2,lp,primos);
    }
    public void cargarComparendosTxt(String file1,String file2, int lp, Primos primos) {
        try (ProgressBar pb = new ProgressBar("Loading Data", 100,ProgressBarStyle.ASCII)) { // name, initial max
            try {
                long startTime = System.nanoTime();
                VertexParser vp = new VertexParser(file1,file2);
                this.NWG = vp.ReadVertexInfo(lp,primos);
                this.WG = new LinearProbingHashST<>(lp,primos);
           /* getMayorOBJ();
            getMinOBJ();*/
                pb.stepTo(100);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                double convertET = (double) elapsedTime / 1000000000;
                System.out.println("Linear Probing hash table size: \n\tM: " + this.NWG.sizeM() + "\n\tN: "+this.NWG.sizeN());
                System.out.println(
                        "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Use ProgressBar("Test", 100, ProgressBarStyle.ASCII) if you want ASCII output styl
        }
    }
    public LinearProbingHashST<Integer, Vertex<Integer,WeightedEdge<Integer>>> WeightedGraph() {
        Queue<Integer> keys = this.NWG.keys();
        try (ProgressBar pb = new ProgressBar("Updating to weighted graph", 228045,ProgressBarStyle.ASCII)) {
            while (!keys.isEmpty()){
                pb.step();
                Vertex<Integer,Integer> v = NWG.get(keys.dequeue());
                Vertex<Integer,WeightedEdge<Integer>> r = new Vertex<>(v.getId(),v.getLat(),v.getLongi());
                Iterator iter = v.getAdj().iterator();
                while (iter.hasNext()){
                    Vertex<Integer,Integer> w = NWG.get((Integer) iter.next());
                    double weight = AvDistance.distance(v.getLat(),v.getLongi(),w.getLat(),w.getLongi());
                    WeightedEdge<Integer> weightedEdge = new WeightedEdge<>(v.getId(),w.getId(),weight);
                    r.addAdj(weightedEdge);
                    }
                WG.put(r.getId(),r);
            }
        }
        NWG = null;
        return WG;
    }
    public void writeGraph(){
        GraphWriter.writeGraph(WG);
    }
    /*public String getMayorOBJ() {
        mayorObj = this.rbt1.max();
        return mayorObj;
    }
    public String getMinOBJ(){
        minObj = this.rbt1.min();
        return minObj;
    }
    public Double[] getMinMax() {
        return MinMax;
    }

    public int getTamanio() {
        return tamanio;
    }*/

}

