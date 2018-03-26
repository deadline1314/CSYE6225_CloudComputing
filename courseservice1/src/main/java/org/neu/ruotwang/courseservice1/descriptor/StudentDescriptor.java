package org.neu.ruotwang.courseservice1.descriptor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentDescriptor {
	
	private String studentName;
	private String studentEmail;
	private String imagePath;
	private List<String> courseIds;
	private String programId;
	
	public StudentDescriptor() {
		super();
	}
	
	@JsonCreator
	public StudentDescriptor(
			@JsonProperty("studentName") String studentName,
			@JsonProperty("studentEmail") String studentEmail,
			@JsonProperty("imagePath") String imagePath,
			@JsonProperty("courseIds") List<String> courseIds,
			@JsonProperty("programId") String programId) {
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.imagePath = imagePath;
		this.courseIds = courseIds;
		this.programId = programId;
	}
	
	@JsonProperty("studentName")
	public String getStudentName() {
		return studentName;
	}
	
	@JsonProperty("studentEmail")
	public String getStudentEmail() {
		return studentEmail;
	}
	
	@JsonProperty("imagePath")
	public String getImagePath() {
		return imagePath;
	}
	
	@JsonProperty("courseIds")
	public List<String> getCourseIds() {
		return courseIds;
	}
	
	@JsonProperty("programId")
	public String getProgramId() {
		return programId;
	}
}
