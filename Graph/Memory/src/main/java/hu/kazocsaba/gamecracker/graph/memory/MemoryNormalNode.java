package hu.kazocsaba.gamecracker.graph.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.GraphResultComputer;
import hu.kazocsaba.gamecracker.graph.Node;
import hu.kazocsaba.gamecracker.graph.NormalNode;

/**
 *
 * @author Kazó Csaba
 */
class MemoryNormalNode<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends NormalNode<P,M,T> {
	private GraphResult result;
	private P position;
	private List<Node<P,M,T>> parents=new ArrayList<>(2);
	private List<Node<P,M,T>> children;

	MemoryNormalNode(P position) {
		this.position=Objects.requireNonNull(position);
		int childCount=position.getMoves().size();
		GameStatus status=position.getStatus();
		if (childCount==0) {
			if (!status.isFinal()) throw new AssertionError("Non-final position has no valid moves");
			children=Collections.emptyList();
			result=GraphResult.from(status);
		} else {
			if (status.isFinal()) throw new AssertionError("Final position has valid moves");
			children=new ArrayList<>(Collections.<Node<P,M,T>>nCopies(childCount, null));
			result=GraphResult.UNKNOWN;
		}
	}

	void setChild(int index, Node<P,M,T> node) {
		children.set(index, node);
		if (node instanceof MemoryNormalNode)
			((MemoryNormalNode<P,M,T>)node).parents.add(this);
		else
			((MemoryTransformationNode<P,M,T>)node).parents.add(this);
		if (!result.isKnown()) {
			recomputeResult(this);
		}
	}
	private static <P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> GraphResult computeResult(MemoryNormalNode<P,M,T> node) {
		GraphResultComputer resultComputer = GraphResultComputer.start(node.position.getStatus().getCurrentPlayer());
		for (Node<P,M,T> n: node.children)
			resultComputer=resultComputer.withMove(n==null ? GraphResult.UNKNOWN : n.getResult());
		return resultComputer.get();
	}
	private static <P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> void recomputeResult(Node<P,M,T> node) {
		if (node instanceof NormalNode)
			recomputeResultNm((MemoryNormalNode<P,M,T>)node);
		else
			recomputeResultTr((MemoryTransformationNode<P,M,T>)node);
	}
	private static <P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> void recomputeResults(List<Node<P,M,T>> nodes) {
		do {
			Node<P,M,T> next=nodes.remove(0);
			if (next instanceof NormalNode) {
				MemoryNormalNode<P,M,T> node=(MemoryNormalNode<P,M,T>)next;
				if (!node.getResult().isKnown()) {
					GraphResult newResult=computeResult(node);
					if (newResult!=node.getResult()) {
						node.result=newResult;
						for (Node<P,M,T> parent: node.parents)
							if (!nodes.contains(parent)) nodes.add(parent);
					}
				}
			} else {
				MemoryTransformationNode<P,M,T> node=(MemoryTransformationNode<P,M,T>)next;
				GraphResult newResult=node.getLinkedNode().getResult();
				if (node.getTransformation().isPlayerSwitching())
					newResult=newResult.getOther();
				if (node.getResult()!=newResult) {
					node.result=newResult;
					for (Node<P,M,T> parent: node.parents)
						if (!nodes.contains(parent)) nodes.add(parent);
				}
			}
		} while (!nodes.isEmpty());
	}
	private static <P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> void recomputeResultTr(MemoryTransformationNode<P,M,T> node) {
		GraphResult result=node.getLinkedNode().getResult();
		if (node.getTransformation().isPlayerSwitching())
			result=result.getOther();
		if (node.getResult()!=result) {
			node.result=result;
			if (node.parents.size()==1)
				recomputeResult(node.parents.get(0));
			else
				recomputeResults(new ArrayList<>(node.parents));
		}
	}
	private static <P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> void recomputeResultNm(MemoryNormalNode<P,M,T> node) {
		GraphResult newResult=computeResult(node);
		if (newResult!=node.result) {
			node.result=newResult;
			if (node.parents.size()==1)
				recomputeResult(node.parents.get(0));
			else if (node.parents.size()>0)
				recomputeResults(new ArrayList<>(node.parents));
		}
		
	}
	
	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public Node<P,M,T> getChild(int index) {
		return children.get(index);
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
