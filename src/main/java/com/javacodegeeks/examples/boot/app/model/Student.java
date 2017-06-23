package com.javacodegeeks.examples.boot.app.model;

import java.util.List;

public class Student extends Person {
	private int currentClass;
	private String grade;
	private List<String> previousGrades;

	public int getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(int currentClass) {
		this.currentClass = currentClass;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<String> getPreviousGrades() {
		return previousGrades;
	}

	public void setPreviousGrades(List<String> previousGrades) {
		this.previousGrades = previousGrades;
	}

}
