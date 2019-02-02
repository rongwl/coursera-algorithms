import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class Solver {
	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private Board prev;

		public SearchNode(Board board, Board prev) {
			this.board = board;
			this.prev = prev;
		}

		public Board board() {
			return this.board;
		}

		public Board prevBoard() {
			return prev;
		}

		public int compareTo(SearchNode other) {
			return (this.board.manhattan() - other.board.manhattan());
		}
	}

	private int moves;
	private boolean isSolvable;
	private List<Board> solutions;

	private SearchNode move(SearchNode node) {
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		/*StdOut.println("node:");
		StdOut.println(node.board());
		if (node.prevBoard() != null) {
			StdOut.println("prev node:");
			StdOut.println(node.prevBoard());
		}
		StdOut.println("neighbors:");*/
		for (Board neighbors : node.board().neighbors()) {
			if (!neighbors.equals(node.prevBoard())) {
				//StdOut.println(neighbors);
				pq.insert(new SearchNode(neighbors, node.board()));
			}
		}

		return pq.min();
	}

	public Solver(Board initial) {
		if (initial == null)
			throw new java.lang.IllegalArgumentException("initial board is null");

		solutions = new ArrayList<Board>();
		Board twin = initial.twin();
		SearchNode node1 = new SearchNode(initial, null);
		SearchNode node2 = new SearchNode(twin, null);
		while (true) {
			if (node1.board().isGoal()) {
				isSolvable = true;
				break;
			}
			if (node2.board().isGoal()) {
				isSolvable = false;
				break;
			}
			node1 = move(node1);
			solutions.add(node1.board());
			//StdOut.println("solutions:");
			//StdOut.println(node1.board());
			//StdOut.println("goal:"+ node1.board().isGoal() + " size:" + solutions.size());
			node2 = move(node2);
		}
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	public int moves() {
		if (isSolvable())
			return solutions.size();
		return 0;
	}

	public Iterable<Board> solution() 
	{
		return solutions;
	}

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}		
	}
}
