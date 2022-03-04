package com.ibm.epricer.test.svclib;
/*
 * Build EpricerMockGateway server which will act as the Gateway server and serve the mock responses for the remote services call
 * 
 * @author Kiran Chowdhury
 */
public class EpricerMockGatewayBuilder {
	private String serviceId;
	
	private String host;
	
	private int port;
	
	/*
	 * Provide the epricer.service-id defined in src/main/resources/application.properties
	 */
	public EpricerMockGatewayBuilder service(String serviceId) {
		this.serviceId = serviceId;
		return this;
	}
	
	/*
	 * Provide the host defined in epricer.gateway under src/test/resources/test.properties
	 */
	public EpricerMockGatewayBuilder withHost(String host) {
		this.host = host;
		return this;
	}
	
	/*
	 * Provide the port defined in epricer.gateway under src/test/resources/test.properties
	 */
	public EpricerMockGatewayBuilder withPort(int port) {
		this.port = port;
		return this;
	}
	
	public EpricerMockGateway build() {
		return new EpricerMockGateway(host, port, serviceId);
	}
	
	

}
