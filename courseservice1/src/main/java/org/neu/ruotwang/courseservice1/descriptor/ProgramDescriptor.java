package org.neu.ruotwang.courseservice1.descriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramDescriptor {
	
	private String programName;
	
	public ProgramDescriptor() {
		super();
	}
	
	@JsonCreator
	public ProgramDescriptor(@JsonProperty("programName") String programName) {
		this.programName = programName;
	}
	
	@JsonProperty("programName")
	public String getProgramName() {
		return programName;
	}

}
