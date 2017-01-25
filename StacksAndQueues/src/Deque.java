

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;



public class Deque<Item> implements Iterable<Item> {
    
    // rep
    private int N;
    private Node<Item> first = null;
    private Node<Item> last = null;
    
    private class Node<Item> {
        
        Item item;
        private Node<Item> next;
        private Node<Item> prev;
        
    }
    
   public Deque()                           // construct an empty deque
   {
       first = null;
       last = null;
       N = 0;
       
   }
   
   public boolean isEmpty()                 // is the deque empty?
   {
       return N == 0;
   }
   public int size()                        // return the number of items on the deque
   {
       return N;
   }
   public void addFirst(Item item)          // add the item to the front
   {
       if (item == null) throw new java.lang.NullPointerException();
       
       Node<Item> newNode  = new Node<Item>();
       
       newNode.prev = null;
       newNode.item = item;
       newNode.next = first;
       
       if (isEmpty()) {
           
          last = newNode;

       }
       else {
           
           first.prev = newNode;
       }
       first = newNode;
       N++;
       
   }
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null) throw new java.lang.NullPointerException();
       
       Node<Item> newNode  = new Node<Item>();
       
       newNode.prev = last;
       newNode.item = item;
       newNode.next = null;
       
       if (isEmpty()) {
           
          first = newNode;

       }
       else {
           
           last.next = newNode;
       }
       last = newNode;
       N++; 
   }
   public Item removeFirst()                // remove and return the item from the front
   {

     
       if (isEmpty()) {
           
           throw new java.util.NoSuchElementException();

       }
       
       Item item = first.item;
       
       if (N == 1) {
           first = null;
           last = null;
       }
       else {
           
           first.prev = null;
           first = first.next;
           
       }
       N--;
       return item;
   }
   public Item removeLast()                 // remove and return the item from the end
   {
       if (isEmpty()) {
           
           throw new java.util.NoSuchElementException();

       }
       
       Item item = last.item;
       last = last.prev;
       
       if (N == 1) {
           first = null;

       }
       else {
           
           last.next = null;
           
       }
       N--;
       return item; 
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator(first);
   }
   
   private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;
        
        public DequeIterator(Node<Item> first) {
            this.current = first;
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if(hasNext()) {
                
                Item item = current.item;
                current = current.next;
                return item;
            }
            else throw new java.util.NoSuchElementException();
            
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
}
   public static void main(String[] args)   // unit testing
   {
       Deque<Integer> deque = new Deque<Integer>();
       deque.addLast(1);
       deque.removeLast();
       
       deque.addFirst(3);
       deque.removeFirst();
       deque.addFirst(5);
       deque.addLast(6);
       deque.removeLast();
       for (int i : deque) {
           StdOut.println("it " + i);
       }
   }
}