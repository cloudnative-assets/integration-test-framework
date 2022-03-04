package com.ibm.epricer.test;

import com.ibm.epricer.test.solr.SolrDocumentBuilder;

public class DocTreeTest {
	public static void testBuilder() {
		SolrDocumentBuilder builder = new SolrDocumentBuilder();
		builder
			.start()
			 .addField("name", "kiran")
			 .addField("age", 42)
			 .startStructure()
			 	.addField("name", "Tanima")
			 	.addField("age", 39)
			 	.addStructure("spouce")
			 	.endStructure()
			 .startStructure()
			 	.addField("name", "reyan")
			 	.addField("age", "9")
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
			 		.addField("car", "honda accord")
			 		.addField("year", 2002)
			 	.createChild()
			 		.addField("car", "bmw")
			 		.addField("year", 2021)
			 	.addToCollection("cars")
			 	.endCollection()
			 .end()
			 .build();
			 		
		 	
	}
}
