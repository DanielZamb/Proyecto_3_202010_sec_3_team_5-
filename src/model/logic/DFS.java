package model.logic;

import model.data_structures.*;

import java.util.Iterator;

public class DFS<K extends Comparable<K>>{
    // Has dfs() been called for this vertex?
    private LinearProbingHashST<K,K> edgeTo;
    private final K s; // source
    public DFS(Graph G, K s, Primos primos)
    {
        edgeTo = new LinearProbingHashST<>(G.V(),primos);
        this.s = s;
        DFS(G, s);
    }
    private void DFS(Graph G, K v)
    {
        Bag<WeightedEdge<K>> adj = G.adj(v);
        Iterator iter = adj.iterator();
        while (iter.hasNext()){
            WeightedEdge<K> edge = (WeightedEdge<K>) iter.next();
            K w = edge.other(v);
            Vertex<K,WeightedEdge<K>> w1 = G.getInfoVertex(w);
            if (w1.getMarked() == 0){
                edgeTo.put(w,v);
                DFS(G,w);
            }
        }
    }
    public boolean hasPathTo(Graph G,K v)
    {
        return G.getInfoVertex(v).getMarked() == 1;
    }
    public Iterable<Integer> pathTo(Graph G,K v)
    {
        if (!hasPathTo(G,v)) return null;
        Stack<Integer> path = new Stack<>();
        for (K x = v; x != s; x = edgeTo.get(x))
            path.push(x);
        path.push(s);
        return path;
    }
}
