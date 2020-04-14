package model.data_structures;

import java.util.Iterator;

public class SequentialSearchST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
    private Node first;

    public SequentialSearchST(Node first) {
        this.first = first;
    }
    //no modifier to a type, package private
    class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public Node getFirst() {
        return first;
    }

    public Value get(Key key) {
        Node actual = first;
        while (actual != null) {
            if (key.equals(actual.key))
                return actual.val;
            actual = actual.next;
        }
        return null;
    }

    public void put(Key key, Value value) {
        Node actual = first;
        while (actual != null) {
            if (key.equals(actual.key)) {
                actual.val = value;
                return;
            }
            actual = actual.next;
        }
        first = new Node(key, value, first);
    }

    public Value delete(Key key) {
        Value value = null;
        Node anterior = null;
        Node actual = first;
        Node siguiente = actual.next;
        boolean cent = false;
        while (actual != null && !cent) {
            if (key.equals(actual.key)) {
                cent = true;
            }
            anterior = actual;
            actual = siguiente;
            siguiente = siguiente.next;
        }
        value = actual.val;
        if (siguiente == null && actual != null) {
            anterior.next = null;
        } else if (anterior == null) {
            first.next = null;
            first = siguiente;
        } else {
            anterior.next = actual.next;
            actual.next = null;
        }
        return value;
    }

    public Iterator<Key> iterator() {
        return new LinkedListIterator<Key>(this);
    }

    private class LinkedListIterator<Key extends Comparable<Key>> implements Iterator<Key> {
        Node actual;

        public LinkedListIterator(SequentialSearchST<Key, Value> sq) {
            actual = (Node) sq.first;
        }

        public boolean hasNext() {
            return (actual.next != null);
        }

        public Key next() {
            Key key = (Key) actual.key;
            actual = actual.next;
            return key;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
