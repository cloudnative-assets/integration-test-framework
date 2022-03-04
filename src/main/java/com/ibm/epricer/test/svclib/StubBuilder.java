package com.ibm.epricer.test.svclib;

/*
 * Use this builder to build Stubs for stateful behavior (when an endpoint calls multiple dependent services)
 */
public class StubBuilder {
	
	private Stub stub;
	
	
	public StubBuilder() {
		this.stub = new Stub();
	}
	
	/*
	 * Provide remote service id
	 */
	public StubBuilder forRemoteServiceId(String serviceId) {
		stub.remoteServiceId=serviceId;
		return this;
	}
	/*
	 * Provide remote service endpoint id
	 */
	public StubBuilder forRemoteServiceEndpointId(String endpointId) {
		stub.remoteServiceEndpointId=endpointId;
		return this;
	}
	
	/*
	 * Provide remote service endpoint version
	 */
	public StubBuilder forRemoteServiceEndpointVersion(Integer endpointVersion) {
		stub.remoteServiceEndpointVersion=endpointVersion;
		return this;
	}
	
	/*
	 * Provide expected StubResponseType
	 */
	public StubBuilder withResponseType(String type) {
		stub.responseType=type;
		return this;
	}
	
	/*
	 * Provide expected response object
	 */
	public StubBuilder withResponse(Object response) {
		stub.response=response;
		return this;
	}
	
	
	public Stub build() {
		return stub;
	}
	
	
}
