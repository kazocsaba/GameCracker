package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.TransformationSerializer;

/**
 * Base class for Reversi games.
 * 
 * @author Kazó Csaba
 */
public abstract class Reversi<P extends ReversiPosition<P,M>, M extends ReversiMove<M>> extends Game<P, M, SwitchableSquareSymmetry> {
	private final int size;

	Reversi(int size) {
		this.size = size;
	}

	@Override
	public int getMaxPossibleMoves() {
		return size*size-4;
	}

	@Override
	public TransformationSerializer<SwitchableSquareSymmetry> getTransformationSerializer() {
		return SwitchableSquareSymmetry.SERIALIZER;
	}

	abstract M getMove(int x, int y);
	
	/**
	 * Parses strings in the form of 'b1' or 'a3'. The first character identifies the column starting from
	 * 'a', and the second character identifies the row starting from '1'.
	 * 
	 * @param s the string to parse
	 * @return the move represented by the string
	 * @throws IllegalArgumentException if the string does not denote a cell on the board
	 */
	@Override
	public M parseMove(String s) {
		if (s.length()!=2) throw new IllegalArgumentException("Only 2-character strings can be parsed");
		int x=s.charAt(0)-'a';
		int y=s.charAt(1)-'1';
		if (x<0 || x>=size) throw new IllegalArgumentException("Invalid column character: '"+x+"'");
		if (y<0 || y>=size) throw new IllegalArgumentException("Invalid row character: '"+y+"'");
		return getMove(x, y);
	}
	
}
