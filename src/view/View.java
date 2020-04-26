package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("Acceder a la opcion 0 como primera accion al ejecutar el programa");
			System.out.println("0.calcular primos");
			System.out.println("1.Cargar Datos");
			System.out.println("2.Retornar M comparendos con mayor prioridad.");
			System.out.println("3.Retornar M comparendos por mes y dia.");
			System.out.println("4.Retornar M comparendos dentro de un rango de fecha y una localidad.");
			System.out.println("5.Retornar M comparendos con mayor cercania al CAI del campin.");
			System.out.println("6.Retornar M comparendos medio de detección, clase de vehículo, tipo de servicio y localidad.");
			System.out.println("7.Retornar M comparendos dentro de un rango de latitud y un tipo de vehiculo.");
			System.out.println("8.Imprimir tabla ASCII por rango de fecha dado en todo el anio.");

		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
