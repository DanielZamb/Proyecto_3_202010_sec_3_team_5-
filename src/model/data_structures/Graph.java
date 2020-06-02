package model.data_structures;

import java.util.Iterator;

public class Graph<K extends Comparable<K>> {
    public int V;
    public int E;
    private LinearProbingHashST<K,Vertex<K,WeightedEdge<K>>> G;
    public Graph(int E, LinearProbingHashST G){
        this.G = G;
        this.V = (int) G.sizeN();
        this.E = E;
    }
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    public void addVertex(K idVertex,Vertex<K, WeightedEdge<K>> infoVertex){
        G.put(idVertex,infoVertex);
        V++;
    }
    public Vertex<K, WeightedEdge<K>> getInfoVertex(K idVertex){
        Vertex<K, WeightedEdge<K>> value = G.get(idVertex);
        boolean r = value != null;
        if (r) return value;
        else return null;
    }
    public void setInfoVertex(K idVertex,Vertex<K, WeightedEdge<K>> infoVertex){
        G.put(idVertex,infoVertex);
    }
    //all the vertices are already loaded in the data structure.
    public void addEdge(K initVertex, K finVertex,double cost){
        WeightedEdge<K> edgeInit = new WeightedEdge<>(initVertex,finVertex,cost);
        WeightedEdge<K> edgeFin = new WeightedEdge<>(finVertex,initVertex,cost);
        Vertex<K, WeightedEdge<K>> updateInit = G.get(initVertex);
        Vertex<K, WeightedEdge<K>> updateFin = G.get(finVertex);
        updateInit.getAdj().add(edgeInit);
        updateFin.getAdj().add(edgeFin);
        G.put(initVertex,updateInit);
        G.put(finVertex,updateFin);
        E++;
    }
    public double getCostEdge(K initVertex, K finVertex){
        double cost = 0.0;
        boolean cent = false;
        Vertex<K, WeightedEdge<K>> v = G.get(initVertex);
        Iterator iter = v.getAdj().iterator();
        while(iter.hasNext()&& !cent){
            WeightedEdge<K> currentEdge = (WeightedEdge<K>) iter.next();
            K vid = currentEdge.either();
            K wid = currentEdge.other(vid);
            if (vid == initVertex && wid == finVertex){
                cost = currentEdge.getWeight();
                cent = true;
            }
        }
        return cost;
    }
    public void setCostEdge(K initVertex, K finVertex,double cost){
        Vertex<K,WeightedEdge<K>> v = G.get(initVertex);
        boolean cent = false;
        Iterator iter = v.getAdj().iterator();
        while(iter.hasNext()&& !cent){
            WeightedEdge<K> currentEdge =(WeightedEdge<K>) iter.next();
            K vid = currentEdge.either();
            K wid = currentEdge.other(vid);
            if (vid == initVertex && wid == finVertex){
                currentEdge.setWeight(cost);
                cent = true;
            }
        }
    }
    public Bag<WeightedEdge<K>> adj(K sourceVertex){ //la bolsa implementa un iterable
        Vertex<K,WeightedEdge<K>> v = G.get(sourceVertex);
        return v.getAdj();
    }
    public void uncheck(){
        Queue<K> keys = G.keys();
        while(!keys.isEmpty()){
            Vertex<K,WeightedEdge<K>> v = G.get(keys.dequeue());
            v.unmark();
            G.put(v.getId(),v);
        }
    }
    public void dfs(K source){
    }




}
