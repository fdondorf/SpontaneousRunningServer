package org.spontaneous.server.client.service.exception;

import org.spontaneous.server.common.api.exception.TechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for requests with an invalid header
 *
 * @author hahmad
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends TechnicalException {

  private static final long serialVersionUID = 7225928938778285749L;

  /**
   * Instantiates a new Invalid header exception.
   */
  public InvalidRequestException() {
    super("invalid http request data");
  }

  @Override
  public String getErrorCode() {
    return "invalid_data";
  }

}
