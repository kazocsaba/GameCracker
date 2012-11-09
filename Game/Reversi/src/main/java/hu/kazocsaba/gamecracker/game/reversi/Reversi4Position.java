package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.GameStatus;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4Position extends ReversiPosition<Reversi4Position, Reversi4Move> {

	Reversi4Position(GameStatus status, ReversiBoard board) {
		super(status, board);
	}

	@Override
	int getSize() {
		return 4;
	}

	@Override
	Reversi4Move getMove(int x, int y) {
		return Reversi4Move.get(x, y);
	}

	@Override
	Reversi4Position create(ReversiBoard board, GameStatus status) {
		return new Reversi4Position(status, board);
	}

}
