package model.data_structures;

import java.util.Iterator;

public class Bag<T> implements Iterable<T>{
    private Node first;
    private class Node{
        T item;

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    ", next=" + next +
                    '}';
        }

        Node next;
    }
    public void add(T item){
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }
    public void put(T oldItem,T newItem){
        Node current = first;
        Boolean cent = false;
        while (current != null && !cent){
            if(current.item.equals(oldItem)){
                current.item = newItem;
                cent = true;
            }
        }
        current = current.next;
    }
    public void delete(T item){
        Boolean cent = false;
        Node past = null;
        Node current = first;
        Node next = first.next;
        while (current != null && !cent){
            if(current.item.equals(item)){
                if(past !=null &&  next != null){
                    past.next = next;
                    current.next = null;
                }
                else if (past != null){
                    past.next = null;
                }
                else if (next != null){
                    first = current.next;
                    current.next = null;
                }
                else{
                    current = null;
                }
                cent = true;
            }
        }
        past = current;
        current = next;
        next = next.next;
    }
    public Iterator<T> iterator(){
        return new BagIterator();
    }
    private class BagIterator implements Iterator<T>{
        private Node current = first;
        public boolean hasNext(){
            return current != null;
        }
        public T next(){
            T item  = current.item;
            current = current.next;
            return item;
        }
    }
}
