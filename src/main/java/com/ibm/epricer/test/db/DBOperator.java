package com.ibm.epricer.test.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
/*
 * Internal facade to execute db commands
 * 
 * @author Kiran Chowdhury
 */
class DBOperator {
	
	private EmbeddedDatabase DB;
	
	private static final Logger LOG = LoggerFactory.getLogger(DBOperator.class);
	
	DBOperator() {
		this.DB = new EmbeddedDatabaseBuilder().build();
	}
	
	DBOperator(List<String> scripts) {
		LOG.info("**********************************************");
		LOG.info("Building inmemory database...... ");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		 for (String script : scripts) {
			 LOG.info("Executing DB script {} ", script);
			 builder.addScript(script);
		 }
		this.DB = builder.build();
		LOG.info("Inmemory database is now up and running");
		LOG.info("**********************************************");
	}
	
	
	void insertOne(String insertStamenet) throws Exception {
		Connection con = this.DB.getConnection();
		Statement stmt = con.createStatement();
		try {
			stmt.executeUpdate(insertStamenet);
			con.commit();
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	void insertMany(List<String> insertStatements) throws Exception {
		Connection con = this.DB.getConnection();
		Statement stmt = con.createStatement();
		try {
			insertStatements.stream().forEach(q -> {
				try {
					stmt.addBatch(q);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			int[] updateCount = stmt.executeBatch();
			con.commit();
			LOG.info("{} record(s) inserted ", updateCount);
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}

	}
	
	void deleteAllData(String tableName) throws Exception{
		Connection con = this.DB.getConnection();
		Statement stmt = con.createStatement();
		try {
			stmt.execute("Truncate table "+tableName);
			con.commit();
			LOG.info("All data are deleted.");
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	void updateMany(List<String> updateStatements) throws Exception {
		Connection con = this.DB.getConnection();
		Statement stmt = con.createStatement();
		try {
			updateStatements.stream().forEach(q -> {
				try {
					stmt.addBatch(q);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			int[] updateCount = stmt.executeBatch();
			con.commit();
			LOG.info("{} record(s) updated ", updateCount);
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}		
	}
	
	void deleteData(List<String> deleteStaments) throws Exception {
		Connection con = this.DB.getConnection();
		Statement stmt = con.createStatement();
		try {
			deleteStaments.forEach(q -> {
				try {
					stmt.executeQuery(q);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			con.commit();
			LOG.info("{} record(s) deleted ", deleteStaments.size());
		} finally {
			closeStatement(stmt);
			closeConnection(con);
		}
	}
	
	void shutdown() {
		this.DB.shutdown();
	}
	
	private void closeConnection(Connection con) throws Exception {
		if (con !=null)
			con.close();
	}

	private void closeStatement(Statement stmt) throws Exception {
		if (stmt !=null)
			stmt.close();
	}

}
