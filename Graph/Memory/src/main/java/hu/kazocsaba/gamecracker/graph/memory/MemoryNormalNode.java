package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.GameStatus;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.GraphResultComputer;
import hu.kazocsaba.gamecracker.graph.base.Node;
import hu.kazocsaba.gamecracker.graph.base.NormalNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Kazó Csaba
 */
class MemoryNormalNode<
		P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>>
    extends MemoryNode<P, M, T> implements NormalNode<P, M, T> {

  private final MemoryGraph<P, M, T> graph;
  private final List<MemoryNode<P, M, T>> children;

  MemoryNormalNode(MemoryGraph<P, M, T> graph, P position) {
    super(position);
    this.graph = Objects.requireNonNull(graph);
    int childCount = position.getMoves().size();
    GameStatus status = position.getStatus();
    if (childCount == 0) {
      if (!status.isFinal()) {
        throw new AssertionError("Non-final position has no valid moves");
      }
      children = Collections.emptyList();
      result = GraphResult.from(status);
    } else {
      if (status.isFinal()) {
        throw new AssertionError("Final position has valid moves");
      }
      children = new ArrayList<>(Collections.<MemoryNode<P, M, T>>nCopies(childCount, null));
      result = GraphResult.UNKNOWN;
    }
  }

  @Override
  public GraphResult getResult() {
    return result;
  }

  @Override
  public Node<P, M, T> getNextNode(M move) {
    return graph.getNextNode(this, move);
  }

  void setChild(int index, MemoryNode<P, M, T> node) {
    children.set(index, node);
    node.parents.add(this);
    if (!result.isKnown()) {
      recomputeResult(this);
    }
  }

  private static <
      P extends Position<P, M, T>,
      M extends Move<M, T>,
      T extends Transformation<T>> GraphResult computeResult(MemoryNormalNode<P, M, T> node) {
    GraphResultComputer resultComputer =
        GraphResultComputer.start(node.position.getStatus().getCurrentPlayer());
    for (MemoryNode<P, M, T> n : node.children) {
      resultComputer = resultComputer.withMove(n == null ? GraphResult.UNKNOWN : n.result);
    }
    return resultComputer.get();
  }

  private static <
      P extends Position<P, M, T>,
      M extends Move<M, T>,
      T extends Transformation<T>> void recomputeResult(MemoryNode<P, M, T> node) {
    if (node instanceof MemoryNormalNode) {
      recomputeResultNm((MemoryNormalNode<P, M, T>) node);
    } else {
      recomputeResultTr((MemoryTransformationNode<P, M, T>) node);
    }
  }

  private static <E> E removeOne(Set<E> set) {
    Iterator<E> iterator = set.iterator();
    E result = iterator.next();
    iterator.remove();
    return result;
  }

  private static <
      P extends Position<P, M, T>,
      M extends Move<M, T>,
      T extends Transformation<T>> void recomputeResults(Set<MemoryNode<P, M, T>> nodes) {
    do {
      MemoryNode<P, M, T> node = removeOne(nodes);
      GraphResult result = node.result;
      if (result.isKnown()) {
        continue;
      }
      if (node instanceof MemoryNormalNode) {
        result = computeResult((MemoryNormalNode<P, M, T>) node);
      } else {
        MemoryTransformationNode<P, M, T> trNode = (MemoryTransformationNode<P, M, T>) node;
        result = trNode.linkedNode.result;
        if (trNode.transformation.isPlayerSwitching()) {
          result = result.getOther();
        }
      }
      if (result != node.result) {
        node.result = result;
        nodes.addAll(node.parents);
      }
    } while (!nodes.isEmpty());
  }

  private static <
      P extends Position<P, M, T>,
      M extends Move<M, T>,
      T extends Transformation<T>> void recomputeResultTr(MemoryTransformationNode<P, M, T> node) {
    GraphResult result = node.linkedNode.result;
    if (node.transformation.isPlayerSwitching()) {
      result = result.getOther();
    }
    if (node.result != result) {
      node.result = result;
      if (node.parents.size() == 1) {
        recomputeResult(node.parents.get(0));
      } else {
        recomputeResults(new HashSet<>(node.parents));
      }
    }
  }

  private static <
      P extends Position<P, M, T>,
      M extends Move<M, T>,
      T extends Transformation<T>> void recomputeResultNm(MemoryNormalNode<P, M, T> node) {
    GraphResult newResult = computeResult(node);
    if (newResult != node.result) {
      node.result = newResult;
      if (node.parents.size() == 1) {
        recomputeResult(node.parents.get(0));
      } else if (node.parents.size() > 0) {
        recomputeResults(new HashSet<>(node.parents));
      }
    }

  }

  public int getChildCount() {
    return children.size();
  }

  public MemoryNode<P, M, T> getChild(int index) {
    return children.get(index);
  }

}
