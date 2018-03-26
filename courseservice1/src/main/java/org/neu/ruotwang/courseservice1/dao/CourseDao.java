package org.neu.ruotwang.courseservice1.dao;

import java.util.Collection;
import java.util.Optional;

import org.neu.ruotwang.courseservice1.configuration.AWSDynamoDBClient;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public class CourseDao extends AbstractServiceDao<Course> {

	protected final DynamoDBMapper mapper = AWSDynamoDBClient.getDynamoDBMapper();

	@Override
	public Optional<Course> get(String courseId) throws RuntimeException {
		try {
			Course course = new Course();
			course.setCourseId(courseId);
			return Optional.ofNullable(mapper.load(course));
		} catch (Exception ex) {
			throw new RuntimeException("Exception while querying an course " + courseId, ex);
		}
	}
	
	public Collection<Course> getAll() throws RuntimeException {
		try {
			return mapper.scan(Course.class, new DynamoDBScanExpression());
		} catch (Exception ex) {
			throw new RuntimeException("Exception while scaning all Courses", ex);

		}
	}

}
