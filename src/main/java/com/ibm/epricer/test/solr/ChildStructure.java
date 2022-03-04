package com.ibm.epricer.test.solr;

import java.util.HashMap;
import java.util.Map;
/*
 * Data structure of to hold the child structures under parent (EpricerSolrDocument) node
 * 
 * @author Kiran Chowdhury
 */
public class ChildStructure {
	Map<String, Map<String, Object>> childMap = new HashMap<>();
	private Map<String, Object> child = new HashMap<String, Object>();
	private EpricerSolrDocument parent;
	ChildStructure(EpricerSolrDocument parent) {
		this.parent=parent;
	}
	
	/*
	 * Add a field to the child structure
	 */
	public ChildStructure addField(String fieldName, Object fieldValue) {
		child.put(fieldName, fieldValue);
		return this;
	}
	
	/*
	 * Command to hold the whole structure against the given structure name
	 */
	public ChildStructure addStructure(String structureName) {
		childMap.put(structureName, child);
		return this;
	}
	
	/*
	 * Command to end child structure creation and return the parent reference
	 */
	public EpricerSolrDocument endStructure() {
		return this.parent;
	}

}
