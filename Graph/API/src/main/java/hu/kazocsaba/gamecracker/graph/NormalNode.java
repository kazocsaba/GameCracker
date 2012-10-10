package hu.kazocsaba.gamecracker.graph;

/**
 * A normal node in a game graph. The children of normal nodes correspond to moves available in the game position stored
 * in the node.
 *
 * @author Kazó Csaba
 */
public abstract class NormalNode implements Node {
	/**
	 * Returns the number of child nodes. This function returns the same value as
	 * {@code getPosition().getMoves().size()}, regardless of how many of the child nodes are actually present in the
	 * graph.
	 *
	 * @return the number of child nodes
	 */
	public abstract int getChildCount();

	/**
	 * Returns the child node at the specified index. If the requested node is not present in the graph, {@code null} is
	 * returned.
	 *
	 * @param index the index of the child node
	 * @return a child node, or {@code null} if the child at {@code index} is not present in the graph
	 * @throws IndexOutOfBoundsException if {@code index} is out of range ({@code index < 0 || index >= getChildCount()})
	 */
	public abstract Node getChild(int index);
}
