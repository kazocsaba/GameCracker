package hu.kazocsaba.gamecracker.game;

import java.util.List;

/**
 * A position in a game. It must contain all the state that is needed to determine all possible move sequences starting
 * from this position.
 * <p>
 * Implementations of this class should be immutable.
 * 
 * @author Kazó Csaba
 */
public abstract class Position {
	/**
	 * Returns the status of this position. In final positions, this function returns the outcome as either
	 * {@code GameStatus.WHITE_WINS}, {@code GameStatus.BLACK_WINS}, or {@code GameStatus.DRAW}. Otherwise the return
	 * value specifies the current player: {@code GameStatus.WHITE_MOVES} or {@code GameStatus.BLACK_MOVES}.
	 * 
	 * @return the status of this position (the player to move or the outcome)
	 */
	public abstract GameStatus getStatus();
	
	/**
	 * Returns the valid moves in this position. If this position is final ({@code getStatus().isFinal()} return
	 * {@code true}), then this function returns an empty list.
	 * <p>
	 * For a given position, this function must return the moves in the same order every time. More formally, if
	 * {@code position1.equals(position2)}, then {@code position1.getMoves().equals(position2.getMoves())} must also
	 * be true.
	 * 
	 * @return a list of the valid moves in this position
	 */
	public abstract List<Move> getMoves();
	
	/**
	 * Applies a move in this position and returns the object describing the position following the move.
	 * 
	 * @param move the move to make
	 * @return the position after the move
	 * @throws IllegalArgumentException if {@code move} is not one of the moves returned by {@link #getMoves()}
	 */
	public abstract Position move(Move move);

	/**
	 * Transforms this position with the specified transformation and returns the result.
	 * <p>
	 * Let {@code p} be a position, {@code t(x)} denote the application of the transformation, and {@code ==}
	 * denote the {@code equals} function. Then the transformation operation has the following properties:
	 * <ul>
	 * <li>if the transformation is the {@link Transformation#isIdentity() identity}, then
	 * {@code p == t(p)};
	 * <li>if the transformation {@link Transformation#isPlayerSwitching() switches the players}, then
	 * {@code p.getStatus().getOther() == t(p).getStatus()};
	 * <li>if the transformation does not switch the players, then {@code p.getStatus() == t(p).getStatus()};
	 * <li>if {@code m} is a move in position {@code p}, then {@code t(m)} is a valid move in position {@code t(p)}, and
	 * {@code t(p).move(t(m)) == t(p.move(m))};
	 * <li>if {@code s} is the {@link Transformation#inverse() inverse} of {@code t}, then
	 * {@code s(t(p)) == p}.
	 * </ul>
	 * 
	 * @param t the transformation to apply
	 * @return the result of the transformation
	 * @throws IllegalArgumentException if the argument is not a valid transformation for this game
	 */
	public abstract Position transform(Transformation t);
	
	/**
	 * Finds the transformation which, when applied to this position, returns the target position specified in the argument.
	 * 
	 * @param target the position to reach
	 * @return a transformation {@code trans} for which {@code this.transform(trans).equals(target)}, or {@code null} if no
	 * such transformation exists
	 */
	public abstract Transformation getTransformationTo(Position target);
	
	/**
	 * Returns whether the specified object is "equal to" this one. Beside the general contract of the equals method,
	 * it is required that if two positions {@code p1} and {@code p2} are equal, then
	 * <ol>
	 * <li>Their {@link #getStatus() status} must be the same:
	 * {@code p1.getStatus() == p2.getStatus()}.</li>
	 * <li>The {@link #getMoves() valid moves} must be the same in the two positions:
	 * {@code p1.getMoves().equals(p2.getMoves())}.</li>
	 * <li>Applying the same valid move must lead to equal positions: for each valid move {@code m},
	 * {@code p1.move(m).equals(p2.move(m))}.
	 * </ol>
	 * 
	 * @param obj the object with which to compare
	 * @return true if this object is equal to the argument
	 */
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();
	
	
}
