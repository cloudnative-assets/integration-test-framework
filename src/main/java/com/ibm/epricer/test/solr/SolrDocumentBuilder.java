package com.ibm.epricer.test.solr;

import org.apache.solr.common.SolrInputDocument;
/*
 * Provide easy interface to build solr input document
 * e.g - 
 * `
 *		SolrDocumentBuilder builder = new SolrDocumentBuilder();
		builder
			.start()
			 .addField("name", "Some Name")
			 .addField("age", 40)
			 .startStructure()
			 	.addField("name", "Spouse Age")
			 	.addField("age", 35)
			 	.addStructure("spouse")
			 	.endStructure()
			 .startStructure()
			 	.addField("name", "Son Name")
			 	.addField("age", 10)
			 	.addStructure("son")
			 	.endStructure()
			 .startCollection()
			 	.createChild()
			 		.addField("company", "ibm")
			 		.addField("location", "usa")
			 	.createChild()
			 		.addField("company", "aztec")
			 		.addField("location", "india")
			 	.addToCollection("companies")
			 	.endCollection()
			 .startCollection()
			 	.createChild()
			 		.addField("car", "Honda Accord")
			 		.addField("year", 2002)
			 	.createChild()
			 		.addField("car", "BMW")
			 		.addField("year", 2021)
			 	.addToCollection("cars")
			 	.endCollection()
			 .end()
			 .build();
 * `
 * 
 * @author Kiran Chowdhury 
 */
public class SolrDocumentBuilder {	
	EpricerSolrDocument doc;

	public EpricerSolrDocument start() {
		this.doc = new EpricerSolrDocument(this);
		return this.doc;
	}
	
	public SolrInputDocument build() {
		handleChildStructs();
		handleCollections();
		System.out.println(doc.solrDoc.jsonStr());
		return doc.solrDoc;
	}

	private void handleCollections() {
		doc
		  .collections
		  	.forEach(col -> {
		  		col
		  		.childsMap
				.entrySet()
				.stream()
				.forEach(e -> {
					String name = e.getKey();
					String encodedStr = ChildNodeEncoder.encodeChildCollection(e.getValue());
					doc.solrDoc.addField(name, encodedStr);
				});		  			
		  	});
	}

	private void handleChildStructs() {
		doc.structs
			.forEach(struct -> {
				struct
				.childMap
				.entrySet()
				.stream()
				.forEach(e -> {
					String name = e.getKey();
					String encodedString = ChildNodeEncoder.encodeChildStructure(e.getValue());
					doc.addField(name, encodedString);
				});
			});
	}
	
	
	
}
