package org.neu.ruotwang.courseservice1.dao.converter;

import org.neu.ruotwang.courseservice1.dao.Roster;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RosterConverter implements DynamoDBTypeConverter<String, Roster>{
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final TypeReference<Roster> TYPE_REFERENCE = new TypeReference<Roster>(){};

	@Override
	public String convert(Roster roster) {
		try {
			return OBJECT_MAPPER.writeValueAsString(roster);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot serialize " + roster, ex);
		}
	}

	@Override
	public Roster unconvert(String obj) {
		try {
			return OBJECT_MAPPER.readValue(obj, TYPE_REFERENCE);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot deserialize board " + obj, ex);
		}
	}

}
