package hu.kazocsaba.gamecracker.game.tictactoe;

import hu.kazocsaba.gamecracker.game.CategoryFunction;
import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.PositionSerializer;
import hu.kazocsaba.gamecracker.game.SquareTransformation;
import hu.kazocsaba.gamecracker.game.TransformationSerializer;

/**
 * The game Tic Tac Toe.
 * @author Kazó Csaba
 */
public class TicTacToe extends Game<TicTacToePosition, TicTacToeMove, SquareTransformation> {
	private static final TicTacToePosition INITIAL=new TicTacToePosition();
	
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
	public TransformationSerializer<SquareTransformation> getTransformationSerializer() {
		return SquareTransformation.SERIALIZER;
	}


}
