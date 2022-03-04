package com.ibm.epricer.test.svclib;

import java.util.LinkedList;

import com.github.tomakehurst.wiremock.stubbing.Scenario;


public class StatefullBehaviorStubs {
	LinkedList<Stub> stubs = new LinkedList<Stub>();
	String scenario;
	boolean lastExist = false;
	StatefullBehaviorStubs(String scenario) {
		this.scenario=scenario;
	}
	
	/*
	 * Register the next stub of the  remote service response in the chain
	 */
	public StatefullBehaviorStubs addNext(Stub stub) {
		stubs.add(stub);
		return this;
	}
	
	StatefullBehaviorStubs addFirst(Stub stub) {
		stub.first=true;
		stub.currentState=Scenario.STARTED;
		stubs.add(stub);
		return this;
	}
	/*
	 * Register the last stub of the  remote service response in the chain
	 */
	public StatefullBehaviorStubs addLast(Stub stub) {
		stub.finalStub=true;
		lastExist=true;
		stubs.add(stub);
		return this;		
	}
}
