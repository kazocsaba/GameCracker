package hu.kazocsaba.gamecracker.graph.memory;

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
class MemoryGraphMatch<P extends Position<P,M,T>, M extends Move<M,T>, T extends Transformation<T>> extends GraphMatch<P, M, T> {
	private final MemoryGraph<P, M, T> graph;
	
	private Object[] positions;
	/* The number of actually valid positions. */
	private int length;

	public MemoryGraphMatch(final MemoryGraph<P, M, T> graph) {
		this.graph = graph;
		positions = new Object[16];
		for (int i = 0; i < positions.length; i++)
			positions[i] = new MemoryGraphMatchPosition();
		length = 1;
		getLastPosition().set(graph.root.position, graph.root, graph.game.getIdentityTransformation());
	}

	class MemoryGraphMatchPosition implements Point<P, M, T> {

		private P position;
		private int moveIndex;
		private List<M> moves;
		private MemoryNormalNode<P, M, T> node;
		private T transformation;

		@Override
		public P getPosition() {
			return position;
		}

		@Override
		public M getMove() {
			if (moveIndex == -1)
				return null;
			return moves.get(moveIndex);
		}

		void set(P position, MemoryNormalNode<P, M, T> node, T transformation) {
			this.position = position;
			this.node = node;
			this.transformation = transformation;
			moveIndex = -1;
			moves = position.getMoves();
		}
	}

	class PointIterator implements Iterator<Point<P, M, T>> {

		int nextIndex = 0;

		@Override
		public boolean hasNext() {
			return nextIndex < length;
		}

		@SuppressWarnings(value = "unchecked")
      @Override
		public Point<P, M, T> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return (Point<P, M, T>) positions[nextIndex++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@SuppressWarnings(value = "unchecked")
	private MemoryGraphMatchPosition getLastPosition() {
		return (MemoryGraphMatchPosition) positions[length - 1];
	}

	@Override
	public int getLength() {
		return length-1;
	}

	@Override
	public M getMove(int index) {
		return getLastPosition().moves.get(index);
	}

	@Override
	public GraphResult getResult() {
		if (getLastPosition().transformation.isPlayerSwitching())
			return getLastPosition().node.result.getOther();
		else
			return getLastPosition().node.result;
	}

	@Override
	public P getPosition() {
		return getLastPosition().position;
	}

	@Override
	public void move(M move) {
		int moveIndex = getLastPosition().moves.indexOf(move);
		if (moveIndex == -1)
			throw new IllegalArgumentException("Invalid move: " + move);
		move(move, moveIndex);
	}

	@Override
	public void move(int moveIndex) {
		move(getLastPosition().moves.get(moveIndex), moveIndex);
	}

	@Override
	public int getMoveCount() {
		return getLastPosition().moves.size();
	}

	private void move(M move, int moveIndex) {
		if (positions.length == length) {
			Object[] newPositions = new Object[2 * length];
			System.arraycopy(positions, 0, newPositions, 0, length);
			for (int i = length; i < newPositions.length; i++)
				newPositions[i] = new MemoryGraphMatchPosition();
			positions = newPositions;
		}
		MemoryNode<P, M, T> childNode = graph.getNextNode(getLastPosition().node, move.transform(getLastPosition().transformation));
		getLastPosition().moveIndex = moveIndex;
		T newTrans = getLastPosition().transformation;
		MemoryNormalNode<P, M, T> nextNormal;
		if (childNode instanceof MemoryTransformationNode) {
			MemoryTransformationNode<P, M, T> childTrans = (MemoryTransformationNode<P, M, T>) childNode;
			newTrans = newTrans.compose(childTrans.transformation);
			nextNormal = childTrans.linkedNode;
		} else {
			nextNormal = (MemoryNormalNode<P, M, T>) childNode;
		}
		length++;
		getLastPosition().set(nextNormal.position.transform(newTrans.inverse()), nextNormal, newTrans);
	}

	@Override
	public void back() {
		if (length == 1)
			throw new IllegalStateException("No move to undo");
		length--;
		getLastPosition().moveIndex = -1;
	}

	@Override
	public Iterator<Point<P, M, T>> iterator() {
		return new PointIterator();
	}

}
