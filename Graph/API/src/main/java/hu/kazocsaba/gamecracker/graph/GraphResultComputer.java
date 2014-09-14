package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.Player;

/**
 * Utility class to compute the graph result of a node based on the graph results of the moves
 * available in it.
 *
 * <p>Use it by first specifying the player to move, and then update the instance by providing it
 * with results of all possible moves (including unknown results and unexpanded moves).
 * <h4>Usage example</h4>
 * <pre>
 * {@code
 * NormalNode node = ...;
 * GraphResult nodeResult;
 * GameStatus status = node.getPosition().getStatus();
 * if (!status.isFinal()) {
 *    GraphResultComputer resultComputer = GraphResultComputer.start(status.getCurrentPlayer());
 *    for (int i = 0; i < node.getChildCount(); i++) {
 *       resultComputer = resultComputer.withMove(node.getChild(i).getResult());
 *    }
 *    nodeResult = resultComputer.get();
 * } else {
 *    nodeResult = GraphResult.from(status);
 * }
 * // nodeResult is the correct result for node
 * }</pre>
 *
 * @author Kazó Csaba
 */
public abstract class GraphResultComputer {
  /*
   * The idea for how this is implemented comes directly from Guava's ComparisonChain. All
   * allocations are avoided by describing the computation of the result as a graph traversal, and
   * the graph is statically coded as private objects in this class. It works because our regarding
   * the result can be limited step by step as the results of each possible move are iterated.
   *
   * To put it another way, given the current player, if the results of the first i moves imply
   * GraphResult r, then the results of the first i+1 moves can be given as a function of r and the
   * result of move i+1. So r and the current player are encoded as separate objects, and the
   * function which produces the new result is defined for each of them separately.
   */

  private GraphResultComputer() {
  }

  public static GraphResultComputer start(Player player) {
    switch (player) {
      case WHITE: return W_START;
      default: return B_START;
    }
  }

  /**
   * Used to add a move which leads to the specified result.
   *
   * @param moveResult the result of the graph node after the move
   * @return the {@code GraphResultComputer} reflecting the result after including the new move
   */
  public abstract GraphResultComputer withMove(GraphResult moveResult);

  /**
   * Returns the result based on the move results provided so far.
   *
   * @return the current graph result
   */
  public abstract GraphResult get();

  // *********** \\
  // White moves \\
  // *********** \\
  private static final GraphResultComputer W_START = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return W_WHITE_WINS;
        case BLACK_WINS: return W_BLACK_WINS;
        case DRAW: return W_DRAW;
        case BLACK_WONT_WIN: return W_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return W_WHITE_WONT_WIN;
        default: return W_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      throw new IllegalStateException("No moves provided");
    }

  };
  private static final GraphResultComputer W_WHITE_WINS = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      return W_WHITE_WINS;
    }

    @Override
    public GraphResult get() {
      return GraphResult.WHITE_WINS;
    }
  };
  private static final GraphResultComputer W_DRAW = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return W_WHITE_WINS;
        case BLACK_WINS: return W_DRAW;
        case DRAW: return W_DRAW;
        case BLACK_WONT_WIN: return W_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return W_DRAW;
        default: return W_BLACK_WONT_WIN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.DRAW;
    }
  };
  private static final GraphResultComputer W_BLACK_WINS = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return W_WHITE_WINS;
        case BLACK_WINS: return W_BLACK_WINS;
        case DRAW: return W_DRAW;
        case BLACK_WONT_WIN: return W_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return W_WHITE_WONT_WIN;
        default: return W_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.BLACK_WINS;
    }
  };
  private static final GraphResultComputer W_UNKNOWN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return W_WHITE_WINS;
        case BLACK_WINS: return W_UNKNOWN;
        case DRAW: return W_BLACK_WONT_WIN;
        case BLACK_WONT_WIN: return W_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return W_UNKNOWN;
        default: return W_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.UNKNOWN;
    }
  };
  private static final GraphResultComputer W_WHITE_WONT_WIN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return W_WHITE_WINS;
        case BLACK_WINS: return W_WHITE_WONT_WIN;
        case DRAW: return W_DRAW;
        case BLACK_WONT_WIN: return W_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return W_WHITE_WONT_WIN;
        default: return W_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.WHITE_WONT_WIN;
    }
  };
  private static final GraphResultComputer W_BLACK_WONT_WIN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      if (moveResult == GraphResult.WHITE_WINS) {
        return W_WHITE_WINS;
      }
      return W_BLACK_WONT_WIN;
    }

    @Override
    public GraphResult get() {
      return GraphResult.BLACK_WONT_WIN;
    }
  };
  // *********** \\
  // Black moves \\
  // *********** \\
  private static final GraphResultComputer B_START = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return B_WHITE_WINS;
        case BLACK_WINS: return B_BLACK_WINS;
        case DRAW: return B_DRAW;
        case BLACK_WONT_WIN: return B_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return B_WHITE_WONT_WIN;
        default: return B_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      throw new IllegalStateException("No moves provided");
    }

  };
  private static final GraphResultComputer B_WHITE_WINS = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return B_WHITE_WINS;
        case BLACK_WINS: return B_BLACK_WINS;
        case DRAW: return B_DRAW;
        case BLACK_WONT_WIN: return B_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return B_WHITE_WONT_WIN;
        default: return B_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.WHITE_WINS;
    }
  };
  private static final GraphResultComputer B_DRAW = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return B_DRAW;
        case BLACK_WINS: return B_BLACK_WINS;
        case DRAW: return B_DRAW;
        case BLACK_WONT_WIN: return B_DRAW;
        case WHITE_WONT_WIN: return B_WHITE_WONT_WIN;
        default: return B_WHITE_WONT_WIN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.DRAW;
    }
  };
  private static final GraphResultComputer B_BLACK_WINS = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      return B_BLACK_WINS;
    }

    @Override
    public GraphResult get() {
      return GraphResult.BLACK_WINS;
    }
  };
  private static final GraphResultComputer B_UNKNOWN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return B_UNKNOWN;
        case BLACK_WINS: return B_BLACK_WINS;
        case DRAW: return B_WHITE_WONT_WIN;
        case BLACK_WONT_WIN: return B_UNKNOWN;
        case WHITE_WONT_WIN: return B_WHITE_WONT_WIN;
        default: return B_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.UNKNOWN;
    }
  };
  private static final GraphResultComputer B_WHITE_WONT_WIN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      if (moveResult == GraphResult.BLACK_WINS) {
        return B_BLACK_WINS;
      }
      return B_WHITE_WONT_WIN;
    }

    @Override
    public GraphResult get() {
      return GraphResult.WHITE_WONT_WIN;
    }
  };
  private static final GraphResultComputer B_BLACK_WONT_WIN = new GraphResultComputer() {

    @Override
    public GraphResultComputer withMove(GraphResult moveResult) {
      switch (moveResult) {
        case WHITE_WINS: return B_BLACK_WONT_WIN;
        case BLACK_WINS: return B_BLACK_WINS;
        case DRAW: return B_DRAW;
        case BLACK_WONT_WIN: return B_BLACK_WONT_WIN;
        case WHITE_WONT_WIN: return B_WHITE_WONT_WIN;
        default: return B_UNKNOWN;
      }
    }

    @Override
    public GraphResult get() {
      return GraphResult.BLACK_WONT_WIN;
    }
  };
}
