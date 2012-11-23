package hu.kazocsaba.gamecracker.graph.memory;

import java.util.Objects;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.NormalNode;
import hu.kazocsaba.gamecracker.graph.TransformationNode;

/**
 *
 * @author Kazó Csaba
 */
class MemoryTransformationNode<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends MemoryNode<P,M,T> implements TransformationNode<P,M,T> {
	private final T transformation;
	private final NormalNode<P,M,T> linkedNode;

	MemoryTransformationNode(T transformation, NormalNode<P,M,T> linkedNode, P position) {
		super(position);
		this.transformation = Objects.requireNonNull(transformation);
		this.linkedNode = Objects.requireNonNull(linkedNode);
		result=transformation.isPlayerSwitching() ? linkedNode.getResult().getOther() : linkedNode.getResult();
	}
	
	
	@Override
	public T getTransformation() {
		return transformation;
	}

	@Override
	public NormalNode<P,M,T> getLinkedNode() {
		return linkedNode;
	}

}
