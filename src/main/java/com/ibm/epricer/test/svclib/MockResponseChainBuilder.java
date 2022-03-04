package com.ibm.epricer.test.svclib;

/*
 * Builder class to build chain of multiple remote service responses for single endpoint request
 * 
 * @author Kiran Chowdhury
 */
public class MockResponseChainBuilder {
	private StatefullBehaviorStubs stubs;
	public  MockResponseChainBuilder withName(String name) {
		stubs = new StatefullBehaviorStubs(name);
		return this;
	}
	
	/*
	 * Register the first stub of the first remote service response
	 */
	public StatefullBehaviorStubs addFirst(Stub stub) {
		this.stubs.addFirst(stub);
		return stubs;
	}
	
}
