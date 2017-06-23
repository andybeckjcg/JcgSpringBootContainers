package com.javacodegeeks.examples.boot.app.model;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelLoadTest {

	@Test
	public void writeTest() {
		Teacher teacher = new Teacher();
		teacher.setDateOfBirth(Date.valueOf("1990-01-01"));
		teacher.setEmailAddress("sallymentor@teach.edu");
		teacher.setFirstName("Sally");
		teacher.setLastName("Mentor");
		teacher.setPersonId(1);
		teacher.setPhoneNumber("");
		teacher.setSalary(10000);
		teacher.setYearsOfExperience(10);
		
		Student student1 = new Student();
		student1.setCurrentClass(1);
		student1.setDateOfBirth(Date.valueOf("2010-01-01"));
		student1.setEmailAddress("sallymentee@teach.edu");
		student1.setFirstName("Sally");
		student1.setLastName("Mentee");
		student1.setPersonId(1);
		student1.setPhoneNumber("");
		student1.setGrade("A");
		student1.setPreviousGrades(null);
		
		Class clas = new Class();
		clas.setClassId(1);
		clas.setClassName("First1");
		clas.setClassroom("101A");
		clas.setGrade(1);
		clas.setStudents(Arrays.asList(student1));
		clas.setTeacher(teacher);
		
		School school = new School();
		school.setSchoolId(1);
		school.setAddress("1 Main Street");
		school.setCity("Smalltown");
		school.setState("CI");
		school.setZip("00000");
		school.setClasses(Arrays.asList(clas));
		
		ObjectMapper objectMapper = new ObjectMapper();
		String schoolStr;
		try {
			schoolStr = objectMapper.writeValueAsString(school);
			FileUtils.writeStringToFile(new File("result.json"), schoolStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readTest() {
		ObjectMapper objectMapper = new ObjectMapper();
		//String fileStr = FileUtils.readFileToString("schoolSample.json");
		try {
			School mySchool = objectMapper.readValue(ClassLoader.getSystemResourceAsStream("schoolSample.json"), School.class);
			System.out.println(objectMapper.writeValueAsString(mySchool));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
