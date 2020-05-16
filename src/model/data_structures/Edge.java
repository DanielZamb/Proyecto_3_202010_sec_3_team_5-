package model.data_structures;

public class Edge<K extends Comparable<K>> implements Comparable<Edge> {
    private final K v;
    private final K w;
    private final double weight;
    public Edge(K v,K w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public double getWeight(){
        return weight;
    }
    public K either() {
        return v;
    }

    public K other(K vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Arco inconsistente");
    }
    public int compareTo(Edge that){
        if (this.weight > that.weight) return 1;
        else if (this.weight<that.weight) return -1;
        else return 0;
    }
    public String toString(){
        return "this edge is incident to: "+ w.toString()+","+v.toString()+"\n and its weight: " +weight;
    }
}
