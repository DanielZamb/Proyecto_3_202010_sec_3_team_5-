package model.data_structures;

import java.util.ArrayList;

public class LinearProbingHashST<Key extends Comparable<Key>,Value> {
    private int N;
    private int M = 43;
    private Key[] keys;
    private Value[] val;
    private Integer[] primos;

    public LinearProbingHashST(int cap){
        N = 0;
        M = reviewSize(cap);
        keys = (Key[]) new Object[M];
        val = (Value[]) new Object[M];
    }
    private int reviewSize(int cap){
        ArrayList<Integer> temp = Primos.darPrimos(cap);
        for (int j=0; j < temp.size();j++)
            primos[j] = temp.get(j);
        return primos[primos.length-1];
    }
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public void put (Key key, Value value){
        if (N >= ((3/4)*M))
            resize(2*M);
        int i = 0; // contador en modulo
        for (i = hash(key) ; keys[i] != null ; i = (i+1) % M ){
            if (key.equals(keys[i])) {
                val[i] = value;
                return;
            }

        }
        keys[i] = key;
        val[i] = value;
        N++;
    }
    private void resize(int capacity){
        LinearProbingHashST<Key,Value> t = new LinearProbingHashST<>(capacity);
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                t.put(keys[i], val[i]);
        keys = t.keys;
        val = t.val;
        M = t.M;
    }
    public Value get(Key key){
        Value hit = null;
        for (int k = hash(key) ; keys[k] != null ; k = (k+1) % M ){
            if (key.equals(keys[k])) {
                hit = val[k];
            }
        }
        return hit;
    }
    public Value delete(Key key){
        Value value = null;
        if (get(key) == null ) return value;
        else {
            int m = hash(key);
            while(!key.equals(keys[m]))
                m = (m+1) % M;
            value = val[m];
            keys[m] = null;
            val[m] = null;
            m= (m+1) % M;
            while (keys[m] != null){
                Key keyRedo = keys[m];
                Value valRedo = val[m];
                keys[m] = null;
                val[m] =null;
                N--;
                put(keyRedo,valRedo);
                m = (m+1) % M;
            }
            N--;
            if (N > 0 && N == M/8) resize(M/2);
        }
        return value;
    }
    public Queue<Key> keys(){
        Queue<Key> keyQueue = new Queue<>();
        for(int t=0; t<M ;t++){
            if (keys[t]!=null){
                keyQueue.enqueue(keys[t]);
            }
        }
        return keyQueue;
    }
}
