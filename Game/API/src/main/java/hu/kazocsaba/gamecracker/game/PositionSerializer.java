package hu.kazocsaba.gamecracker.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A facility for reading and writing positions.
 *
 * @param <P> the concrete position type
 * @author Kazó Csaba
 */
public abstract class PositionSerializer<P extends Position<P, ?, ?>> {

  /**
   * Returns the maximum size of serialized positions. This is an upper limit to the number of bytes
   * {@link #writePosition(Position, DataOutput)} will write.
   *
   * @return the maximum size of serialized positions
   */
  public abstract int getPositionMaxSerializedSize();

  /**
   * Serializes a position. The data written to the output must not be longer than
   * {@link #getPositionMaxSerializedSize()}, and passing it to {@link #readPosition(DataInput)}
   * must return the original position.
   *
   * @param position a position of this game
   * @param out the output to receive the byte data
   * @throws IOException if the {@code DataOutput} encounters an I/O error
   */
  public abstract void writePosition(P position, DataOutput out) throws IOException;

  /**
   * Reads a position. It is assumed that the input contains data previously produced by a call to
   * {@link #writePosition(Position,DataOutput)}; this function must return the position that was
   * written. Furthermore, this function must read the same number of bytes as written by
   * {@code writePosition}.
   *
   * @param in the source to read from
   * @return the position read from the input
   * @throws IOException if the {@code DataInput} encounters an I/O error
   * @throws hu.kazocsaba.gamecracker.InconsistencyError if the data in the input was definitely not
   * produced by {@code writePosition}
   */
  public abstract P readPosition(DataInput in) throws IOException;

}
