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
			System.out.println("1.Cargar Datos del JSON");
			System.out.println("2.Encontrar vertice mas cercano");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
