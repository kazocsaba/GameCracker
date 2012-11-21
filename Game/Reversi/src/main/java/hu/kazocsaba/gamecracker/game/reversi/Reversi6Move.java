package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 * A Reversi move on a 6x6 board.
 * 
 * @author Kazó Csaba
 */
public class Reversi6Move extends ReversiMove<Reversi6Move> {
	Reversi6Move(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Returns the move placing a token on the cell identified by the arguments.
	 * 
	 * @param x the column index of the cell
	 * @param y the row index of the cell
	 * @return the move
	 * @throws IndexOutOfBoundsException if either index is invalid
	 */
	public static Reversi6Move get(int x, int y) {
		if (x<0 || x>=6) throw new IndexOutOfBoundsException("Invalid x: "+x);
		if (y<0 || y>=6) throw new IndexOutOfBoundsException("Invalid y: "+y);
		return new Reversi6Move(x, y);
	}

	@Override
	public Reversi6Move transform(SwitchableSquareSymmetry t) {
		return get(t.transformX(getX(), getY(), 6), t.transformY(getX(), getY(), 6));
	}

}
