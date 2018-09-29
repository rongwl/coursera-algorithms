import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private int n;

	public RandomizedQueue() {
		q = (Item[])new Object[2];
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public int size() {
		return n;
	}

	private void resize(int capacity) {
		assert capacity > n;
		Item[] buffer = (Item[])new Object[capacity];
		for (int i = 0; i < n; i++)
			buffer[i] = q[i];
		q = buffer;
	}

	public void enqueue(Item item) {
		if (null == item)
			throw new java.lang.IllegalArgumentException();
		if (n == q.length)
			resize(n*2);
		q[n++] = item;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();

		int i = StdRandom.uniform(0, n);
		Item ret = q[i];
		if (i != n-1)
			q[i] = q[n-1];
		q[--n] = null;
		if (n > 0 && n == q.length/4)
			resize(q.length/2);
		
		return ret;
	}

	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		return q[StdRandom.uniform(0,n)];
	}


	public Iterator<Item> iterator() {
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<Item> {
		private Item[] mirror;
		private int num;

		public ReverseArrayIterator() {
			mirror = (Item[])new Object[n];
			for (int i = 0; i < n; i++) 
				mirror[i] = q[i];
			num = n;
		}

		public boolean hasNext() {
			return num > 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int i = StdRandom.uniform(0, num);
			Item ret = mirror[num-1];
			if (i != num-1) {
				ret = mirror[i];
				mirror[i] = mirror[num-1];
			}
			mirror[--num] = null;

			return ret;
		}
	}
	
	public static void main(String[] args) {
	}
}
