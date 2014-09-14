package hu.kazocsaba.gamecracker.graph.base;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;

/**
 *
 * @author Kazó Csaba
 */
public interface NormalNode<
		P extends Position<P,M,T>,
		M extends Move<M,T>,
		T extends Transformation<T>> extends Node<P,M,T> {

	/**
	 * Returns a child node of this normal node. If the graph does not currently
	 * contain the requested child, it should be added; this method should
	 * never return {@code null}.
	 * 
	 * @param move the move defining the edge to the next node
	 * @return the child node
   * @throws IllegalArgumentException if the move is not valid in the position corresponding to this
   *     node
	 */
	Node<P,M,T> getNextNode(M move);
	
	/**
	 * Returns the result of this node.
	 * 
	 * @return the graph result of this node
	 */
	GraphResult getResult();

	@Override
	default boolean isNormal() {
		return true;
	}

	@Override
	default NormalNode<P, M, T> asNormalNode() {
		return this;
	}

	@Override
	default TransformationNode<P, M, T> asTransformationNode() {
		throw new ClassCastException("Not a transformation node");
	}
}
