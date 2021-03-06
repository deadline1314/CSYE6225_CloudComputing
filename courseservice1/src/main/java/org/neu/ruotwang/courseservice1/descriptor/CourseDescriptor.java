package org.neu.ruotwang.courseservice1.descriptor;

import java.util.List;

import org.neu.ruotwang.courseservice1.dao.Board;
import org.neu.ruotwang.courseservice1.dao.Lecture;
import org.neu.ruotwang.courseservice1.dao.Roster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDescriptor {
	
	private String courseName;
	private List<Lecture> lectureList;
	private Board board;
	private Roster roster;
	private List<String> studentIds;
	
	public CourseDescriptor() {
		super();
	}
	
	@JsonCreator
	public CourseDescriptor(
			@JsonProperty("courseName") String courseName,
			@JsonProperty("lectureList") List<Lecture> lectureList,
			@JsonProperty("board") Board board,
			@JsonProperty("roster") Roster roster,
			@JsonProperty("studentIds") List<String> studentIds) {
		this.courseName = courseName;
		this.lectureList = lectureList;
		this.board = board;
		this.roster = roster;
		this.studentIds = studentIds;
	}
	
	@JsonProperty("courseName")
	public String getCourseName() {
		return courseName;
	}
	
	@JsonProperty("lectureList")
	public List<Lecture> getLectureList() {
		return lectureList;
	}
	
	@JsonProperty("board")
	public Board getBoard() {
		return board;
	}
	
	@JsonProperty("roster")
	public Roster getRoster() {
		return roster;
	}
	
	@JsonProperty("studentIds")
	public List<String> getStudentIds() {
		return studentIds;
	}
}
