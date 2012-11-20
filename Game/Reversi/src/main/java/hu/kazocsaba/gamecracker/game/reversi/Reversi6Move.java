package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi6Move extends ReversiMove<Reversi6Move> {
	Reversi6Move(int x, int y) {
		super(x, y);
	}
	
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
