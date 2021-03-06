package org.neu.ruotwang.courseservice1.dao;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public abstract class AbstractServiceDao<T> implements ServiceDao<T> {
	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();
	
	@Override
	public void save(T value) throws RuntimeException {
		try {
			mapper.save(value);
		} catch (Exception ex) {
			throw new RuntimeException("Exception while trying to save " + value, ex);
		}
	}
	
	@Override
	public void delete(T value) throws RuntimeException {
		try {
			mapper.delete(value);
		} catch (Exception ex){
			throw new RuntimeException("Exception while trying to delete " + value, ex);
		}
	}
	
}
