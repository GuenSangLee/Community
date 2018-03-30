package com.ktds.util;
import java.util.Calendar;

public class DateUtil {

   public static final int YEAR = 1;
   public static final int MONTH = 2;
   public static final int DATE = 3;

   public static String makeDate(String year, String month, String date) {
      return year + "-" + month + "-" + date;
   }

   public static int getActualMaximunDate(int year, int month) {
      Calendar cal = getDateCalendar(year, month, 1);
      return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
   }

   public static Calendar getDateCalendar(int year, int month, int date) {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR, year);
      cal.set(Calendar.MONTH, month - 1);
      cal.set(Calendar.DAY_OF_MONTH, date);
      return cal;

   }

   public static String getComputedDate(int year, int month, int date, int type, int add) {

      Calendar cal = getDateCalendar(year, month, date);

      if (type == YEAR) {
         cal.add(Calendar.YEAR, add);
      } else if (type == MONTH) {
         cal.add(Calendar.MONTH, add);
      } else if (type == DATE) {
         cal.add(Calendar.DAY_OF_MONTH, add);
      }

      year = cal.get(Calendar.YEAR);
      month = cal.get(Calendar.MONTH) + 1;
      date = cal.get(Calendar.DAY_OF_MONTH);

      String yearStr = year + "";
      String monthStr = (month < 10) ? "0" + month : month + "";
      String dateStr = (date < 10) ? "0" + date : date + "";

      return makeDate(yearStr, monthStr, dateStr);
   }

   public static String getNowDate() {
      Calendar cal = Calendar.getInstance();
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH) + 1;
      int date = cal.get(Calendar.DAY_OF_MONTH);

      String yearStr = year + "";
      String monthStr = (month < 10) ? "0" + month : month + "";
      String dateStr = (date < 10) ? "0" + date : date + "";

      return makeDate(yearStr, monthStr, dateStr);
   }
}