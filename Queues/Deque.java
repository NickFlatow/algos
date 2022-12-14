import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> 
// public class Deque<Item>
{
    private Node first,last;
    private int count = 0; 

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque(){}

    // is the deque empty?
    public boolean isEmpty(){
        return (size() == 0);
    }

    // return the number of items on the deque
    public int size(){
        return count;
    }

    // add the item to the front
    public void addFirst(Item item){
        IllegalArgumentExceptionCheck(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if(isEmpty()){last = first;}
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item){
        IllegalArgumentExceptionCheck(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()){first = last;}
        else{
            oldLast.next = last;
            last.prev = oldLast;
        }
        count++;
    }
    private void IllegalArgumentExceptionCheck(Item item){
        if (item == null){
            throw new IllegalArgumentException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (isEmpty()){
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        this.count--;
        if (isEmpty()) last = first;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        this.count--;
        if (isEmpty()) first = last;
        else{last.next = null;}
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {return current != null;}
        public void remove() {throw new UnsupportedOperationException();}
        public Item next() { 
            if (current == null){
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> d = new Deque<Integer>();
        StdOut.println(d.isEmpty());
        d.addLast(4);
        StdOut.println(d.size());
        d.addFirst(5);
        d.removeLast();
        d.removeFirst();
        for(Integer i: d){
            StdOut.println(i);
        }

    }

}