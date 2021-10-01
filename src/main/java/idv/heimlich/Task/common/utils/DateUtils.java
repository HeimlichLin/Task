package idv.heimlich.Task.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import idv.heimlich.Task.common.exception.ApBusinessException;

public class DateUtils {

	public static boolean isYYYYMMDD(String date) {
		final String reg = "^\\d{8}$";
		if (date == null || date.equals("") || !date.matches(reg)) {
			return false;
		}
		final int year = Integer.parseInt(date.substring(0, 4));
		final int month = Integer.parseInt(date.substring(4, 6));
		final int day = Integer.parseInt(date.substring(6, 8));
		if (year < 1 || month < 1 || month > 12 || day < 1 || day > 31) {
			return false;
		} else if ((month == 2 && day > 29)
				|| ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30))) {
			return false;
		} else if (month == 2) {
			if (year % 4 != 0 && day > 28) {
				return false;
			}
		}
		return true;
	}

	public enum SimpleDates {
		YYYYMMDDHHMMSS_1("yyyyMMddHHmmss"), //
		YYYYMMDDHHMMSS_2("yyyy/MM/dd HH:mm:ss"),//

		;

		String format;

		private SimpleDates(String format) {
			this.format = format;
		}

		public String getFormat() {
			return this.format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

	}

	public static String format(SimpleDates sd, Date date) {
		Optional.of(sd);
		Optional.of(date);
		return new SimpleDateFormat(sd.getFormat()).format(date);

	}

	public static String format(SimpleDates sd) {
		return format(sd, new Date());
	}

	public static Date parse(SimpleDates sd, String str) {
		Optional.of(sd);
		Optional.of(str);
		try {
			return new SimpleDateFormat(sd.getFormat()).parse(str);
		} catch (final ParseException e) {
			throw new ApBusinessException("ParseException error", e);
		}
	}

	public static String getDiff(Long time) {
		Long min = 0l;
		Long hour = 0l;
		Long sec = 0l;
		Long day = 0l;
		sec = time / 1000;
		min = sec / 60;
		sec = sec % 60;
		hour = min / 60;
		min = min % 60;

		day = hour / 24;
		hour = hour % 24;

		final StringBuffer buffer = new StringBuffer();
		if (day > 0l) {
			buffer.append(day + "天");
			buffer.append(hour + "時");
			buffer.append(min + "分");
		} else if (hour > 0l) {
			buffer.append(hour + "時");
			buffer.append(min + "分");
		} else if (min > 0l) {
			buffer.append(min + "分");
		}
		buffer.append(sec + "秒");
		return buffer.toString();
	}

	/**
	 * 差異時間顯示目前到天
	 * 
	 * @param time
	 * @return
	 */
	public static String getDiff(Long beginTime, Long endTime) {
		final long time = endTime - beginTime;
		return getDiff(time);
	}

	public static String toDateString(String format, Date date) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}

	public static String toDateCommonString(Date date) {
		return toDateString("yyyy/MM/dd hh:mm:ss", date);
	}

	public static String toDateCommonString(Long time) {
		return toDateString("yyyy/MM/dd hh:mm:ss", new Date(time));
	}

}
