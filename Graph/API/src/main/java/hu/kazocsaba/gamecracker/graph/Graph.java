package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 * A game state graph.
 *
 * @author Kazó Csaba
 */
public abstract class Graph<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> {
	/**
	 * Returns the game whose states this graph contains.
	 *
	 * @return the game
	 */
	public abstract Game<P,M,T> getGame();

	/**
	 * Expands the graph by adding the edge corresponding to a move from an existing node. The graph must not already
	 * contain that edge: {@code node.getChild(moveIndex)} must return {@code null}. The child node corresponding to the
	 * move may be an entirely new {@code NormalNode}, an existing {@code NormalNode} with the proper position, or a
	 * {@code TransformationNode} referencing an existing normal node.
	 *
	 * @param node the parent node
	 * @param moveIndex the index of the move to add
	 * @return the child node at index {@code moveIndex} after the edge has been added
	 * @throws IndexOutOfBoundsException if {@code moveIndex} is out of range
	 * ({@code moveIndex < 0 || moveIndex >= node.getChildCount()})
	 * @throws IllegalArgumentException if {@code node.getChild(moveIndex) != null}
	 */
	public abstract Node<P,M,T> expand(NormalNode<P,M,T> node, int moveIndex);
	
	/**
	 * Returns the root node of the graph. The root is always a normal node containing the initial position
	 * of the game.
	 * 
	 * @return the root node
	 */
	public abstract NormalNode<P,M,T> getRoot();
}
