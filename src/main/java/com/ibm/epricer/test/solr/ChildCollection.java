package com.ibm.epricer.test.solr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * Data structure of to hold the child collections under parent (EpricerSolrDocument) node
 * 
 * @author Kiran Chowdhury
 */
public class ChildCollection {
	Map<String, List<Map<String, Object>>> childsMap = new HashMap<>();
	private List<Map<String, Object>> childs = new ArrayList<>();
	
	private Map<String, Object> child;
	
	private EpricerSolrDocument parent;
	
	
	ChildCollection(EpricerSolrDocument parent) {
		this.parent = parent;
	}
	
	/*
	 * Command to create a child structure that will be added as an item to this ChildConnection
	 */
	public ChildCollection createChild() {
		child = new HashMap<>();
		childs.add(child);
		return this;
	}
	
	/*
	 * Command to add a field as leaf to the child item. This command must be issued after createChild command
	 */
	public ChildCollection addField(String name, Object value) {
		this.child.put(name, value);
		return this;
	}
	
	/*
	 * Command to add the child item in this ChildCollection against the given collectionName
	 */
	public ChildCollection addToCollection(String collectionName) {
		childsMap.put(collectionName, childs);
		return this;
	}
	
	/*
	 * Command to end the childcollection and return the parent (EpricerSolrDocument) reference
	 */
	public EpricerSolrDocument endCollection() {
		return this.parent;
	}
	
	
}
