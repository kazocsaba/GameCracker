package hu.kazocsaba.gamecracker.graph.base;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphMatch;
import hu.kazocsaba.gamecracker.graph.GraphResult;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Kazó Csaba
 */
public class DefaultGraphMatch<
		P extends Position<P, M, T>,
		M extends Move<M, T>,
		T extends Transformation<T>> extends GraphMatch<P, M, T> {
	
	private final PointArrayList pointList;

	/**
	 * Creates a new match.
	 * 
	 * @param rootPosition the starting position
	 * @param rootNode the node corresponding to the starting position
	 * @param transformationToGraph the transformation from {@code rootPosition}
	 * to the {@code rootNode}'s position
	 */
	public DefaultGraphMatch(P rootPosition, NormalNode<P,M,T> rootNode, T transformationToGraph) {
		pointList=new PointArrayList(64, rootPosition, rootNode, transformationToGraph);
	}

	@Override
	public Iterator<Point<P, M, T>> iterator() {
		return pointList.iterator();
	}

	@Override
	public int getLength() {
		return pointList.size()-1;
	}

	@Override
	public int getMoveCount() {
		return pointList.getLast().moves.size();
	}

	@Override
	public M getMove(int index) {
		return pointList.getLast().moves.get(index);
	}

	@Override
	public P getPosition() {
		return pointList.getLast().getPosition();
	}

	@Override
	public void move(M move) {
		int moveIndex = pointList.getLast().moves.indexOf(move);
		if (moveIndex == -1) {
			throw new IllegalArgumentException("Invalid move: " + move);
    }
		move(move, moveIndex);
	}

	@Override
	public void move(int moveIndex) {
		move(pointList.getLast().moves.get(moveIndex), moveIndex);
	}
	
	private void move(M move, int moveIndex) {
		PointBase currentPoint = pointList.getLast();
		
		/*
		 * Note that moveIndex is the index in the current position, not the graph position!
		 * If there is a transformation, then the order of the transformed moves is independent
		 * from the order of the original moves.
		 */
		
		Node<P,M,T> childNode=currentPoint.node.getNextNode(move.transform(currentPoint.transformationToGraph));
		NormalNode<P,M,T> nextNormal;
		T newTransformation=currentPoint.transformationToGraph;
		
		if (childNode.isNormal()) {
			nextNormal=childNode.asNormalNode();
		} else {
			TransformationNode<P,M,T> childTrans=childNode.asTransformationNode();
			newTransformation=newTransformation.compose(childTrans.getTransformation());
			nextNormal=childTrans.getLinkedNode();
		}
		
		pointList.add(moveIndex, currentPoint.position.move(move), nextNormal, newTransformation);
	}

	@Override
	public void back() {
		pointList.back();
	}

	@Override
	public GraphResult getResult() {
		PointBase currentPoint = pointList.getLast();
		
		GraphResult graphResult=currentPoint.node.getResult();
		if (currentPoint.transformationToGraph.isPlayerSwitching()) {
			graphResult=graphResult.getOther();
    }
		return graphResult;
	}
	
	/*
	 * A dynamic array that reuses PointBase instances.
	 */
	private final class PointArrayList {
		/**
		 * The array containing PointBase instances. None of the elements are null.
		 * They are created when the array is allocated.
		 */
		private Object[] points;
		/** The number of valid points. */
		private int length;
		
		private PointArrayList(int initialCapacity, P position, NormalNode<P,M,T> node, T transformationToGraph) {
			points=new Object[initialCapacity];
			length=0;
			fillNewArrayElements();
			
			// add/initialize the first point
			length=1;
			getLast().set(position, node, transformationToGraph);
		}
		
		/**
		 * Doubles the length of the array.
		 */
		private void grow() {
			Object[] newPoints=new Object[2*points.length];
			System.arraycopy(points, 0, newPoints, 0, length);
			points=newPoints;
			fillNewArrayElements();
		}
		
		private void fillNewArrayElements() {
			for (int i=length; i<points.length; i++) {
				points[i]=new PointBase();
      }
		}
		
		@SuppressWarnings("unchecked")
		public PointBase getLast() {
			/*
			 * Length will never actually be zero, the root Point is added
			 * in the constructor and is never allowed to be
			 * removed.
			 */
			return (PointBase)points[length-1];
		}
		
		public int size() {
			return length;
		}
		
		public Iterator<Point<P,M,T>> iterator() {
			return new PointIterator();
		}

		public void add(int moveIndex, P position, NormalNode<P,M,T> node, T transformation) {
			if (length == points.length) {
				grow();
      }
			getLast().moveIndex = moveIndex;
			length++;
			getLast().set(position, node, transformation);
		}

		public void back() {
			if (length == 1) {
				throw new IllegalStateException("No move to undo");
      }
			length--;
			getLast().moveIndex = -1;
		}
		
		private class PointIterator implements Iterator<Point<P,M,T>> {
			private int nextIndex=0;

			@Override
			public boolean hasNext() {
				return nextIndex < length;
			}

			@SuppressWarnings(value = "unchecked")
			@Override
			public Point<P, M, T> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
        }
				return (Point<P, M, T>) points[nextIndex++];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		}
	}
	
	private final class PointBase implements Point<P, M, T> {
		/** The position. */
		private P position;
		
		/** The corresponding normal node. */
		private NormalNode<P,M,T> node;
		
		/**
		 * The transformation from this position to the corresponding
		 * node's position.
		 */
		private T transformationToGraph;
		
		/**
		 * The index of the move chosen in this position;
		 * -1 if this is the last position.
		 */
		private int moveIndex;
		
		/**
		 * The valid moves of the current position; retrieved and cached
		 * from {@link Position#getMoves()}.
		 */
		private List<M> moves;

		@Override
		public P getPosition() {
			return position;
		}

		@Override
		public M getMove() {
			if (moveIndex == -1) {
				return null;
      }
			return moves.get(moveIndex);
		}
		
		/**
		 * Fills in the values assuming that this is the last position
		 * of the match.
		 */
		public void set(P position, NormalNode<P,M,T> node, T transformationToGraph) {
			this.position = position;
			this.node = node;
			this.transformationToGraph = transformationToGraph;
			moveIndex = -1;
			moves = position.getMoves();
		}
	}
}
