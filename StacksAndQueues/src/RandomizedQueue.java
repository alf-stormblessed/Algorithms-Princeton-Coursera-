import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    // rep
    private Item[] array;
    private int size;

   public RandomizedQueue()                 // construct an empty randomized queue
   {
       array = (Item[]) new Object[1];
       size = 0;
   }
   
   public boolean isEmpty()                 // is the queue empty?
   {
       return size == 0;
   }
   
   public int size()                        // return the number of items on the queue
   {
       return size;
   }
   
   public void enqueue(Item item)           // add the item
   {    
       if (item == null) throw new java.lang.NullPointerException();
       
       if (size == array.length) resize(2 * array.length);
       
       // StdOut.println("size: " + size + " length" + array.length + ", item: " + item);
       array[size++] = item;
   }
   public Item dequeue()                    // remove and return a random item
   {
       if (isEmpty()) {
           
           throw new java.util.NoSuchElementException();

       }
      int index = StdRandom.uniform(size);
      Item item = array[index];
      array[index] = array[size - 1];
      array[size-1] = null;
      size--;
      
      if (size > 0 && size == array.length/4) resize(array.length/2);
      
      return item;
       

   }
   public Item sample()                     // return (but do not remove) a random item
   {
       if (isEmpty()) {
           
           throw new java.util.NoSuchElementException();

       }
       return array[StdRandom.uniform(size)];
       
   }
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {   
       return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item> {
       private int i;
       private Item[] iteratorCopy;
       
       public RandomizedQueueIterator() {
           i = size;
           iteratorCopy = (Item[]) new Object[size];
           for (int i = 0; i < size; i++) {
               iteratorCopy[i] = array[i];
               // StdOut.println("ic: (" + i + ") = " + iteratorCopy[i]);
           }
       }
       
       public boolean hasNext() {
           return i > 0;
       }
       
       public Item next() {
           if(this.hasNext()) {
               
               int index = StdRandom.uniform(i);
               // StdOut.println(i + ", " + index);
               Item item = iteratorCopy[index];
               // StdOut.println("i: " + i + ", " + index + ", " + item);
               iteratorCopy[index] = iteratorCopy[i - 1];
               iteratorCopy[i-1] = null;
               i--;
               return item;
           }
           else throw new java.util.NoSuchElementException();
           
       }
       
       public void remove() {
           throw new UnsupportedOperationException();
       }
   }
   
   private void resize(int capacity)
   {
       Item[] copy = (Item[]) new Object[capacity];
       for (int i = 0; i < size; i++) {
           copy[i] = array[i];
           
       }
       array = copy;
   }
   
   public static void main(String[] args)   // unit testing
   {


       
   }
   
}