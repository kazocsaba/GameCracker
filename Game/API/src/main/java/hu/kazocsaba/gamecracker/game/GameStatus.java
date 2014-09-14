package hu.kazocsaba.gamecracker.game;

/**
 * The status of a game in a certain position. In final positions, it represents the outcome;
 * otherwise it represents the player to move.
 *
 * @author Kazó Csaba
 */
public enum GameStatus {

  /** Describes position where it is White's turn to move. */
  WHITE_MOVES,
  /** Describes position where it is Black's turn to move. */
  BLACK_MOVES,
  /** Describes final position where White has won. */
  WHITE_WINS,
  /** Describes final position where Black has won. */
  BLACK_WINS,
  /** Describes final position with a draw outcome. */
  DRAW;

  /* A private, unmodified array of values. */
  private static final GameStatus[] values = values();

  /**
   * Returns a status by its ordinal. This method is equivalent to {@code values()[ordinal]}, but
   * avoids copying the value array.
   *
   * @param ordinal the index of the status to return
   * @return the {@code GameStatus} at the given index
   * @throws IndexOutOfBoundsException if {@code ordinal} is invalid
   */
  public static GameStatus getByOrdinal(int ordinal) {
    return values[ordinal];
  }

  /**
   * Returns the number of constants defined in this enumeration. The return value is the same as
   * {@code values().length}.
   *
   * @return the number of {@code GameStatus} constants
   */
  public static int getCount() {
    return values.length;
  }

  /**
   * Returns {@code true} if this status describes a final position, and {@code false} otherwise.
   *
   * @return whether the game position described by this status is a final position
   */
  public boolean isFinal() {
    return this != WHITE_MOVES && this != BLACK_MOVES;
  }

  /**
   * Returns the player whose turn it is to move, if this status is not final.
   *
   * @return {@code Player.WHITE} if this status is {@code WHITE_MOVES}, and {@code Player.BLACK} if
   * this status is {@code BLACK_MOVES}
   * @throws IllegalStateException if this is a final status
   */
  public Player getCurrentPlayer() {
    switch (this) {
      case WHITE_MOVES: return Player.WHITE;
      case BLACK_MOVES: return Player.BLACK;
      default: throw new IllegalStateException("The status is final");
    }
  }

  /**
   * Returns the winner player, or {@code null} is the outcome is a draw. Throws an exception if
   * this status is not final.
   *
   * @return {@code Player.WHITE} if this status is {@code WHITE_WINS}, {@code Player.BLACK} if this
   * status is {@code BLACK_WINS}, and {@code null} if this status is {@code DRAW}
   * @throws IllegalStateException if this is not a final status
   */
  public Player getWinner() {
    switch (this) {
      case WHITE_WINS: return Player.WHITE;
      case BLACK_WINS: return Player.BLACK;
      case DRAW: return null;
      default: throw new IllegalStateException("The status is not final");
    }
  }

  /**
   * Returns the {@code GameStatus} that results from switching the players.
   *
   * <p><table border="1" cellpadding="3" cellspacing="0">
   * <tr align="left"><th>This status</th><th>Returned status</th></tr>
   * <tr><td><code>WHITE_MOVES</code></td><td><code>BLACK_MOVES</code></td></tr>
   * <tr><td><code>BLACK_MOVES</code></td><td><code>WHITE_MOVES</code></td></tr>
   * <tr><td><code>WHITE_WINS</code></td><td><code>BLACK_WINS</code></td></tr>
   * <tr><td><code>BLACK_WINS</code></td><td><code>WHITE_WINS</code></td></tr>
   * <tr><td><code>DRAW</code></td><td><code>DRAW</code></td></tr>
   * </table>
   *
   * @return the status corresponding to the other player
   */
  public GameStatus getOther() {
    switch (this) {
      case WHITE_MOVES: return BLACK_MOVES;
      case BLACK_MOVES: return WHITE_MOVES;
      case WHITE_WINS: return BLACK_WINS;
      case BLACK_WINS: return WHITE_WINS;
      default: return DRAW;
    }
  }
}
