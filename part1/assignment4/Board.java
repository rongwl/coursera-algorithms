import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class Board {
	private int[][] board;
	private int n;

	public Board(int[][] blocks)
	{
		if (blocks == null) 
			throw new java.lang.IllegalArgumentException("blocks is null");
		if (blocks.length != blocks[0].length)
			throw new java.lang.IllegalArgumentException("blocks is not n*n");
		
		n = blocks.length;
		boolean blank = false;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (blocks[i][j] == 0) {
					if (blank)
						throw new java.lang.IllegalArgumentException("more than one block in blocks");
					else
						blank = true;
				}
				if (blocks[i][j] > n*n-1 || blocks[i][j] < 0)
					throw new java.lang.IllegalArgumentException("block value is out of range");
			}
		}
		board = blocks;
	}

	public int dimension()
	{
		return n;
	}

	public int hamming()
	{
		int hamming = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] != 0 && board[i][j] != n*i+j+1) 
					hamming++;
			}
		}
		return hamming;
	}

	public int manhattan()
	{
		int manhattan = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] != 0 && board[i][j] != n*i+j+1) {
					int goal_i = (board[i][j] - 1) / n;
					int goal_j = (board[i][j] - 1) % n;
					manhattan += (Math.abs(goal_i-i) + Math.abs(goal_j-j));
				}
			}
		}
		return manhattan;
	}

	public boolean isGoal()
	{
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] != 0 && n*i+j+1 != board[i][j])
					return false;
			}
		}
		return true;
	}

	public Board twin()
	{
		int[][] twinBoard = copyBoard(board);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n-1; j++) {
				if (twinBoard[i][j] != 0 && twinBoard[i][j+1] != 0) {
					int tmp = twinBoard[i][j];
					twinBoard[i][j] = twinBoard[i][j+1];
					twinBoard[i][j+1] = tmp;
					return new Board(twinBoard);
				}
			}
		}
		
		return new Board(twinBoard);
	}

	public boolean equals(Object y)
	{
		if (y == null)
			return false;
		if (!(y instanceof Board))
			return false;
		if (y == this)
			return true;
		if (((Board)y).n != this.n)
			return false;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (this.board[i][j] != ((Board)y).board[i][j])
					return false;
		return true;
	}

	public Iterable<Board> neighbors()
	{
		List<Board> neighborBoards = new ArrayList<Board>();
		int[] mx = {1,-1,0,0};
		int[] my = {0,0,1,-1};
		//StdOut.println("board:");
		//StdOut.println(this);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) {
					for (int k = 0; k < 4; k++) {
						int ni = i + mx[k];
						int nj = j + my[k];
						if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
							int[][] newBoard = copyBoard(board);
							newBoard[i][j] = board[ni][nj];
							newBoard[ni][nj] = 0;
							neighborBoards.add(new Board(newBoard));	
						}				
					}
					return neighborBoards;
				}
			}
		}

		return neighborBoards;
	}

	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(n+"\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				str.append(board[i][j] + " ");
			str.append("\n");
		}

		return str.toString();
	}

	private int[][] copyBoard(int[][] src) {
		int[][] dest = new int[src.length][src.length];
		for(int i=0;i<src.length;i++)
			for(int j=0;j<src.length;j++)
			   	dest[i][j]=src[i][j];
		return dest;
	}

	public static void main(String[] args)
	{
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		StdOut.println("initial:");
		StdOut.println(initial);
		StdOut.println("hamming:" + initial.hamming());
		StdOut.println("manhattan:" + initial.manhattan());
		StdOut.println("goal:" + initial.isGoal());
		StdOut.println("neighbors:");
		for (Board obj : initial.neighbors())
			StdOut.println(obj);

		StdOut.println("twin:");
		Board twin = initial.twin();
		StdOut.println(twin);
		StdOut.println("hamming:" + twin.hamming());
		StdOut.println("manhattan:" + twin.manhattan());
		StdOut.println("goal:" + twin.isGoal());
		StdOut.println("neighbors:");
		for (Board obj : twin.neighbors())
			StdOut.println(obj);

		StdOut.println("equal:"+initial.equals(twin));
	}
}
