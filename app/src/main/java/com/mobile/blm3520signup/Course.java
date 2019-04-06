package com.mobile.blm3520signup;

import java.util.ArrayList;

public class Course
{
	private String code;
	private String name;
	private int grade;
	private int studentCount;
	private double averageGrade;

	public Course(String code, String name, int grade, int studentCount, double averageGrade)
	{
		this.code = code;
		this.name = name;
		this.grade = grade;
		this.studentCount = studentCount;
		this.averageGrade = averageGrade;
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public int getGrade()
	{
		return grade;
	}

	public int getStudentCount()
	{
		return studentCount;
	}

	public double getAverageGrade()
	{
		return averageGrade;
	}

	public static ArrayList<Course> getCourseList()
	{
		ArrayList<Course> courses = new ArrayList<Course>();

		courses.add(new Course("BLM3520", "Introduction to Mobile Programming", 100, 45, 75.0));
		courses.add(new Course("BLM3022", "Computer Networking Technologies", 90, 85, 65.5));
		courses.add(new Course("BLM3590", "Statistical Data Analysis", 67, 30, 78.4));

		return courses;
	}
}
