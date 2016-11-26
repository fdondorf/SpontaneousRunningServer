package org.spontaneous.server.usermanagement.dao;

import org.junit.runner.RunWith;
import org.spontaneous.server.Application;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Abstract class for Unit Tests for the Crud-Repository
 * 
 * @author fdondorf
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
//@PropertySource(value = { "classpath:application.properties" })
@TestPropertySource(locations="classpath:application.properties")
public abstract class AbstractJPATest {

}
