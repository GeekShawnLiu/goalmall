package www.tonghao.common.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * <p>Title: DateUtilEx</p>
 *
 * <p>Description: 日期拓展工具类</p>
 *
 * @author Yml
 * @date 2018年10月28日
 */
public class DateUtilEx extends DateFormatUtils {

    /**
     * 日期格式  yyyy-MM-dd
     **/
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间格式  yyyy-MM-dd HH:mm:ss
     **/
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 一年中的哪一天    例：20180616
     *
     * @param date
     * @return
     */
    public static Integer getIndexDay(Date date) {
        if (date == null) {
            return null;
        }
        String day = format(date, "yyyyMMdd");
        return Integer.parseInt(day);
    }

    /**
     * 一年中的哪一天    例：16
     *
     * @param date
     * @return
     */
    public static Integer getIndexOnlyDay(Date date) {
        if (date == null) {
            return null;
        }
        String day = format(date, "dd");
        return Integer.parseInt(day);
    }

    /**
     * 一年中的哪一月 例：201809
     *
     * @param date
     * @return
     */
    public static Integer getIndexMonth(Date date) {
        if (date == null) {
            return null;
        }
        String money = format(date, "yyyyMM");
        return Integer.parseInt(money);
    }

    /**
     * 一年中的哪一月  例：09
     *
     * @param date
     * @return
     */
    public static Integer getIndexOnlyMonth(Date date) {
        if (date == null) {
            return null;
        }
        String money = format(date, "MM");
        return Integer.parseInt(money);
    }

    /**
     * 一年中的年
     *
     * @param date
     * @return
     */
    public static Integer getIndexYear(Date date) {
        if (date == null) {
            return null;
        }
        String year = format(date, "yyyy");
        return Integer.parseInt(year);
    }

    /**
     * 一年中的第多少周
     *
     * @param date
     * @return
     */
    public static Integer getIndexWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String yearStr = format(date, "yyyy");
        String indexWeek = yearStr + calendar.get(Calendar.WEEK_OF_YEAR);
        return Integer.parseInt(indexWeek);
    }

    /**
     * 一年中的第多少个季度
     *
     * @param date
     * @return
     */
    public static Integer getIndexQuarter(Date date) {
        if (date == null) {
            return null;
        }
        String monthStr = format(date, "MM");
        int month = Integer.parseInt(monthStr);
        String quarter = "0" + (month % 3 == 0 ? month / 3 : (month / 3 + 1));
        String yearStr = format(date, "yyyy");
        String indexQuarter = yearStr + quarter;
        return Integer.parseInt(indexQuarter);
    }

    /**
     * 当前年的开始时间，即2018-01-1
     *
     * @return
     */
    public static String getYearStartTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String now = "";
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月开始时间
     *
     * @return
     */
    public static String getCurrentMonthStartTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String now = shortSdf.format(c.getTime()) + " 00:00:00";
        return now;
    }

    /**
     * 当前月结束时间
     *
     * @return
     */
    public static String getCurrentMonthEndTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String now = shortSdf.format(c.getTime()) + " 23:59:59";
        return now;
    }


    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = "";
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = shortSdf.format(c.getTime()) + " 00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String now = "";
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = shortSdf.format(c.getTime()) + " 23:59:59";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取月周期
     *
     * @param date
     * @return
     */
    public static String getMonthPeriod(Date date) {
        return DateUtilEx.format(date, "yyyy-MM");
    }

    /**
     * 时间格式化 格式yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 时间格式化 格式yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String timeFormat(Date date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * <p>Title: compare_date</p>
     * <p>Description: </p>
     *
     * @param date1
     * @param date2
     * @param pattern 日期格式
     * @return
     * @author YML
     */
    public static int compareDate(String date1, String date2, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws ParseException {

        System.out.println(compareDate(format(new Date(), TIME_PATTERN), "2019-12-20", DATE_PATTERN));
        ;

        //Date date = new Date();
        //Date date = DateUtils.parseDate("2017-01-01", "yyyy-MM-dd");
		/*System.out.println(format(date , "E"));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		System.out.println(calendar.getWeeksInWeekYear());
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(format(date, "MM"));
		
		String monthStr = format(date, "MM");
		int month = Integer.parseInt(monthStr);
		System.out.println("month = "+month/3);
		
		System.out.println("getIndexDay = "+getIndexDay(date));
		System.out.println("getIndexMonth = "+getIndexMonth(date));
		System.out.println("getIndexWeek = "+getIndexWeek(date));
		System.out.println("getIndexQuarter = "+getIndexQuarter(date));*/

    }

    /**
     * 日期时间字符串转成时间
     *
     * @param timeStr 日期时间字符串 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date timeToDate(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String timeToDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date_str = "";
        try {
            date_str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date_str;
    }

    public static Date strToDate(String timeStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
