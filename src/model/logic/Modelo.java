package model.logic;

import me.tongfei.progressbar.*;
import model.data_structures.*;
import model.logic.Vertices.VertexParserJSON;
import model.logic.comparendos.Features;
import model.logic.estaciones.FeaturesEstaciones;

import java.util.Comparator;
import java.util.List;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private LinearProbingHashST<Integer,Vertex<Integer,WeightedEdge<Integer>>> WG;
    private LinearProbingHashST<String,Features> lpComp;
    private LinearProbingHashST<String,FeaturesEstaciones> lpEst;
    private RedBlackBST<String,Integer> rbt1;
    private String[] comparableTree;
    private Features[] comparableF;
    private FeaturesEstaciones[] comparableE;
    private int tamanio;
    private String mayorCoorVertices;
    private Vertex<Integer,WeightedEdge<Integer>> idMayorVertice;
    private Features mayorComparendo;
    private FeaturesEstaciones mayorEstacion;
    private String minObjVertices;
    private Double[] MinMax;

    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorCoorVertices = null;
    }
    public Modelo(List<VertexParserJSON> listaVertices, List<FeaturesEstaciones> listaEstaciones, List<Features> listaComparendos, int grafo,int tComp,int tEst, Primos primos) {
        cargarComparendos(listaVertices,listaEstaciones,listaComparendos,grafo,tComp,tEst,primos);
    }
    public void cargarComparendos(List<VertexParserJSON> listaVertices,List<FeaturesEstaciones> listaEstaciones, List<Features> listaComparendos,int grafo,int tComp, int tEst,Primos primos) {
        try {
            long startTime = System.nanoTime();
            GRAPH(grafo,primos,listaVertices);
            RBTs(listaVertices);
            HASH(tComp,tEst,primos,listaEstaciones,listaComparendos);
            getMayorCoorVertex();
            getMinOBJ();
            /*Quick.sort(comparableF,"Key");
            this.lp = new LinearProbingHashST<>(lp,primos);
            this.sc = new SeparateChainingHashST<>(sc,primos);
            Keys(comparableF,this.lp,this.sc);*/
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            System.out.println("Red-Black Binary Search Tree size: \n\t N:" + this.rbt1.size());
            System.out.println("Linear Probing hash table size: \n\tM: " + this.WG.sizeM() + "\n\tNumero de vertices: "+this.WG.sizeN());
            System.out.println("Linear Probing hash table size: \n\tM: " + this.lpComp.sizeM() + "\n\tN: "+this.lpComp.sizeN());
            // System.out.println("Separate chaining hash table size: \n\tM: " + this.sc.sizeM() + "\n\tN: "+this.sc.sizeN());
            System.out.println(
                    "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GRAPH(int grafo, Primos primos, List<VertexParserJSON> listaVertices){
        WG = new LinearProbingHashST<>(grafo,primos);
        listaVertices.forEach(vertex -> {
            Vertex<Integer,WeightedEdge<Integer>> v = new Vertex<>(vertex.getIDINICIAL(),vertex.getLATITUD(),vertex.getLONGITUD());
            vertex.getARCOS().forEach(edge ->{
                WeightedEdge<Integer> weightedEdge = new WeightedEdge<>(v.getId(),edge.getIDFINAL(),edge.getCOSTO());
                v.addAdj(weightedEdge);
            });

            WG.put(v.getId(),v);
        });
    }
    public void RBTs(List<VertexParserJSON> listaVertices){
        comparableTree = new String[listaVertices.size()];
        for (int i=0;i<listaVertices.size();i++){
            String s = listaVertices.get(i).getLATITUD()+","+listaVertices.get(i).getLONGITUD()+","+listaVertices.get(i).getIDINICIAL();
            comparableTree[i] = s;
        }
        this.rbt1 = new RedBlackBST<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String c1 = (String) o1;
                String c2 = (String) o2;
                String[] temp1 = c1.split(",");
                String[] temp2 = c2.split(",");
                double lat1 = Double.parseDouble(temp1[0]);
                double longi1 = Double.parseDouble(temp1[1]);
                double lat2 = Double.parseDouble(temp2[0]);
                double longi2 = Double.parseDouble(temp2[1]);
                int  c=0;
                if (lat1>lat2) c=1;
                else if(lat1<lat2) c=-1;
                else if (lat1==lat2){
                    if (longi1>longi2) c = 1;
                    else if (longi1<longi2) c=-1;
                }
                return c;
            }
        });
        KeysRedBlack(comparableTree);
    }
    public void KeysRedBlack(Comparable[] list){
        try (ProgressBar pb = new ProgressBar("Loading Data", 228046,ProgressBarStyle.ASCII)){
            for (int i =0 ; i< list.length;i++){
                pb.step();
                String s = (String) list[i];
                String temp[] = s.split(",");
                String key = temp[0]+","+temp[1];
                Integer value = Integer.valueOf(temp[2]);
                this.rbt1.put(key,value);
            }
        }
    }
    public void HASH(int tComp,int tEst, Primos primos, List<FeaturesEstaciones> listaEstaciones , List<Features> listaComparendos){
        comparableF = new Features[listaComparendos.size()];
        for(int i=0;i<listaComparendos.size();i++){
            comparableF[i] = listaComparendos.get(i);
        }
        comparableE = new FeaturesEstaciones[listaEstaciones.size()];
        for (int j=0;j<listaEstaciones.size();j++){
            comparableE[j] = listaEstaciones.get(j);
        }
        Comparator objComp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Features f1 = (Features) o1;
                Features f2 = (Features) o2;
                return f1.getProperties().getOBJECTID().compareTo(f2.getProperties().getOBJECTID());
            }
        };
        Comparator objEst = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                FeaturesEstaciones f1 = (FeaturesEstaciones) o1;
                FeaturesEstaciones f2 = (FeaturesEstaciones) o2;
                return f1.getProperties().getOBJECTID().compareTo(f2.getProperties().getOBJECTID());
            }
        };
        Quick.sort(comparableF,objComp);
        mayorComparendo = comparableF[comparableF.length-1];
        Quick.sort(comparableE,objEst);
        mayorEstacion = comparableE[comparableE.length-1];
        this.lpComp = new LinearProbingHashST<>(tComp,primos);
        this.lpEst = new LinearProbingHashST<>(tEst,primos);
        KeysLp(comparableF,comparableE);
    }
    public void KeysLp(Features[] listComp,FeaturesEstaciones[] listEst){
        for(int i=0;i<listComp.length;i++){
            this.lpComp.put(listComp[i].getGeometry().DarCoordenadas().get(0)+","+listComp[i].getGeometry().DarCoordenadas().get(1),listComp[i]);
        }
        for(int i=0;i<listEst.length;i++){
            this.lpEst.put(listEst[i].getGeometry().DarCoordenadas().get(0)+","+listEst[i].getGeometry().DarCoordenadas().get(1),listEst[i]);
        }
        /*int l=0;
        for(int j=0;j<list.length;j++){
            int comp = 0;
            if (!(j == list.length-1)) comp = c.compare(list[j],list[j+1]);
            if (comp != 0) {
                String key = dataPair.keyReq2A(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lp.put(key,values);
                dataPair.getArr().clear();
            }
            if (j == list.length-1){
                String key = dataPair.keyReq2A(list[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(list[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lp.put(key,values);
                dataPair.getArr().clear();
            }
        }*/
    }
    public Integer getNearestVertex(double sLat, double sLong){
        return 0;
    }
    public String getMayorCoorVertex() {
        mayorCoorVertices = this.rbt1.max();
        return mayorCoorVertices;
    }
    public Features getMayorOBJComparendo(){
        return mayorComparendo;
    }
    public FeaturesEstaciones getMayorEstacion(){
        return mayorEstacion;
    }
    public Vertex<Integer, WeightedEdge<Integer>> getIdMayorVertice() {
        idMayorVertice = WG.get(228045);
        return idMayorVertice;
    }
    public String getMinOBJ(){
        minObjVertices = this.rbt1.min();
        return minObjVertices;
    }
    public LinearProbingHashST<Integer, Vertex<Integer, WeightedEdge<Integer>>> getWG() {
        return WG;
    }

    public void setWG(LinearProbingHashST<Integer, Vertex<Integer, WeightedEdge<Integer>>> WG) {
        this.WG = WG;
    }

    public LinearProbingHashST<String, Features> getLpComp() {
        return lpComp;
    }

    public void setLpComp(LinearProbingHashST<String, Features> lpComp) {
        this.lpComp = lpComp;
    }

    public LinearProbingHashST<String, FeaturesEstaciones> getLpEst() {
        return lpEst;
    }

    public void setLpEst(LinearProbingHashST<String, FeaturesEstaciones> lpEst) {
        this.lpEst = lpEst;
    }

    public RedBlackBST<String, Integer> getRbt1() {
        return rbt1;
    }

    public void setRbt1(RedBlackBST<String, Integer> rbt1) {
        this.rbt1 = rbt1;
    }
}

