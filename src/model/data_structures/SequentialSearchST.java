package model.data_structures;

public class SequentialSearchST<Key  extends Comparable<Key>,Value> {
    private Node first;
    public SequentialSearchST(Node first){
        this.first = first;
    }
    private class Node{
        Key key;
        Value val;
        Node next;
        public Node (Key key, Value val, Node next){
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
    public Value get(Key key){
        Node actual = first;
        while (actual != null){
            if (key.equals(actual.key))
                return actual.val;
            actual = actual.next;
        }
        return null;
    }
    public void put(Key key, Value value){
        Node actual = first;
        while(actual != null){
            if (key.equals(actual.key)){
                actual.val = value;
                return;
            }
        }
        first = new Node(key,value,first);
    }
    public Value delete(Key key){
            Node anterior = null;
            Node actual = first;
            Node siguiente = actual.next;
            boolean cent = false;
            while(actual != null){
                if (key.equals(actual.key)){
                    cent = true;
                }
                anterior = actual;
                actual = siguiente;
                siguiente = siguiente.next;
            }
            if (siguiente == null && actual != null) {
                anterior.next  = null;
            } else if (anterior == null){
                first.next = null;
                first = siguiente;
            } else {
                anterior.next = actual.next;
                actual.next = null;
            }
    }
    //implementar delete? SI

}
