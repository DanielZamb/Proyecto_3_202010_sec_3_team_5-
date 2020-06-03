package model.data_structures;
    /*
    * marked has two values [1,0], 1 represents "visited" and 0 represents "hasn't been visited yet"
    */
public class WeightedEdge<K> implements Comparable<WeightedEdge> {
    private final K v;
    private final K w;
    private double weight;
    private double weight2;
    public WeightedEdge(K v, K w, double weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
        weight2 = 0.0;
    }
    public double getWeight(){
        return weight;
    }
    public void setWeight(double cost){
        weight = cost;
    }
    public void setWeight2(double cost) {
        weight2 = cost;
    }
    public double getWeight2(){
        return weight2;
    }
    public K either() {
        return v;
    }
    public K other(K vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Arco inconsistente");
    }
    public int compareTo(WeightedEdge that){
        if (this.weight > that.weight) return 1;
        else if (this.weight<that.weight) return -1;
        else return 0;
    }
    public String toString(){
        return w.toString()+"-"+v.toString()+"\n cost: " +weight;
    }
}
