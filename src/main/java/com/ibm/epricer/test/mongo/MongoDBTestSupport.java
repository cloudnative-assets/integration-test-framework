package com.ibm.epricer.test.mongo;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;


@Configuration
public class MongoDBTestSupport implements ApplicationListener<ApplicationContextInitializedEvent>{
	private static final Logger LOG = LoggerFactory.getLogger(MongoDBTestSupport.class);
    
	private static final String KEY = "spring.autoconfigure.exclude";
	private static final String VALUE = "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration";

	@Override
	public void onApplicationEvent(ApplicationContextInitializedEvent event) {
		// TODO Auto-generated method stub
        LOG.debug("Checking if mongo database test support needs to be enabled...");
        if (!MongoDBTestSupportChecker.isMongoDBEnabled()) {
        	LOG.debug("Mongo DB Test Support not required, Disabling Mongo DB Auto Configuration.........");
            ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
            /*
             * If KEY property already exists, override it with the combined value 
             */
            String existingValue = env.getProperty(KEY);
            String value = VALUE + (isNotBlank(existingValue) ? "," + existingValue : "");
            MutablePropertySources sources = env.getPropertySources();
            sources.addFirst(new MapPropertySource("disable-database", Collections.singletonMap(KEY, value)));
            
        }
	}

}
