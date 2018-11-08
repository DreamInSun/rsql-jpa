package xyz.xyan.orm.rsql.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DreamInSun on 2018/11/7.
 */
public class TimeUtil {

    /*========== Constant ==========*/
    public static Pattern g_ptnNumeric = Pattern.compile("^[-+]?([1-9]\\d*|0)$");
    public static Pattern g_ptnInteger = Pattern.compile("^[-+]?[0-9]+$");

    public static final long MS_IN_ONE_DAY = 24 * 60 * 60 * 1000l;
    public static final long MS_IN_ONE_HOUR = 60 * 60 * 1000l;

    public static final ZoneId g_defaultZone = ZoneId.systemDefault();
    public static final int TIME_ZONE_OFFSET  ;

   static {
       TIME_ZONE_OFFSET = getDefaultTimeZoneRawOffset();
   }

    /*===== Data Format =====*/
    public static SimpleDateFormat g_sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static SimpleDateFormat g_sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public static SimpleDateFormat g_sdfTime = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

        /*========== Properties ==========*/

        /*========== Getter & Setter ==========*/
    /**
     * 获取系统当前默认时区与UTC的时间差.(单位:毫秒)
     *
     * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)
     */
    private static int getDefaultTimeZoneRawOffset() {
        return TimeZone.getDefault().getRawOffset();
    }

        /*========== Constructor ==========*/

    /*========== Export Function ==========*/

    public static boolean isInteger(String str) {
        Matcher isNum = g_ptnInteger.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = g_ptnNumeric.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isUnixTsSec() {
        return true;
    }

    /*=====  =====*/

    /**
     * 将日期字符串转为Timestamp,以秒为单位
     *  参数Long时的 值被理解为 标准时间，    输出的本地时间带偏差
     *  参数为时间格式时 值被理解为 本地时间， 输出的本地时间不带偏差
     *
     * @param value “yyyy-mm-dd hh:mm:ss”格式或者 或者 Unit_Time 毫秒数； 为避免空格解析问题 可用'_'代替' '
     * @return
     */
    public static Timestamp parseTimestamp(String value) {
        Timestamp tsRet = null;
        if (isInteger(value)) {
            tsRet = new Timestamp(Long.parseLong(value ));
        } else {
            value = value.replace('_', ' ');
            tsRet = Timestamp.valueOf(value);
        }
        return tsRet;
    }

    /**
     * 将日期字符串格式转化为java.sql.Date
     *  参数Long时的 值被理解为 标准时间，输出的本地时间带偏差
     *  参数为时间格式时 值被理解为 本地时间， 输出的本地时间不带偏差
     *
     * @param value “YYYY-dd-mm”格式 或者 Unit_Time 毫秒数
     * @return
     */
    public static Date parseDate(String value) {
        Date dtRet = null;
        if (isInteger(value)) {
            Long unixTime = Long.parseLong(value );
            Long remain = (unixTime / MS_IN_ONE_DAY) * MS_IN_ONE_DAY  ;
            dtRet = new Date(remain);
        } else {
            dtRet = Date.valueOf(value);
        }
        return dtRet;
    }

    /**
     * 将时间转换为java.sql.Time
     *  参数Long时的 值被理解为 标准时间，输出的本地时间带偏差
     *  参数为时间格式时 值被理解为 本地时间， 输出的本地时间不带偏差
     *
     * @param value "HH:mm:ss" 格式 或者 Unit_Time 毫秒数
     * @return
     */
    public static Time parseTime(String value) {
        Time tmRet = null;
        if (isInteger(value)) {
            Long unixTime = Long.parseLong(value );
            Long remain = unixTime % MS_IN_ONE_DAY  ;
            tmRet = new Time(remain);
        } else {
            tmRet = Time.valueOf(value);
        }
        return tmRet;
    }

    /*========== Assistant Function ==========*/

    /**
     * Get date regarding the operation (less then or greater than)
     *
     * @param argument Date to be modified
     * @param days Days to be added or removed form argument;
     *@return Date modified date
     */
    public static java.util.Date modifyDay(Object argument, int days) {
        java.util.Date date = (java.util.Date) argument;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        date = c.getTime();
        return date;
    }
    /**
     * Get date regarding the operation (less then or greater than)
     *
     * @param argument Date to be modified
     * @param seconds Seconds to be added or removed form argument;
     *@return Date modified date
     */
    public static java.util.Date modifySecond(Object argument, int seconds) {
        java.util.Date date = (java.util.Date) argument;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, seconds);
        date = c.getTime();
        return date;
    }

}
