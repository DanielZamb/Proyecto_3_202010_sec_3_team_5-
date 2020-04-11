package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class SeparateChainingHashST<Key extends Comparable<Key>,Value> {
    private int N;
    private int M;
    private SequentialSearchST<Key,Value>[] st;
    private Integer[] primos;
    public SeparateChainingHashST(int M) {
        this.M = checkSize(M);
        st = (SequentialSearchST<Key,Value>[]) new SequentialSearchST[M];
        for (int i=0; i<M; i++){
            st[i] = new SequentialSearchST<>(null);
        }
    }
    private int checkSize(int M){
        ArrayList<Integer> temp = Primos.darPrimos(M);
        primos = new Integer[temp.size()];
        for (int j=0; j < temp.size();j++)
            primos[j] = temp.get(j);
        return primos[primos.length-1];
    }
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public Value get(Key key){
        return (Value) st[hash(key)].get(key);
    }
    public void put(Key key, Value val){
        if (N > (M/2))
            reSize(2*M);
        st[hash(key)].put(key,val);
        N++;
    }
    public Value delete(Key key){
        if (get(key) == null) return null;
        N--;
        return (Value) st[hash(key)].delete(key);
    }
    private void reSize(int M){
        SeparateChainingHashST<Key, Value> s = new SeparateChainingHashST<>(M);
        for(int j =0 ; j< M; j++){
            if (st[j] != null){
                SequentialSearchST.Node actual = st[j].getFirst();
                while(actual != null){
                 s.put((Key) actual.key,(Value) actual.val);
                 actual = actual.next;
                }
            }
        }
        this.st = s.st;
        this.M = M;
    }
    public Queue<Key> keys(){
        Queue<Key> keyQueue = new Queue<>();
        for(int k =0; k<M;k++) {
            Iterator iter = st[k].iterator();
            while(iter.hasNext()){
                keyQueue.enqueue(iter.next());
            }
        }
        return keyQueue;
    }
    public int sizeM(){
        return M;
    }
    public int sizeN(){
        return N;
    }
    //keys


}
