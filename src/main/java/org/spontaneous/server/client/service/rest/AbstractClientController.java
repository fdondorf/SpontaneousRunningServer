package org.spontaneous.server.client.service.rest;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spontaneous.server.client.service.AppSystemType;
import org.spontaneous.server.client.service.Header;
import org.spontaneous.server.client.service.exception.InvalidApiVersionException;
import org.spontaneous.server.client.service.exception.InvalidAppKeyException;
import org.spontaneous.server.client.service.exception.InvalidAppSystemException;
import org.spontaneous.server.client.service.exception.InvalidAppVersionException;
import org.spontaneous.server.client.service.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Superclass for all client controllers used for the mobile client requests not under oauth control..
 * <p>
 * Common functionality to get a logged in user and to validate header data.
 *
 * @author fdondorf
 */
@RequestMapping("/spontaneous")
public class AbstractClientController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractClientController.class);
	
	/**
	 * Regular expression for valid version strings.
	 * 
	 * Examples for allowed values are: "1", "1.0", "2.1.3"
	 */
	private static final String VERSION_REGEX = "^\\d+(\\.\\d+)?(\\.\\d+)?$";

	@Autowired
	private ClientProperties androidClientProperties;

	@Autowired
	private ClientProperties iosClientProperties;
	
	@Autowired
	protected Validator validator;

	  
	/**
	 * Check the validity of the input request aka JSON-Syntax.
	 * Additional checks the header data
	 *
	 * @param inputRequestData
	 */
	protected void validateInputRequestData(Header inputRequestData) {
	  if (inputRequestData == null) {
	    throw new InvalidRequestException();
	  }
	  checkInputRequest(inputRequestData);

	  checkHeader(inputRequestData);
	}

	private void checkHeader(Header inputRequestData) {
	  checkAppSystem(inputRequestData.getAppSystem());
	  ClientProperties clientProperties = getClientProperties(inputRequestData.getAppSystem());

	  checkAppVersion(inputRequestData.getAppVersion(), clientProperties.getValidAppVersions());
	  checkApiVersion(inputRequestData.getApiVersion(), clientProperties.getValidApiVersions());
	  checkAppKey(inputRequestData.getAppKey(), clientProperties.getValidAppKeys());
	}

	private void checkInputRequest(Header inputRequest) {
		Set<ConstraintViolation<Header>> constraintViolations = validator.validate(inputRequest);
		if (constraintViolations.size() != 0) {
			LOG.error("Validation of header data failed. Details: {}", constraintViolations.toString());
			throw new InvalidRequestException();
		}
	}

	private void checkAppSystem(String appSystem) {
	    if (!AppSystemType.isValid(appSystem)) {
	      LOG.error("Request with wrong appSystem: " + appSystem);
	      throw new InvalidAppSystemException("invalid app system");
	    }
	}

	private void checkApiVersion(String apiVersion, List<String> validApiVersions) {
		if (!apiVersion.matches(VERSION_REGEX) || !validApiVersions.contains(apiVersion)) {
	      LOG.error("Request with wrong apiVersion: " + apiVersion);
	      throw new InvalidApiVersionException("invalid api version");
	    }
	}

	private void checkAppVersion(String appVersion, List<String> validAppVersions) {
	    if (!appVersion.matches(VERSION_REGEX)) {
	      LOG.error("Request with wrong appVersion: " + appVersion);
	      throw new InvalidAppVersionException("invalid app version");
	  }

	  boolean valid = false;
	  String[] appVersionSeperated = appVersion.split("\\.");
	  for (String validAppVersion : validAppVersions) {
		  if (validAppVersion.equalsIgnoreCase(appVersionSeperated[0])) {
	        valid = true;
	        break;
	      }
	  }
	  if (!valid) {
	      LOG.error("Request with wrong appVersion: " + appVersion);
	      throw new InvalidAppVersionException("invalid app version");
	  }
	}

	private void checkAppKey(String appKey, List<String> validAppKeys) {
	    if (!validAppKeys.contains(appKey)) {
	      LOG.error("Request with wrong appKey: " + appKey);
	      throw new InvalidAppKeyException("invalid app key");
	    }
	}
	
	protected ClientProperties getClientProperties(String appSystemString) {
		AppSystemType appSystem = AppSystemType.fromName(appSystemString);
		switch (appSystem) {
			case ANDROID:
		      return androidClientProperties;
		    case IOS:
		      return iosClientProperties;
		    default:
		      throw new IllegalArgumentException("not a valid app system " + appSystem);
		}
	}

	  
}
