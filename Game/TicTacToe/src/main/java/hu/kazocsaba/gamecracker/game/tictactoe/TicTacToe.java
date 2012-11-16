package hu.kazocsaba.gamecracker.game.tictactoe;

import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.GameComponent;
import hu.kazocsaba.gamecracker.game.PositionSerializer;
import hu.kazocsaba.gamecracker.game.SquareSymmetry;
import hu.kazocsaba.gamecracker.game.TransformationSerializer;

/**
 * The game Tic Tac Toe.
 * @author Kazó Csaba
 */
public class TicTacToe extends Game<TicTacToePosition, TicTacToeMove, SquareSymmetry> {
	private static final TicTacToePosition INITIAL=new TicTacToePosition();

	@Override
	public String getName() {
		return "Tic Tac Toe";
	}
	
	@Override
	public TicTacToePosition getInitialPosition() {
		return INITIAL;
	}

	@Override
	public CategoryFunction<TicTacToePosition> getCategoryFunction() {
		return TicTacToePosition.MOVE_COUNT_CATEGORY;
	}

	@Override
	public int getMaxPossibleMoves() {
		return 9;
	}

	@Override
	public PositionSerializer<TicTacToePosition> getPositionSerializer() {
		return TicTacToePosition.SERIALIZER;
	}

	@Override
	public TransformationSerializer<SquareSymmetry> getTransformationSerializer() {
		return SquareSymmetry.SERIALIZER;
	}

	@Override
	public GameComponent<TicTacToePosition, TicTacToeMove, SquareSymmetry> createComponent() {
		return new TicTacToeComponent(getInitialPosition());
	}
}
