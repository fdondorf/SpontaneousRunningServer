package org.spontaneous.server.auth.logic.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.spontaneous.server.common.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * This token service computes the remaining time for token validity depending on whether the tenant's shop is within or outside
 * business hours.
 * <p>
 * The server uses the property "oauth.tokenValiditySeconds" as the standard token validity. The computed token validity is clamped at this value.
 * <p>
 * With every valid token request the expires date of an already existing token will be renewed.
 *
 * @author Florian Dondorf
 */
public class TimeToLiveTokenServices extends DefaultTokenServices {

  private TimeService timeService;

  private int maximumTokenValiditySeconds;
  private int outsideBusinessHoursTokenLifetimeMinutes;
  private int businessBeginHour;
  private int businessEndHour;
  private int businessEndMinute;
  private int businessBeginMinute;

  /**
   * {inheritDoc}
   */
  @Override
  public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
    OAuth2AccessToken accessToken = super.createAccessToken(authentication);

    if (accessToken instanceof DefaultOAuth2AccessToken) {
      DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;

      int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
      if (validitySeconds > 0) {
        token.setExpiration(new Date(timeService.timeMillis() + (validitySeconds * 1000L)));
      }
    }

    return accessToken;
  }

  /**
   * {inheritDoc}
   */
  @Override
  protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
    return getAccessTokenValiditySeconds(getTenantSpecificCurrentDate());
  }

  /**
   * Returns access token validity for certain date in seconds.
   *
   * @param referenceDate
   *          Reference data
   * @return Validity in seconds
   */
  protected int getAccessTokenValiditySeconds(Date referenceDate) {
    if (!isWithinBusinessHours(referenceDate)) {
      return outsideBusinessHoursTokenLifetimeMinutes * 60;
    }

    Date tokenExpiryDate = getEarliestExpirationDateAfterBusinessHours(referenceDate);
    int validitySeconds = getDateDifferenceInSeconds(tokenExpiryDate, referenceDate);
    if (validitySeconds == 0) { // rare case
      return outsideBusinessHoursTokenLifetimeMinutes * 60;
    }
    if (validitySeconds < 0 || validitySeconds > maximumTokenValiditySeconds) {
      return maximumTokenValiditySeconds;
    }
    return validitySeconds;
  }

  /**
   * Returns the difference of two dates in seconds.
   *
   * @param dateA
   *          First date
   * @param dateB
   *          Second date
   * @return dateA minus dateB in seconds, can be negative
   */
  protected int getDateDifferenceInSeconds(Date dateA, Date dateB) {
    return (int) ((dateA.getTime() - dateB.getTime()) / 1000L);
  }

  /**
   * Returns the current time in the time zone of the tenant.
   *
   * @return Current time.
   */
  protected Date getTenantSpecificCurrentDate() {
    // TODO: This is not a sustainable solution since it only works if all tenants are in the same time zone as Germany. As soon as tenants' business
    // hours are configurable, we should update this method.
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(timeService.dateNow());
    calendar.setTimeZone(TimeZone.getTimeZone("Europe/Germany"));
    return calendar.getTime();
  }

  /**
   * Checks whether a date is within business hours.
   *
   * @param date
   *          Date to check.
   * @return true iff date is within business hours
   */
  protected boolean isWithinBusinessHours(Date date) {
    Date todaysBusinessHourStart = getDateWithNewHourMinute(date, businessBeginHour, businessBeginMinute);
    Date todaysBusinessHourEnd = getDateWithNewHourMinute(date, businessEndHour, businessEndMinute);

    return (date.equals(todaysBusinessHourStart) || date.after(todaysBusinessHourStart))
        && (date.equals(todaysBusinessHourEnd) || date.before(todaysBusinessHourEnd));
  }

  /**
   * Returns the expiration date for the first moment after end of business hours for a given date.
   *
   * @param date
   *          Date to modify
   * @return After hour expiration time at that date
   */
  protected Date getEarliestExpirationDateAfterBusinessHours(Date date) {
    return new Date(getDateWithNewHourMinute(date, businessEndHour, businessEndMinute).getTime() + outsideBusinessHoursTokenLifetimeMinutes * 60000L);
  }

  /**
   * Returns a modified copy of date in which hour and minute are changed.
   *
   * @param date
   *          Date to modify
   * @param hour
   *          Hour
   * @param minute
   *          Minute
   * @return Modified date
   */
  protected static Date getDateWithNewHourMinute(Date date, int hour, int minute) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(date.getTime());
    calendar.set(Calendar.HOUR_OF_DAY, hour);
    calendar.set(Calendar.MINUTE, minute);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  @Value("${oauth.tokenValiditySeconds}")
  public void setMaximumTokenValiditySeconds(int maximumTokenValiditySeconds) {
    this.maximumTokenValiditySeconds = maximumTokenValiditySeconds;
  }

  @Value("${oauth.tokenValidityAfterBusinessHoursMinutes}")
  public void setOutsideBusinessHoursTokenLifetimeMinutes(int outsideBusinessHoursTokenLifetimeMinutes) {
    this.outsideBusinessHoursTokenLifetimeMinutes = outsideBusinessHoursTokenLifetimeMinutes;
  }

  @Value("${businesshours.begin.hour}")
  public void setBusinessBeginHour(int businessBeginHour) {
    this.businessBeginHour = businessBeginHour;
  }

  @Value("${businesshours.begin.minute}")
  public void setBusinessBeginMinute(int businessBeginMinute) {
    this.businessBeginMinute = businessBeginMinute;
  }

  @Value("${businesshours.end.hour}")
  public void setBusinessEndHour(int businessEndHour) {
    this.businessEndHour = businessEndHour;
  }

  @Value("${businesshours.end.minute}")
  public void setBusinessEndMinute(int businessEndMinute) {
    this.businessEndMinute = businessEndMinute;
  }

  @Autowired
  public void setTimeService(TimeService timeService) {
    this.timeService = timeService;
  }

}
