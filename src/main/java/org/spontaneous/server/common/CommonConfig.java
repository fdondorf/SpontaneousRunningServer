package org.spontaneous.server.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Beans of the common component.
 * 
 * @author fdondorf
 *
 */
@Configuration
@Import({ CommonBaseConfig.class})
public class CommonConfig {

}
