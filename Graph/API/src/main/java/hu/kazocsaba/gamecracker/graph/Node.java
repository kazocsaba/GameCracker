package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Position;

/**
 * A node in a graph. Each node is either a {@link NormalNode normal} or a {@link TransformationNode transformation}
 * node.
 * <p>
 * As the graph is traversed, its structure changes. It is the responsibility of the graph to ensure that all the nodes
 * it provides are up-to-date.
 *
 * @author Kazó Csaba
 */
public interface Node {
	/**
	 * Returns what is currently known of the result of the state contained in this node. It is the responsibility of the
	 * graph to update the result of the nodes as subgraphs are expanded and more information becomes available.
	 *
	 * @return the most concrete information on the result of this node
	 */
	public GraphResult getResult();

	/**
	 * Returns the game position this node contains.
	 *
	 * @return the position of this node
	 */
	public Position getPosition();
	
	/**
	 * Returns the number of parent nodes. Parents are the nodes that have an outgoing edge leading to
	 * this node.
	 * 
	 * @return the number of parent nodes
	 */
	public int getParentCount();
	
	/**
	 * Returns the parent node at the specified index.
	 * 
	 * @param index the index of the parent node
	 * @return a parent node
	 * @throws IndexOutOfBoundsException if {@code index} is out of range
	 * ({@code index < 0 || index >= getParentCount()}) 
	 */
	public Node getParent(int index);
}
