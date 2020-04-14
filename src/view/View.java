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
			System.out.println("0.calcular primos");
			System.out.println("1.Cargar Datos");
			System.out.println("2.Retornar valores de la llave dada - Linear Probing");
			System.out.println("3.Retornar valores de la llave dada - Separate Chaining");
			System.out.println("4.Pruebas de desempeño");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
