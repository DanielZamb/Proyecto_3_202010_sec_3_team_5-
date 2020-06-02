package model.data_structures;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WGraph {

    public String typeGraph = "Graph";
    public String name = "Weighted Undirected Graph";
    public ArrayList<vertex> vertexCollection;
    public vertex vertex;
    public WGraph(){
        vertexCollection = new ArrayList<>();
    }
    public void addVertex(int VID,double lat, double longi,short marked,Bag<WeightedEdge<Integer>> adj){
        vertex = new vertex(VID,lat,longi,marked,adj);
        vertexCollection.add(vertex);
    }
    class vertex {
        public String typeV = "vertex";
        public int VID;
        public properties properties;
        public geometry  geometry;
        public vertex(int VID,double lat, double longi,short marked,Bag<WeightedEdge<Integer>> adj){
            this.VID = VID;
            properties = new properties(marked,adj);
            geometry = new geometry(lat, longi);
        }
        class properties {
            short MARKED;
            ArrayList<WeightedEdge<Integer>> ADJ_VID;
            public properties(short marked, Bag<WeightedEdge<Integer>> adj){
                MARKED = marked;
                ADJ_VID = new ArrayList<>();
                addArray(adj);
            }
            private void addArray(Bag<WeightedEdge<Integer>> adj){
                Iterator iter = adj.iterator();
                while(iter.hasNext()){
                    WeightedEdge<Integer> weightedEdge = (WeightedEdge<Integer>) iter.next();
                    ADJ_VID.add(weightedEdge);
                }
            }
        }
        class  geometry{
            String type;
            ArrayList<Double> coordinates;
            public geometry(double lat, double longi){
                coordinates = new ArrayList<>();
                coordinates.add(lat);
                coordinates.add(longi);
                type = "point";
            }
        }

    }


}
