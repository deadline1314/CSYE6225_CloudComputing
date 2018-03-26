package org.neu.ruotwang.courseservice1.dao.converter;

import org.neu.ruotwang.courseservice1.dao.Board;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BoardConverter implements DynamoDBTypeConverter<String, Board>{

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final TypeReference<Board> TYPE_REFERENCE = new TypeReference<Board>() {};

	@Override
	public String convert(Board board) {
		try {
			return OBJECT_MAPPER.writeValueAsString(board);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot serialize " + board, ex);
		}
	}

	@Override
	public Board unconvert(String obj) {
		try {
			return OBJECT_MAPPER.readValue(obj, TYPE_REFERENCE);
		} catch (Exception ex) {
			throw new RuntimeException("Cannot deserialize board " + obj, ex);
		}
	}

}
