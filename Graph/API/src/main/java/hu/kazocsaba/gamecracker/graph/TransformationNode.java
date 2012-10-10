package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Transformation;

/**
 * A link to a different node via a transformation.
 *
 * @author Kazó Csaba
 */
public abstract class TransformationNode implements Node {
	/**
	 * Returns the transformation that needs to be applied to the position in this node.
	 *
	 * @return the transformation of this node
	 */
	public abstract Transformation getTransformation();

	/**
	 * Returns the node this transformation node references. Transforming this node's position with this node's
	 * transformation results in the position of the linked node. The results of the two nodes are the same, unless the
	 * transformation is player-switching; then the results are switched as well.
	 *
	 * @return the node referenced by this transformation node
	 */
	public abstract NormalNode getLinkedNode();
}
