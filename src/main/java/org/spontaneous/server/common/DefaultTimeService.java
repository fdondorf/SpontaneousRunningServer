/**
 * 
 */
package org.spontaneous.server.common;

import java.util.Date;

/**
 * Default implementation of the {@link TimeService} which just returns the "real" time.
 * 
 * @author ohecker
 *
 */
public class DefaultTimeService implements TimeService {

  @Override
  public Date dateNow() {
    return new Date();
  }

//  @Override
//  public DateTime dateTimeNow() {
//    return new DateTime();
//  }

  @Override
  public long timeMillis() {
    return System.currentTimeMillis();
  }

}
