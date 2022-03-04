package com.ibm.epricer.test.svclib;

/*
 * Holds information create Stub
 * 
 * @author Kiran Chowdhury
 */
public class Stub {
	public static final String SUCCESS_RESPONSE="S";
	
	public static final String BUSINE_RULE_VIOLATION_RESPONSE="B";
	
	public static final String UNHANDLED_TECHNICAL_RESPONSE="U";
	
	String currentState;
	
	Object response;
	
	String responseType;
	
	String remoteServiceId;
	
	String remoteServiceEndpointId;
	
	Integer remoteServiceEndpointVersion=1;
	
	boolean finalStub;
	
	boolean first;
	
	String nextState;
}
