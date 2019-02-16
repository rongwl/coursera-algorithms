import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.List;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class Solver {
	private class SearchNode implements Comparable<SearchNode> {
		private Board board;
		private SearchNode prev;
		private int moves;

		public SearchNode(Board board, SearchNode prev, int moves) {
			this.board = board;
			this.prev = prev;
			this.moves = moves;
		}

		public Board board() {
			return this.board;
		}

		public SearchNode prevNode() {
			return prev;
		}

		public int moves() {
			return this.moves;
		}

		public int compareTo(SearchNode other) {
			return ((this.board.manhattan()+this.moves) - (other.board.manhattan()+other.moves));
		}
	}

	private int moves;
	private boolean isSolvable;
	private Stack<Board> solutions;

	private SearchNode move(SearchNode node, MinPQ<SearchNode> pq) {
		/*StdOut.println("search node:");
		StdOut.println(node.board());
		if (node.prevBoard() != null) {
			StdOut.println("prev node:");
			StdOut.println(node.prevBoard());
		}
		StdOut.println("neighbors:");*/
		for (Board neighbors : node.board().neighbors()) {
			if (node.prevNode() == null || !neighbors.equals(node.prevNode().board())) {
				//StdOut.println(neighbors);
				pq.insert(new SearchNode(neighbors, node, node.moves+1));
			}
		}
		/*for (SearchNode snode : pq) {
			StdOut.println("pq:");
			StdOut.println("priority:" + (snode.board().manhattan() + snode.moves()));
			StdOut.println("manhattan:" + snode.board().manhattan());
			StdOut.println("moves:" + snode.moves());
			StdOut.println(snode.board());
		}*/

		return pq.delMin();
	}

	private void makeSolutions(SearchNode node) {
		while (node != null) {
			solutions.push(node.board());
			node = node.prevNode();	
		}
	}

	public Solver(Board initial) {
		if (initial == null)
			throw new java.lang.IllegalArgumentException("initial board is null");

		solutions = new Stack<Board>();
		MinPQ<SearchNode> pq1 = new MinPQ<SearchNode>();
		MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>();
		Board twin = initial.twin();
		SearchNode node1 = new SearchNode(initial, null, 0);
		SearchNode node2 = new SearchNode(twin, null, 0);
		StdOut.println("initial:");
		StdOut.println(initial);
		int i = 0;
		while (true) {
			if (node1.board().isGoal()) {
				isSolvable = true;
				makeSolutions(node1);
				break;
			}
			if (node2.board().isGoal()) {
				isSolvable = false;
				break;
			}
			//StdOut.println("node1:");
			//StdOut.println(node1.board());
			node1 = move(node1, pq1);

			//StdOut.println("solutions:");
			/*StdOut.println("priority:" + (node1.board().manhattan() + node1.moves()));
			StdOut.println("manhattan:" + node1.board().manhattan());
			StdOut.println("moves:" + node1.moves());
			StdOut.println(node1.board());*/
			//StdOut.println("goal:"+ node1.board().isGoal() + " size:" + solutions.size());
			node2 = move(node2, pq2);
			//if (i++ > 100)
			//	break;
		}
	}

	public boolean isSolvable() {
		return isSolvable;
	}

	public int moves() {
		if (!isSolvable())
			return -1;
		return solutions.size() - 1;
	}

	public Iterable<Board> solution() 
	{
		if (!isSolvable())
			return null;
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
