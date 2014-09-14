package hu.kazocsaba.gamecracker.graph.memory;

import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;
import hu.kazocsaba.gamecracker.graph.GraphResult;
import hu.kazocsaba.gamecracker.graph.base.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Kazó Csaba
 */
abstract class MemoryNode<
		P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>>
    implements Node<P, M, T> {

  final P position;
  List<MemoryNode<P, M, T>> parents = new ArrayList<>(2);
  GraphResult result;

  MemoryNode(P position) {
    this.position = Objects.requireNonNull(position);
  }

}
