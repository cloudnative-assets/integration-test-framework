package com.ibm.epricer.test.db;

import java.util.ArrayList;
import java.util.List;
/*
 * Epricer database instance
 * 
 * @author Kiran Chowdhury
 */
public class EpricerDatabase {
	 
	 private List<String> scriptPaths = new ArrayList<String>();
	 
	 
	 private DBOperator operator;
	 
	 void addScript(String script) {
		 this.scriptPaths.add(script);
	 }
	 
	 EpricerDatabase build() {
		 this.operator = new DBOperator(this.scriptPaths);
		 return this;
	 }
	 
	 DBOperator operator() {
		 return this.operator;
	 }
	 
	 public void shutdown() {
		 this.operator.shutdown();
	 }
	 
	 
	 
	 
	 
}
