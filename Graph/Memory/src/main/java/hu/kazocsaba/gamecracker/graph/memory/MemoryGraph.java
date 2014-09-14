package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.Graph;
import hu.kazocsaba.gamecracker.graph.GraphMatch;
import hu.kazocsaba.gamecracker.graph.base.DefaultGraphMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An entirely in-memory Graph implementation.
 *
 * @author Kazó Csaba
 */
public class MemoryGraph<
    P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>>
    extends Graph<P, M, T> {

  final Game<P, M, T> game;
  final MemoryNormalNode<P, M, T> root;
  private int size;
  private final Map<Long, List<MemoryNormalNode<P, M, T>>> categories = new HashMap<>();

  public MemoryGraph(Game<P, M, T> game) {
    this.game = Objects.requireNonNull(game);
    root = new MemoryNormalNode<>(this, game.getInitialPosition());
    size = 1;
  }

  @Override
  public Game<P, M, T> getGame() {
    return game;
  }

  MemoryNode<P, M, T> getNextNode(MemoryNormalNode<P, M, T> node, M move) {
    int moveIndex = node.position.getMoves().indexOf(move);
    if (node.getChild(moveIndex) != null) {
      return node.getChild(moveIndex);
    }
    P nextPosition = node.position.move(move);
    long category = game.getCategoryFunction().category(nextPosition);

    /*
     * We search the category list of the category of the new position, and try to find an
     * equivalent position.
     */
    MemoryNode<P, M, T> newNode = null;
    List<MemoryNormalNode<P, M, T>> categoryList = categories.get(category);
    if (categoryList == null) {
      /*
       * This is a brand new category, there can be no match; we will need a new NormalNode.
       */
      categoryList = new ArrayList<>(4);
      categories.put(category, categoryList);
    } else {
      MemoryNormalNode<P, M, T> match = null;
      T trans = null;
      for (MemoryNormalNode<P, M, T> chainNode : categoryList) {
        trans = nextPosition.getTransformationTo(chainNode.position);
        if (trans != null) {
          match = chainNode;
          break;
        }
      }
      if (match != null) {
        /*
         * We have found that applying trans to match.getPosition produces the new position.
         */
        if (trans.isIdentity()) {
          /*
           * The matching node itself can be used, since it contains the same position.
           */
          newNode = match;
        } else {
          /*
           * The transformation is not identity, we need a transformation node with trans. Before we
           * create one, let us check if a suitable transformation node parent of our match already
           * exists.
           */
          for (int i = 0; i < match.parents.size(); i++) {
            MemoryNode<P, M, T> parent = match.parents.get(i);
            if (parent instanceof MemoryTransformationNode
                && parent.position.equals(nextPosition)) {
              /*
               * We have found the new position in the graph, in a transformation node.
               */
              newNode = parent;
              break;
            }
          }
          if (newNode == null) {
            /*
             * An exact match for the position is not found, so we add it in a transformation node.
             */
            newNode = new MemoryTransformationNode<>(trans, match, nextPosition);
            size++;
          }
        }
      }
    }
    if (newNode == null) {
      /*
       * No match has been found in the graph, so a new normal node is created for the position.
       */
      MemoryNormalNode<P, M, T> newNormalNode = new MemoryNormalNode<>(this, nextPosition);
      newNode = newNormalNode;
      categoryList.add(newNormalNode);
      size++;
    }
    /*
     * Hook up the node to its new parent. If its result is known (either because it contains a
     * final position, or because it is an existing node found in the graph), this function will
     * trigger a recalculation of the result towards the root.
     */
    node.setChild(moveIndex, newNode);
    return newNode;
  }

  public int size() {
    return size;
  }

  @Override
  public GraphMatch<P, M, T> createMatch() {
    return new DefaultGraphMatch<>(root.position, root, game.getIdentityTransformation());
  }
}
