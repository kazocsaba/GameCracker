package hu.kazocsaba.gamecracker.graph.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.Node;

/**
 *
 * @author Kazó Csaba
 */
abstract class MemoryNode<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> implements Node<P, M, T> {
	final P position;
	List<Node<P,M,T>> parents=new ArrayList<>(2);
	GraphResult result;

	MemoryNode(P position) {
		this.position = Objects.requireNonNull(position);
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

	@Override
	public GraphResult getResult() {
		return result;
	}
	
}
