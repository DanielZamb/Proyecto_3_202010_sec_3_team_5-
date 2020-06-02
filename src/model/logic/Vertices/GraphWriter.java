package model.logic.Vertices;

import com.google.gson.Gson;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import model.data_structures.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class GraphWriter {
    private static FileWriter file;
    public static void writeGraph(LinearProbingHashST Graph){
        Gson gson = new Gson();
        Queue<Integer> keys = Graph.keys();
        WGraph toWrite = new WGraph();
        try (ProgressBar pb = new ProgressBar("writing Graph to .JSON", 228045,ProgressBarStyle.ASCII)){
        while (!keys.isEmpty()){
            pb.step();
            Vertex<Integer,WeightedEdge<Integer>> v = (Vertex<Integer, WeightedEdge<Integer>>) Graph.get(keys.dequeue());
            Integer id = v.getId();
            Double lat = v.getLat();
            Double longi = v.getLongi();
            Short marked = v.getMarked();
            Bag<WeightedEdge<Integer>> bag = v.getAdj();
            toWrite.addVertex(id,lat,longi,marked,bag);
            try {
                // Constructs a FileWriter given a file name, using the platform's default charset
                file = new FileWriter(new File("./data/prueba1.txt"));
                file.write(gson.toJson(toWrite));
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                try {
                    file.flush();
                    file.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        }
    }
    private static void CrunchifyLog(String str) {
        System.out.println("str");
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        WGraph test = new WGraph();
        Bag<WeightedEdge<Integer>> adj = new Bag<>();
        WeightedEdge<Integer> weightedEdge = new WeightedEdge<>(100,10,0.564);
        WeightedEdge<Integer> weightedEdge1 = new WeightedEdge<>(100,23,0.345);
        WeightedEdge<Integer> weightedEdge2 = new WeightedEdge<>(100,45,0.1390);
        WeightedEdge<Integer> weightedEdge3 = new WeightedEdge<>(100,67,0.3450);
        adj.add(weightedEdge);
        adj.add(weightedEdge1);
        adj.add(weightedEdge2);
        adj.add(weightedEdge3);
        test.addVertex(100,0.5677,0.976,(short)1, adj);
        try {
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(new File("./data/prueba1.txt"));
            file.write(gson.toJson(test));
            CrunchifyLog("Successfully Copied JSON Object to File...");
            CrunchifyLog("\nJSON Object: " + test);
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
