package xyz.xyan.orm.rsql.util;

import junit.framework.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by DreamInSun on 2018/11/7.
 */
public class TimeUtilTest {

    @Test
    public void testIsNumeric() throws Exception {

    }

    @Test
    public void testIsUnixTsSec() throws Exception {

    }

    @Test
    public void testParseTimestamp() throws Exception {
        Long tsBase = 1525195425000l; //GMT+8 2018-05-02 01:23:45 GMT0  2018-05-01 17:23:45
        Long tsZero = 3600000l; //GMT+8 1970-01-01 09:00:00 GMT0   1970-01-01 01:00:00
        /* 零时 */
        String tsStr0 = "1970-01-01_09:00:00"; //GMT+8 1970-01-01 09:00:00 GMT0 1970-01-01 01:00:00
        Timestamp dt0 = TimeUtil.parseTimestamp(tsStr0);
        Timestamp dtStd0 = new Timestamp(tsZero);
        Assert.assertEquals(dt0, dtStd0);
        /* 零时 */
        String tsStr0_1 = "3600"; //GMT+8 1970-01-01 00:00:00 GMT0 1969-12-31 08:00:00
        Timestamp dt0_1 = TimeUtil.parseTimestamp(tsStr0_1);
        Timestamp dtStd0_1 = new Timestamp(tsZero);
        Assert.assertEquals(dt0_1, dtStd0_1);
        /* 本地时间 */
        String tsStr1 = "1525195425"; //GMT+8 2018-05-02 01:23:45 GMT0 2018-05-01 17:23:45
        Timestamp dt1 = TimeUtil.parseTimestamp(tsStr1); // GMT8 2018-05-01 00:00:00 GMT
        Timestamp dtStd1 = new Timestamp(tsBase);//GMT0  2018-05-01 00:00:00
        Assert.assertEquals(dt1, dtStd1);
        /* 标准时间 */
        String tsStr2 = "2018-05-02 01:23:45"; //本地时间
        Timestamp dt2 = TimeUtil.parseTimestamp(tsStr2);
        Timestamp dtStd2 = new Timestamp(tsBase );//北京时间 2018-05-01 00:00:00 减去偏差转标准时间
        Assert.assertEquals(dt2, dtStd2);
    }

    @Test
    public void testParseDate() throws Exception {
        Long tsBase = 1525132800000l; //GMT+8 2018-05-01 08:00:00 GMT0  2018-05-01 00:00:00
        /* 零时 */
        String tsStr0 = "1970-01-01"; //GMT+8 1970-01-01 00:00:00 GMT0 1969-12-31 08:00:00
        Date dt0 = TimeUtil.parseDate(tsStr0);
        Date dtStd0 = new Date(0l - TimeUtil.TIME_ZONE_OFFSET);
        Assert.assertEquals(dt0, dtStd0);
        /* 本地时间 */
        String tsStr1 = "1525195425"; //GMT+8 2018-05-02 01:23:45 GMT0 2018-05-01 17:23:45
        Date dt1 = TimeUtil.parseDate(tsStr1); // GMT8 2018-05-01 00:00:00 GMT
        Date dtStd1 = new Date(tsBase);//GMT0  2018-05-01 00:00:00
        Assert.assertEquals(dt1, dtStd1);
        /* 标准时间 */
        String tsStr2 = "2018-05-01"; //本地时间
        Date dt2 = TimeUtil.parseDate(tsStr2);
        Date dtStd2 = new Date(tsBase - TimeUtil.TIME_ZONE_OFFSET);//北京时间 2018-05-01 00:00:00 减去偏差转标准时间
        Assert.assertEquals(dt2, dtStd2);
    }

    @Test
    public void testParseTime() throws Exception {
        long stdTime = 45296000; //GMT+8的 "20:34:56" GMT0的 "12:34:56"
        /* 零时 */
        String tsStr0 = "12:34:56"; //
        Time dt0 = TimeUtil.parseTime(tsStr0); //GMT+8的 "12:34:56"
        Time dtStd0 = new Time(stdTime - TimeUtil.TIME_ZONE_OFFSET); //GMT+8的 "12:34:56" GMT0的"04:34:56"
        Assert.assertEquals(dt0, dtStd0);
        /* 本地时间 */
        String tsStr1 = "1525149296"; //GMT+8 的 "2018-05-01 12:34:56" GMT0 的 "2018-05-01 04:34:45"
        Time dt1 = TimeUtil.parseTime(tsStr1); //GMT+8 的 "2018-05-01 12:34:56"
        Time dtStd1 = new Time(stdTime - TimeUtil.TIME_ZONE_OFFSET); //GMT+8的 "12:34:56" GMT0的 "04:34:56"
        Assert.assertEquals(dt1, dtStd1);
        /* 标准时间 */
        String tsStr2 = "45296"; //本地时间
        Time dt2 = TimeUtil.parseTime(tsStr2); //转为标准时间
        Time dtStd2 = new Time(stdTime); //按照标准时间比较
        Assert.assertEquals(dt2, dtStd2);
    }

}