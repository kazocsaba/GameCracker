package hu.kazocsaba.gamecracker.game.reversi;

/**
 *
 * @author Kazó Csaba
 */
class Reversi6Component extends ReversiComponent<Reversi6Position, Reversi6Move> {

	Reversi6Component(Reversi6Position position) {
		super(position, 6);
	}

	@Override
	Reversi6Move getMove(int x, int y) {
		return Reversi6Move.get(x, y);
	}

}
