package com.ibm.epricer.test.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;


import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class MongoDBTestSupportChecker {
	private static final Logger LOG = LoggerFactory.getLogger(MongoDBTestSupportChecker.class);
	
	public static boolean isMongoDBEnabled() {
		return FieldHolder.DB_ENABLED;
	}
	
	/*
     * Lazy initialization holder class
     */
    private static class FieldHolder {
    	static final boolean DB_ENABLED = checkIfEntityClassesExist();
        static final String DATA_PACKAGES = "com.ibm.epricer";    	
    	private static boolean checkIfEntityClassesExist() {
    		try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(DATA_PACKAGES).scan()) {
    			ClassInfoList entityList = scanResult.getClassesWithAnnotation(Document.class.getName());
    			long found = entityList.stream().filter(ci -> {
    				LOG.info(">>>>>>>>>>>>>>>>Mongo Entity Classes>>>>>>>>>>>>>>>>>>>", ci.getPackageName());
    				return ci.getPackageName().startsWith(DATA_PACKAGES);
    				}).count();
                if (found > 0) {
                    LOG.info("Mongo Database transactions ENABLED (found {} entities)", found);
                    return true;
                }
    		}
    		
    		// Next look if any JPA repositories exist
    		try (ScanResult scanResult = new ClassGraph().acceptPackages(DATA_PACKAGES).scan()) {
    			ClassInfoList repoClasses = scanResult.getClassesImplementing(MongoRepository.class.getName());
    			long found = repoClasses.stream().filter(ci -> ci.getPackageName().startsWith(DATA_PACKAGES)).count();
                if (found > 0) {
                    LOG.info("Mongo Database transactions ENABLED (found {} repositories)", found);
                    return true;
                }    			
    		}
    		LOG.info("Mongo Database transactions DISABLED");
    		return false;
    		
    	}
    
    }

}
