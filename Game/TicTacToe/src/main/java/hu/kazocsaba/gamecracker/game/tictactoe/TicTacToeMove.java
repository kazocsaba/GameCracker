package hu.kazocsaba.gamecracker.game.tictactoe;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.SquareTransformation;

/**
 * A move in Tic Tac Toe.
 * @author Kazó Csaba
 */
public class TicTacToeMove extends Move<TicTacToeMove, SquareTransformation> {
	private int x, y;

	/**
	 * Returns a move where the player marks the cell at the intersection of column x and row y.
	 * @param x the column index
	 * @param y the row index
	 */
	public static TicTacToeMove get(int x, int y) {
		return new TicTacToeMove(x, y);
	}
	
	private TicTacToeMove(int x, int y) {
		if (x<0 || x>=3) throw new IllegalArgumentException("Invalid x: "+x);
		if (y<0 || y>=3) throw new IllegalArgumentException("Invalid y: "+y);
		this.x = x;
		this.y = y;
	}
	
	@Override
	public TicTacToeMove transform(SquareTransformation t) {
		return new TicTacToeMove(t.transformX(x, y, 3), t.transformY(x, y, 3));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return String.format("%s%s", 'a'+x, y+1);
	}

	@Override
	public int hashCode() {
		return 4*x+y;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TicTacToeMove)) return false;
		final TicTacToeMove other = (TicTacToeMove)obj;
		return x==other.x && y==other.y;
	}

}
