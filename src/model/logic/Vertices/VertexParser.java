package model.logic.Vertices;
import model.data_structures.*;
import model.logic.Primos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VertexParser {
    Scanner input1;
    Scanner input2;
    public VertexParser(String file1, String file2) throws FileNotFoundException {
        input1 = new Scanner(new File(file1));
        input1.useDelimiter("[,\n]");
        input2 = new Scanner(new File(file2));
        input2.useDelimiter("[ \n]");
    }
    public LinearProbingHashST<Integer, Vertex<Integer,Integer>> ReadVertexInfo(int V, Primos primos){
        LinearProbingHashST<Integer,Vertex<Integer,Integer>> G = new LinearProbingHashST<>(V,primos);
        while (input1.hasNext()){
            int id = input1.nextInt();
            double lat = Double.parseDouble(input1.next());;
            double longi = Double.parseDouble(input1.next());
            String line = input2.nextLine();
            String[] readThis = line.split(" ");
            Vertex<Integer,Integer> v = new Vertex(id,lat,longi);
            for (int i = 1; i < readThis.length; i++) {
                int w = Integer.parseInt(readThis[i]);
                v.addAdj(w);
            }
            G.put(id , v);
        }
    return G;
    }
}
