package edu.csuohio.photomanager.data.exception;

/**
 * A generic exception to throw if a file that is to be stored using the
 * <code>StorageService</code> cannot be found.
 */
public class StorageFileNotFoundException extends StorageException {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}