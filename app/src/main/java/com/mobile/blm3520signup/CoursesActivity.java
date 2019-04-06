package com.mobile.blm3520signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity
{
	RecyclerView recyclerView;
	ArrayList<Course> list;
	CoursesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_courses);

		recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Courses);
		recyclerView.setLayoutManager(new LinearLayoutManager(CoursesActivity.this));

		list = Course.getCourseList();

		adapter = new CoursesAdapter(CoursesActivity.this, list);
		recyclerView.setAdapter(adapter);
	}
}