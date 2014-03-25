package hu.kazocsaba.gamecracker.graph.base;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 *
 * @author Kazó Csaba
 */
public interface TransformationNode<
		P extends Position<P,M,T>,
		M extends Move<M,T>,
		T extends Transformation<T>> extends Node<P,M,T> {
	
	T getTransformation();
	NormalNode<P,M,T> getLinkedNode();

	@Override
	default boolean isNormal() {
		return false;
	}

	@Override
	default NormalNode<P, M, T> asNormalNode() {
		throw new ClassCastException("Not a normal node");
	}

	@Override
	default TransformationNode<P, M, T> asTransformationNode() {
		return this;
	}
}
