package com.saimaddhi.graph;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * The queue class that helps for the breadth first traversal
 *
 * @param <Item> the type of the queue
 */
public class Queue<Item> implements Iterable<Item> {
	/**
	 * The head of the queue
	 */
    private Node<Item> first;
    /**
     * The tail of the queue
     */
    private Node<Item> last;
    /**
     * The size of the queue
     */
    private int n;
    /**
     * represents one element of the queue
     * The type of the element
     * @param <Item>
     */
    private static class Node<Item> {
    	/**
    	 * The item represented by the node
    	 */
        private Item item;
        /**
         * The next item
         */
        private Node<Item> next;
    }
    /**
     * The queue constructor
     */
    public Queue() {
        first = null;
        last  = null;
        n = 0;
    }
    /**
     * true if the queue is empty
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return first == null;
    }
    /**
     * the getter for the size of the queue
     * @return the size of the queue
     */
    public int size() {
        return n;
    }
    /**
     * Returns the first element but does not remove it
     * @return the first element
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }
    /**
     * Adds the specified element into the queue
     * @param item the item to be added
     */
    public void push(Item item) {
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else           oldlast.next = last;
        n++;
    }
    /**
     * Returns the first element AND removes it from the queue
     * @return the first element
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return item;
    }
    /**
     * The to string of this method that formats them as such : item1 item2 item3...
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    } 
    /**
     * the iterator for the queue class
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);  
    }
    /**
     * The iterator
     *
     * @param <Item>
     */
    private class ListIterator<Item> implements Iterator<Item> {
    	/**
    	 * Current node
    	 */
        private Node<Item> current;
        /**
         * constructor with the speciifec first node
         * @param first the first node
         */
        public ListIterator(Node<Item> first) {
            current = first;
        }
        /**
         * True if there is another element
         */
        public boolean hasNext()  {
        	return current != null;                     
        }
        /**
         * The next element in the queue
         */
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}