package hu.kazocsaba.gamecracker;

/**
 * Thrown to indicate that some kind of inconsistency has been detected.
 *
 * @author Kazó Csaba
 */
public class InconsistencyError extends AssertionError {
	/**
	 * Creates a new instance of {@code InconsistencyError} without detail message.
	 */
	public InconsistencyError() {
	}

	/**
	 * Constructs an instance of {@link InconsistencyError} with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InconsistencyError(String msg) {
		super(msg);
	}
}
