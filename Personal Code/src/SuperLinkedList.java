import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SuperLinkedList<E> extends AbstractSequentialList<E> {

    /**
     * Node that makes up each position in the LinkedList
     * @param <E>
     */
    private static class Node<E>{

        private E data;
        private Node<E> previous;
        private Node<E> next;

        /**
         * Creates a node with given previous, next, and data
         * @param data dat to store in node
         * @param previous previous node in list
         * @param next next node in list
         */
        public Node(E data, Node<E> previous, Node<E> next){
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    /**
     * Custom ListIterator
     */
    private class MyListIterator implements ListIterator<E> {

        private Node<E> nextItem;
        private Node<E> lastItem;

        private int index;

        /**
         * Creates a ListIterator at the begining of the LinkedList
         */
        public MyListIterator(){
            nextItem = head;
            lastItem = null;
        }

        /**
         * Creates a ListIterator that starts at index in LinkedList
         * @param index starting index
         * @return ListIterator
         */
        public ListIterator<E> ListIterator(int index){
            this.index = index;
            for (int x = 0; x < index; x++){
                this.next();
            }
            return this;
        }

        /**
         * Check if the next node is available
         * @return True or False
         */
        @Override
        public boolean hasNext() {
            if (nextItem != null){
                return true;
            }
            return false;
        }

        /**
         * Returns next node
         * @return next node
         */
        @Override
        public E next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }
            index++;
            lastItem = nextItem;
            nextItem = nextItem.next;
            return lastItem.data;
        }

        /**
         * Checks if the previous node is available
         * @return True or False
         */
        @Override
        public boolean hasPrevious() {
            if (lastItem != null){
                return true;
            }
            return false;
        }

        /**
         * Returns previous node
         * @return previous node
         */
        @Override
        public E previous() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            index--;
            nextItem = lastItem;
            lastItem = lastItem.previous;
            return nextItem.data;
        }

        /**
         * Returns next index in list
         * @return next index
         */
        @Override
        public int nextIndex() {
            return index;
        }

        /**
         * Returns previous index in list
         * @return previous index
         */
        @Override
        public int previousIndex() {
            return index - 1;
        }

        /**
         * Removes previous node
         */
        @Override
        public void remove() {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            index--;
            size--;
            lastItem = lastItem.previous;
            nextItem.previous = lastItem;
        }

        /**
         * Sets previous node data to e
         * @param e new data for previous node
         */
        @Override
        public void set(E e) {
            if (!hasPrevious()){
                throw new NoSuchElementException();
            }
            lastItem.data = e;
        }

        /**
         * Adds enw node in between previous and next nodes in list
         * @param e dat for new node in list
         */
        @Override
        public void add(E e) {
            lastItem = new Node(e,lastItem,nextItem);
            size++;
        }
    }

    private Node<E> head = null;
    private int size = 0;

    /**
     * Returns the node at given index
     * @param index index of given node
     * @return node at given index
     */
    private Node<E> getNode(int index){

        Node<E> runningNode = head;

        if (index >= size){
            throw new IndexOutOfBoundsException("Size: " + size + ", Index: " + index);
        }

        try{
            for (int x = 0; x < index; x++){
                runningNode = runningNode.next;
            }
        }
        catch (Exception e){
            throw new IndexOutOfBoundsException("Size: " + size + ", Index: " + index);
        }
        return runningNode;
    }

    //Public Methods

    /**
     * Returns custom ListIterator MyListIterator with a given starting index
     * @param index starting index in ListIterator
     * @return ListIterator
     */
    @Override
    public ListIterator listIterator(int index) {
        return new MyListIterator().ListIterator(index);
    }

    /**
     * Adds data to the LinkedList chain.
     * @param item data to be added.
     */
    public boolean add(E item){
        if (head == null){
            head = new Node<>(item,null,null);
            size++;
            return true;
        }
        else{
            add(size,item);
            return true;
        }
    }

    /**
     * Adds data to the LinkedList chain at specified position.
     * @param index position of data to be added.
     * @param item data to be added.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void add(int index, E item){
        if (index == 0){
            if (head == null){
                add(item);
                size++;
            }
            else {
                Node<E> oldHead = head;
                head = new Node(item, null, oldHead);
                size++;
            }
        }
        else if (index == size){
            Node<E> before = getNode(index-1);
            before.next = new Node(item,before,null);
            size++;
        }
        else{
            Node<E> before = getNode(index-1);
            Node<E> after = getNode(index);
            before.next = new Node(item,before,after);
            size++;
        }
    }

    /**
     * Sets the data value at index.
     * @param index position of new data.
     * @param item new data.
     * @return the data previously at index
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public E set(int index, E item){
        E oldData = get(index);
        getNode(index).data = item;
        return oldData;
    }

    /**
     * Gets the data from index.
     * @param index index of data to return.
     * @return the data at index.
     */
    public E get(int index){
        return getNode(index).data;
    }

    /**
     * Removes data at position in list.
     * @param index position of data to be removed.
     * @return data previously at index.
     */
    public E remove(int index){

        E oldData = get(index);

        if (index == 0){
            getNode(index+1).previous = null;
            head = getNode(index+1);
            size--;
        }
        else{
            getNode(index).previous.next = getNode(index).next;
            size--;
        }

        return oldData;

    }

    /**
     * Size of the LinkedList.
     * @return number of objects in list.
     */
    public int size(){
        return size;
    }

    /**
     * Obtain a string representation of the list.
     * @return A string representation of the list.
     */
    @Override
    public String toString(){

        String finalString = "[";

        for (int x = 0; x < size; x++){
            if (x == size-1){
                finalString += get(x);
            }
            else {
                finalString += get(x) + ", ";
            }
        }

        finalString += "]";

        return finalString;
    }


}
