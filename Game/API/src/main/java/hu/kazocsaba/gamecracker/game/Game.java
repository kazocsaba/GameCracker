package hu.kazocsaba.gamecracker.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A game. This class is used to access the initial position, and it also provides I/O functions on the game objects:
 * the {@link Position positions}, {@link Move moves}, and {@link Transformation transformations}.
 *
 * @author Kazó Csaba
 */
public abstract class Game {
	/**
	 * Returns the initial position of the game.
	 *
	 * @return the initial position
	 */
	public abstract Position getInitialPosition();
	
	/**
	 * Returns the category function for computing the categories of this game's positions.
	 * 
	 * @return a category function for this game
	 */
	public abstract CategoryFunction getCategoryFunction();

	/**
	 * Returns the maximum size of serialized positions. This is an upper limit to the number of bytes
	 * {@link #writePosition(Position, DataOutput)} will write.
	 *
	 * @return the maximum size of serialized positions
	 */
	public abstract int getPositionMaxSerializedSize();

	/**
	 * Serializes a position. The data written to the output must not be longer than
	 * {@link #getPositionMaxSerializedSize()}, and passing it to {@link #readPosition(DataInput)} must return the
	 * original position.
	 *
	 * @param position a position of this game
	 * @param out the output to receive the byte data
	 * @throws IOException if the {@code DataOutput} encounters an I/O error
	 */
	public abstract void writePosition(Position position, DataOutput out) throws IOException;

	/**
	 * Reads a position. It is assumed that the input contains data previously produced by a call to
	 * {@link #writePosition(Position,DataOutput)}; this function must return the position that was written. Furthermore,
	 * this function must read the same number of bytes as written by {@code writePosition}.
	 *
	 * @param in the source to read from
	 * @return the position read from the input
	 * @throws IOException if the {@code DataInput} encounters an I/O error
	 * @throws hu.kazocsaba.gamecracker.InconsistencyException if the data in the input was definitely not produced
	 * by {@code writePosition}
	 */
	public abstract Position readPosition(DataInput in) throws IOException;

	/**
	 * Returns an upper limit to the number of valid moves in a position of a valid game. If a position {@code pos} is
	 * reachable from the initial position via {@link Position#move(Move)}, then {@code pos.getMoves().size()} must not
	 * exceed the value returned by this function.
	 *
	 * @return the maximum number of possible moves in a valid position
	 */
	public abstract int getMaxPossibleMoves();
	
	/**
	 * Returns the size of serialized transformation. This is the exact number of bytes written by
	 * {@link #writeTransformation(Transformation,DataOutput) writeTransformation} and
	 * {@link #readTransformation(DataInput) readTransformation} for any of the game's transformations.
	 * 
	 * @return the exact size of serialized transformations
	 */
	public abstract int getTransformationSerializedSize();
	
	/**
	 * Serializes a transformation. The data written to the output must be exactly
	 * {@link #getTransformationSerializedSize()} bytes, and passing it to {@link #readTransformation(DataInput)} must
	 * return the original transformation.
	 * 
	 * @param transformation a transformation of this game
	 * @param out the output to receive the byte data
	 * @throws IOException if the {@code DataOutput} encounters an I/O error
	 */
	public abstract void writeTransformation(Transformation transformation, DataOutput out) throws IOException;
	
	/**
	 * Reads a transformation. This function must read exactly {@link #getTransformationSerializedSize()} bytes and,
	 * when provided with the data written by {@link #writeTransformation(Transformation, DataOutput)}, it must return
	 * the original transformation.
	 * 
	 * @param in the source to read from
	 * @return the transformation read from the input
	 * @throws IOException if the {@code DataInput} encounters an I/O error
	 * @throws hu.kazocsaba.gamecracker.InconsistencyException if the data in the input was definitely not produced
	 * by {@code writeTransformation}
	 */
	public abstract Transformation readTransformation(DataInput in) throws IOException;
}
