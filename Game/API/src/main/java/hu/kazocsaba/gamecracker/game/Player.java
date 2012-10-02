package hu.kazocsaba.gamecracker.game;

/**
 * A player of a game. The two players are called White and Black. Usually, but not necessarily, White is the player
 * with the first move.
 *
 * @author Kazó Csaba
 */
public enum Player {
	/** The player called White. */
	WHITE,
	/** The player called Black. */
	BLACK;

	/**
	 * Returns the other player.
	 *
	 * @return the other player (White if this player is Black, and Black if this player is White)
	 */
	public Player getOther() {
		return this == WHITE ? BLACK : WHITE;
	}

	/**
	 * Returns the game status in which this player has won.
	 *
	 * @return {@code GameStatus.WHITE_WINS} if this player is White, and {@code GameStatus.BLACK_WINS} if this player
	 * is Black
	 */
	public GameStatus getWinStatus() {
		return this == WHITE ? GameStatus.WHITE_WINS : GameStatus.BLACK_WINS;
	}

	/**
	 * Returns the game status in which this player is to move.
	 *
	 * @return {@code GameStatus.WHITE_MOVES} if this player is White, and {@code GameStatus.BLACK_MOVES} if this player
	 * is Black
	 */
	public GameStatus getMoveStatus() {
		return this == WHITE ? GameStatus.WHITE_MOVES : GameStatus.BLACK_MOVES;
	}
}
