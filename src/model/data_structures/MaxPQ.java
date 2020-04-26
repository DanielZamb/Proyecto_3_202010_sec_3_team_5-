package model.data_structures;

import java.util.Comparator;

public class MaxPQ<Key extends Comparable<Key>>{
    private Key[] pq;
    private int N;// como el numero de elementos en un arreglo esta relacionado con los indices del mismo, se puede usar como contador ya que de todas formas siempre se aniade al final
    private Comparator<Key> comparator;
    public MaxPQ(int MaxN,Comparator comparator){
        this.comparator = (Comparator<Key>) comparator;
        N=0;
        pq = (Key[])new Comparable[MaxN+1];
    }
    public boolean isEmpty(){
        return (N==0);
    }
    public int size(){
        return N;
    }
    public void put(Key key){
        // el N evita la complejidad lineal de un ciclo for en el caso de insercion.
        pq[++N] = key;
        swim(N);
    }
    public Key delMax(){
        Key max  = pq[1];
        exch(1,N--); // usa el decremento como postfix para que retorne N primero y luego reste.
        pq[N+1] = null;
        sink(1);
        return max;
    }
    public Key peek(){
        return pq[1];
    }
    private boolean less(int i, int j) {
        return comparator.compare(pq[i],pq[j])<0;
    }
    private void exch(int i, int j){
        Key k = pq[i];
        pq[i] = pq[j];
        pq[j] = k;
    }
    private void swim(int i){
        while (i>1 && less(i/2,i) ){
            exch(i/2,i);
            i = i/2;
        }
    }
    private void sink(int i){
        while(2*i<=N){
            int j = 2*i;
            if(j < N && less(j,j+1)) j++;
            if(!less(i,j)) break;
            exch(i,j);
            i=j;
        }
    }
}
