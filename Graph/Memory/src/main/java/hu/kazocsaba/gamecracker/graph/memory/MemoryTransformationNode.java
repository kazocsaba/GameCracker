package hu.kazocsaba.gamecracker.graph.memory;

import java.util.ArrayList;
import java.util.List;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.Node;
import hu.kazocsaba.gamecracker.graph.NormalNode;
import hu.kazocsaba.gamecracker.graph.TransformationNode;

/**
 *
 * @author Kazó Csaba
 */
class MemoryTransformationNode<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends TransformationNode<P,M,T> {
	private final T transformation;
	private final NormalNode<P,M,T> linkedNode;
	private final P position;
	List<Node<P,M,T>> parents=new ArrayList<>(2);
	GraphResult result;

	MemoryTransformationNode(T transformation, NormalNode<P,M,T> linkedNode, P position) {
		this.transformation = transformation;
		this.linkedNode = linkedNode;
		this.position = position;
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

	@Override
	public GraphResult getResult() {
		return result;
	}

	@Override
	public P getPosition() {
		return position;
	}

	@Override
	public int getParentCount() {
		return parents.size();
	}

	@Override
	public Node<P,M,T> getParent(int index) {
		return parents.get(index);
	}

}
