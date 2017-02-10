package org.spontaneous.server.client.service.rest;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Container for the common mobile client app properties.
 *
 * @author Horst Jilg
 */
@ConfigurationProperties(prefix = "spontaneous.client")
public abstract class CommonClientProperties {

  private String apiVersion;

  private List<String> validApiVersions;

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public List<String> getValidApiVersions() {
    return validApiVersions;
  }

  public void setValidApiVersions(List<String> validApiVersions) {
    this.validApiVersions = validApiVersions;
  }

}
