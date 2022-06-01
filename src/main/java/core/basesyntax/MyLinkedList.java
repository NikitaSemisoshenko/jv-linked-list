package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    public MyLinkedList() {
        size = 0;
    }

    private void insert(Node<T> nodeT, T value) {
        Node<T> previousNode = nodeT.prev;
        Node<T> node = new Node<>(value);
        previousNode.next = node;
        node.prev = previousNode;
        node.next = nodeT;
        nodeT.prev = node;
        size++;
    }

    private void insertFirst(T value) {
        Node<T> node = new Node<>(value);
        node.prev = null;
        node.next = null;
        head = node;
        tail = node;
        size++;
    }

    private void insertLast(T value) {
        Node<T> node = new Node<>(value);
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
        size++;
    }

    private void insertHead(T value) {
        Node<T> node = new Node<>(value);
        Node<T> nextNode = head;
        node.prev = null;
        node.next = nextNode;
        nextNode.prev = node;
        head = node;
        size++;
    }

    private Node<T> iterator(int index) {
        return (index < size - index) ? headIterator(index) : tailIterator(index);
    }

    private Node<T> headIterator(int index) {
        Node<T> nodeT = head;
        for (int i = 0; i < index; i++) {
            nodeT = nodeT.next;
        }
        return nodeT;
    }

    private Node<T> tailIterator(int index) {
        Node<T> nodeT = tail;
        for (int i = 0; i < size - index - 1; i++) {
            nodeT = nodeT.prev;
        }
        return nodeT;
    }

    public void unlink(Node node) {
        Node previous = node.prev;
        Node next = node.next;
        if (previous == null && size != 1) {
            next.prev = null;
            head = next;
        } else if (next == null && size != 1) {
            previous.next = null;
            tail = previous;
        } else if (size == 1) {
            head.prev = null;
            head.next = null;
            head.value = null;
        } else {
            previous.next = next;
            next.prev = previous;
        }
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    private void indexCheckAdd(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    private void addByIndex(int index, T value) {
        if (index == size && size != 0) {
            insertLast(value);
        } else if (index == 0 && size != 0) {
            insertHead(value);
        } else if (index == 0 && size == 0) {
            insertFirst(value);
        } else {
            insert(iterator(index), value);
        }
    }

    public boolean removeByValue(T object) {
        Node<T> nodeT = head;
        for (int i = 0; i < size; i++) {
            if (nodeT.value == object || nodeT.value != null && nodeT.value.equals(object)) {
                unlink(nodeT);
                size--;
                return true;
            } else {
                nodeT = nodeT.next;
            }
        }
        return false;
    }

    private T removeByIndex(int index) {
        Node old = iterator(index);
        indexCheck(index);
        unlink(old);
        size--;
        return (T)old.value;
    }

    private T setByIndex(int index, T value) {
        indexCheck(index);
        Node<T> nodeT = iterator(index);
        T oldValue = nodeT.value;
        nodeT.value = value;
        return oldValue;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            insertFirst(value);
        } else {
            insertLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        indexCheckAdd(index);
        addByIndex(index, value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return iterator(index).value;
    }

    @Override
    public T set(T value, int index) {
        return setByIndex(index, value);
    }

    @Override
    public T remove(int index) {
        return removeByIndex(index);
    }

    @Override
    public boolean remove(T object) {
        return removeByValue(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
