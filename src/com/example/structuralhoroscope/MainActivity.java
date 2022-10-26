package com.example.structuralhoroscope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {

	final String DATE_PATTERN = "dd.MM.yyyy";

	EditText birthdayField;
	Button chooseButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		birthdayField = (EditText) findViewById(R.id.BirthdayField);
		chooseButton = (Button) findViewById(R.id.ChooseButton);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		sdf.setTimeZone(TimeZone.getDefault());
		birthdayField.setText(sdf.format(date));
		
	}

}
