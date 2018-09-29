import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

public class Permutation {
	public static void main(String[] args) {
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			queue.enqueue(item);
		}

		int k = Integer.parseInt(args[0]);
		if (k < 0 || k > queue.size())
			throw new java.util.NoSuchElementException();
		for (int i = 0; i < k; i++)
			StdOut.println(queue.dequeue());
	}
}
