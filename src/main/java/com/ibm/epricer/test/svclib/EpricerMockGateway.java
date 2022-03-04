package com.ibm.epricer.test.svclib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;


public class EpricerMockGateway {
	private static final Logger LOG = LoggerFactory.getLogger(EpricerMockGateway.class);
	
	
	private EpricerMockCommand mockCommand;
	
	EpricerMockGateway(String host, int port, String serviceId) {
		this.wireMockServer(host, port, serviceId);
	}
	
	void wireMockServer(String host, int port, String serviceId) {
		WireMockServer server = new WireMockServer(
				WireMockConfiguration.options()
				.port(port)
				.bindAddress(host));
		server.start();
		LOG.info("Epricer Mock Gateway Server running on {}", server.baseUrl());
		this.mockCommand = new EpricerMockCommand(server, serviceId);
	}
	
	public EpricerMockCommand mockCommander() {
		return this.mockCommand;
	}
	
	
	
	
}
