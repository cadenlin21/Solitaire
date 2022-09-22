
/**
 * The Queue class implements a Queue using ListNodes. 
 *
 * @author Caden Lin 
 * @version Dec 07 2020 
 */
public class Queue
{
    private Node front, rear;
    /** The Node class creates nodes for the LinkedList. 
     * 
     * @author Caden Lin 
     * @version Dec 07 2020 
     * 
     */
    private class Node
    {
        private int value; 
        private Node next; 
        /** Constructor for Node objects 
         * @param value the value stored in the node 
         * 
         */
        public Node(int value)
        {
            this.value = value;
            this.next = null;
        }

        /** determines the value stored in the node 
         * @return the value stored in the node 
         * 
         */
        public int getValue()
        {
            return value;
        }

        /** determines the next node after this one 
         * @return a reference to the next node in the list
         *              (null if there is no next node) 
         * 
         */
        public Node getNext()
        {
            return next; 
        }

        /** changes the next node after this onen 
         * @postcondition the next variable is replaced with a reference to the new node 
         * @param node the new node to be set as the next node 
         * 
         */
        public void setNext(Node node)
        {
            next=node;
        }

    }

    /**
     * Constructor for objects of class Queue
     */
    public Queue()
    {
        front = null;
        rear = null;

    }

    /** determines if the queue is empty
     * @return true if the queue is empty (if the first node is null) 
     *             false if the queue is not empty 
     * 
     */
    public boolean isEmpty()
    {
        return front==null;
    }

    /** removes an element from the queue 
     * @precondition an element in removed from the queue by removing the first 
     *              node of the list 
     * 
     */
    public void remove()
    {
        if(this.isEmpty())
            return;
        else
        {

            front = front.getNext();
            if(this.isEmpty())
                rear=null;

        }
    }

    /** adds another element to the queue 
     * @postcondition a new element is added to the queue as a node 
     *                  to the end of the linkedlist 
     *                  
     *  @param value the value to store in the new element                  
     * 
     */
    public void add(int value)
    {
        Node newNode = new Node(value);
        if(this.isEmpty())
        {
            front = newNode;
            rear = newNode;
        }
        else
        {
            rear.setNext(newNode);
            rear = rear.getNext();
        }
    }

    /**
     * determines the value stored in the top element of the queue
     * @return the value of the top element of the queue, or the first 
     *          element of the linkedlist (the first element to have been added) 
     *
     */
    public int peek()
    {
        return front.getValue(); 
    }
}
