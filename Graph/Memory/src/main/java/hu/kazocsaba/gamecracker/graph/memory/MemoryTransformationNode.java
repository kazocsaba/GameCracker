package hu.kazocsaba.gamecracker.graph.memory;

import java.util.Objects;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 *
 * @author Kazó Csaba
 */
class MemoryTransformationNode<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends MemoryNode<P,M,T> {
	final T transformation;
	final MemoryNormalNode<P,M,T> linkedNode;

	MemoryTransformationNode(T transformation, MemoryNormalNode<P,M,T> linkedNode, P position) {
		super(position);
		this.transformation = Objects.requireNonNull(transformation);
		this.linkedNode = Objects.requireNonNull(linkedNode);
		result=transformation.isPlayerSwitching() ? linkedNode.result.getOther() : linkedNode.result;
	}
}
