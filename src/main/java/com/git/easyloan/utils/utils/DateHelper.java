package com.git.easyloan.utils.utils;


import com.git.easyloan.utils.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private static Logger log = LoggerFactory.getLogger(DateHelper.class);
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String DATE_YYYYMMDD_FORMAT = "yyyyMMdd";
    public static String TIME_FORMAT = "HH:mm:ss ";
    public static String TIME_HHMMSS_FORMAT = "HHmmss";
    private static int[] LAST_DAY_OF_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private DateHelper() {
    }

    public static String formatDate(Date aValue) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return aValue == null ? "" : df.format(aValue);
    }

    public static String formatDateYYYYMMDD(Date aValue) {
        if (aValue == null) {
            return "";
        } else {
            DateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
            return vFormat.format(aValue);
        }
    }

    public static String dateAdd(String interval, int count, String dateStr) throws Exception {
        if (dateStr != null && !dateStr.equals("")) {
            Calendar cl = Calendar.getInstance();
            Date d1 = getDate(dateStr);
            cl.setTime(d1);
            if (interval.equalsIgnoreCase("y")) {
                cl.add(1, count);
            } else if (interval.equalsIgnoreCase("m")) {
                cl.add(2, count);
            } else {
                if (!interval.equalsIgnoreCase("d")) {
                    throw new Exception("日期增加类型不对！");
                }

                cl.add(5, count);
            }

            Date d2 = cl.getTime();
            return formatDate(d2);
        } else {
            return "";
        }
    }

    public static String formatDate() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(Calendar.getInstance().getTime());
    }

    public static String formatTime() {
        DateFormat stf = new SimpleDateFormat(TIME_HHMMSS_FORMAT);
        return stf.format(Calendar.getInstance().getTime());
    }

    public static String formatDateYYYYMMDD() {
        Date today = new Date();
        DateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
        return vFormat.format(today);
    }

    public static String formatDateTime(Date aValue) {
        DateFormat dtf = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        return aValue == null ? "" : dtf.format(aValue);
    }

    public static String formatDate(Timestamp value) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return value == null ? "" : df.format(value);
    }

    public static String formatDateTimeYYYYMMDDHHMMSS(Date aValue) {
        if (aValue == null) {
            return "";
        } else {
            DateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT + TIME_HHMMSS_FORMAT);
            return vFormat.format(aValue);
        }
    }

    public static String formatCDate() {
        Date today = new Date();
        DateFormat vFormat = DateFormat.getDateInstance(1);
        return vFormat.format(today);
    }

    public static String formatCDay() {
        Date today = new Date();
        DateFormat vFormat = DateFormat.getDateInstance(0);
        return vFormat.format(today);
    }

    public static String foramteDate(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat = new SimpleDateFormat(DATE_FORMAT);
            Date sDate = vFormat.parse(aValue);
            return vFormat.format(sDate);
        } else {
            return "";
        }
    }

    public static String foramteDateYYYYMMDD(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
            Date sDate = getDate(aValue);
            return vFormat.format(sDate);
        } else {
            return "";
        }
    }

    public static Date getDate(String aValue) throws ParseException {
        Date sDate = null;
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat;
            if (aValue.length() == 10) {
                vFormat = new SimpleDateFormat(DATE_FORMAT);
                sDate = vFormat.parse(aValue);
            } else {
                vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
                sDate = vFormat.parse(aValue);
            }

            return sDate;
        } else {
            return null;
        }
    }

    public static Date getDateYYYYMMDD(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
            Date sDate = vFormat.parse(aValue);
            return sDate;
        } else {
            return null;
        }
    }

    public static Date getDateTime(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
            Date sDate = vFormat.parse(aValue);
            return sDate;
        } else {
            return null;
        }
    }

    public static Date getDateTimeYYYYMMDDHHMMSS(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            SimpleDateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT + TIME_HHMMSS_FORMAT);
            Date sDate = vFormat.parse(aValue);
            return sDate;
        } else {
            return null;
        }
    }

    public static String formatDateTimeYYYYMMDDHHMMSS() {
        Date today = new Date();
        DateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT + TIME_HHMMSS_FORMAT);
        return vFormat.format(today);
    }

    public static String getPreYearDay(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            Date d0 = getDate(aValue);
            d0.setDate(1);
            d0.setMonth(d0.getMonth() + 1);
            d0.setYear(d0.getYear() - 1);
            d0.setDate(d0.getDate() - 1);
            return formatDate(d0);
        } else {
            return "";
        }
    }

    public static String getPreYearLastDay(String aValue) throws Exception {
        return aValue != null && !aValue.equals("") ? calculateDate((String)aValue, -1, 0, 0).substring(0, 4) + "-12-31" : "";
    }

    public static String getPreMonthLastDay(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            String val = calculateDate((String)aValue, 0, -1, 0);
            return val.substring(0, 8) + lastDayOfMonth(val);
        } else {
            return "";
        }
    }

    public static String getPreDay(String aValue) throws Exception {
        return aValue != null && !aValue.equals("") ? calculateDate((String)aValue, 0, 0, -1) : "";
    }

    public static String getYear(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            String[] array = aValue.split("-");
            return array[0];
        } else {
            return "";
        }
    }

    public static String getMonth(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            if (aValue.indexOf("-") > -1) {
                String[] array = aValue.split("-");
                return array[1];
            } else {
                return aValue.substring(4, 6);
            }
        } else {
            return "";
        }
    }

    public static String getDay(String aValue) throws Exception {
        if (aValue != null && !aValue.equals("")) {
            if (aValue.indexOf("-") > -1) {
                String[] array = aValue.split("-");
                return array[2];
            } else {
                return aValue.substring(6, 8);
            }
        } else {
            return "";
        }
    }

    public static Integer dateToInt(String rq) {
        return rq != null && rq.length() >= 10 ? Integer.valueOf(rq.substring(0, 4) + rq.substring(5, 7) + rq.substring(8, 10)) : new Integer(0);
    }

    public static String lastDayOfMonth(String year, String month) {
        String lt = Integer.toString(lastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month) - 1));
        return lt.length() == 1 ? "0" + lt : lt;
    }

    public static String lastDayOfMonth(String date) {
        return lastDayOfMonth(date.substring(0, 4), date.substring(5, 7));
    }

    public static int lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return lastDayOfMonth(cal.get(1), cal.get(2) - 1);
    }

    public static int lastDayOfMonth(int year, int month) {
        return month != 1 || year % 400 != 0 && (year % 100 == 0 || year % 4 != 0) ? LAST_DAY_OF_MONTH[month] : 29;
    }

    public static String calculateDate(String date, int year, int month, int day) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date calDate = calculateDate(df.parse(date), year, month, day);
            return df.format(calDate);
        } catch (Exception var6) {
            log.error("日期计算出错", var6);
            return "";
        }
    }

    public static Date calculateDate(Date date, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, year);
        calendar.add(2, month);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static String currDateAndTime() {
        DateFormat dtf = new SimpleDateFormat(DATE_FORMAT + " " + TIME_FORMAT);
        return dtf.format(Calendar.getInstance().getTime()).trim();
    }

    public static java.sql.Timestamp currDateTime() throws Exception {
        Date today = new Date();
        SimpleDateFormat vFormat = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT + TIME_HHMMSS_FORMAT);
        vFormat.format(today);
        java.sql.Timestamp sDate = java.sql.Timestamp.valueOf(currDate() + " " + currTime());
        return sDate;
    }

    public static String currDate() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(Calendar.getInstance().getTime()).trim();
    }

    public static String currTime() {
        DateFormat tf = new SimpleDateFormat(TIME_FORMAT);
        return tf.format(Calendar.getInstance().getTime()).trim();
    }

    public static int compare(String d1, String d2) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return compare(df.parse(d1), df.parse(d2));
    }

    public static int compare2(String d1, String d2) throws ParseException {
        DateFormat df = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
        return compare(df.parse(d1), df.parse(d2));
    }

    public static int compare(Date d1, Date d2) {
        return d1.compareTo(d2);
    }

    public static Calendar switchStringToCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static String getNMonthAfterOneDay(Date sDate, int n) {
        Calendar c = switchStringToCalendar(sDate);
        c.add(2, n);
        return "" + c.get(1) + "-" + (c.get(2) + 1) + "-" + c.get(5);
    }

    public static java.sql.Timestamp businessTime() throws Exception {
        return currDateTime();
    }

    public static java.sql.Timestamp businessTimeDefault() throws Exception {
        return businessTime();
    }

    public static String businessDate() throws MyException {
        return businessDate("");
    }

    public static String businessDate(String orgCd) throws MyException {
        Date businessDate = new Date();
        Date minDate = calculateDate((Date)Calendar.getInstance().getTime(), -100, 0, 0);
        Date maxDate = calculateDate((Date)Calendar.getInstance().getTime(), 100, 0, 0);
        if (businessDate.before(minDate)) {
            throw new MyException("获取的营业日期不正确，营业日期[" + businessDate + "]不能早于[" + minDate + "]");
        } else if (businessDate.after(maxDate)) {
            throw new MyException("获取的营业日期不正确，营业日期[" + businessDate + "]不能晚于[" + maxDate + "]");
        } else {
            return formatDate(businessDate);
        }
    }

    public static int diffMonths(Date d1, Date d2, boolean isRoundUp) {
        if (d1.after(d2)) {
            Date tempDate = d1;
            d1 = d2;
            d2 = tempDate;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        int iMonth = Math.abs((c1.get(1) - c2.get(1)) * 12 + (c1.get(2) - c2.get(2)));
        if (isRoundUp && c1.get(5) < c2.get(5)) {
            ++iMonth;
        }

        if (!isRoundUp && c1.get(5) > c2.get(5)) {
            --iMonth;
        }

        return iMonth;
    }

    public static int getMonths(Date end, Date begin) {
        return diffMonths(end, begin, false);
    }

    public static int getMonths2(Date end, Date begin) {
        return diffMonths(end, begin, true);
    }

    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate.after(endDate)) {
            Date mDate = beginDate;
            beginDate = endDate;
            endDate = mDate;
        }

        int iDays = 0;
        Calendar date1 = Calendar.getInstance();
        date1.setTime(beginDate);
        Calendar date2 = Calendar.getInstance();
        date2.setTime(endDate);

        while(date1.before(date2)) {
            ++iDays;
            date1.add(6, 1);
        }

        return iDays;
    }

    public static Calendar getCalendarForStr(String str) {
        if (str == null) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;

            try {
                date = sdf.parse(str);
            } catch (ParseException var4) {
                return null;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
    }

    public static boolean isDateStr(String dateStr) {
        if (dateStr == null) {
            return false;
        } else {
            try {
                if (dateStr.length() != 8) {
                    return false;
                } else {
                    getDate(dateStr);
                    return true;
                }
            } catch (ParseException var2) {
                return false;
            }
        }
    }

    public static String dateAddDaysYYYYMMDD(String beginDate, int days) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(beginDate));
            calendar.add(5, days);
            Date date = calendar.getTime();
            return df.format(date);
        } catch (Exception var5) {
            log.error("日期计算出错", var5);
            return "";
        }
    }

    public static String dateSubDaysYYYYMMDD(String beginDate, int days) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(beginDate));
            calendar.add(5, -days);
            Date date = calendar.getTime();
            return df.format(date);
        } catch (Exception var5) {
            log.error("日期计算出错", var5);
            return "";
        }
    }

    public static String lastDayOfMonthYYYYMMDD(String dateStr) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(dateStr));
            calendar.set(5, calendar.getActualMaximum(5));
            Date date = calendar.getTime();
            return df.format(date);
        } catch (Exception var4) {
            var4.printStackTrace();
            log.error("日期计算出错", var4);
            return "";
        }
    }

    public static boolean isValidDate(String dateStr) {
        try {
            if (dateStr != null && !dateStr.equals("")) {
                DateFormat df = new SimpleDateFormat(DATE_YYYYMMDD_FORMAT);
                df.setLenient(false);
                df.parse(dateStr);
                return true;
            } else {
                return false;
            }
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isValidSpecPaymentDate(String param) {
        if (param != null && !param.equals("")) {
            int temp = Integer.valueOf(param);
            return temp >= 1 && temp <= 31;
        } else {
            return false;
        }
    }

    public static boolean isValidGracePrdDays(String param) {
        if (param != null && !param.equals("")) {
            int temp = Integer.valueOf(param);
            return temp >= 1 && temp < 28;
        } else {
            return false;
        }
    }
    public static boolean isDateType(Class<?> targetType) {
        if (targetType == null) {
            return false;
        } else {
            return targetType == Date.class || targetType == java.sql.Timestamp.class || targetType == java.sql.Date.class || targetType == Time.class;
        }
    }

    public static Date parseDate(String value, Class targetType, String... formats) {
        String[] arr$ = formats;
        int len$ = formats.length;
        int i$ = 0;

        while(i$ < len$) {
            String format = arr$[i$];

            try {
                long v = (new SimpleDateFormat(format)).parse(value).getTime();
                return (Date)targetType.getConstructor(Long.TYPE).newInstance(v);
            } catch (ParseException var10) {
                try {
                    return (Date)targetType.getConstructor(String.class).newInstance(value);
                } catch (Exception var9) {
                    ++i$;
                }
            } catch (Exception var11) {
                throw new RuntimeException(var11);
            }
        }

        throw new IllegalArgumentException("cannot parse:" + value + " for date by formats:" + Arrays.asList(formats));
    }

    public static void main(String[] args) throws Exception {
        new SimpleDateFormat("yyyy-MM-dd");
    }
}
