package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.GameComponent;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Player;
import hu.kazocsaba.gamecracker.game.PositionSerializer;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;

/**
 *
 * @author Kazó Csaba
 */
public class Reversi4 extends Reversi<Reversi4Position, Reversi4Move> {
	private final Reversi4Position initial;

	public Reversi4() {
		super(4);
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

	@Override
	public PositionSerializer<Reversi4Position> getPositionSerializer() {
		return Reversi4Position.SERIALIZER;
	}

	@Override
	public CategoryFunction<Reversi4Position> getCategoryFunction() {
		return Reversi4Position.CATEGORY_FUNCTION;
	}

	@Override
	public GameComponent<Reversi4Position, Reversi4Move, SwitchableSquareSymmetry> createComponent() {
		return new Reversi4Component(getInitialPosition());
	}

	@Override
	Reversi4Move getMove(int x, int y) {
		return Reversi4Move.get(x, y);
	}
	
}
