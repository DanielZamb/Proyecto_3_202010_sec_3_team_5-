package model.data_structures;

public class Nodo<T> {
        private Nodo<T> siguiente;
        private T info;
        public Nodo(){
            this.siguiente = null;
            this.info = null;
        }
        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public T getInfo() {
            return info;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

        public void setInfo(T feature) {
            this.info = feature;
        }
    }
