package com.ibm.epricer.test.db;

import java.util.ArrayList;
import java.util.List;
/*
 * Builder to build queries
 * 
 * @author Kiran Chowdhury
 */
public class QueryBuilder {
	
	List<String> queryList = new ArrayList<>();
	
	String truncateTable;
	
	DBOperator operator;
	

	
	public QueryBuilder(EpricerDatabase db) {
		this.operator=db.operator();
	}
	
	
	public QueryBuilder addQuery(String query) {
		queryList.add(query);
		return this;
	}
	
	public QueryBuilder truncateTable(String schemaName, String tableName) {
		this.truncateTable = schemaName+"."+tableName;
		return this;
	}
	
}
