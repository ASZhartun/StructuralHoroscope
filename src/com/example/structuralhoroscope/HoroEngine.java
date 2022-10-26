package com.example.structuralhoroscope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;

public class HoroEngine {
	Context ctx;

	public static HashMap<Integer, String> chineseSigns = new HashMap<Integer, String>();
	public static HashMap<Integer, String> zodiacSigns = new HashMap<Integer, String>();
	public static HashMap<Integer, String> extraChineseSigns = new HashMap<Integer, String>();
	public static HashMap<Integer, String> contents = new HashMap<Integer, String>();
	public static HashMap<Integer, String> titles = new HashMap<Integer, String>();
	public static int[][] horoTable = new int[][] { { 1, 2, 3, 4, 5, 6, 7, 6, 7, 4, 3, 2 }, // Крыса
			{ 2, 1, 7, 3, 4, 5, 6, 4, 6, 5, 7, 3 }, // Бык
			{ 3, 7, 1, 2, 3, 4, 5, 7, 4, 6, 5, 4 }, // Тигр
			{ 4, 3, 2, 1, 7, 3, 4, 5, 6, 7, 6, 5 }, // Заяц
			{ 5, 4, 3, 7, 1, 2, 3, 4, 5, 6, 4, 7 }, // Дракон
			{ 6, 5, 4, 3, 2, 1, 2, 7, 7, 5, 6, 4 }, // Змея
			{ 7, 6, 5, 4, 3, 2, 1, 2, 3, 4, 5, 7 }, // Лошадь
			{ 6, 4, 7, 5, 4, 7, 2, 1, 2, 3, 4, 5 }, // Коза
			{ 7, 6, 4, 6, 5, 7, 3, 2, 1, 2, 3, 4 }, // Обезьяна
			{ 4, 5, 6, 7, 6, 5, 4, 3, 2, 1, 7, 3 }, // Петух
			{ 3, 7, 5, 6, 4, 6, 5, 4, 3, 7, 1, 2 }, // Собака
			{ 2, 3, 4, 5, 7, 4, 7, 5, 4, 3, 2, 1 }, // Кабан
	};

	int currYear;
	int currMonth;
	int currDay;

	public HoroEngine(Context context) {
		this.ctx = context;
		init();
		readContents();
	}

	public void init() {

		zodiacSigns.put(0, "Овен");
		zodiacSigns.put(1, "Телец");
		zodiacSigns.put(2, "Близнецы");
		zodiacSigns.put(3, "Рак");
		zodiacSigns.put(4, "Лев");
		zodiacSigns.put(5, "Дева");
		zodiacSigns.put(6, "Весы");
		zodiacSigns.put(7, "Скорпион");
		zodiacSigns.put(8, "Стрелец");
		zodiacSigns.put(9, "Козерог");
		zodiacSigns.put(10, "Водолей");
		zodiacSigns.put(11, "Рыбы");

		chineseSigns.put(0, "Крыса");
		chineseSigns.put(1, "Бык");
		chineseSigns.put(2, "Тигр");
		chineseSigns.put(3, "Кролик");
		chineseSigns.put(4, "Дракон");
		chineseSigns.put(5, "Змея");
		chineseSigns.put(6, "Лошадь");
		chineseSigns.put(7, "Коза");
		chineseSigns.put(8, "Обезьяна");
		chineseSigns.put(9, "Петух");
		chineseSigns.put(10, "Собака");
		chineseSigns.put(11, "Кабан");

		extraChineseSigns.put(0, "Металл");
		extraChineseSigns.put(1, "Металл");
		extraChineseSigns.put(2, "Вода");
		extraChineseSigns.put(3, "Вода");
		extraChineseSigns.put(4, "Дерево");
		extraChineseSigns.put(5, "Дерево");
		extraChineseSigns.put(6, "Огонь");
		extraChineseSigns.put(7, "Огонь");
		extraChineseSigns.put(8, "Земля");
		extraChineseSigns.put(9, "Земля");

	}
	
	public String[] calculateHoroSign(String date) {
		parseDate(date);

		int index = getHoroIndex();
		
		String[] preset = new String[2];
		preset[0]= titles.get(index); 
		preset[1]= contents.get(index);
		return preset;
	}

	public String getZodiacSign(String date) {
		parseDate(date);
		
		return zodiacSigns.get(zodiacSign(currYear, currMonth, currDay));
	}

	public String getChineseSign(String date) {
		parseDate(date);
		
		return chineseSigns.get(chineseSign(currYear));
	}

	public String getExtraChineseSign(String date) {
		String[] parts = date.split("\\.");
		return extraChineseSigns.get(extraСhineseSign(Integer.parseInt(parts[2])));
	}
	
	public void parseDate(String date) {
		String[] parts = date.split("\\.");
		currYear = Integer.parseInt(parts[2]);
		currMonth = Integer.parseInt(parts[1]);
		currDay = Integer.parseInt(parts[0]);
	}
	
	public int getHoroIndex() {
		return horoTable[chineseSign(currYear)][zodiacSign(currYear, currMonth, currDay)];
	}

// Вычисление юлианской даты по Y-год, M-месяц, D-день
	private long julian(int Y, int M, int D) {
		return (1461 * (Y + 4800 + (M - 14) / 12)) / 4 + (367 * (M - 2 - 12 * ((M - 14) / 12))) / 12
				- (3 * ((Y + 4900 + (M - 14) / 12) / 100)) / 4 + D - 32075;
	}

// Выделяем дробную часть числа
	private static double fraction(double x) {
		return x - Math.floor(x);
	}

// Вычисление эклиптической долготы Солнца, градусы
	private double sunPosition(double jd) {
		double M, L, pi2 = Math.PI * 2, T = (jd - 2451545.) / 36525.;
		M = pi2 * fraction(0.993133 + 99.997361 * T);
		L = pi2 * fraction(
				0.7859453 + M / pi2 + (6893.0 * Math.sin(M) + 72.0 * Math.sin(2.0 * M) + 6191.2 * T) / 1296.0e3);
		return L * 180.0 / Math.PI;
	}

// Вычисление номера Зодиака (0-Овен, ...)
	public int zodiacSign(int Y, int M, int D) {
		return (int) sunPosition(julian(Y, M, D)) / 30;
	}

	public int chineseSign(int year) {
		return (year - 1900) % 12;
	}

	private int extraСhineseSign(int year) {
		return year % 10;
	}

	public void readContents() {
		String path = "res/raw/treats.txt";
		InputStream in = ctx.getClass().getClassLoader().getResourceAsStream(path);
		InputStreamReader inStream = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(inStream);
		String str = "";
		StringBuilder sb = new StringBuilder();
		try {
			while ((str = reader.readLine()) != null) {
				sb.append(str).append("$");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.close();
			inStream.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		str = sb.toString();
		String[] parts = str.split("[$]");
		int edge = parts.length;

		for (int i = 0; i < edge; i += 2) {
			contents.put(i / 2 + 1, parts[i + 1]);
			titles.put(i / 2 + 1, parts[i].substring(3));
		}

	}

	public int getCurrYear() {
		return currYear;
	}

	public void setCurrYear(int currYear) {
		this.currYear = currYear;
	}

	public int getCurrMonth() {
		return currMonth;
	}

	public void setCurrMonth(int currMonth) {
		this.currMonth = currMonth;
	}

	public int getCurrDay() {
		return currDay;
	}

	public void setCurrDay(int currDay) {
		this.currDay = currDay;
	}

}
