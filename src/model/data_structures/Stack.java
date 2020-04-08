package model.data_structures;

public class Stack<T> implements StackI{
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
}
