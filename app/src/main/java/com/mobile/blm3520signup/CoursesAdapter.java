package com.mobile.blm3520signup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>
{
	Context context;
	ArrayList<Course> list;

	public CoursesAdapter(Context context, ArrayList<Course> list)
	{
		this.context = context;
		this.list = list;
	}

	@NonNull
	@Override
	public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

		return new CoursesViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull CoursesViewHolder coursesViewHolder, int i)
	{
		coursesViewHolder.courseCode.setText(list.get(i).getCode());
		coursesViewHolder.grade.setText(String.valueOf(list.get(i).getGrade()));
	}

	@Override
	public int getItemCount()
	{
		return list.size();
	}

	class CoursesViewHolder extends RecyclerView.ViewHolder
	{
		TextView courseCode, grade;

		public CoursesViewHolder(@NonNull View itemView)
		{
			super(itemView);
			courseCode = (TextView) itemView.findViewById(R.id.textView_CourseCode);
			grade = (TextView) itemView.findViewById(R.id.textView_Grade);
		}
	}
}
