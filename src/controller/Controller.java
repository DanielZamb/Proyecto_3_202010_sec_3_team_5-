package controller;

import model.data_structures.ArregloDinamico;
import model.data_structures.Graph;
import model.logic.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.*;

import model.logic.Vertices.GraphWriter;
import model.logic.Vertices.VertexParser;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	

	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}
		
	public void run() throws IOException 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";
		Primos primos = new Primos();
		final int CONS = 20;
		while( !fin ){
			view.printMenu();
			int option = lector.nextInt();
			switch(option){
				case 0:
					view.printMessage("Loading...");
					try{
						view.printMessage("introduzca numero maximo de capacidad");
						int num = lector.nextInt();
						primos.darPrimos(num);
					}catch (Exception e){
						e.printStackTrace();
					}
					break;
			case 1:
				view.printMessage("Loading...");
				try {
					Gson gson = new Gson();
					String json = "./data/Comparendos_DEI_2018_BIG.geojson";
					String infoVer = "./data/info vertices.txt";
					String adj = "./data/lista de adj.txt";
					view.printMessage("Ingrese tamaño de tabla de simbolos tipo \'Linear Probing\'");
					int lp = lector.nextInt();
					Modelo mdl = new Modelo(infoVer,adj,lp,primos);
					modelo = mdl;
					/*view.printMessage("Valor maximo de OBJECT-ID: \n\t"+modelo.getMayorOBJ());
					view.printMessage("Valor minimo de OBJECT-ID: \n\t"+modelo.getMinOBJ());*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				case 2:
					GraphWriter.writeGraph(modelo.WeightedGraph());
					view.printMessage("Archivo guardado en /data/weightedGraph.txt!");
					break;
			}
		}
		
	}	
}
