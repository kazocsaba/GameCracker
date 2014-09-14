package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Match;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 * A match in the context of a graph. All the positions that are encountered in the match are added
 * to the graph if they were missing. The path defined by the match will always have a corresponding
 * path in the graph. The graph result of the current position of the match can be queried with
 * {@link #getResult()}.
 *
 * @author Kazó Csaba
 */
public abstract class GraphMatch<
    P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>>
    implements Match<P, M, T> {

  /**
   * Returns what is known about the result of the final position of this match based on the graph.
   *
   * @return the graph result of the current position
   */
  public abstract GraphResult getResult();
}
