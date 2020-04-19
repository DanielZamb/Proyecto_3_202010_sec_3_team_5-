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
			System.out.println("2.Retornar valores de la llave dada");
			System.out.println("3.Retornar llaves dentro un rango");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
		}
}
