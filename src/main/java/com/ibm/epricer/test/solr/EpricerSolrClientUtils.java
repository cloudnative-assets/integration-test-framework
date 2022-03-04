package com.ibm.epricer.test.solr;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testcontainers.containers.SolrClientUtils;


/**
 * Mostly copied code from {@link SolrClientUtils} as it's not open for
 * extension :-/
 * @author Kiran S Chowdhury
 */
public class EpricerSolrClientUtils extends SolrClientUtils {
	static void createCollection(EpricerSolrContainer solr, String collectionName, String configurationName,
            int numShards) {
		
        // compose create collection url
        HttpGet createCollection = new HttpGet(String.format(
                "%s/admin/collections?action=CREATE&name=%s&numShards=%s&replicationFactor=1&wt=json&collection.configName=%s&maxShardsPerNode=%s",
                solr.getSolrUrl(), collectionName, numShards, configurationName, numShards));
        // execute request
        try (CloseableHttpClient client = HttpClients.createMinimal();
            CloseableHttpResponse response = client.execute(createCollection);) {
            
            if (response.getStatusLine().getStatusCode() > 299) {
                throw new IllegalArgumentException(response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
