package hu.kazocsaba.gamecracker.game.reversi;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.SwitchableSquareSymmetry;
import hu.kazocsaba.gamecracker.game.TransformationSerializer;

/**
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
	
}
