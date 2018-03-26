package org.neu.ruotwang.courseservice1.dao;

import java.util.Collection;
import java.util.Optional;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public class StudentDao extends AbstractServiceDao<Student> {

	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();
	
	@Override
	public Optional<Student> get(String studentId) throws RuntimeException {
		try {
			Student student = new Student();
			student.setStudentId(studentId);
			return Optional.ofNullable(mapper.load(student));
		} catch (Exception ex) {
			throw new RuntimeException("Exception while querying an student " + studentId, ex);
		}
	}
	
	public Collection<Student> getAll() throws RuntimeException {
		try {
			return mapper.scan(Student.class, new DynamoDBScanExpression());
		} catch (Exception ex) {
			throw new RuntimeException("Exception while scaning all Students", ex);
		}
	}

}
