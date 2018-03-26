package org.neu.ruotwang.courseservice1.dao;

import java.util.Optional;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class ProfessorDao extends AbstractServiceDao<Professor> {
	
	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();

	@Override
	public Optional<Professor> get(String professorId) throws RuntimeException {
		try {
			Professor professor = new Professor();
			professor.setProfessorId(professorId);
			return Optional.ofNullable(mapper.load(professor));
		} catch (Exception ex) {
			throw new RuntimeException("Exception while querying an professor " + professorId, ex);
		}
	}

}
