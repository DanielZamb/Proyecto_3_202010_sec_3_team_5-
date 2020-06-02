package model.data_structures;

import java.util.Iterator;

public class Stack<T> implements StackI,Iterable<T>{
    private Nodo<T> first;
    // top of stack (most recently added node)
    private int N;      // number of items
    public boolean isEmpty() {
        return first == null;
    }  // Or: N == 0.  0
    public int size()        {
        return N;
    }
    public void push(Object item)   {  // Add item to top of stack.
        T itemT = (T)item;
        Nodo<T> oldfirst = first;
        first = new Nodo<T>();
        first.setInfo(itemT);
        first.setSiguiente(oldfirst);
        N++;
    }
    public T pop()   {  // Remove item from top of stack.
        T item = first.getInfo();
        first = first.getSiguiente();
        N--;
        return item;
    }
    public T peek(){
        T item = first.getInfo();
        return item;
    }
    public Iterator<T> iterator()
    { return new ListIterator(); }
    private class ListIterator implements Iterator<T>
    {
        private Nodo<T> current = first;
        public boolean hasNext()
        { return current != null; }
        public void remove() { }
        public T next()
        {
            T item = current.getInfo();
            current = current.getSiguiente();
            return item;
        }
    }
}
