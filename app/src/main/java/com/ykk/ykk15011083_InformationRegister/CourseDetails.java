package com.ykk.ykk15011083_InformationRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourseDetails extends AppCompatActivity
{
	TextView code, name, grade, count, average;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_details);

		code = (TextView) findViewById(R.id.textView_CourseCode);
		name = (TextView) findViewById(R.id.textView_CourseName);
		grade = (TextView) findViewById(R.id.textView_Grade);
		count  = (TextView) findViewById(R.id.textView_StudentCount);
		average = (TextView) findViewById(R.id.textView_AverageGrade);

		final Intent intent = getIntent();
		Course course = (Course) intent.getSerializableExtra("course");

		code.setText(course.getCode());
		name.setText(course.getName());
		grade.setText(String.valueOf(course.getGrade()));
		count.setText(String.valueOf(course.getStudentCount()));
		average.setText(String.valueOf(course.getAverageGrade()));
	}
}
