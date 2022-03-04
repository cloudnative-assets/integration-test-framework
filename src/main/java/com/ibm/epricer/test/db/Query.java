package com.ibm.epricer.test.db;

/*
 * Provide interfaces to do CRUD operations
 * 
 * @author Kiran Chowdhury
 */
public class Query {
	public static void insert(QueryBuilder qb) throws Exception{
		qb.operator.insertMany(qb.queryList);
	}
	
	public static void update(QueryBuilder qb) throws Exception{
		qb.operator.updateMany(qb.queryList);
	}
	
	public static void delete(QueryBuilder qb) throws Exception{
		qb.operator.deleteData(qb.queryList);
	}
	
	public static void truncate(QueryBuilder qb) throws Exception{
		qb.operator.deleteAllData(qb.truncateTable);
	}
}
