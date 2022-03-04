package com.ibm.epricer.test.mongo;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;



public class MongoDatabaseTestContainerIntitializer extends MongoDBContainer {
	
	public static final Logger LOG = LoggerFactory.getLogger(MongoDatabaseTestContainerIntitializer.class);
	
	@Value("${mongodb.version:4.0.0}")
	private String mongoDBVersion;
	
	public static final MongoDBContainer mongoDBContainer = build();
	
	
	static MongoDBContainer build() {
		if (MongoDBTestSupportChecker.isMongoDBEnabled()) {
	 		final MongoDBContainer mongoDBContainer = 
					new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
			mongoDBContainer.start();
			return mongoDBContainer;			
		} else {
			LOG.info("MongoDBTestContainer will not be created as MongoDBTestSupport is not required");
			return null;
		}

	}
	
	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			if (MongoDBTestSupportChecker.isMongoDBEnabled()) {
				String mongUri = format("spring.data.mongodb.uri=mongodb://%s:%s/testdb",mongoDBContainer.getContainerIpAddress(), mongoDBContainer.getMappedPort(27017));
				TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,mongUri);
				LOG.info("Test mongo db started in {}", mongUri);				
			}

		}
		
	}

}
