package hu.kazocsaba.gamecracker.game.reversi;

/**
 *
 * @author Kazó Csaba
 */
class Reversi4Component extends ReversiComponent<Reversi4Position, Reversi4Move> {

	Reversi4Component(Reversi4Position position) {
		super(position, 4);
	}

	@Override
	Reversi4Move getMove(int x, int y) {
		return Reversi4Move.get(x, y);
	}

}
