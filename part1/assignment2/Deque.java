import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;
	private int n;

	private static class Node<Item> {
		private Item item;
		private Node<Item> prev;
		private Node<Item> next;
	}

	public Deque() {
		first = null;
		last = null;
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	public void addFirst(Item item) {
		if (null == item)
			throw new java.lang.IllegalArgumentException();

		Node<Item> node = new Node<Item>();
		node.item = item;
		node.next = first;
		if (first != null)
			first.prev = node;
		else
			last = node;
		first = node;
		n++;
	}

	public void addLast(Item item) {
		if (null == item)
			throw new java.lang.IllegalArgumentException();

		Node<Item> node = new Node<Item>();
		node.item = item;
		node.prev = last;
		if (last != null)
			last.next = node;
		else
			first = node;
		last = node;
		n++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = first.item;
		first = first.next;
		if (null == first)
			last = null;
		else
			first.prev = null;
		n--;
		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		last = last.prev;
		if (null == last)
			first = null;
		else
			last.next = null;
		n--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;

		public ListIterator(Node<Item> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	public static void main(String[] args) {
		Deque<String> deque = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				deque.addLast(item);
			else if (!deque.isEmpty())
				StdOut.print(deque.removeFirst() + " ");
		}	
		StdOut.println("(" + deque.size() + " left on deque)");
	}
}


