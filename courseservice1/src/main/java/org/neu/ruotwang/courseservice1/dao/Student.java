package org.neu.ruotwang.courseservice1.dao;

import java.util.List;

import lombok.Data;

@Data
public class Student {
	private String studentId;
	private String studentName;
	private String imagePath;
	private List<String> courseIds;
	private String programId;
}
