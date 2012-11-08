package hu.kazocsaba.gamecracker.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A facility for reading and writing transformation.
 * 
 * @param <P> the concrete Position type
 * @author Kazó Csaba
 */
public abstract class TransformationSerializer<T extends Transformation<T>> {	
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
	public abstract void writeTransformation(T transformation, DataOutput out) throws IOException;
	
	/**
	 * Reads a transformation. This function must read exactly {@link #getTransformationSerializedSize()} bytes and,
	 * when provided with the data written by {@link #writeTransformation(Transformation, DataOutput)}, it must return
	 * the original transformation.
	 * 
	 * @param in the source to read from
	 * @return the transformation read from the input
	 * @throws IOException if the {@code DataInput} encounters an I/O error
	 * @throws hu.kazocsaba.gamecracker.InconsistencyError if the data in the input was definitely not produced
	 * by {@code writeTransformation}
	 */
	public abstract T readTransformation(DataInput in) throws IOException;
}
