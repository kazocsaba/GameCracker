package hu.kazocsaba.gamecracker.graph.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.Graph;
import hu.kazocsaba.gamecracker.graph.Node;
import hu.kazocsaba.gamecracker.graph.NormalNode;
import hu.kazocsaba.gamecracker.graph.TransformationNode;

/**
 * An entirely in-memory Graph implementation.
 * 
 * @author Kazó Csaba
 */
public class MemoryGraph<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends Graph<P,M,T> {
	private final Game<P,M,T> game;
	private final MemoryNormalNode<P,M,T> root;
	private int size;
	private final Map<Long, List<NormalNode<P,M,T>>> categories=new HashMap<>();

	public MemoryGraph(Game<P,M,T> game) {
		this.game = Objects.requireNonNull(game);
		root=new MemoryNormalNode<>(game.getInitialPosition());
		size=1;
	}

	@Override
	public Game<P,M,T> getGame() {
		return game;
	}

	@Override
	public NormalNode<P,M,T> getRoot() {
		return root;
	}

	@Override
	public Node<P,M,T> expand(NormalNode<P,M,T> node, int moveIndex) {
		MemoryNormalNode<P,M,T> memnode=(MemoryNormalNode<P,M,T>)node;
		if (memnode.getChild(moveIndex)!=null) throw new IllegalArgumentException("Move already expanded");
		P nextPosition=node.getPosition().move(node.getPosition().getMoves().get(moveIndex));
		long category=game.getCategoryFunction().category(nextPosition);
		
		Node<P,M,T> newNode=null;
		List<NormalNode<P,M,T>> categoryList=categories.get(category);
		if (categoryList==null) {
			categoryList=new ArrayList<>(4);
			categories.put(category, categoryList);
		} else {
			NormalNode<P,M,T> match=null;
			T trans=null;
			for (NormalNode<P,M,T> chainNode: categoryList) {
				trans=nextPosition.getTransformationTo(chainNode.getPosition());
				if (trans!=null) {
					match=chainNode;
					break;
				}
			}
			if (match!=null) {
				if (trans.isIdentity())
					newNode=match;
				else {
					// try to find a transformation parent of the match with the correct transformation
					for (int i=0; i<match.getParentCount(); i++) {
						Node<P,M,T> parent=match.getParent(i);
						if (parent instanceof TransformationNode && ((TransformationNode)parent).getTransformation()==trans) {
							newNode=parent;
							break;
						}
					}
					if (newNode==null) {
						newNode=new MemoryTransformationNode<>(trans, match, nextPosition);
						size++;
					}
				}
			}
		}
		if (newNode==null) {
			MemoryNormalNode<P,M,T> newNormalNode=new MemoryNormalNode<>(nextPosition);
			newNode=newNormalNode;
			categoryList.add(newNormalNode);
			size++;
		}
		memnode.setChild(moveIndex, newNode);
		return newNode;
	}

	public int size() {
		return size;
	}
	
}
