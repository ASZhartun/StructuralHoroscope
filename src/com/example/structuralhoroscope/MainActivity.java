package com.example.structuralhoroscope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MainActivity extends FragmentActivity {

	final String DATE_PATTERN = "dd.MM.yyyy";
	int mYear = 1990, mMonth = 0, mDay = 1;

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

	public void onDatePicker(View v) {
		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				mDay = dayOfMonth;
				mMonth = month;
				mYear = year;
				month++;
				String day = String.valueOf(dayOfMonth);
				String corrMonth = String.valueOf(month);
				if (dayOfMonth < 10) {
					day = "0" + dayOfMonth;
				}
				if (month < 10) {
					corrMonth = "0" + month;
				}

				StringBuilder sb = new StringBuilder();
				sb.append(day).append(".").append(corrMonth).append(".").append(year);
				birthdayField.setText(sb.toString());
			}
		}, mYear, mMonth, mDay);
		dpd.show();
	}

}
