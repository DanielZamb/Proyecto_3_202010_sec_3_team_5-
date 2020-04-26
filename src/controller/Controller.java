package controller;

import model.data_structures.ArregloDinamico;
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
					String json = "./data/Comparendos_DEI_2018_small.geojson";
					BufferedReader br;
					br = new BufferedReader(new FileReader(json));
					Comparendos comparendos = gson.fromJson(br, Comparendos.class);
					//revisar
					view.printMessage("Ingrese tamaño de tabla de simbolos tipo \'Linear Probing\'");
					int lp = lector.nextInt();
					view.printMessage("Ingrese tamaño de tabla de simbolos tipo \'Separate Chaining\'");
					int sc = lector.nextInt();
					view.printMessage("Ingrese tamaño de la cola de prioridad: ");
					int pq  = lector.nextInt();
					Modelo mdl = new Modelo(comparendos.darListaFeatures(),lp,sc,pq,primos);
					modelo = mdl;
					view.printMessage("Valor maximo de OBJECT-ID: \n\t"+modelo.getMayorOBJ());
					view.printMessage("Valor minimo de OBJECT-ID: \n\t"+modelo.getMinOBJ());
					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    view.printMessage("Datos Cargados.");
				break;
				case 2:
					view.printMessage("Digite el numero de comparendos a buscar por prioridad:");
					int M = lector.nextInt();
					view.printMessage("Loading...");
					Features[] rta1A = modelo.Req1A(M);
					for(int i =0;i<CONS && i<rta1A.length;i++){
						view.printMessage(rta1A[i].toString());
					}
					break;
				case 3:
					view.printMessage("Digite el numero del mes a buscar:");
					int Month = lector.nextInt();
					view.printMessage("Escriba la inicial del dia a buscar:");
					String day = lector.next();
					Features[][] rta2A = modelo.Req2A(Month,day);
					for(int i =0;i<rta2A.length;i++){
						if (rta2A[i] != null){
						for (int j=0;j<5 && j<rta2A[i].length ;j++){
								view.printMessage(rta2A[i][j].toString());
							}
						}
					}
					break;
				case 4:
					view.printMessage("Digite las fechas en el formato YYYY-MM-DD-HH:MM:ss");
					view.printMessage("Digite la fecha de inicio para el rango de la busqueda:");
					String sDate = lector.next();
					view.printMessage("Digite la fecha final para el rango de la busqueda:");
					String eDate = lector.next();
					view.printMessage("Digite la localidad en la cual realizar la busqueda:");
					String local = lector.next();
					ArregloDinamico<Features> rta3A = modelo.Req3A(sDate,eDate,local);
					for(int i =0;i<CONS && i<rta3A.darTamano();i++){ //LOCALIDADES CON ESPACIO, SE PUTEA
						view.printMessage(rta3A.darElemento(i).toString());
					}
					break;
				case 5:
					view.printMessage("Digite el numero de comparendos a buscar por prioridad:");
					int M1B = lector.nextInt();
					view.printMessage("Loading...");
					Features[] rta1B = modelo.Req1B(M1B);
					for(int i =0;i<CONS && i<rta1B.length;i++){
						view.printMessage(rta1B[i].toString());
					}
					break;
				case 6:
					view.printMessage("Digite el medio de deteccion a buscar:");
					String meDet = lector.next();
					view.printMessage("Digite la clase de vehiculo a buscar:");
					String veh = lector.next();
					view.printMessage("Digite el tipo de servicio a buscar:");
					String serv = lector.next();
					view.printMessage("Digite la localidad a buscar:");
					String local_ = lector.next();
					Features[] rta2B = modelo.Req2B(meDet,veh,serv,local_);
					for(int i =0;i<rta2B.length && i<CONS;i++){ //LOCALIDADES CON ESPACIO, SE PUTEA
						if (rta2B[i] != null){
							view.printMessage(rta2B[i].toString());
						}
					}
					break;
				case 7:
					view.printMessage("Digite la latitud de inicio para el rango de la busqueda:");
					String sLat = lector.next();
					view.printMessage("Digite la latitud final para el rango de la busqueda:");
					String eLat = lector.next();
					view.printMessage("Digite el tipo de vehiculo para el cual realizar la busqueda:");
					String veh_ = lector.next();
					ArregloDinamico<Features> rta3B = modelo.Req3B(sLat,eLat,veh_);
					for(int i =0;i<CONS && i<rta3B.darTamano();i++){
						view.printMessage(rta3B.darElemento(i).toString());
					}
					break;
			}
		}
		
	}	
}
