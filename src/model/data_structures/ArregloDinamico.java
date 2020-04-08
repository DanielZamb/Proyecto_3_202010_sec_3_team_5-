package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 *
 * @author Fernando De la Rosa
 */
public class ArregloDinamico<K> implements IArregloDinamico {
    /**
     * Capacidad maxima del arreglo
     */
    private int tamanoMax;
    /**
     * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
     */
    private int tamanoAct;
    /**
     * Arreglo de elementos de tamaNo maximo
     */
    private K[] elementos;

    /**
     * Construir un arreglo con la capacidad maxima inicial.
     *
     * @param max Capacidad maxima inicial
     */
    public ArregloDinamico(int max) {
        elementos = (K[]) new Comparable[max];
        tamanoMax = max;
        tamanoAct = 0;
    }

    public void agregar(Comparable dato) {
        if (tamanoAct == tamanoMax) {  // caso de arreglo lleno (aumentar tamaNo)
            tamanoMax = 2 * tamanoMax;
            K[] copia = (K[]) elementos;
            elementos = (K[]) new Comparable[tamanoMax];
            for (int i = 0; i < tamanoAct; i++) {
                elementos[i] = copia[i];
            }
            System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
        }
        elementos[tamanoAct] = (K) dato;
        tamanoAct++;
    }

    public int darCapacidad() {
        return tamanoMax;
    }

    public int darTamano() {
        return tamanoAct;
    }

    public Comparable<K> darElemento(int i) {
        if (i > elementos.length) return null;
        return (Comparable<K>) elementos[i];

    }

    //hacer metodo compareto en caso de usar esta clase para manejar colas y pilas.
    public Comparable<K> buscar(Comparable dato) {
        Comparable buscado = null;
        for (int i = 0; i < elementos.length; i++) {
            if (darElemento(i).compareTo((K) dato) == 0) {
                buscado = darElemento(i);
            }
        }

        return (Comparable<K>) buscado;
    }

    public Comparable<K> eliminar(Comparable dato) {

        K[] copia = elementos;
        K eliminado = null;
        boolean cent = false;
        for (int i = 0; i < elementos.length && !cent; i++) {
            if (darElemento(i).compareTo((K) dato) == 0) {
                eliminado = (K) elementos[i];
                cent = true;
            }
            if (cent) {
                tamanoAct--;
                elementos[i] = null;
                int k = 0;
                for (int j = 0; j < elementos.length; j++) {
                    if (elementos[j] != null) {
                        k = j;
                        copia[j] = elementos[k];
                    } else
                        k++;
                }
                elementos = copia;
            }
        }
        return (Comparable<K>) eliminado;
    }

    public void eliminarEnPos(int Pos) {
        K[] copia = elementos;
        boolean cent = false;
        tamanoAct--;
        elementos[Pos] = null;
        int k = 0;
        for (int j = 0; j < elementos.length; j++) {
            if (elementos[j] != null) {
                copia[k] = elementos[j];
                k++;
            }
        }
		elementos = copia;
    }
}



