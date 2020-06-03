package controller;

import model.logic.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.*;

import model.logic.Vertices.GraphParser;
import model.logic.comparendos.Comparendos;
import model.logic.estaciones.EstacionPolicia;
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
					String jsonGrafo = "./data/grafo.geojson";
					String jsonEstaciones = "./data/estacionpolicia.geojson";
					String jsonComparendos = "./data/Comparendos_DEI_2018_BIG.geojson";
					BufferedReader br;
					br = new BufferedReader(new FileReader(jsonGrafo));
					GraphParser G = gson.fromJson(br, GraphParser.class);
					br = new BufferedReader(new FileReader(jsonEstaciones));
					EstacionPolicia e = gson.fromJson(br,EstacionPolicia.class);
					br = new BufferedReader(new FileReader(jsonComparendos));
					Comparendos c = gson.fromJson(br,Comparendos.class);
					view.printMessage("Ingrese tamaño del grafo (# vertices)");
					int grafo = lector.nextInt();
					view.printMessage("Ingrese tamaño de tabla de simbolos de los comparendos");
					int lp = lector.nextInt();
					view.printMessage("Ingrese tamaño de tabla de simbolos de las estaciones");
					int lp2 = lector.nextInt();
					Modelo mdl = new Modelo(G.getFeatures(),e.darListaFeatures(),c.darListaFeatures(),grafo,lp,lp2,primos);
					modelo = mdl;
					view.printMessage("Valor maximo de OBJECT-ID (Comparendos): \n\t"+modelo.getMayorOBJComparendo().toString());
					view.printMessage("Valor maximo de OBJECT-ID (Estaciones): \n\t"+modelo.getMayorEstacion().toString());
					view.printMessage("Valor maximo de OBJECT-ID (Vertice): \n\t"+modelo.getIdMayorVertice().toString());
					view.printMessage("Total de comparendos en el archivo : \n\t"+c.darListaFeatures().size());
					view.printMessage("Total de Estaciones de Policia en el archivo : \n\t"+modelo.getLpEst().sizeN());
					view.printMessage("Total de Vertices en el archivo : \n\t"+modelo.getWG().sizeN());
					view.printMessage("Total de Arcos en el archivo : \n\t"+modelo.getArcos());
					view.printMessage("Rango de coordenadas de los vertices : \n\t"+"["+modelo.getMayorCoorVertex()+"]-["+modelo.getMinCoorVertice()+"]");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				case 2:
					view.printMessage("Ingrese la longitud del punto");
					String Long = lector.next();
					view.printMessage("Ingrese la latitud del punto");
					String Lat = lector.next();
					Double sLat = Double.parseDouble(Lat);
					Double sLong = Double.parseDouble(Long);
					modelo.getNearestVertex(sLong,sLat);
					break;
			}
		}
		
	}	
}
