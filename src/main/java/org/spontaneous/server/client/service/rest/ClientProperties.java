package org.spontaneous.server.client.service.rest;

import java.util.List;

/**
 * Container for the platform specific mobile client app properties.
 *
 * @author Horst Jilg
 */
public class ClientProperties extends CommonClientProperties {

  private List<String> validAppVersions;

  private String recommendedAppVersion;

  private List<String> validAppKeys;

  public List<String> getValidAppVersions() {
    return validAppVersions;
  }

  public void setValidAppVersions(List<String> validAppVersions) {
    this.validAppVersions = validAppVersions;
  }

  public String getRecommendedAppVersion() {
    return recommendedAppVersion;
  }

  public void setRecommendedAppVersion(String recommendedAppVersion) {
    this.recommendedAppVersion = recommendedAppVersion;
  }

  public List<String> getValidAppKeys() {
    return validAppKeys;
  }

  public void setValidAppKeys(List<String> validAppKeys) {
    this.validAppKeys = validAppKeys;
  }

}
