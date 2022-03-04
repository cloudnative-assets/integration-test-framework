package com.ibm.epricer.test.mongo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Query;

public interface DBOperator {
	
	/*
	 * Create an uncapped collection with a name based on the provided entity class.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> void createCollection(Class<T> entityClass);
	
	
	/*
	 * Create an uncapped collection with the specified name.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public void createCollection(String collectionName);
	
	/*
	 * First Create an uncapped collection with the specified name and then insert a batch of objects into the specified collection in a single batch write to the database.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> Collection<T> createCollectionWithObjects(Collection<? extends T> objectsToSave, String collectionName);
	
	
	/*
	 * Find All records for the specified entity
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> Optional<List<T>> findAll(Class<T> entityClass);
	
	
	/*
	 * Find All records for the specified query for in the specified entity
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> Optional<List<T>> find(Query query, Class<T> entityClass);
	
	/*
	 * Insert the object into the specified collection.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> T insertOne(T objectToSave, String collectionName);
	
	
	/*
	 * Insert the object in the collection based on provided type of the the object to be saved.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> T insertOne(T objectToSave);

	/*
	 * Insert a batch of objects into the specified collection in a single batch write to the database.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> Collection<T> insertMany(Collection<? extends T> batchToSave, String collectionName);
	
	
	/*
	 * Insert a batch of objects into the collection based on the provided entity class in a single batch write to the database.
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> Collection<T> insertMany(Collection<? extends T> batchToSave, Class<T> entityClass);
	
	
	/*
	 * Delete the object from the specified collection.
	 * 
	 * @author Kiran S Chowdhury
	 */	
	public <T> long deleteOne(T objectToDelete, String collectionName);
	
	/*
	 * Delete objects by query
	 * 
	 * @author Kiran S Chowdhury
	 */
	public <T> long deleteByQuery(Query query, Class<T> entityClass);
	
	
	/*
	 * Drop specified collection..
	 * 
	 * @author Kiran S Chowdhury
	 */
	public void dropCollection(String collectionName);
	
	/*
	 * Drop all collections.
	 */
	public void cleanUp();
	
}
