package model.logic;

import me.tongfei.progressbar.*;
import model.data_structures.*;
import model.logic.Vertices.VertexParserJSON;
import model.logic.comparendos.Features;
import model.logic.estaciones.FeaturesEstaciones;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Definicion del modelo del mundo
 */
public class Modelo{
    /**
     * Atributos del modelo del mundo
     */
    private Graph<Integer> G;
    private LinearProbingHashST<Integer,Vertex<Integer,WeightedEdge<Integer>>> WG;
    private LinearProbingHashST<String,Features[]> lpComp;
    private LinearProbingHashST<String,FeaturesEstaciones> lpEst;
    private RedBlackBST<String,Integer> rbt1;
    private String[] comparableTree;
    private Features[] comparableF;
    private FeaturesEstaciones[] comparableE;
    private int tamanio;
    private int Arcos;
    private String mayorCoorVertices;
    private Vertex<Integer,WeightedEdge<Integer>> idMayorVertice;
    private Features mayorComparendo;
    private FeaturesEstaciones mayorEstacion;
    private String minCoorVertices;
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
            G = new Graph<>(Arcos,WG);
            RBTs(listaVertices);
            HASH(tComp,tEst,primos,listaEstaciones,listaComparendos);
            getMayorCoorVertex();
            getMinCoorVertice();
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
                Arcos++;
            });
            WG.put(v.getId(),v);
        });
    }
    public void RBTs(List<VertexParserJSON> listaVertices){
        comparableTree = new String[listaVertices.size()];
        for (int i=0;i<listaVertices.size();i++){
            String s = listaVertices.get(i).getLONGITUD()+","+listaVertices.get(i).getLATITUD()+","+listaVertices.get(i).getIDINICIAL();
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
                double lat1 = f1.getGeometry().DarCoordenadas().get(0);
                double longi1 =f1.getGeometry().DarCoordenadas().get(1);
                double lat2 = f2.getGeometry().DarCoordenadas().get(0);
                double longi2 = f2.getGeometry().DarCoordenadas().get(1);
                int c=0;
                if (lat1>lat2) c=1;
                else if(lat1<lat2) c=-1;
                else if (lat1==lat2){
                    if (longi1>longi2) c = 1;
                    else if (longi1<longi2) c=-1;
                }
                return c;
            }
        };
        Comparator coorComp= new Comparator() {
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
        Quick.sort(comparableF,coorComp);
        Quick.sort(comparableE,objEst);
        mayorEstacion = comparableE[comparableE.length-1];
        this.lpComp = new LinearProbingHashST<>(tComp,primos);
        this.lpEst = new LinearProbingHashST<>(tEst,primos);
        KeysLp(comparableF,comparableE,coorComp);
    }
    public void KeysLp(Features[] listComp,FeaturesEstaciones[] listEst,Comparator c){
        comparableF = null;
        try (ProgressBar pb = new ProgressBar("Loading Data", listComp.length,ProgressBarStyle.ASCII)) {
        }
        Llave dataPair = new Llave();
        int l=0;
        for(int j=0;j<listComp.length;j++){
            int comp = 0;
            if (!(j == listComp.length-1)) comp = c.compare(listComp[j],listComp[j+1]);
            if (comp != 0) {
                String key = dataPair.keyP3C(listComp[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(listComp[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lpComp.put(key,values);
                dataPair.getArr().clear();
            }
            if (j == listComp.length-1){
                String key = dataPair.keyP3C(listComp[j]);
                ArregloDinamico<Features> valuesTemp =  dataPair.getArr();
                for (;l<=j;l++)
                    valuesTemp.agregar(listComp[l]);
                Features[] values = dataPair.values();
                Quick.sort(values, new Comparator<Features>() {
                    @Override
                    public int compare(Features o1, Features o2) {
                        return o1.getProperties().getOBJECTID().compareTo(o2.getProperties().getOBJECTID());
                    }
                });
                lpComp.put(key,values);
                dataPair.getArr().clear();
            }

        }
        for(int i=0;i<listEst.length;i++){
            this.lpEst.put(listEst[i].getGeometry().DarCoordenadas().get(0)+","+listEst[i].getGeometry().DarCoordenadas().get(1),listEst[i]);
        }
        comparableE = null;
    }
    public Integer getNearestVertex(double sLong, double sLat){
        MaxPQ<String> pq = new MaxPQ<>(rbt1.size() + 10, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String temp[] = o1.split(",");
                Double dist1 = Double.parseDouble(temp[1]);
                String temp2[] = o2.split(",");
                Double dist2 = Double.parseDouble(temp2[1]);
                return dist1.compareTo(dist2);
            }
        });
        Iterator iter = rbt1.iteratorInRValue(minCoorVertices,mayorCoorVertices);
        while (iter.hasNext()){
            Vertex<Integer,WeightedEdge<Integer>> v = WG.get((Integer)iter.next());
            double dist = AvDistance.distance(sLat,sLong,v.getLat(),v.getLongi());
            pq.put(v.getId()+","+dist);
        }
        String sVertex[] = pq.delMax().split(",");
        return Integer.parseInt(sVertex[0]);
    }
    public void cargarComparendosGrafo(){
        Queue<String> keysCmp = lpComp.keys();
        while (!keysCmp.isEmpty()){
            String coord[] = keysCmp.dequeue().split(",");
            int vid = getNearestVertex(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));
            Vertex<Integer,WeightedEdge<Integer>> v = WG.get(vid);
            Features[] toLoop = lpComp.get(coord[0]+","+coord[1]);
            for (int i=0;i<toLoop.length;i++){
                v.addCmp(toLoop[i]);
            }
            WG.put(v.getId(),v);
        }
    }
    public void actualizarPesosGrafo(){
        Queue<Integer> keys = WG.keys();
        while(!keys.isEmpty()){
            Integer key = keys.dequeue();
            Vertex<Integer,WeightedEdge<Integer>> v = WG.get(key);
            Iterator iter = v.getAdj().iterator();
            while (iter.hasNext()){
                WeightedEdge<Integer> arc = (WeightedEdge<Integer>) iter.next();
                v.getAdj().delete(arc);
                Integer w = arc.other(v.getId());
                double cost = 0.0;
                Iterator iter2 = WG.get(w).getcBag().iterator();
                while (iter2.hasNext()){
                    cost++;
                }
                arc.setWeight2(cost);
                v.addAdj(arc);
            }
            WG.put(v.getId(),v);
        }
    }
    public void cargarEstacionesGrafo(){
        Queue<String> keysEst = lpEst.keys();
        while (!keysEst.isEmpty()){
            String coord[] = keysEst.dequeue().split(",");
            int vid = getNearestVertex(Double.parseDouble(coord[0]),Double.parseDouble(coord[1]));
            Vertex<Integer,WeightedEdge<Integer>> v = WG.get(vid);
            FeaturesEstaciones toAdd = lpEst.get(coord[0]+","+coord[1]);
            v.getEstBag().add(toAdd);
            WG.put(v.getId(),v);
        }
    }
    public String req1A(Double long1, Double lat1, Double long2, Double lat2)
    {
        Vertex<Integer, WeightedEdge<Integer>> origen = G.getInfoVertex(getNearestVertex(long1, lat1));
        G.shortestPaths(origen, 1);
        Stack<Vertex<Integer, WeightedEdge<Integer>>> stack = new Stack<>();
        Vertex<Integer, WeightedEdge<Integer>> destino = G.getInfoVertex(getNearestVertex(long2, lat2));
        while(destino.arrivalEdge != null)
        {
            stack.push(destino);
            destino = destino.arrivalEdge.destiny.id.equals(destino.id)? destino.arrivalEdge.origin : destino.arrivalEdge.destiny;
        }
        if(stack.isEmpty())
        {
            return "No existe camino entre estas 2 cordenadas";
        }
        else
        {
            StringBuilder rta = new StringBuilder("\nEl camino m�s corto es: [");
            for(Vertex<Integer, WeightedEdge<Integer>> actual : stack) {
                rta.append(actual.getId());
                rta.append("-");
            }
            return rta.toString();
        }
    }
    /*public String req2A(int M)
    {
        MaxHeapCP<Comparendo> masGraves = new MaxHeapCP<Comparendo>(datosComparendos.length);
        for(Comparendo a : datosComparendos)
        {
            a.cambiarCompraTo(1);
            masGraves.agregar(a);
        }
        Graph<Integer, Interseccion> grafito = new Graph<Integer, Interseccion>(M);
        int i = 0;
        while(i < M)
        {
            Comparendo a = masGraves.sacarMax();
            Integer idInter = a.keyVertex;
            if(grafito.getVertex(idInter) == null)
                grafito.addVertex(idInter, grafo.getInfoVertex(idInter));
            i++;
        }
        Iterator<Integer> vertexId = grafito.vertices.iterator();
        while(vertexId.hasNext())
        {
            Integer actualID = vertexId.next();
            Iterator<Integer> otherVertexId = grafito.vertices.iterator();
            while(otherVertexId.hasNext())
            {
                Integer otherID = otherVertexId.next();
                if(grafito.getEdge(actualID, otherID) == null && grafito.getEdge(otherID, actualID) == null)
                {
                    Interseccion interA = grafito.getInfoVertex(actualID);
                    Interseccion interB = grafito.getInfoVertex(otherID);
                    grafito.addEdge(actualID, otherID, haversine(interA.longitud, interA.latitud, interB.longitud, interB.latitud));
                }
            }
        }
        grafito.generateMST(1);
        vertexId = grafito.vertices.iterator();
        Queue<Edge<Integer, Interseccion>> graficar = graficarMST(grafito);
        @SuppressWarnings("unused")
        Maps mapa = new Maps("1B", graficar, true);
        String rta = "El arbol formado fue: \n";
        grafito.generateMST(1);
        return consolaParteB(rta, graficar);
    }*/
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
    public String getMinCoorVertice(){
        minCoorVertices = this.rbt1.min();
        return minCoorVertices;
    }
    public LinearProbingHashST<Integer, Vertex<Integer, WeightedEdge<Integer>>> getWG() {
        return WG;
    }

    public void setWG(LinearProbingHashST<Integer, Vertex<Integer, WeightedEdge<Integer>>> WG) {
        this.WG = WG;
    }

    public LinearProbingHashST<String, Features[]> getLpComp() {
        return lpComp;
    }

    public void setLpComp(LinearProbingHashST<String, Features[]> lpComp) {
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
    public int getArcos(){
        return Arcos;
    }
}

