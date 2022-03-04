package com.ibm.epricer.test.db;
/*
 * Builder to spinup a in-memory database to be used by integration tests
 * 
 * @author Kiran Chowdhury
 */
public class EpricerDatabaseBuilder {

	private EpricerDatabase epricerDB;
	

	public EpricerDatabaseBuilder() {
		epricerDB = new EpricerDatabase();
	}
	
	/*
	 * Add initial db script path. Usually contains create schema, tables and or initial data loads sqls.
	 */
	public EpricerDatabaseBuilder addScript(String path) {
		this.epricerDB.addScript(path);
		return this;
	}
	
	 /*
	  * Build and start the database and return the reference to the EpricerDatabase 
	  */
	 public EpricerDatabase build() {
		 return this.epricerDB.build();
	 }
	 
	 

}
