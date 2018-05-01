package edu.csuohio.photomanager.data.exception;

/**
 * A generic exception to throw if the data cannot be stored using the
 * <code>StorageService</code>.
 */
public class StorageException extends RuntimeException {

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}