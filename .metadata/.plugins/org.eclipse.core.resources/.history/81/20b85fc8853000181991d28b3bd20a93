package org.neu.ruotwang.courseservice1.descriptor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramDescriptor {
	
	private String programName;
	private List<String> courseIds;
	
	public ProgramDescriptor() {
		super();
	}
	
	@JsonCreator
	public ProgramDescriptor(
			@JsonProperty("programName") String programName,
			@JsonProperty("courseIds") List<String> courseIds) {
		this.programName = programName;
		this.courseIds = courseIds;
	}
	
	@JsonProperty("programName")
	public String getProgramName() {
		return programName;
	}
	
	@JsonProperty("courseIds")
	public List<String> getCourseList() {
		return courseIds;
	}
}
