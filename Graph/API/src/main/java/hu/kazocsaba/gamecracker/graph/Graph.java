package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Game;
import hu.kazocsaba.gamecracker.game.Move;
import hu.kazocsaba.gamecracker.game.Position;
import hu.kazocsaba.gamecracker.game.Transformation;

/**
 * A game state graph.
 *
 * @author Kazó Csaba
 */
public abstract class Graph<
    P extends Position<P, M, T>, M extends Move<M, T>, T extends Transformation<T>> {

  /**
   * Returns the game whose states this graph contains.
   *
   * @return the game
   */
  public abstract Game<P, M, T> getGame();

  /**
   * Creates a new match on this graph. The initial position of the match is the root position of
   * this graph, and it contains no moves.
   *
   * @return a new match on this graph
   */
  public abstract GraphMatch<P, M, T> createMatch();
}
