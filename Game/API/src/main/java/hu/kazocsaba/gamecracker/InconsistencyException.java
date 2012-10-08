package hu.kazocsaba.gamecracker;

/**
 * Thrown to indicate that some kind of inconsistency has been detected.
 *
 * @author Kazó Csaba
 */
public class InconsistencyException extends Exception {
	/**
	 * Creates a new instance of {@code InconsistencyException} without detail message.
	 */
	public InconsistencyException() {
	}

	/**
	 * Constructs an instance of {@link InconsistencyException} with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InconsistencyException(String msg) {
		super(msg);
	}
}
