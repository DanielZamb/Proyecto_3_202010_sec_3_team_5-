package model.logic;

import model.data_structures.*;


import java.awt.geom.Area;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Definicion del modelo del mundo
 */
public class Modelo<T, S extends Comparable<S>> {
    /**
     * Atributos del modelo del mundo
     */
    private IArregloDinamico<S> arregloD;
    private Istack<T> stackComparendos;
    private IQueue<T> colaComparendos;
    private IListaEncadenada<T> listaComparendos;
    private Nodo<T> nodoComparendo;
    private int tamanio;
    private Features mayorObj;
    private Double[] MinMax;

    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public Modelo() {
        // arregloD = null;
        tamanio = 0;
        mayorObj = null;
    }

    public Modelo(List<T> listaFeatures) {
        cargarComparendos(listaFeatures);
        objConMayorOBJID();
        MiniMax();
    }

    public void cargarComparendos(List<T> listaFeatures) {
        try {
            long startTime = System.nanoTime();
            Nodo<Features> primero = new Nodo<Features>(null, (Features) listaFeatures.get(0));
            Nodo<Features> ultimo = new Nodo<Features>(null, (Features) listaFeatures.get(listaFeatures.size() - 1));
            Features item1 = (Features) listaFeatures.get(0);
            listaFeatures.remove(0);
            arregloD = (IArregloDinamico<S>) new ArregloDinamico<S>(listaFeatures.size() + 1);
            arregloD.agregar((S) item1);
            colaComparendos = (IQueue<T>) new Queue<T>((Nodo<T>) primero);
            stackComparendos = (Istack<T>) new Stack<T>((Nodo<T>) primero);
            listaComparendos = (IListaEncadenada<T>) new ListaEncadenada<T>((Nodo<T>) primero);
            listaFeatures.forEach(feature -> {
                nodoComparendo = new Nodo<T>(null, (T) feature);
                arregloD.agregar((S) feature);
                colaComparendos.enqueue((T) nodoComparendo);
                stackComparendos.push((T) nodoComparendo);
                listaComparendos.AppendNode(nodoComparendo);
            });
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            double convertET = (double) elapsedTime / 1000000000;
            System.out.println("tama�o de la queue: " + colaComparendos.size());
            System.out.println("tama�o del stack: " + stackComparendos.getSize());
            System.out.println("tama�o de la lista: " + listaComparendos.getTamanio());
            System.out.println("tamanio del arreglo: " + arregloD.darTamano());
            System.out.println(
                    "Datos cargados en estructuras -----// \n\tTime elapsed loading data: " + convertET + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void objConMayorOBJID() {
        Features obj = null;
        Nodo<Features> evaluado = (Nodo<Features>) listaComparendos.getPrimerNodo();
        int mayor = 0;
        while (evaluado != null) {
            int comp = evaluado.getInfo().getProperties().getOBJECTID();
            if (comp > mayor) {
                obj = evaluado.getInfo();
                mayor = comp;
            }
            evaluado = (Nodo<Features>) evaluado.getSiguiente();
        }
        mayorObj = obj;
    }

    public void MiniMax() {
        Nodo<Features> evaluado = (Nodo<Features>) listaComparendos.getPrimerNodo();
        double HiLat = 0;
        double HiLong = 0;
        double LoLat = 0;
        double LoLong = 0;
        while (evaluado != null) {
            double compLat = evaluado.getInfo().getGeometry().DarCoordenadas().get(0);
            double compLong = evaluado.getInfo().getGeometry().DarCoordenadas().get(1);
            if (compLat > HiLat) HiLat = compLat;
            else if (compLat < LoLat) LoLat = compLat;
            if (compLong > HiLong) HiLong = compLong;
            else if (compLong < LoLong) LoLong = compLong;
            evaluado = (Nodo<Features>) evaluado.getSiguiente();
        }
        Double[] coor = {HiLat, HiLong, LoLat, LoLong};
        MinMax = coor;
    }

    public String getPrimerComparendoInfrac(String pInfraccion) {
        Nodo<Features> buscado = null;
        Nodo<Features> iterador = (Nodo<Features>) listaComparendos.getPrimerNodo();
        Nodo<Features> ultimo = (Nodo<Features>) listaComparendos.getUltimoNodo();
        Boolean comp1 = iterador.getInfo().getProperties().getINFRACCION().equalsIgnoreCase(pInfraccion);
        Boolean comp2 = ultimo.getInfo().getProperties().getINFRACCION().equalsIgnoreCase(pInfraccion);
        Boolean cent = false;
        if (comp1) return iterador.getInfo().getProperties().toString();
        if (comp2) return ultimo.getInfo().getProperties().toString();
        iterador = iterador.getSiguiente();
        while (iterador.getSiguiente() != null && !cent) {
            if (iterador.getInfo().getProperties().getINFRACCION().equalsIgnoreCase(pInfraccion)) {
                buscado = iterador;
                cent = true;
            }
            iterador = iterador.getSiguiente();
        }
        if (buscado != null) return buscado.getInfo().getProperties().toString();
        else return "No existen comparendos con tal codigo de infraccion.";
    }

    public Features[] getListaComparendosInfrac(String pInfraccion) {
        Nodo<Features> buscado = null;
        Nodo<Features> iter = (Nodo<Features>) listaComparendos.getPrimerNodo();
        ArregloDinamico copia = new ArregloDinamico<Features>(300);
        while (iter != null) {
            if (iter.getInfo().getProperties().getINFRACCION().equalsIgnoreCase(pInfraccion)) {
                copia.agregar(iter.getInfo());
                tamanio++;
            }
            iter = iter.getSiguiente();
        }
        Features[] ordenado = new Features[copia.darTamano()];
        for (int i = 0; i < copia.darTamano(); i++) {
            ordenado[i] = (Features) copia.darElemento(i);
        }
        Merge.sort(ordenado);
        return ordenado;
    }

    public Object[] compareNumInfraccionesTipoSevicio() {
        ArregloDinamico<String> infracList = new ArregloDinamico<>(listaComparendos.getTamanio());
        ArregloDinamico<Integer> res = new ArregloDinamico<>(listaComparendos.getTamanio());
        Integer publico = 0, particular = 0;
        Features[] toSortP = new Features[arregloD.darTamano()];
        for (int i = 0; i < arregloD.darTamano(); i++) {
            toSortP[i] = (Features) arregloD.darElemento(i);
        }
        Merge.sortP(toSortP);
        for (int f = 0; f < toSortP.length; f++) {
            String tipo = toSortP[f].getProperties().getTIPO_SERVI();
            Boolean comp = false;
            if (!(f == toSortP.length - 1)) comp = toSortP[f].compareToP(toSortP[f + 1]) == 0;
            if (!comp) {
                if (tipo.equalsIgnoreCase("público")) publico++;
                if (tipo.equalsIgnoreCase("particular")) particular++;
                res.agregar(particular);
                res.agregar(publico);
                infracList.agregar(toSortP[f].getProperties().getINFRACCION());
                publico = 0;
                particular = 0;
            }
            if (comp) {
                if (tipo.equalsIgnoreCase("público")) publico++;
                if (tipo.equalsIgnoreCase("particular")) particular++;
            }
        }
        Object[] comparacion = new Object[2];
        comparacion[0] = infracList;
        comparacion[1] = res;
        return comparacion;
    }

    public Object[] mostrarComparendosLocalidadFecha(String Localidad, String startDate, String endDate) {
        ArregloDinamico<String> infracList = new ArregloDinamico<>(listaComparendos.getTamanio());
        ArregloDinamico<Integer> res = new ArregloDinamico<>(listaComparendos.getTamanio());
        Nodo<Features> iter = (Nodo<Features>) listaComparendos.getPrimerNodo();
        Queue<Features> queueTemp = null;
        Features[] toSort = null;
        int i = 0;
        while (iter != null) {
            Boolean bLocal = iter.getInfo().getProperties().getLOCALIDAD().equalsIgnoreCase(Localidad);
            Boolean bDate = iter.getInfo().compareD(startDate, endDate);
            if (bLocal && bDate) {
                Nodo<Features> temp = new Nodo<>(null, iter.getInfo());
                if (queueTemp == null) {
                    queueTemp = new Queue<>(temp);
                    queueTemp.peek().setSiguiente(null);
                }
                queueTemp.enqueue(temp);
            }
            iter = iter.getSiguiente();
        }
        toSort = new Features[queueTemp.size() - 1];
        queueTemp.dequeue();
        while (!queueTemp.isEmpty()) {
            Nodo<Features> nodoTemp = queueTemp.dequeue();
            toSort[i] = nodoTemp.getInfo();
            ++i;
        }
        Object[] comparacion = contarComparendos(toSort, infracList, res,false);
        return comparacion;
    }

    public Object[] NInfraccionesMasComparendosPorTiempo(int N, String startDate, String endDate) {
        ArregloDinamico<String> infracList = new ArregloDinamico<>(listaComparendos.getTamanio());
        ArregloDinamico<Integer> res = new ArregloDinamico<>(listaComparendos.getTamanio());
        Nodo<Features> iter = (Nodo<Features>) listaComparendos.getPrimerNodo();
        Queue<Features> queueTemp = null;
        Features[] toSort = null;
        String[] nInfrac = null;
        Integer[] datosInfrac = null;
        Integer mayor = 0, actual, pos = 0, k = 0;
        while (iter != null) {
            Boolean bDate = iter.getInfo().compareD(startDate, endDate);
            if (bDate) {
                Nodo<Features> temp = new Nodo<>(null, iter.getInfo());
                if (queueTemp == null) {
                    queueTemp = new Queue<>(temp);
                    queueTemp.peek().setSiguiente(null);
                }
                queueTemp.enqueue(temp);
            }
            iter = iter.getSiguiente();
        }
        int i = 0;
        toSort = new Features[queueTemp.size() - 1];
        queueTemp.dequeue();
        while (!queueTemp.isEmpty()) {
            Nodo<Features> nodoTemp = queueTemp.dequeue();
            toSort[i] = nodoTemp.getInfo();
            ++i;
        }
        Object[] comparacion = contarComparendos(toSort, infracList, res,false);
        infracList = (ArregloDinamico<String>) comparacion[0];
        res = (ArregloDinamico<Integer>) comparacion[1];
        nInfrac = new String[N];
        datosInfrac = new Integer[N];
        while (k < N ) {
            for (int m = 0; m < res.darTamano(); m++) {
                actual = (Integer) res.darElemento(m);
                if (mayor < actual) {
                    mayor = actual;
                    pos = m;
                }
            }
            Integer numC = (Integer) res.darElemento(pos);
            String infrac = (String) infracList.darElemento(pos);
            res.eliminarEnPos(pos);
            infracList.eliminarEnPos(pos);
            nInfrac[k] = infrac;
            datosInfrac[k] = numC;
            k++;
            mayor=0;
        }
        comparacion[0]=nInfrac;
        comparacion[1]=datosInfrac;
        return comparacion;
    }
    public Object[] generarHistograma() {
        ArregloDinamico<String> localidades = new ArregloDinamico<>(listaComparendos.getTamanio());
        ArregloDinamico<Integer> res = new ArregloDinamico<>(listaComparendos.getTamanio());
        Nodo<Features> iter = (Nodo<Features>) listaComparendos.getPrimerNodo();
        Features[] toSort = new Features[listaComparendos.getTamanio()];
        int k=0,cont=0;
        while (iter != null) {
            toSort[k]=iter.getInfo();
            k++;
            iter = iter.getSiguiente();
        }
        Object[] listaR = contarComparendos(toSort,localidades,res,true);
        return listaR;
    }

    public Object[] contarComparendos(Features[] toCount, ArregloDinamico<String> List, ArregloDinamico<Integer> res,Boolean histograma) {
        int cont = 0;
        if (histograma){
            Quick.sortL(toCount);
        }
        else{
        Merge.sortP(toCount);
        }
        for (int f = 0; f < toCount.length; f++) {
            Boolean comp = false;
            if (!(f == toCount.length - 1) && !histograma) comp = toCount[f].compareToP(toCount[f + 1]) == 0;
            else if (!(f == toCount.length - 1)) comp = toCount[f].compareToL(toCount[f + 1]) == 0;
            if (!comp) {
                cont++;
                res.agregar(cont);
                if (histograma){
                List.agregar(toCount[f].getProperties().getLOCALIDAD());
                }
                else{
                List.agregar(toCount[f].getProperties().getINFRACCION());
                }
                cont = 0;
            }
            if (comp) {
                cont++;
            }
        }

        Object[] comparacion = new Object[2];
        comparacion[0] = List;
        comparacion[1] = res;
        return comparacion;
    }

    public String comparendosPorFecha_hora(String FECHA_HORA)
    {
        Nodo<Features> buscado = null;
        Nodo<Features> actual = (Nodo<Features>) listaComparendos.getPrimerNodo();
        ArregloDinamico copiado = new ArregloDinamico<Features>(listaComparendos.getTamanio());
        while (actual != null) {
            if (actual.getInfo().getProperties().getFECHA_HORA().equalsIgnoreCase(FECHA_HORA))
            {
                copiado.agregar(actual.getInfo());
                tamanio++;
            }
            actual = actual.getSiguiente();
        }
        Features[] ordenar = new Features[copiado.darTamano()];
        for (int i = 0; i < copiado.darTamano(); i++) {
            ordenar[i] = (Features) copiado.darElemento(i);
        }
        Shell.sort(ordenar);

        String devolver = "";

        for (int i = 0; i<ordenar.length; i++)
        {
            devolver += ordenar[i].getProperties().toString()+"\n";
        }
        return devolver;
    }



    public Object[] consultarNumInfraccionesMasComparendosPorTiempo(String startDate, String endDate) {
        ArregloDinamico<String> lista1 = new ArregloDinamico<>(listaComparendos.getTamanio());
        ArregloDinamico<Integer> lista2 = new ArregloDinamico<>(listaComparendos.getTamanio());
        Integer fechaInicial = 0, fechaFinal = 0;
        Features[] paraOrdenar = new Features[arregloD.darTamano()];
        for (int i = 0; i<arregloD.darTamano();i++){
            paraOrdenar[i] = (Features) arregloD.darElemento(i);
        }
        Merge.sortP(paraOrdenar);
        for(int i = 0; i<paraOrdenar.length-1;i++){
            String comparendo = paraOrdenar[i].getProperties().getINFRACCION();
            if (paraOrdenar[i].compareToP(paraOrdenar[i+1])==0) {
                if (comparendo.equalsIgnoreCase(startDate)) fechaInicial++;
                if (comparendo.equalsIgnoreCase(endDate)) fechaFinal++;
            }
            else{
                if (comparendo.equalsIgnoreCase(startDate)) fechaInicial++;
                if (comparendo.equalsIgnoreCase(endDate)) fechaFinal++;
                lista2.agregar(fechaFinal);
                lista2.agregar(fechaInicial);
                lista1.agregar(paraOrdenar[i].getProperties().getINFRACCION());
                fechaInicial = 0;
                fechaFinal = 0;
                comparendo = paraOrdenar[i+1].getProperties().getINFRACCION();
                if (comparendo.equalsIgnoreCase(startDate)) fechaInicial++;
                if (comparendo.equalsIgnoreCase(endDate)) fechaFinal++;
            }
        }
        lista1.agregar(paraOrdenar[paraOrdenar.length-1].getProperties().getINFRACCION());
        lista2.agregar(fechaFinal);
        lista2.agregar(fechaInicial);
        Object[] comparacion = new Object[2];
        comparacion[0] = lista1;
        comparacion[1] = lista2;
        return comparacion;
    }

    public Features getMayorOBJ() {
        return mayorObj;
    }

    public Double[] getMinMax() {
        return MinMax;
    }

    public int getTamanio() {
        return tamanio;
    }

    public Comparable[] copiarComparendos() {
        Features[] nuevo = new Features[arregloD.darTamano()];
        for (int i = 0; i < arregloD.darTamano(); i++) {
            nuevo[i] = (Features) arregloD.darElemento(i);
        }
        return nuevo;
    }

}

