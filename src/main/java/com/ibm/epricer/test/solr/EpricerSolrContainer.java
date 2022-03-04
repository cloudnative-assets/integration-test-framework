package com.ibm.epricer.test.solr;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.SolrContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

import com.github.dockerjava.api.command.InspectContainerResponse;

public class EpricerSolrContainer extends SolrContainer{
	private static final Logger LOG = LoggerFactory.getLogger(EpricerSolrContainer.class);
		
	private List<EpricerSolrCollection> collections = new ArrayList<>();
	
	private String solrUrlKey;
		
	private int numShards = 1;
		
	private SolrClient client;
		

	
	EpricerSolrContainer(String image, List<EpricerSolrCollection> collections, String solrUrlKey, boolean streamContainerLog) {
		super(DockerImageName.parse(image));
		this.collections=collections;
		this.solrUrlKey=solrUrlKey;
		collections.forEach(c -> {
			addFileSystemBind(getConfigPath(c.path),
	                String.format("/opt/solr/server/solr/configsets/%s", c.path), BindMode.READ_ONLY);			
		});

		this.withExposedPorts(8983).start();
		if (streamContainerLog) {
			this.withLogConsumer(new Slf4jLogConsumer(LOG));
		}
		this.start();
	}

	/*
	 * Return the solr client
	 */
	public SolrClient getClient() {
		return this.client;
	}
	
	String getConfigPath(String configPath) {
		final String absoluteConfigPath = Paths.get("src", "test", "resources").toFile().getAbsolutePath()+"/"+configPath;
		return absoluteConfigPath;
	}
	
	String getSolrUrl() {
		String solrUrl = String.format("http://%s:%s/solr", getContainerIpAddress(), getSolrPort());
		
		return solrUrl;
	}
	
    /**
     * create a new SolrClient usable by tests
     */
    void createSolrClient() {
    	String solrUrl = String.format("http://%s:%s/solr", getContainerIpAddress(), getMappedPort(8983));
    	System.setProperty(this.solrUrlKey, solrUrl);
    	this.client = new Http2SolrClient.Builder(solrUrl).build();
        LOG.info("Solr container started and ready to serve from {}", solrUrl);

    }
	
    /**
     * Overwrite how the Solr container is initialized. We use the internal solr
     * command to upload our whole mounted configset folder.
     */
    @Override
    protected void containerIsStarted(InspectContainerResponse containerInfo) {
    	try {
    		// upload configset (config) that we linked into the container
    		collections.forEach(c -> {
                
				try {
					ExecResult result = execInContainer("solr", "zk", "upconfig", "-z", "localhost:9983", "-n",
							c.path, "-d",
					        String.format("/opt/solr/server/solr/configsets/%s", c.path));
	                if (result.getExitCode() != 0) {
	                    throw new IllegalStateException(
	                            String.format("Could not upload %s configset to Zookeeper: %s", c.path, result));
	                }
	                LOG.info(String.format("Successfully uploaded %s configset to Zookeeper: %s", c.path, result));
	                LOG.info("Config path: {}",String.format("/opt/solr/server/solr/configsets/%s", c.path));
	                // create collection with said configset
	                EpricerSolrClientUtils.createCollection(this, c.name, c.path, numShards);
	                LOG.info("Successfully created the collection:collectionName:{} configPath:{} numberOfShards:{}", c.name, c.path, numShards);
				} catch (UnsupportedOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		});

            createSolrClient();

    	} catch (Exception ex) {
    		throw new RuntimeException(ex);
    	}
    }
	
	
	
	
}
