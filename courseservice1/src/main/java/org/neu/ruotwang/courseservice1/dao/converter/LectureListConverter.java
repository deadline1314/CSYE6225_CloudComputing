package org.neu.ruotwang.courseservice1.dao.converter;

import java.util.List;

import org.neu.ruotwang.courseservice1.dao.Lecture;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LectureListConverter implements DynamoDBTypeConverter<String, List<Lecture>>{

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final TypeReference<List<Lecture>> TYPE_REFERENCE = new TypeReference<List<Lecture>>(){};
	
	@Override
	public String convert(List<Lecture> lectureList) {
		try {
			return OBJECT_MAPPER.writeValueAsString(lectureList);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot serialize lecture list " + lectureList, ex);
		}
	}

	@Override
	public List<Lecture> unconvert(String obj) {
		try {
			return OBJECT_MAPPER.readValue(obj, TYPE_REFERENCE);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot deserialize lecture list " + obj, ex);
		}
	}

}
