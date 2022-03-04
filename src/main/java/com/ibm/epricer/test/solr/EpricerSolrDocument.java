package com.ibm.epricer.test.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

/*
 * Builder class that builds the input document to be saved in solr
 */
public class EpricerSolrDocument {
	
	
	SolrInputDocument solrDoc;
	
	List<ChildStructure> structs = new ArrayList<>();
	List<ChildCollection> collections = new ArrayList<>();
	
	private ChildStructure struct;
	
	private ChildCollection collection;
	
	private SolrDocumentBuilder builder;
	
	EpricerSolrDocument(SolrDocumentBuilder builder) {
		this.solrDoc=new SolrInputDocument();

		this.builder=builder;
	}
	
	/*
	 * Add a field to the this node structure as leaf
	 */
	public EpricerSolrDocument addField(String fieldName, Object value) {
		this.solrDoc.addField(fieldName, value);
		return this;
	}
	
	/*
	 * Start to create a structure to this node and return the reference to the child node structure
	 */
	public ChildStructure startStructure() {
		this.struct = new ChildStructure(this);
		this.structs.add(this.struct);
		return this.struct;
	}
	
	/*
	 * Start to create a collection to this node and return the reference to the child collection structure
	 */
	public ChildCollection startCollection() {
		this.collection = new ChildCollection(this);
		this.collections.add(collection);
		return this.collection;
	}
	/*
	 * End the Solr doc building process and return the reference to the SolrDocumentBuilder
	 */
	public SolrDocumentBuilder end() {
		return this.builder;
	}
	
}
