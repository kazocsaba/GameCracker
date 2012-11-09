package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Player;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4 extends Game<Reversi4Position, Reversi4Move, SwitchableSquareSymmetry> {
	private final Reversi4Position initial;

	public Reversi4() {
		ReversiBoard board=new ReversiBoard();
		board.setCell(2, 2, Player.WHITE);
		board.setCell(1, 1, Player.WHITE);
		board.setCell(1, 2, Player.BLACK);
		board.setCell(2, 1, Player.BLACK);
		initial=new Reversi4Position(GameStatus.WHITE_MOVES, board);
	}
	
	@Override
	public Reversi4Position getInitialPosition() {
		return initial;
	}

	@Override
	public String getName() {
		return "Reversi 4x4";
	}

}
