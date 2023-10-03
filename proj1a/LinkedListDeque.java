public class LinkedListDeque<T> {

    private class Node {
        public T item;
        public Node next;
        public Node pre;

        public Node(T i, Node p, Node n) {
            item = i;
            pre = p;
            next = n;
        }

        public Node() {
        }

    }

    private Node sentinel;
    private int size;

    public int size() {
        return size;
    }

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        size = 0;
    }

    public void addFirst(T i) {
        Node first = new Node(i, sentinel, sentinel.next);
        sentinel.next.pre = first;
        sentinel.next = first;
        size++;
    }

    public void addLast(T i) {
        Node last = new Node(i, sentinel.pre, sentinel);
        sentinel.pre.next = last;
        sentinel.pre = last;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        Node p = sentinel.next;
        for (int i = size; i != 0; i--) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0)
            return null;
        T first = sentinel.next.item;
        sentinel.next.next.pre = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return first;
    }

    public T removeLast() {
        if (size == 0)
            return null;
        T last = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;
        size--;
        return last;
    }

    public T get(int index) {
        if(index >= size)
            return null;
        Node p = sentinel.next;
        while (index != 0) {
            p = p.next;
            index--;
        }

        return p.item;
    }

    public T RecursiveHelp(int index, Node start) {
        if (index == 0)
            return start.item;

        return RecursiveHelp(index - 1, start.next);
    }

    public T getRecursive(int index) {
        return RecursiveHelp(index, sentinel.next);
    }

}
