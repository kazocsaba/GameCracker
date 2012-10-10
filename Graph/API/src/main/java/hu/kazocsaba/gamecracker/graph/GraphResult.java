package hu.kazocsaba.gamecracker.graph;

import hu.kazocsaba.gamecracker.game.GameStatus;

/**
 * The result of a node in the game graph. This enumeration describes our knowledge of the result of game positions
 * (assuming perfect play) based on the current graph.
 * 
 * @author Kazó Csaba
 */
public enum GraphResult {
	/** Nothing is known, all of the game results are possible. */
	UNKNOWN,
	/** It will either be a draw, or black will win. */
	WHITE_WONT_WIN,
	/** It will either be a draw, or white will win. */
	BLACK_WONT_WIN,
	/** White will win. */
	WHITE_WINS,
	/** Black will win. */
	BLACK_WINS,
	/** It will be a draw. */
	DRAW;
	
	/**
	 * Returns either {@code WHITE_WINS}, {@code BLACK_WINS}, or {@code DRAW}, based on the value of {@code gameStatus}.
	 *
	 * @param gameStatus a game status
	 * @return the status as a known graph result
	 * @throws IllegalArgumentException if the argument is not a final status
	 */
	public static GraphResult from(GameStatus gameStatus) {
		switch (gameStatus) {
			case WHITE_WINS: return WHITE_WINS;
			case BLACK_WINS: return BLACK_WINS;
			case DRAW: return DRAW;
			default: throw new IllegalArgumentException("Game status is not final");
		}
	}
	
	/**
	 * Returns whether this result is known.
	 * 
	 * @return {@code true} if the result is known (i.e. it is either {@code WHITE_WINS}, {@code BLACK_WINS}, or
	 * {@code DRAW})
	 */
	public boolean isKnown() {
		return this == DRAW || this == WHITE_WINS || this == BLACK_WINS;
	}
	
	/**
	 * Returns either {@code GameStatus.WHITE_WINS}, {@code GameStatus.BLACK_WINS}, or {@code GameStatus.DRAW}, based
	 * on the value of this result.
	 * 
	 * @return the result
	 * @throws IllegalStateException if this result is not known
	 */
	public GameStatus asGameStatus() {
		switch (this) {
			case WHITE_WINS: return GameStatus.WHITE_WINS;
			case BLACK_WINS: return GameStatus.BLACK_WINS;
			case DRAW: return GameStatus.DRAW;
			default: throw new IllegalStateException(String.format("Result (%s) is not final", this));
		}
	}
	
	/**
	 * Returns the {@code GraphResult} that results from switching the players.
	 * <p>
	 * <table border="1" cellpadding="3" cellspacing="0">
	 * <tr align="left"><th>This result</th><th>Returned result</th></tr>
	 * <tr><td><code>WHITE_WINS</code></td><td><code>BLACK_WINS</code></td></tr>
	 * <tr><td><code>BLACK_WINS</code></td><td><code>WHITE_WINS</code></td></tr>
	 * <tr><td><code>WHITE_WONT_WIN</code></td><td><code>BLACK_WONT_WIN</code></td></tr>
	 * <tr><td><code>BLACK_WONT_WIN</code></td><td><code>WHITE_WONT_WIN</code></td></tr>
	 * <tr><td><code>DRAW</code></td><td><code>DRAW</code></td></tr>
	 * <tr><td><code>UNKNOWN</code></td><td><code>UNKNOWN</code></td></tr>
	 * </table>
	 * 
	 * @return the result corresponding to the other player
	 */
	public GraphResult getOther() {
		switch (this) {
			case WHITE_WINS: return BLACK_WINS;
			case BLACK_WINS: return WHITE_WINS;
			case WHITE_WONT_WIN: return BLACK_WONT_WIN;
			case BLACK_WONT_WIN: return WHITE_WONT_WIN;
			case DRAW: return DRAW;
			default: return UNKNOWN;
		}
	}
}
