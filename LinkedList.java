class LinkedList {
    Node head;

    public void push(Event data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    private Node sortedMerge(Node a, Node b, String attribute, boolean reverse) {
        if (a == null) return b;
        if (b == null) return a;

        Node result;
        int comparison = compareByAttribute(a.data, b.data, attribute);

        if (reverse) {
            comparison = -comparison;
        }

        if (comparison <= 0) {
            result = a;
            result.next = sortedMerge(a.next, b, attribute, reverse);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next, attribute, reverse);
        }
        return result;
    }

    private int compareByAttribute(Event a, Event b, String attribute) {
        return switch (attribute) {
            case "date" -> a.date.compareTo(b.date);
            case "title" -> a.title.compareTo(b.title);
            case "priority" -> compareByPriority(a, b);
            default -> a.date.compareTo(b.date); // Default to date comparison
        };
    }

    private int compareByPriority(Event a, Event b) {
        int dateComparison = a.date.compareTo(b.date);
        if (dateComparison != 0) {
            return dateComparison;
        } else {
            return a.title.compareTo(b.title);
        }
    }

    private Node getMiddle(Node head) {
        if (head == null) return head;

        Node slow = head;
        Node fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private Node mergeSort(Node head, String attribute, boolean reverse) {
        if (head == null || head.next == null) return head;

        Node middle = getMiddle(head);
        Node nextToMiddle = middle.next;
        middle.next = null;

        Node left = mergeSort(head, attribute, reverse);
        Node right = mergeSort(nextToMiddle, attribute, reverse);

        return sortedMerge(left, right, attribute, reverse);
    }

    public void sort(String attribute, boolean reverse) {
        head = mergeSort(head, attribute, reverse);
    }
}