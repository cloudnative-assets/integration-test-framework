package com.ibm.epricer.test.solr;

public class EpricerSolrCollection {
	String name;
	String path;

	/*
	 * Name of the collection (param or auth or document)
	 */
	public EpricerSolrCollection withName(String name) {
		this.name=name;
		return this;
	}
	
	/*
	 * solr config directory name under src/test/resource
	 */
	public EpricerSolrCollection withConfigPath(String path) {
		this.path=path;
		return this;
	}
}
