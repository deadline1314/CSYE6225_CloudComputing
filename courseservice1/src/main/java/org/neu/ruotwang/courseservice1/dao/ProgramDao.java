package org.neu.ruotwang.courseservice1.dao;

import java.util.Optional;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class ProgramDao extends AbstractServiceDao<Program> {

	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();
	@Override
	public Optional<Program> get(String programId) throws RuntimeException {
		try {
			Program program = new Program();
			program.setProgramId(programId);
			return Optional.ofNullable(mapper.load(program));
		} catch(Exception ex) {
			throw new RuntimeException("Exception while querying an program " + programId, ex);
		}
	}

}
