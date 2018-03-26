package org.neu.ruotwang.courseservice1.descriptor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfessorDescriptor {
	
	private String professorName;
	private List<String> courseIdList;
	
	public ProfessorDescriptor() {
		super();
	}
	
	@JsonCreator
	public ProfessorDescriptor(
			@JsonProperty("professorName") String professorName,
			@JsonProperty("courseIdList") List<String> courseIdList) {
		this.professorName = professorName;
		this.courseIdList = courseIdList;
	}
	
	@JsonProperty("professorName")
	public String getProfessorName() {
		return professorName;
	}
	
	@JsonProperty("courseIdList")
	public List<String> getCourseIdList() {
		return courseIdList;
	}
	
}
