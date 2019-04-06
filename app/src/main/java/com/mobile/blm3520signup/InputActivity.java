package com.mobile.blm3520signup;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InputActivity extends AppCompatActivity
{

	private EditText inputName, inputSurname, inputID, inputBirthday, inputPlace, inputPhone, inputMail;
	private TextInputLayout layoutName, layoutSurname, layoutID, layoutBirthday, layoutPlace, layoutPhone, layoutMail;
	private Button choosePP, submitButton, clearButton, captureButton;
	private ImageView viewPP;
	private final Calendar calendar = Calendar.getInstance();

	private final static int REQUEST_CODE_PP = 5;
	private final static int REQUEST_CODE_GALLERY = 3;
	private final static int REQUEST_CODE_CAMERA = 7;
	private final static String myFileProvider = "com.mobile.blm3520signup.fileprovider";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);

		inputName = (EditText) findViewById(R.id.editText_Name);
		inputSurname = (EditText) findViewById(R.id.editText_Surname);
		inputID = (EditText) findViewById(R.id.editText_ID);
		inputBirthday = (EditText) findViewById(R.id.editText_Birthday);
		inputPlace = (EditText) findViewById(R.id.editText_BirthPlace);
		inputPhone = (EditText) findViewById(R.id.editText_Phone);
		inputMail = (EditText) findViewById(R.id.editText_Email);

		layoutName = (TextInputLayout) findViewById(R.id.textLayout_Name);
		layoutSurname = (TextInputLayout) findViewById(R.id.textLayout_Surname);
		layoutID = (TextInputLayout) findViewById(R.id.textLayout_ID);
		layoutBirthday = (TextInputLayout) findViewById(R.id.textLayout_Birthday);
		layoutPlace = (TextInputLayout) findViewById(R.id.textLayout_BirthPlace);
		layoutPhone = (TextInputLayout) findViewById(R.id.textLayout_Phone);
		layoutMail = (TextInputLayout) findViewById(R.id.textLayout_Email);

		choosePP = (Button) findViewById(R.id.button_Choose);
		viewPP = (ImageView) findViewById(R.id.imageView_PP);
		submitButton = (Button) findViewById(R.id.button_Submit);
		clearButton = (Button) findViewById(R.id.button_Clear);
		captureButton = (Button) findViewById(R.id.button_Capture);

		inputName.addTextChangedListener(new MyTextWatcher(inputName));
		inputSurname.addTextChangedListener(new MyTextWatcher(inputSurname));
		inputID.addTextChangedListener(new MyTextWatcher(inputID));
		inputBirthday.addTextChangedListener(new MyTextWatcher(inputBirthday));
		inputPlace.addTextChangedListener(new MyTextWatcher(inputPlace));
		inputPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		inputMail.addTextChangedListener(new MyTextWatcher(inputMail));

		if (savedInstanceState != null)
		{
			inputName.setText(savedInstanceState.getString("name"));
			inputSurname.setText(savedInstanceState.getString("surname"));
			inputID.setText(savedInstanceState.getString("id"));
			inputBirthday.setText(savedInstanceState.getString("birthday"));
			inputPlace.setText(savedInstanceState.getString("place"));
			inputPhone.setText(savedInstanceState.getString("phone"));
			inputMail.setText(savedInstanceState.getString("mail"));
			Toast.makeText(getApplicationContext(), "Bundle restored", Toast.LENGTH_SHORT).show();
		}

		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
			{
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateBirthday();
			}
		};

		inputBirthday.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new DatePickerDialog(InputActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		choosePP.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ContextCompat.checkSelfPermission(InputActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(InputActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
				}
				else
				{
					Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
					getIntent.setType("image/*");

					Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					pickIntent.setType("image/*");

					Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
					chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

					if (chooserIntent.resolveActivity(InputActivity.this.getPackageManager()) != null)
					{
						startActivityForResult(chooserIntent, REQUEST_CODE_GALLERY);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "There is no app for picking image!", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});

		captureButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ContextCompat.checkSelfPermission(InputActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
				{
					ActivityCompat.requestPermissions(InputActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
				}
				else
				{
					if (ContextCompat.checkSelfPermission(InputActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
					{
						ActivityCompat.requestPermissions(InputActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
					}
					else
					{
						Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

						if (InputActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
						{
							if (captureIntent.resolveActivity(InputActivity.this.getPackageManager()) != null)
							{
								File photoFile = null;
								try
								{
									photoFile = createImageFile();
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}

								if (photoFile != null)
								{
									Uri photoURI = FileProvider.getUriForFile(InputActivity.this,
											myFileProvider,
											photoFile);
									captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
								}

								Intent captureChooser = Intent.createChooser(captureIntent, "Capture photo");
								startActivityForResult(captureChooser, REQUEST_CODE_CAMERA);
							}
							else
							{
								Toast.makeText(getApplicationContext(), "There is no app for capturing photo!", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "There is no camera on this device!", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});

		submitButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
				BitmapDrawable drawable = (BitmapDrawable) viewPP.getDrawable();
				Bitmap bitmap = drawable.getBitmap();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baoStream);
				byte[] byteArray = baoStream.toByteArray();

				Intent intent = new Intent(InputActivity.this, ProfilePage.class);
				intent.putExtra("name", inputName.getText().toString().trim());
				intent.putExtra("surname", inputSurname.getText().toString().trim());
				intent.putExtra("phone", inputPhone.getText().toString().trim());
				intent.putExtra("id", inputID.getText().toString().trim());
				intent.putExtra("birthday", inputBirthday.getText().toString());
				intent.putExtra("place", inputPlace.getText().toString());
				intent.putExtra("mail", inputMail.getText().toString());
				intent.putExtra("pp_image", byteArray);
				startActivityForResult(intent, REQUEST_CODE_PP);
			}
		});

		clearButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inputName.setText("");
				layoutName.setErrorEnabled(false);
				inputSurname.setText("");
				layoutSurname.setErrorEnabled(false);
				inputID.setText("");
				layoutID.setErrorEnabled(false);
				inputBirthday.setText("");
				layoutBirthday.setErrorEnabled(false);
				inputPlace.setText("");
				layoutPlace.setErrorEnabled(false);
				inputPhone.setText("");
				layoutPlace.setErrorEnabled(false);
				inputMail.setText("");
				layoutMail.setErrorEnabled(false);
				viewPP.setImageResource(R.drawable.pp);
				try
				{
					getCurrentFocus().clearFocus();
				}
				catch (NullPointerException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)
		{
			case REQUEST_CODE_PP:
				System.out.println("Turned back from intent with request code: " + REQUEST_CODE_PP + " result code: " + resultCode);
				if (resultCode == RESULT_OK)
				{
					Toast.makeText(getApplicationContext(), data.getStringExtra("return"), Toast.LENGTH_SHORT).show();
				}
				break;
			case REQUEST_CODE_GALLERY:
				System.out.println("Turned back from intent with request code: " + REQUEST_CODE_GALLERY + " result code: " + resultCode);
				if (resultCode == RESULT_OK)
				{
					if (data == null)
					{
						Toast.makeText(getApplicationContext(), "Image could not selected", Toast.LENGTH_SHORT).show();
						return;
					}
					try
					{
						InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
						viewPP.setImageBitmap(BitmapFactory.decodeStream(inputStream));
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
				}
			case REQUEST_CODE_CAMERA:
				System.out.println("Turned back from intent with request code: " + REQUEST_CODE_CAMERA + " result code: " + resultCode);
				if (resultCode == RESULT_OK)
				{
					try
					{
						Uri bitmapURI = FileProvider.getUriForFile(InputActivity.this,
								myFileProvider,
								new File(currentPhotoPath));
						Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(InputActivity.this.getContentResolver(), bitmapURI);
						viewPP.setImageBitmap(imageBitmap);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				break;

		}
	}

	private void updateBirthday() {
		String myFormat = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		inputBirthday.setText(sdf.format(calendar.getTime()));
	}

	String currentPhotoPath;

	private File createImageFile() throws IOException
	{
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = image.getAbsolutePath();
		return image;
	}

	private class MyTextWatcher implements TextWatcher
	{
		private View view;

		private MyTextWatcher(View view)
		{
			this.view = view;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{

		}

		@Override
		public void afterTextChanged(Editable s)
		{
			switch (view.getId())
			{
				case R.id.editText_Name:
					if (inputName.getText().toString().isEmpty())
					{
						layoutName.setError("Enter name");
						inputName.requestFocus();
					}
					else
					{
						layoutName.setErrorEnabled(false);
					}
					break;

				case R.id.editText_Surname:
					if (inputSurname.getText().toString().isEmpty())
					{
						layoutSurname.setError("Enter surname");
						inputSurname.requestFocus();
					}
					else
					{
						layoutSurname.setErrorEnabled(false);
					}
					break;
				case R.id.editText_BirthPlace:
					if (inputPlace.getText().toString().isEmpty())
					{
						layoutPlace.setError("Enter birth place");
						inputPlace.requestFocus();
					}
					else
					{
						layoutPlace.setErrorEnabled(false);
					}
					break;
				case R.id.editText_ID:
					if (inputID.getText().toString().isEmpty())
					{
						layoutID.setError("Enter TC ID");
						inputID.requestFocus();
					}
					else
					{
						layoutID.setErrorEnabled(false);
					}
					break;
				case R.id.editText_Phone:
					if (inputPhone.getText().toString().isEmpty())
					{
						layoutPhone.setError("Enter TC ID");
						inputPhone.requestFocus();
					}
					else
					{
						layoutPhone.setErrorEnabled(false);
					}
					break;
				case R.id.editText_Email:
					if (inputMail.getText().toString().isEmpty())
					{
						layoutMail.setError("Enter E-Mail");
						inputMail.requestFocus();
					}
					else
					{
						layoutMail.setErrorEnabled(false);
					}
					break;
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putString("name",inputName.getText().toString());
		outState.putString("surname", inputSurname.getText().toString());
		outState.putString("id", inputID.getText().toString());
		outState.putString("birthday", inputBirthday.getText().toString());
		outState.putString("place", inputPlace.getText().toString());
		outState.putString("phone", inputPhone.getText().toString());
		outState.putString("mail", inputMail.getText().toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		inputName.setText(savedInstanceState.getString("name"));
		inputSurname.setText(savedInstanceState.getString("surname"));
		inputID.setText(savedInstanceState.getString("id"));
		inputBirthday.setText(savedInstanceState.getString("birthday"));
		inputPlace.setText(savedInstanceState.getString("place"));
		inputPhone.setText(savedInstanceState.getString("phone"));
		inputMail.setText(savedInstanceState.getString("mail"));
		Toast.makeText(getApplicationContext(), "Bundle restored", Toast.LENGTH_SHORT).show();
	}
}
