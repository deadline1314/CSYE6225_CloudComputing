package org.neu.ruotwang.courseservice1.descriptor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnouncementDescriptor {
	private String professorId;
	private String content;
	
	@JsonCreator
	public AnnouncementDescriptor(
			@JsonProperty("professorId") String professorId,
			@JsonProperty("content") String content) {
		this.professorId = professorId;
		this.content = content;
	}
	
	@JsonProperty("professorId")
	public String getProfessorId() {
		return professorId;
	}
	@JsonProperty("content")
	public String getContent() {
		return content;
	}
}
