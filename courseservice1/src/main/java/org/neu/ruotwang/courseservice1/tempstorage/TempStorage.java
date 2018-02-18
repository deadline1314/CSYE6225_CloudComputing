package org.neu.ruotwang.courseservice1.tempstorage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.neu.ruotwang.courseservice1.dao.Board;
import org.neu.ruotwang.courseservice1.dao.Lecture;
import org.neu.ruotwang.courseservice1.dao.Roster;
import org.neu.ruotwang.courseservice1.descriptor.CourseDescriptor;
import org.neu.ruotwang.courseservice1.descriptor.ProgramDescriptor;
import org.neu.ruotwang.courseservice1.descriptor.StudentDescriptor;

@Singleton
public class TempStorage {
	// --------<StudentId, StudentDescriptor>
	private static Map<String, StudentDescriptor> STUDENT_TABLE = new HashMap<>();
	// --------<CourseId, CourseDescriptor>
	private static Map<String, CourseDescriptor> COURSE_TABLE = new HashMap<>();
	// --------<ProgramId, ProgramDescriptor>
	private static Map<String, ProgramDescriptor> PROGRAM_TABLE = new HashMap<>();
	
	public TempStorage() {
		STUDENT_TABLE.put("001stu", new StudentDescriptor("ruotwang", "C:/xxx/xxx.img",
				Arrays.asList("001cs", "002cs"), "csye"));
		COURSE_TABLE.put("001cs", new CourseDescriptor("cloud", Arrays.asList(new Lecture()),
				new Board(), new Roster(), Arrays.asList("001stu")));
		PROGRAM_TABLE.put("001is", new ProgramDescriptor("mis", Arrays.asList("001cs")));
	}
	
	public Map<String, StudentDescriptor> getSTUDENT_TABLE() {
		return STUDENT_TABLE;
	}

	public Map<String, CourseDescriptor> getCOURSE_TABLE() {
		return COURSE_TABLE;
	}

	public Map<String, ProgramDescriptor> getPROGRAM_TABLE() {
		return PROGRAM_TABLE;
	}
}
