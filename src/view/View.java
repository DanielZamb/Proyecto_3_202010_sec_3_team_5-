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

		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
