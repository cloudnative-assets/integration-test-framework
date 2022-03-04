package com.ibm.epricer.test.mongo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Conditional(IsMongoDBEnabled.class)
@Component
class MongoDBOperator implements DBOperator {

	@Autowired(required = false)
	private MongoTemplate mongoTemplate;
	
	@Override
	public <T> void createCollection(Class<T> entityClass) {
		mongoTemplate.createCollection(entityClass);
	}

	@Override
	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);
	}

	@Override
	public <T> Collection<T> createCollectionWithObjects(Collection<? extends T> objectsToSave, String collectionName) {
		mongoTemplate.createCollection(collectionName);
		Collection<T> t = mongoTemplate.insert(objectsToSave, collectionName);
		return t;
	}

	@Override
	public <T> T insertOne(T objectToSave, String collectionName) {
		return mongoTemplate.insert(objectToSave);
	}

	@Override
	public <T> Collection<T> insertMany(Collection<? extends T> batchToSave, String collectionName) {
		return mongoTemplate.insert(batchToSave, collectionName);
	}

	@Override
	public <T> long deleteOne(T objectToDelete, String collectionName) {
		// TODO Auto-generated method stub
		
		return mongoTemplate.remove(objectToDelete).getDeletedCount();
	}

	@Override
	public <T> long deleteByQuery(Query query, Class<T> entityClass) {
		// TODO Auto-generated method stub
		return mongoTemplate.remove(query, entityClass).getDeletedCount();
	}

	@Override
	public void dropCollection(String collectionName) {
		mongoTemplate.dropCollection(collectionName);
	}

	@Override
	public <T> Optional<List<T>> findAll(Class<T> entityClass) {
		return Optional.ofNullable(mongoTemplate.findAll(entityClass));
	}

	@Override
	public <T> Optional<List<T>> find(Query query, Class<T> entityClass) {
		return Optional.ofNullable(mongoTemplate.find(query, entityClass));
	}

	@Override
	public <T> T insertOne(T objectToSave) {
		return mongoTemplate.insert(objectToSave);
	}

	@Override
	public <T> Collection<T> insertMany(Collection<? extends T> batchToSave, Class<T> entityClass) {
		return mongoTemplate.insert(batchToSave, entityClass);
	}

	@Override
	public void cleanUp() {
		mongoTemplate
		.getCollectionNames()
		.stream()
		.forEach(collection -> mongoTemplate.dropCollection(collection));
	}

}

class IsMongoDBEnabled implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return MongoDBTestSupportChecker.isMongoDBEnabled();
	}

	
}
