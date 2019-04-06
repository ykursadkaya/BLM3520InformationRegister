package com.ykk.ykk15011083_InformationRegister;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfilePage extends AppCompatActivity
{
	private EditText viewName, viewSurname, viewID, viewBirthday, viewPlace, viewAge, viewPhone, viewMail;
	private Button back, call, addEvent, mail, courses;
	private ImageView ppView;
	private final Calendar calendar = Calendar.getInstance();

	private final static int REQUEST_CODE_CALL = 9;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_page);

		viewName = (EditText) findViewById(R.id.viewText_Name);
		viewSurname = (EditText) findViewById(R.id.viewTextSurname);
		viewID = (EditText) findViewById(R.id.viewTextID);
		viewBirthday = (EditText) findViewById(R.id.viewTextBirthday);
		viewPlace = (EditText) findViewById(R.id.viewTextBirthPlace);
		viewAge = (EditText) findViewById(R.id.viewTextAge);
		viewPhone = (EditText) findViewById(R.id.viewTextPhone);
		viewMail = (EditText) findViewById(R.id.viewTextMail);
		back = (Button) findViewById(R.id.button_Back);
		call = (Button) findViewById(R.id.button_Call);
		mail = (Button) findViewById(R.id.button_Email);
		addEvent = (Button) findViewById(R.id.button_AddEvent);
		courses = (Button) findViewById(R.id.button_ListCourses);
		ppView = (ImageView) findViewById(R.id.imageView_PP_PPage);

		final Intent intent = getIntent();
		viewName.setText(intent.getStringExtra("name"));
		viewSurname.setText(intent.getStringExtra("surname"));
		viewID.setText(intent.getStringExtra("id"));
		final String birthdayStr = intent.getStringExtra("birthday");
		viewBirthday.setText(birthdayStr);
		viewPlace.setText(intent.getStringExtra("place"));
		viewPhone.setText(intent.getStringExtra("phone"));
		viewMail.setText(intent.getStringExtra("mail"));

		call.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ContextCompat.checkSelfPermission(ProfilePage.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(ProfilePage.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
				}
				else
				{
					if (!viewPhone.getText().toString().equals(""))
					{
						String uri = "tel:" + viewPhone.getText();
						Intent callIntent = new Intent(Intent.ACTION_DIAL);
						callIntent.setData(Uri.parse(uri));

						if (callIntent.resolveActivity(ProfilePage.this.getPackageManager()) != null)
						{
							startActivity(callIntent);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "There is no app for calling!", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

		mail.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!viewMail.getText().toString().equals(""))
				{
					Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
					mailIntent.setData(Uri.parse("mailto:"));
					mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{viewMail.getText().toString().trim()});
					mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Intent Mail");

					if (mailIntent.resolveActivity(ProfilePage.this.getPackageManager()) != null)
					{
						Intent mailChooser = Intent.createChooser(mailIntent, "Send mail");
						startActivity(mailChooser);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "There is no app for mail!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		addEvent.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!birthdayStr.equals(""))
				{
					String myFormat = "dd/MM/yyyy";
					SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
					Calendar beginTime = Calendar.getInstance();
					Calendar endTime = Calendar.getInstance();
					try
					{
						beginTime.setTime(sdf.parse(birthdayStr));
						beginTime.set(Calendar.HOUR, 12);
						beginTime.set(Calendar.MINUTE, 30);
						endTime.setTime(sdf.parse(birthdayStr));
						endTime.set(Calendar.HOUR, 13);
						endTime.set(Calendar.MINUTE, 30);
					}
					catch (ParseException e)
					{
						e.printStackTrace();
					}

					Intent eventIntent = new Intent(Intent.ACTION_INSERT)
							.setData(CalendarContract.Events.CONTENT_URI)
							.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
							.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
							.putExtra(CalendarContract.Events.TITLE, viewName.getText().toString() + " " + viewSurname.getText().toString() + " Birthday")
							.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

					if (eventIntent.resolveActivity(ProfilePage.this.getPackageManager()) != null)
					{
						startActivity(eventIntent);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "There is no app for calendar!", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		if (!birthdayStr.equals(""))
		{
			Integer birthYear = Integer.parseInt(birthdayStr.substring(birthdayStr.lastIndexOf("/") + 1));
			Integer age = calendar.get(Calendar.YEAR) - birthYear;
			viewAge.setText(age.toString());
		}

		byte[] byteArray = getIntent().getByteArrayExtra("pp_image");
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		ppView.setImageBitmap(bitmap);

		back.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent returnIntent = new Intent();
				returnIntent.putExtra("return", "Turned back from profile page successfully");
				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});

		courses.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent coursesIntent = new Intent(ProfilePage.this, CoursesActivity.class);
				startActivity(coursesIntent);
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		Intent returnIntent = new Intent();
		returnIntent.putExtra("return", "Turned back from profile page successfully");
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
