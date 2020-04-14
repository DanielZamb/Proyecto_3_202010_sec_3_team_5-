package controller;

import model.logic.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.*;

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
					String json = "./data/comparendos_dei_2018_BIG.geojson";
					BufferedReader br;
					br = new BufferedReader(new FileReader(json));
					Comparendos comparendos = gson.fromJson(br, Comparendos.class);
					//revisar
					view.printMessage("Ingrese tamaño de tabla de simbolos tipo \'Linear Probing\'");
					int lp = lector.nextInt();
					view.printMessage("Ingrese tamaño de tabla de simbolos tipo \'Separate Chaining\'");
					int sc = lector.nextInt();
					Modelo mdl = new Modelo(comparendos.darListaFeatures(),lp,sc,primos);
					modelo = mdl;
					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    view.printMessage("Datos Cargados.");
				break;
				case 2:
					view.printMessage("Ingrese la fecha de los comparendos a buscar: \nFormato YYYY/MM/DD");
					String date = lector.next();
					view.printMessage("Ingrese el tipo de vehiculo (en mayusculas) a buscar: ");
					String type = lector.next();
					view.printMessage("Ingrese la infraccion a buscar: ");
					String infrac = lector.next();
					Llave key = new Llave();
					String keyS = key.keyA(date,type,infrac);
					Features[] rta = modelo.Requerimiento1(keyS);
					if (rta != null){
						for(int i=0;i<rta.length;i++){
						view.printMessage(rta[i].toString());
						}
					}
					else
						view.printMessage("no se encontraron comparendos con tal llave");
					break;
				case 3:
					view.printMessage("Ingrese la fecha de los comparendos a buscar: \nFormato YYYY/MM/DD");
					String date_ = lector.next();
					view.printMessage("Ingrese el tipo de vehiculo (en mayusculas) a buscar: ");
					String type_ = lector.next();
					view.printMessage("Ingrese la infraccion a buscar: ");
					String infrac_ = lector.next();
					Llave key_ = new Llave();
					String keyS_ = key_.keyA(date_,type_,infrac_);
					Features[] rta_ = modelo.Requerimiento2(keyS_);
					if (rta_ != null){
						for(int i=0;i<rta_.length;i++){
							view.printMessage(rta_[i].toString());
						}
					}
					else
						view.printMessage("no se encontraron comparendos con tal llave");
					break;
				case 4:
					view.printMessage("loading...");
					double[] rta4 = modelo.Rendimiento();
					view.printMessage("||\t\t\t\t\t\t\t|| Linear Probing || Separate chaining ||");
					view.printMessage("||Tiempo minimo de \'get()\'  || "+rta4[1]+"s || "+rta4[4]+"s ||");
					view.printMessage("||Tiempo maximo de \'get()\'  || "+rta4[0]+"s || "+rta4[3]+"s ||");
					view.printMessage("||Tiempo promedio de \'get()\'|| "+rta4[2]+"s || "+rta4[5]+"s ||");
					break;
			}
		}
		
	}	
}
