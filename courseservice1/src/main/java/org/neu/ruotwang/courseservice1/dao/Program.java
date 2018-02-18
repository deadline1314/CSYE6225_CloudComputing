package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import lombok.Data;

@Data
public class Program {
	private String programId;
	private String programName;
	private List<Course> courseList;
}
