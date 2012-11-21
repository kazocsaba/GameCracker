package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 * A Reversi move on a 4x4 board.
 * 
 * @author Kazó Csaba
 */
public class Reversi4Move extends ReversiMove<Reversi4Move> {
	Reversi4Move(int x, int y) {
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
	public static Reversi4Move get(int x, int y) {
		if (x<0 || x>=4) throw new IndexOutOfBoundsException("Invalid x: "+x);
		if (y<0 || y>=4) throw new IndexOutOfBoundsException("Invalid y: "+y);
		return new Reversi4Move(x, y);
	}

	@Override
	public Reversi4Move transform(SwitchableSquareSymmetry t) {
		return get(t.transformX(getX(), getY(), 4), t.transformY(getX(), getY(), 4));
	}

}
