package model.data_structures;

import org.jetbrains.annotations.NotNull;

public class Vertex<K extends Comparable<K>,V> implements Comparable<Vertex<K,V>> {
    private K id;
    private double lat;


    private double longi;
    private Bag<V> adj;
    short marked;
    public Vertex(K id, double lat, double longi){
        this.id = id;
        this.lat = lat;
        this.longi = longi;
        adj = new Bag<>();
    }
    public void addAdj(V v){
        adj.add(v);
    }
    public Bag<V> getAdj(){
        return adj;
    }
    /*
     * marks the vertex as visited.
     */
    public void mark(){
        marked = 1;
    }
    /*
     * un-marks the vertex.
     */
    public void unmark(){
        marked = 0;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public short getMarked() {
        return marked;
    }
    public int compareTo(Vertex that){
        return this.id.compareTo((K) that.id);
    }

    @Override
    public String toString() {
        StringBuilder arcs = new StringBuilder("[");
        while(adj.iterator().hasNext()){
            V edge = adj.iterator().next();
            arcs.append(edge.toString()).append(",");
        }
        arcs.append("]");
        return "Vertex{" +
                "id=" + id +
                ", lat=" + lat +
                ", longi=" + longi +
                ", adj=" + arcs +
                ", marked=" + marked +
                '}';
    }

}
