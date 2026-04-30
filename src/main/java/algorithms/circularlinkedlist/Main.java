package algorithms.circularlinkedlist;


public class Main {

    public static void main(String[] args) {
        Node list = ListFactory.buildList(1, 2, 3, 4, 5);
        if (!hasCycle(list))
            System.out.println("Ingen cyklus fundet: " + list);

        Node circularList = ListFactory.buildListWithCycle();
        if (!hasCycle(circularList))
            System.out.println("Head: " + circularList.value);
        else
            System.out.println("Cyklus fundet — printer ikke (ville løkke for evigt)");
    }


    public static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (true) {
            // Hvis slow er null er der ingen cyklus
            if (slow == null) return false;
            slow = slow.next;

            // Hvis fast er null er der ingen cyklus
            if (fast == null) return false;
            fast = fast.next;

            // Hvis fast nu er null er der ingen cyklus
            if (fast == null) return false;
            fast = fast.next;

            // Check om de mødes
            if (slow == fast) return true;
        }
    }
    public static boolean hasCycle2(Node head) {
        Node slow = head; // et skridt
        Node fast = head; // to skridt

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) return true; // de mødes = cyklus
        }
        return false; // fast ramte null = ingen cyklus
    }



}
