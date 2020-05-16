package model.data_structures;

import model.logic.Primos;

import javax.xml.stream.events.EntityDeclaration;
import java.util.Iterator;

public class Graph<K extends Comparable<K>,V> {
    public int V;
    public int E;
    private LinearProbingHashST<K,Bag<Edge<K>>> G;
    private SeparateChainingHashST<K,V> vertexDB;
    public Graph(int V,Primos primos){
        this.V = V;
        this.E = 0;
        G = new LinearProbingHashST<>(V,primos);
        vertexDB = new SeparateChainingHashST<>(V,primos);
    }
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    public void addVertex(K vertex,V infoVertex){
        vertexDB.put(vertex,infoVertex);
        G.put(vertex,new Bag<>());
        V++;
    }
    public V getInfoVertex(K vertex){
        V value = vertexDB.get(vertex);
        boolean r = value != null;
        if (r) return value;
        else return null;
    }
    public void setInfoVertex(K vertex,V infoVertex){
        vertexDB.put(vertex,infoVertex);
    }
    //all the vertices are already loaded in the data structure.
    public void addEdge(K initVertex, K finVertex,double cost){
        Edge<K> edgeInit = new Edge<>(initVertex,finVertex,cost);
        Edge<K> edgeFin = new Edge<>(finVertex,initVertex,cost);
        Bag<Edge<K>> updateInit = G.get(initVertex);
        Bag<Edge<K>> updateFin = G.get(finVertex);
        updateInit.add(edgeInit);
        updateFin.add(edgeFin);
        G.put(initVertex,updateInit);
        G.put(finVertex,updateFin);
        E++;
    }
    public double getCostEdge(K initVertex, K finVertex){
        double cost = 0.0;
        boolean cent = false;
        Bag<Edge<K>> edges = G.get(initVertex);
        Iterator iter = edges.iterator();
        while(iter.hasNext()&& !cent){
            Edge<K> currentEdge =(Edge<K>) iter.next();
            K v = currentEdge.either();
            K w = currentEdge.other(v);
            if (v == initVertex && w == finVertex){
                cost = currentEdge.getWeight();
                cent = true;
            }
        }
        return cost;
    }
    public void setCostEdge(K initVertex, K finVertex,double cost){
        Bag<Edge<K>> edges = G.get(initVertex);
        boolean cent = false;
        Iterator iter = edges.iterator();
        while(iter.hasNext()&& !cent){
            Edge<K> currentEdge =(Edge<K>) iter.next();
            K v = currentEdge.either();
            K w = currentEdge.other(v);
            if (v == initVertex && w == finVertex){
                edges.put(currentEdge,new Edge<>(initVertex,finVertex,cost));
                cent = true;
            }
        }
    }
    public Iterable<K> adj(K sourceVertex){
    }




}
