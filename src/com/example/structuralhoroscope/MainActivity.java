package com.example.structuralhoroscope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	
	HoroEngine engine = new HoroEngine(this);

	final String DATE_PATTERN = "dd.MM.yyyy";
	int mYear = 1990, mMonth = 0, mDay = 1;

	EditText birthdayField;
	View overviewZodiacFragment;
	View overviewChineseFragment;
	View descriptionFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		birthdayField = (EditText) findViewById(R.id.BirthdayField);
		overviewZodiacFragment = findViewById(R.id.OverviewZodiac);
		overviewChineseFragment = findViewById(R.id.OverviewChineseSign);
		descriptionFragment = findViewById(R.id.decriptionContent);
		
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
	
	public void onCalculate(View v) {
		String date = birthdayField.getText().toString();
		// Устанавливаем значения для китайской карточки
		TextView tvChineseName = (TextView) overviewChineseFragment.findViewById(R.id.signName);
		TextView tvChineseExtraName = (TextView) overviewChineseFragment.findViewById(R.id.signCategory);
		ImageView chineseImage = (ImageView) overviewChineseFragment.findViewById(R.id.decorLeft);
		String chineseSign = engine.getChineseSign(date);
		tvChineseName.setText(chineseSign);
		String chineseExtraSign = engine.getExtraChineseSign(birthdayField.getText().toString());
		tvChineseExtraName.setText(chineseExtraSign);
		chineseImage.setImageResource(getChineseSignImage());
//		chineseImage.setImageResource(getChineseSignImageByName(chineseSign));
		
		// Устанавливаем значения для зодиакальной карточки
		TextView tvZodiacName = (TextView) overviewZodiacFragment.findViewById(R.id.signName);
		TextView tvZodiacExtraName = (TextView) overviewZodiacFragment.findViewById(R.id.signCategory);
		ImageView zodiacImage = (ImageView) overviewZodiacFragment.findViewById(R.id.decorLeft);
		String zodiacSign = engine.getZodiacSign(date);
		tvZodiacName.setText(zodiacSign);
		tvZodiacExtraName.setText("");
		zodiacImage.setImageResource(getZodiacSignImage());
		
		// Устанавливаем значения для описания знака структурного гороскопа
		ImageView horoImage = (ImageView) descriptionFragment.findViewById(R.id.horoSign);
		TextView horoTitle = (TextView) descriptionFragment.findViewById(R.id.title);
		TextView horoContent = (TextView) descriptionFragment.findViewById(R.id.descriptionContent);
		horoTitle.setText(engine.calculateHoroSign(date)[0]);
		horoContent.setText(engine.calculateHoroSign(date)[1]);
		horoImage.setImageResource(getHoroSignImage());
	}
	
	public int getChineseSignImage() {
		int index = engine.chineseSign(engine.getCurrYear());
		String pos = String.valueOf(index);
		if (index < 10) {
			pos = "0" + pos;
		}
		return MainActivity.this.getResources().getIdentifier("chinese_sign_" + pos, "drawable", this.getPackageName());
	}
	
	public int getZodiacSignImage() {
		int index = engine.zodiacSign(engine.getCurrYear(), engine.getCurrMonth(), engine.getCurrDay());
		String pos = String.valueOf(index);
		if (index < 10) {
			pos = "0" + pos;
		}
		return MainActivity.this.getResources().getIdentifier("zodiac_sign_" + pos, "drawable", this.getPackageName());
	}
	
	public int getHoroSignImage() {
		int index = engine.getHoroIndex();
		String pos = String.valueOf(index);
		return MainActivity.this.getResources().getIdentifier("horo_" + pos, "drawable", this.getPackageName());
	}

}
