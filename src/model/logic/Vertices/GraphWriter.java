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
    public void crearJson(LinearProbingHashST Graph)
    {
        Queue<Integer> keys = Graph.keys();
        JSONObject obj = new JSONObject();
        obj.put("name", "Weighted Graph");
        obj.put("type", "Vertex Collection");
        JSONArray vertices = new JSONArray();
        while (!keys.isEmpty())
        {
            Vertex<Integer,WeightedEdge<Integer>> v = (Vertex<Integer, WeightedEdge<Integer>>) Graph.get(keys.dequeue());
            JSONObject verticeActual = new JSONObject();
            verticeActual.put("type","vertex");
            verticeActual.put("Id", v.getId());
            verticeActual.put("LONG", v.getLongi());
            verticeActual.put("LAT", v.getLat());

            JSONArray arcos = new JSONArray();
            Bag<WeightedEdge<Integer>> adj = v.getAdj();
            for (WeightedEdge edge : adj)
            {
                JSONObject arcoActual = new JSONObject();
                arcoActual.put("either", edge.either());
                arcoActual.put("other", edge.other(edge.either()));
                arcoActual.put("weight", edge.getWeight());
                arcos.add(arcoActual);
            }
            verticeActual.put("ADJ_WED", arcos);

            vertices.add(verticeActual);
        }
        obj.put("Vertices", vertices);
        try
        {
            FileWriter filesalida = new FileWriter("./data/grafo_persistido.json");
            filesalida.write(obj.toJSONString());
            filesalida.flush();
            filesalida.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
