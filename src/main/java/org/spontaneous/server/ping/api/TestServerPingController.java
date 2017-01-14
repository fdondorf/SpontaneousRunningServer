package org.spontaneous.server.ping.api;

import org.spontaneous.server.client.service.rest.AbstractClientController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServerPingController extends AbstractClientController {

	@RequestMapping(value = "/ping")
	public String pingServer() {
		 return "Hello! I am alive...";
	}
}
