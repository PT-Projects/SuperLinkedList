import java.util.LinkedList;
import java.util.ListIterator;

public class Driver {
    public static void main(String[] args) {

        SuperLinkedList sll = new SuperLinkedList();

        sll.add(10);

        sll.add(20);

        sll.add(30);

        System.out.println("Size " + sll.size());

        ListIterator li = sll.listIterator(0);

        System.out.println(li.next());

        System.out.println(li.nextIndex());


        /**
         * SuperLinkedList Above
         * LinkedList Below
         */

        System.out.println("------------------------------------");
        System.out.println("SuperLinkedList Above");
        System.out.println("LinkedList Below");
        System.out.println("------------------------------------");

        LinkedList ll = new LinkedList();

        ll.add(10);
        ll.add(20);
        ll.add(30);

        System.out.println("Size " + ll.size());

        ListIterator li1 = ll.listIterator();

        System.out.println(li1.next());

        System.out.println(li1.nextIndex());

    }
}
