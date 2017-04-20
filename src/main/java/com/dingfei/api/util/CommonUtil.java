package com.dingfei.api.util;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CommonUtil {
	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class);
	public static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final TimeZone utc = TimeZone.getTimeZone("GMT+8:00");
	private static final SimpleDateFormat isoFormatter = new SimpleDateFormat(ISO_FORMAT);
	public static final String KEY_CODE = "0123456789ABCDEFGHJKLMNPQRSTUVWXY";
	static {
		isoFormatter.setTimeZone(utc);
	}

	public static final String SOURCE = "yyyy-MM-dd HH:mm:ss";

	public static final String SOURCE_1 = "yyyyMMddHHmmss";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String SOURCE_2 = "yyyy/MM/dd HH:mm:ss";

	public static final String FORMATTER_AM_PM = "MM/dd/yyyy hh:mm a";

	/**
	 * yyyy-MM-dd
	 */
	public static final String TARGET = "yyyy-MM-dd";

	public static final String TARGET_1 = "yyyy-MM";

	/**
	 * Generate UUID for P_ID
	 * 
	 * @return String
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * get current time of String
	 * 
	 * @return
	 */
	public static String now() {
		return now(SOURCE);
	}

	public static Date utcNow() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(utc);
		cal.add(Calendar.HOUR_OF_DAY, -8);
		return cal.getTime();
	}

	public static String getPreviousDay(String time) {
		Date day = formatDate(TARGET, time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return formatSimpleDate(cal.getTime());

	}

	/**
	 * get current time of String
	 * 
	 * @param format
	 * @return String
	 */
	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	/**
	 * get current time
	 * 
	 * @return Date
	 */
	public static Date currentTime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(SOURCE);
		return sdf.format(date);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatSimpleDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TARGET);
		return sdf.format(date);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatGMTDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER_AM_PM);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(date);
	}

	/**
	 * convert String to Date
	 * 
	 * @param pattern
	 * @param stringDate
	 * @return Date
	 */
	public static Date formatDate(String stringDate) {
		return formatDate(TARGET, stringDate);
	}

	/**
	 * convert String to Date
	 * 
	 * @param pattern
	 * @param stringDate
	 * @return Date
	 */
	public static Date formatOracleDate(String stringDate) {
		return formatDate(SOURCE, stringDate);
	}

	/**
	 * convert String to Date
	 * 
	 * @param pattern
	 * @param stringDate
	 * @return Date
	 */
	public static Date formatDate(String pattern, String stringDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	/**
	 * used for SQL injection
	 * 
	 * @param sql
	 * @return
	 */
	public static String transactSQLInjection(String sqlParam) {
		if (sqlParam == null)
			return null;
		// injection string
		// "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|; |or|-|+|,"
		StringBuilder sb = new StringBuilder(sqlParam.length());
		for (int i = 0, n = sqlParam.length(); i < n; i++) {
			char c = sqlParam.charAt(i);
			switch (c) {
			case '\'':
				sb.append("\\'");
				break;
			case '-':
				sb.append("\\-");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case ';':
				sb.append("\\;");
				break;
			case ',':
				sb.append("\\,");
				break;
			case '+':
				sb.append("\\+");
				break;
			case '*':
				sb.append("\\*");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Checks if is valid date str.
	 * 
	 * @param dateStr
	 *        the date str
	 * @return true, if is valid date str
	 */
	public static boolean isValidDateStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(TARGET);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if is valid date str.
	 * 
	 * @param dateStr
	 *        the date str
	 * @return true, if is valid date str
	 */
	public static boolean isValidMonthStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(TARGET_1);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * isValidDateTime
	 * 
	 * @param dateStr
	 *        String
	 * @return boolean
	 */
	public static boolean isValidDateTime(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(SOURCE);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * generateInCondition
	 * 
	 * @param ids
	 *        String[]
	 * @return String
	 */
	public static String generateInCondition(String[] ids) {
		return generateInCondition(Arrays.asList(ids));
	}

	/**
	 * generateInCondition
	 * 
	 * @param ids
	 *        List
	 * @return String
	 */
	public static String generateInCondition(Collection<String> ids) {
		String inCondition = "";
		if (ids != null && !ids.isEmpty()) {
			String tmp;
			for (String id : ids) {
				tmp = "'" + id + "',";
				// remove duplicated
				if (!inCondition.contains(tmp)) {
					inCondition += tmp;
				}
			}
			if (inCondition.length() > 0) {
				inCondition = inCondition.substring(0, inCondition.length() - 1);
			}
		} else {
			inCondition = "''";
		}
		return inCondition;
	}

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			LOGGER.error("sleep error:", e);
		}
	}

	public static Random getRandom() {
		SecureRandom random = new SecureRandom();
		return random;
	}

	public static String getRandomString(int length) {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[length];
		random.nextBytes(bytes);
		return new String(bytes);
	}

	/**
	 * 获取随机字符串
	 * 
	 * @return String
	 */
	public static String getRandomString() {
		return System.currentTimeMillis() / 1000 + "";
	}

	/**
	 * dateBetween
	 * 
	 * @param dateFrom
	 *        String
	 * @param dateTo
	 *        String
	 * @return boolean
	 */
	public static boolean dateBetween(String dateFrom, String dateTo) {
		boolean result = true;
		long millis = System.currentTimeMillis();
		if (StringUtils.isNotBlank(dateFrom)) {
			Date from = formatDate(SOURCE, dateFrom);
			result = from.getTime() <= millis;
		}
		if (StringUtils.isNotBlank(dateTo)) {
			Date to = formatDate(SOURCE, dateTo);
			result = result && millis <= to.getTime();
		}
		return result;
	}

	/**
	 * isMobileNo
	 * 
	 * @param mobile
	 *        String
	 * @return boolean
	 */
	public static boolean isMobileNo(String mobile) {
		if (StringUtils.isNotBlank(mobile)) {
			Pattern p = Pattern.compile("1\\d{10}");
			Matcher m = p.matcher(mobile);
			return m.matches();
		} else {
			return false;
		}
	}

	/**
	 * isValidDate
	 * 
	 * @param date
	 *        String
	 * @return boolean
	 */
	public static boolean isValidDate(String date) {
		if (StringUtils.isBlank(date)) {
			return false;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		boolean isvalid = true;
		try {
			f.parse(date);
		} catch (Exception e) {
			isvalid = false;
		}
		return isvalid;
	}

	public static String filterFileName(String fileName) {
		if (StringUtils.isBlank(fileName))
			return "";

		fileName = fileName.replace("•", "");
		fileName = fileName.replace("\\", "");
		fileName = fileName.replace("/", "");
		fileName = fileName.replace(":", "");
		fileName = fileName.replace("*", "");
		fileName = fileName.replace("?", "");
		fileName = fileName.replace("\"", "");
		fileName = fileName.replace("<", "");
		fileName = fileName.replace(">", "");
		fileName = fileName.replace("|", "");
		fileName = fileName.replace("+", "");
		fileName = fileName.replace("#", "");
		fileName = fileName.trim();
		return fileName;
	}

	public static String dealGuid(String guid) {
		if (StringUtils.isBlank(guid) || guid.length() != 36)
			return guid;

		String dealedGuid = guid.replaceAll("-", "");
		return dealedGuid;
	}

	public static String getFiltedLikeString(String searchKey) {
		if (searchKey == null) {
			return "";
		}
		return searchKey.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
	}

	public static Date parseDate(String stringDate) {
		return formatDate(SOURCE, stringDate);
	}

	public static String getUTCString(Date date) {
		return isoFormatter.format(date);
	}

	/**
	 * UTC时间矫正
	 * 
	 * @param stringDate String
	 * @return Date
	 */
	public static Date correctUTCDate(String stringDate) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		try {
			date = isoFormatter.parse(stringDate);
		} catch (ParseException e) {
			LOGGER.error("parseUTCDate error: ", e);
		}
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, -8);
		return cal.getTime();
	}

	/**
	 * parseUTCDate
	 * 
	 * @param stringDate String
	 * @return Date
	 */
	public static Date parseUTCDate(String stringDate) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		try {
			date = isoFormatter.parse(stringDate);
		} catch (ParseException e) {
			LOGGER.error("parseUTCDate error: ", e);
		}
		if (date != null) {
			cal.setTime(date);
		}
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static long subtraction(String beginDate, String beginFormat, String endDate, String endFormat) {
		DateFormat dfbegin = new SimpleDateFormat(beginFormat);
		DateFormat dfend = new SimpleDateFormat(endFormat);
		try {
			Date begin = dfbegin.parse(beginDate);
			Date end = dfend.parse(endDate);
			long diff = end.getTime() - begin.getTime();
			return diff / 1000;
		} catch (Exception e) {
		}
		return 0;
	}

	public static String getSerialNumber() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String serialNumber = sdf.format(d);
		return serialNumber + CommonUtil.getRandomString(6);
	}

	public static void main(String[] args) {

	}

	/**
	 * getLastDayOfMonth
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());
		return lastDayOfMonth;
	}

	/**
	 * getFirstDayOfMonth
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}

	/**
	 * "2016-12-30T14:01:07Z" -->2016-12-30 14:01:00
	 * 
	 * @param utcDate String
	 * @return String
	 */
	public static String formatUTCDate(String utcDate) {
		Date d = convertUTCDate(utcDate);
		return formatDate(d);
	}

	/**
	 * convertUTCDate
	 * 
	 * @param stringDate String
	 * @return Date
	 */
	public static Date convertUTCDate(String stringDate) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		try {
			date = isoFormatter.parse(stringDate);
		} catch (ParseException e) {
			LOGGER.error("parseUTCDate error: ", e);
		}
		if (date != null) {
			cal.setTime(date);
		}
		return cal.getTime();
	}
}
