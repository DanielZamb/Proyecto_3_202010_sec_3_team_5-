package model.data_structures;

public class ListaEncadenada<T> implements IListaEncadenada<T>{
    private Nodo<T> primerNodo;
    private Nodo<T> ultimoNodo;
    private Nodo<T> primeroAntiguo;
    private int tamanio;
    private Nodo<T> actual;
    private IteratorLista iter;
    public ListaEncadenada(Nodo<T> primerNodo){
        this.primerNodo = primerNodo;
        primeroAntiguo = primerNodo;
        this.tamanio = 1;
        ultimoNodo = primerNodo;
        actual = null;
        iter = null;
    }
    public void Iterador(String ControlS,Nodo<T> primeroPersonal) throws Exception {
        iter = (IteratorLista<T>) new IteratorLista((ListaEncadenada<T>) this,ControlS,(Nodo<T>)primeroPersonal);
    }
    public Nodo<T> consultarActual() {
        return actual;
    }
    public void setActual(Nodo<T> actual) {
        this.actual = actual;
    }

    public int getTamanio(){
        return tamanio;
    }

    public Nodo<T> getPrimerNodo() {
        return primerNodo;
    }

    public Nodo<T> getUltimoNodo() {
        return ultimoNodo;
    }

    public void setPrimerNodo(Nodo<T> primerNodo) {
        this.primerNodo = primerNodo;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    public void setPrimeroAntiguo(Nodo<T> primeroAntiguo) {
        this.primeroAntiguo = primeroAntiguo;
    }

    public void setUltimoNodo(Nodo<T> ultimoNodo) {
        this.ultimoNodo = ultimoNodo;
    }

    public Nodo<T> getPrimeroAntiguo() {
        return primeroAntiguo;
    }

    @Deprecated
    public void darTamanioLista(){
        int contador = 0;
        if (primerNodo!=null){
            Nodo<T> evaluado = primerNodo;
            while (evaluado != null){
                contador++;
                evaluado = (Nodo<T>) evaluado.getSiguiente();
            }
        }
        tamanio = contador;
    }
    public void appendNodeInPos(Nodo<T> nuevo, int pos){}
    public void AppendNode(Nodo<T> nuevo){
        primeroAntiguo = primerNodo;
        nuevo.setSiguiente((Nodo<T>) primerNodo);
        primerNodo = nuevo;
        tamanio++;
    }
    public Nodo<T> ConsultarNodo(int pos_){
        Nodo<T> buscado = null;
        if (pos_==tamanio-1) buscado = ultimoNodo;
        else{
            int iterador = 0;
            Nodo<T> evaluado = primerNodo;
            boolean encontro = false;
            while(evaluado != null && !encontro){
                encontro = (iterador == pos_);
                if (encontro) buscado = evaluado;
                else {
                    evaluado = evaluado.getSiguiente();
                    iterador++;}
            }
        }
        return buscado;
    }
    public void deleteNode(int pos){
        Nodo<T> anterior = ConsultarNodo(pos-1);
        if (pos == tamanio-1) {
            anterior.setSiguiente(null);
            ultimoNodo = anterior;
        } else if (pos == 0){
            Nodo<T> siguiente = (Nodo<T>) primerNodo.getSiguiente();
            primeroAntiguo = primerNodo;
            primerNodo.setSiguiente(null);
            primerNodo = siguiente;
        } else {
            Nodo<T> aBorrar = ConsultarNodo(pos);
            anterior.setSiguiente(aBorrar.getSiguiente());
            aBorrar.setSiguiente(null);
        }
        tamanio--;
    }
}

