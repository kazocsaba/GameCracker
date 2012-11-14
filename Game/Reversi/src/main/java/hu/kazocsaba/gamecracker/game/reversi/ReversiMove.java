package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 * A move in Reversi.
 * @author Kazó Csaba
 */
public abstract class ReversiMove<M extends ReversiMove<M>> extends Move<M, SwitchableSquareSymmetry> {
	private final byte data;
	
	ReversiMove(int x, int y) {
		data = (byte)(x | (y<<4));
	}
	
	/**
	 * Returns the x coordinate of the move.
	 * 
	 * @return the index of the column where the move is made
	 */
	public final int getX() {
		return data & 0x0F;
	}
	
	/**
	 * Returns the y coordinate of the move.
	 * 
	 * @return the index of the row where the move is made
	 */
	public final int getY() {
		return (data & 0xF0) >> 4;
	}

	@Override
	public int hashCode() {
		return data;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		@SuppressWarnings("unchecked")
		final ReversiMove<M> other = (ReversiMove<M>)obj;
		if (this.data != other.data) return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf((char)('a'+getX()))+String.valueOf((char)('1'+getY()));
	}
	
}
