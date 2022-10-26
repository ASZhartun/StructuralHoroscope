package com.example.structuralhoroscope;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HoroEngine {

	public static HashMap<Integer, String> chineseSigns = new HashMap<Integer, String>();
	public static HashMap<Integer, String> zodiacSigns = new HashMap<Integer, String>();
	public static HashMap<Integer, String> extraChineseSigns = new HashMap<Integer, String>();

	public HoroEngine() {
		init();
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

	public String getZodiacSign(String date) {
		String[] parts = date.split("\\.");
		return zodiacSigns
				.get(zodiacSign(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0])));
	}

	public String getChineseSign(String date) {
		String[] parts = date.split("\\.");
		int year = Integer.parseInt(parts[2]);
		return chineseSigns.get(chineseSign(year));
	}
	
	public String getExtraChineseSign(String date) {
		String[] parts = date.split("\\.");
		return extraChineseSigns.get(extraСhineseSign(Integer.parseInt(parts[2])));
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
	private int zodiacSign(int Y, int M, int D) {
		return (int) sunPosition(julian(Y, M, D)) / 30;
	}

	private int chineseSign(int year) {
		return (year - 1900) % 12;
	}
	
	private int extraСhineseSign(int year) {
		return year % 10;
	}

}
