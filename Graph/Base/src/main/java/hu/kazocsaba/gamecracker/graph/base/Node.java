package hu.kazocsaba.gamecracker.graph.base;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 *
 * @author Kazó Csaba
 */
public interface Node<
		P extends Position<P,M,T>,
		M extends Move<M,T>,
		T extends Transformation<T>> {

	boolean isNormal();
	NormalNode<P,M,T> asNormalNode();
	TransformationNode<P,M,T> asTransformationNode();
}
