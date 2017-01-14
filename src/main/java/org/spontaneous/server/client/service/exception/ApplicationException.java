package org.spontaneous.server.client.service.exception;

/**
 * ApplicatoinException for all RuntimeExceptions causing a 500-Server error
 * 
 * @author fdondorf
 *
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 7304323285407219904L;

	public ApplicationException(String msg) {
		super(msg);
	}
	
	public ApplicationException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
