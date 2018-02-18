package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import lombok.Data;

@Data
public class Course {
	private String courseId;
	private String courseName;
	private List<Lecture> lectureList;
	private Board board;
	private Roster roster;
	private List<String> studentIds;
}
