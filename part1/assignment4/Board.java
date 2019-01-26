import edu.princeton.cs.algs4.MinPQ;
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
			for (int j = 0; j < n-1; j++) {
				if (board[i][j] != n*i+j+1) 
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
					int goal_i = board[i][j] / n;
					int goal_j = board[i][j] % n - 1;
					manhattan += (goal_i - i + goal_j - j);
				}
			}
		}
		return manhattan;
	}

	public boolean isGoal()
	{
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n-1; j++) {
				if (n*i+j+1 != board[i][j])
					return false;
			}
		}
		return true;
	}

	public Board twin()
	{
		int[][] twinBoard = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				twinBoard[i][j] = board[i][j];

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

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 0) {
					for (int k = 0; k < 4; k++) {
						int ni = board[i][j] + mx[k];
						int nj = board[i][j] + my[k];
						if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
							int[][] newBoard = new int[i][j];
							for (int l = 0; l < n; i++)
								for (int m = 0; m < n; j++)
									newBoard[l][m] = board[l][m];

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

	public static void main(String[] args)
	{
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		StdOut.println(initial);
		StdOut.println("hamming:"+initial.hamming());
	}
}
