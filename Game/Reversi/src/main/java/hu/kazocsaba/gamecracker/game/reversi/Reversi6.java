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
public class Reversi6 extends Reversi<Reversi6Position, Reversi6Move> {
	private final Reversi6Position initial;

	public Reversi6() {
		super(6);
		ReversiBoard board=new ReversiBoard();
		board.setCell(3, 3, Player.WHITE);
		board.setCell(2, 2, Player.WHITE);
		board.setCell(2, 3, Player.BLACK);
		board.setCell(3, 2, Player.BLACK);
		initial=new Reversi6Position(GameStatus.WHITE_MOVES, board);
	}
	
	@Override
	public Reversi6Position getInitialPosition() {
		return initial;
	}

	@Override
	public String getName() {
		return "Reversi 6x6";
	}

	@Override
	public PositionSerializer<Reversi6Position> getPositionSerializer() {
		return Reversi6Position.SERIALIZER;
	}

	@Override
	public CategoryFunction<Reversi6Position> getCategoryFunction() {
		return Reversi6Position.CATEGORY_FUNCTION;
	}

	@Override
	public GameComponent<Reversi6Position, Reversi6Move, SwitchableSquareSymmetry> createComponent() {
		return new Reversi6Component(getInitialPosition());
	}

	@Override
	Reversi6Move getMove(int x, int y) {
		return Reversi6Move.get(x, y);
	}
	
}
