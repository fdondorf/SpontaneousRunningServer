/**
 * 
 */
package org.spontaneous.server.common;

import java.util.Date;

/**
 * Service for accessing the current time.
 * 
 * @author ohecker
 *
 */
public interface TimeService {

  /**
   * Return the current time as java Date object.
   * 
   * @return the current time
   */
  Date dateNow();

  /**
   * Return the current time as joda DateTime object.
   * 
   * @return the current time
   */
  //DateTime dateTimeNow();

  /**
   * Return the current time as timestamp (milliseconds since the unix zero date).
   * 
   * @return the timestamp in milliseconds.
   */
  long timeMillis();

}
