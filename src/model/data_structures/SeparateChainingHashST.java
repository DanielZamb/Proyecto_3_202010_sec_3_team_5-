package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class SeparateChainingHashST<Key extends Comparable<Key>,Value> {
    private int N;
    private int M;
    private SequentialSearchST<Key,Value>[] st;
    private Primos primos;
    public SeparateChainingHashST(int cap,Primos primos) {
        this.primos = primos;
        M = checkSize(cap);
        st = (SequentialSearchST<Key,Value>[]) new SequentialSearchST[M];
        for (int i=0; i<M; i++){
            st[i] = new SequentialSearchST<>(null);
        }
    }
    private int checkSize(int M){
        Boolean cent = false;
        int hi  = primos.getPrimos().size()-1;
        int lo = 0;
        int mid = 0;
        while(lo <= hi){
            mid  = (hi+lo)/2;
            if (primos.getPrimos().get(mid) == M) { cent = true;return M;}
            if (M>primos.getPrimos().get(mid))
                lo = mid + 1;
            if(M<primos.getPrimos().get(mid))
                hi = mid-1;

        }
        if (!cent)
            return primos.getPrimos().get(mid+1);
        else{
            return 0;
        }
    }
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % this.M;
    }
    public Value get(Key key){
        return (Value) st[hash(key)].get(key);
    }
    public void put(Key key, Value val){
        if (N > ((double)M/2)){
            reSize(2*M);
        }
        st[hash(key)].put(key,val);
        N++;
    }
    public Value delete(Key key){
        if (get(key) == null) return null;
        N--;
        return (Value) st[hash(key)].delete(key);
    }
    private void reSize(int M){
        SeparateChainingHashST<Key, Value> s = new SeparateChainingHashST<>(M,this.primos);
        for(int j =0 ; j < this.M; j++){
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
