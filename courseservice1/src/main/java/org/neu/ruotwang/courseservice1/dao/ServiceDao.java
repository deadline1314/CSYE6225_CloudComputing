package org.neu.ruotwang.courseservice1.dao;

import java.util.Optional;

public interface ServiceDao<T> {
	
	void save(T value) throws RuntimeException;
	
	void delete(T value) throws RuntimeException;
	
	Optional<T> get(String key) throws RuntimeException;

}
