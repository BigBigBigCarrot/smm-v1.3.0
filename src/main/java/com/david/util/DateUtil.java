package com.david.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils
{
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };
	public DateUtil()
	{
	}

	public static int compareSysDate(Date date)
	{
		Date dt2 = getSystemDate();

		if (date.getTime() > dt2.getTime())
			return 1;
		if (date.getTime() < dt2.getTime())
		{
			return -1;
		}
		return 0;
	}

	public static Date getSystemDate()
	{
		return new Date();
	}

	public static Date addDayDate(Date date, int num)
	{
		return addDate(date, 5, num);
	}

	public static Date getMaxTimeSpan()
	{
		return convertStringToDateFormat("2030-12-31 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date getMinTimeSpan()
	{
		return convertStringToDateFormat("1990-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date addDate(Date date, int type, int num)
	{
		if (date != null)
		{
			Calendar cdEndTime = Calendar.getInstance();
			cdEndTime.setTime(date);
			cdEndTime.add(type, num);
			date = cdEndTime.getTime();
		}

		return date;
	}

	public static String convertToDateString(Date date)
	{
		return convertToDateString(date, "yyyy-MM-dd");
	}

	public static String convertToDateTimeString(Date date)
	{
		return convertToDateString(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String convertToDateString(Date date, String dateFormat)
	{
		String result = "";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		if (date != null)
		{
			result = formatter.format(date);
		}

		return result;
	}

	public static Date convertStringToDateFormat(String date, String dateFormat)
	{
		if (date == null)
			return null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		if (date != null)
		{
			try
			{
				return format.parse(date);
			} catch (Exception localException)
			{
			}
		}
		return null;
	}

	public static Date convertStringToDateTime(String date)
	{
		return convertStringToDateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date convertStringToDate(String date)
	{
		return convertStringToDateFormat(date, "yyyy-MM-dd");
	}

	public static int diffDaysCompareSysDate(Date date)
	{
		return diffDays(date, getSystemDate());
	}

	public static void main(String[] args)
	{
		Date date1 = convertStringToDate("2016-06-20");
		Date date2 = convertStringToDate("2017-06-23");
		int diffDays = diffDays(date1, date2);
		System.out.println(diffDays);
	}

	public static int diffDays(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(6);
		int day2 = cal2.get(6);

		int year1 = cal1.get(1);
		int year2 = cal2.get(1);

		if (year1 == year2)
		{
			return day1 - day2;
		}

		int timeDistance = 0;
		if (year1 < year2)
		{
			for (int i = year1; i < year2; i++)
			{
				if (((i % 4 == 0) && (i % 100 != 0)) || (i % 400 == 0))
				{
					timeDistance -= 366;
				} else
				{
					timeDistance -= 365;
				}
			}
			return timeDistance + (day1 - day2);
		}
		for (int i = year2; i < year1; i++)
		{
			if (((i % 4 == 0) && (i % 100 != 0)) || (i % 400 == 0))
			{
				timeDistance += 366;
			} else
			{
				timeDistance += 365;
			}
		}
		return timeDistance + (day1 - day2);
	}

	public static String formatTime(Long ms)
	{
		Integer ss = Integer.valueOf(1000);
		Integer mi = Integer.valueOf(ss.intValue() * 60);
		Integer hh = Integer.valueOf(mi.intValue() * 60);
		Integer dd = Integer.valueOf(hh.intValue() * 24);

		Long day = Long.valueOf(ms.longValue() / dd.intValue());
		Long hour = Long.valueOf((ms.longValue() - day.longValue() * dd.intValue()) / hh.intValue());
		Long minute = Long.valueOf(
				(ms.longValue() - day.longValue() * dd.intValue() - hour.longValue() * hh.intValue()) / mi.intValue());
		Long second = Long.valueOf((ms.longValue() - day.longValue() * dd.intValue() - hour.longValue() * hh.intValue()
				- minute.longValue() * mi.intValue()) / ss.intValue());
		Long milliSecond = Long
				.valueOf(ms.longValue() - day.longValue() * dd.intValue() - hour.longValue() * hh.intValue()
						- minute.longValue() * mi.intValue() - second.longValue() * ss.intValue());

		StringBuffer sb = new StringBuffer();
		if (day.longValue() > 0L)
		{
			sb.append(day + "天");
		}
		if ((day.longValue() > 0L) || (hour.longValue() > 0L))
		{
			sb.append(hour + "小时");
		}
		if ((day.longValue() > 0L) || (hour.longValue() > 0L) || (minute.longValue() > 0L))
		{
			sb.append(minute + "分");
		}
		if ((day.longValue() > 0L) || (hour.longValue() > 0L) || (minute.longValue() > 0L) || (second.longValue() > 0L))
		{
			sb.append(second + "秒");
		}

		return sb.toString();
	}

	public static long pastMinutes(Date date)
	{
		long t = new Date().getTime() - date.getTime();
		return t / 60000L;
	}

	public static Date parseDate(Object str)
	/*     */   {
	/* 220 */     if (str == null) {
	/* 221 */       return null;
	/*     */     }
	/*     */     try {
	/* 224 */       return parseDate(str.toString(), parsePatterns);
	/*     */     } catch (ParseException e) {}
	/* 226 */     return null;
	/*     */   }

	public static Date TimeStamp2Date(String timestampString)
	{
		Date date = null;

		if (StringUtils.isNotBlank(timestampString))
		{
			if (timestampString.length() == 10)
			{
				Long timestamp = Long.valueOf(Long.parseLong(timestampString) * 1000L);
				date = new Date(timestamp.longValue());
			} else
			{
				date = new Date(Long.parseLong(timestampString));
			}
		} else
		{
			date = getMinTimeSpan();
		}

		return date;
	}

	public static int compareDate(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime())
				return 1;
			if (dt1.getTime() < dt2.getTime())
			{
				return -1;
			}
			return 0;
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
		return 2;
	}

	public static Date getMondayOfThisWeek()
	{
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(7) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(5, -day_of_week + 1);
		return c.getTime();
	}

	public static Date getSundayOfThisWeek()
	{
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(7) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(5, -day_of_week + 7);
		return c.getTime();
	}
}
