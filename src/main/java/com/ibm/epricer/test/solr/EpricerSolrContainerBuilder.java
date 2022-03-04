package com.ibm.epricer.test.solr;

import java.util.ArrayList;
import java.util.List;

/*
 * Build EpricerSolrContainer that runs in docker and upconfig the configsets by mounting the config files in the docker container
 * 
 * @author Kiran Chowdhury
 */
public class EpricerSolrContainerBuilder {
	private String image;
	
	private String solrUrlKey;
	
	private boolean streamLog;
	
	private List<EpricerSolrCollection> collections = new ArrayList<>();
	
	private EpricerSolrCollection collection;
	
	public EpricerSolrContainerBuilder() {
		
	}
	
	/*
	 * Fully qualified solr image name (solr:8.10.1) 
	 */
	public EpricerSolrContainerBuilder withImage(String image) {
		this.image = image;
		return this;
	}
	
	/*
	 * Provide the key against which the solr test container url will stored in the system property
	 */
	public EpricerSolrContainerBuilder withSolrUrlKey(String solrUrlKey) {
		this.solrUrlKey=solrUrlKey;
		return this;
	}
	
	/*
	 * If the solr container log will be stream to the test application logger.
	 * Default is false
	 */
	public EpricerSolrContainerBuilder streamContainerLog(boolean streamLog) {
		this.streamLog = streamLog;
		return this;
	}
	
	/*
	 * Add a solr collection to be created
	 * @param collectionName - Name of the collection (param or auth or document etc)
	 * @param configPath - collection's config directory name under src/test/resource
	 */
	public EpricerSolrContainerBuilder addCollection(String collectionName, String configPath) {
		collection= new EpricerSolrCollection();
		collection.name=collectionName;
		collection.path=configPath;
		collections.add(collection);
		return this;
	}
	
	
	

	
	public EpricerSolrContainer build() {
		EpricerSolrContainer container = new EpricerSolrContainer(image, collections, solrUrlKey, streamLog);
		return container;
	}
	
	
	
}
