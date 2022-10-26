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

		zodiacSigns.put(0, "����");
		zodiacSigns.put(1, "�����");
		zodiacSigns.put(2, "��������");
		zodiacSigns.put(3, "���");
		zodiacSigns.put(4, "���");
		zodiacSigns.put(5, "����");
		zodiacSigns.put(6, "����");
		zodiacSigns.put(7, "��������");
		zodiacSigns.put(8, "�������");
		zodiacSigns.put(9, "�������");
		zodiacSigns.put(10, "�������");
		zodiacSigns.put(11, "����");

		chineseSigns.put(0, "�����");
		chineseSigns.put(1, "���");
		chineseSigns.put(2, "����");
		chineseSigns.put(3, "������");
		chineseSigns.put(4, "������");
		chineseSigns.put(5, "����");
		chineseSigns.put(6, "������");
		chineseSigns.put(7, "����");
		chineseSigns.put(8, "��������");
		chineseSigns.put(9, "�����");
		chineseSigns.put(10, "������");
		chineseSigns.put(11, "�����");

		extraChineseSigns.put(0, "������");
		extraChineseSigns.put(1, "������");
		extraChineseSigns.put(2, "����");
		extraChineseSigns.put(3, "����");
		extraChineseSigns.put(4, "������");
		extraChineseSigns.put(5, "������");
		extraChineseSigns.put(6, "�����");
		extraChineseSigns.put(7, "�����");
		extraChineseSigns.put(8, "�����");
		extraChineseSigns.put(9, "�����");
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
		return extraChineseSigns.get(extra�hineseSign(Integer.parseInt(parts[2])));
	}

// ���������� ��������� ���� �� Y-���, M-�����, D-����
	private long julian(int Y, int M, int D) {
		return (1461 * (Y + 4800 + (M - 14) / 12)) / 4 + (367 * (M - 2 - 12 * ((M - 14) / 12))) / 12
				- (3 * ((Y + 4900 + (M - 14) / 12) / 100)) / 4 + D - 32075;
	}

// �������� ������� ����� �����
	private static double fraction(double x) {
		return x - Math.floor(x);
	}

// ���������� ������������� ������� ������, �������
	private double sunPosition(double jd) {
		double M, L, pi2 = Math.PI * 2, T = (jd - 2451545.) / 36525.;
		M = pi2 * fraction(0.993133 + 99.997361 * T);
		L = pi2 * fraction(
				0.7859453 + M / pi2 + (6893.0 * Math.sin(M) + 72.0 * Math.sin(2.0 * M) + 6191.2 * T) / 1296.0e3);
		return L * 180.0 / Math.PI;
	}

// ���������� ������ ������� (0-����, ...)
	private int zodiacSign(int Y, int M, int D) {
		return (int) sunPosition(julian(Y, M, D)) / 30;
	}

	private int chineseSign(int year) {
		return (year - 1900) % 12;
	}
	
	private int extra�hineseSign(int year) {
		return year % 10;
	}

}
