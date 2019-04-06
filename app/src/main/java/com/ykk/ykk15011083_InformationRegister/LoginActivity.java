package com.ykk.ykk15011083_InformationRegister;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
	EditText username, password;
	Button login;
	TextInputLayout usernameLayout, passwordLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.editText_Username);
		password = (EditText) findViewById(R.id.editText_Password);
		login = (Button) findViewById(R.id.button_Login);
		usernameLayout = (TextInputLayout) findViewById(R.id.inputLayout_Username);
		passwordLayout = (TextInputLayout) findViewById(R.id.inputLayout_Password);

		username.addTextChangedListener(new TextWatcher()
		{
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
				usernameLayout.setErrorEnabled(false);
			}
		});

		password.addTextChangedListener(new TextWatcher()
		{
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
				passwordLayout.setErrorEnabled(false);
			}
		});

		login.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (username.getText().toString().equals("admin") && password.getText().toString().equals("password"))
				{
					Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this, InputActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
					usernameLayout.setError("Wrong username or password");
					passwordLayout.setError("Wrong username or password");
				}
			}
		});
	}
}
