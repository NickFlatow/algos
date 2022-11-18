import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemArray;
    private int count = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemArray = (Item[]) new Object[1];   
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return itemArray[0] == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null){
            throw new IllegalArgumentException();
        }
        if(count == itemArray.length) resize(2 * itemArray.length);
        itemArray[count++] = item;

    }
    // remove and return a random item
    public Item dequeue() {
        if (count <= 0){
            throw new NoSuchElementException();
        }
        int randElem = StdRandom.uniformInt(0, count);
        Item item = itemArray[randElem];

        itemArray[randElem] = null;
        swap(randElem,count-1);
        count--;
        if (count > 0 && count == itemArray.length/4) {resize(itemArray.length/2);}

        return item;
    }

    private void swap(int i, int count){
        Item tmp = itemArray[count];
        itemArray[count] = itemArray[i];
        itemArray[i] = tmp;
    }
    //don't loose values to null
    private void resize(int capacity) {
        Item [] copy = (Item[]) new Object[capacity];
        for (int i = 0;i < count; i++){
            copy[i] = itemArray[i];
        }        
        itemArray = copy;
    }
    public Item sample() {
        if (count <= 0){
            throw new NoSuchElementException();
        }
        int randElem = StdRandom.uniformInt(0, count);
        return itemArray[randElem];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] itemsRandom = (Item[]) new Object[count];

        public RandomizedQueueIterator() {
            for (int i = 0; i < count; i++) {
                itemsRandom[i] = itemArray[i];
            }
            StdRandom.shuffle(itemsRandom);
        }

        public Item next() {
            if (current >= count) {
                throw new NoSuchElementException();
            }
            return itemsRandom[current++];
        }

        public boolean hasNext() {
            return current < count;
        }
    }
    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        // rq.enqueue(2);
        // rq.enqueue(3);
        // rq.enqueue(4);
        // rq.enqueue(5);
        // rq.enqueue(6);
        // rq.enqueue(7);
        // rq.enqueue(8);
        // rq.enqueue(9);
        // rq.enqueue(10);
        StdOut.println(rq.isEmpty());
        StdOut.println(rq.size());
        
        StdOut.println(rq.sample());
        rq.dequeue();
        for (Integer i: rq){
            StdOut.println(i);
        }
        // StdOut.println("Start of Two");
        // for (Integer i: rq){
        //     StdOut.println(i);
        // }
    }

}