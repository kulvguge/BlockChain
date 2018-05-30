package block.com.blockchain.utils;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 类说明：
 *
 * @author 作者:tdx
 * @version 版本:1.0
 * @date 时间:2015年4月7日 下午8:08:51
 */
public class TimeUtils {
    public static String FORMATE_DATE_STR = "yyyy-MM-dd";
    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};

    /**
     * 检查时间间隔
     *
     * @param last    需要检查的时间，毫秒
     * @param timeVal 时间间隔,毫秒
     * @return true-超过了timeVal时间间隔;false-没超过timeVal时间间隔
     */
    public static boolean checkTimeVal(long last, long timeVal) {
        long current = System.currentTimeMillis();
        if ((current - last) > timeVal) {
            return true;
        }
        return false;
    }

    /**
     * @param time
     * @return true-为当天;false-不为当天
     * @Title: checkTimeIsCurrentDay
     * @Description: TODO
     * @return: boolean
     */
    public static boolean checkTimeIsCurrentDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int lastDay = cal.get(Calendar.DAY_OF_YEAR);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if (lastDay == currentDay) {
            return true;
        }
        return false;
    }

    public static String formatTimeShort(long when) {
        String formatStr = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String temp = sdf.format(when);
        if (temp != null && temp.length() == 5 && temp.substring(0, 1).equals("0")) {
            temp = temp.substring(1);
        }
        return temp;
    }

    public static Date formatTimeShort(String when) {
        String formatStr = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date temp = null;
        try {
            temp = sdf.parse(when);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static String getYearFromStr(String date) {
        String[] array = date.split("-");
        return Integer.parseInt(array[0]) + "";
    }

    public static String getMonthFromStr(String date) {
        String[] array = date.split("-");
        return Integer.parseInt(array[1]) + "";
    }

    public static String getDayFromStr(String date) {
        String[] array = date.split("-");
        return Integer.parseInt(array[2]) + "";
    }

    public static int getCurrentAgeByBirthdate(long timeMillis) {
        Date date = new Timestamp(timeMillis);
        // SimpleDateFormat sf = new SimpleDateFormat(FORMATE_DATE_STR);
        return getAgeByBirthday(date);
    }

    /**
     * String根据用户生日计算年龄
     *
     * @param brithday
     * @return
     */
    public static int getCurrentAgeByBirthdate(String brithday) {
        int age = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatDate = new SimpleDateFormat(FORMATE_DATE_STR);
            String currentTime = formatDate.format(calendar.getTime());
            Date today = formatDate.parse(currentTime);
            Date brithDay = formatDate.parse(brithday);
            age = today.getYear() - brithDay.getYear();
            if (today.getMonth() - brithDay.getMonth() >= 0) {
                if (today.getDate() - brithDay.getDate() >= 0) {
                    return age;
                } else {
                    return age - 1;
                }
            } else {
                return age - 1;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getAgeStr(Date birthday) {
        Calendar c1 = Calendar.getInstance();
        long nowmillSeconds = c1.getTimeInMillis();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(birthday);
        long birmillSeconds = c2.getTimeInMillis();
        Calendar c3 = Calendar.getInstance();
        long millis = nowmillSeconds - birmillSeconds;
        c3.setTimeInMillis(millis);
        int year = c3.get(Calendar.YEAR);
        int month = c3.get(Calendar.MONTH);
        int day = c3.get(Calendar.DAY_OF_MONTH);
        int hour = c3.get(Calendar.HOUR_OF_DAY);
        if (year > 1970) {
            return year - 1970;
        } else if (month > Calendar.JANUARY) {
            // return month - Calendar.JANUARY + "月";
            return 0;
        } else if (day > 1) {
            // return day - 1 + "天";
            return 0;
        } else {
            // return "1天";
            return 0;
        }
    }

    public static int getAgeByBirthday(String birthday) {
        try {
            int age = getAgeByBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(birthday));
            if (age <= 0) {
                age = 22;
            }
            return age;
        } catch (Exception e) {
            return 22;
        }
    }

    /**
     * Date根据用户生日计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        if (birthday == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static String millisToStr(long timeMillis) {
        Date date = new Timestamp(timeMillis);
        SimpleDateFormat sf = new SimpleDateFormat(FORMATE_DATE_STR);
        return sf.format(date);
    }

    public static String millisToStr1(long timeMillis) {
        Date date = new Timestamp(timeMillis);
        SimpleDateFormat sf = new SimpleDateFormat("mm:ss");
        return sf.format(date);
    }

    public static String millisToStr(String timeMillis) {
        if (TextUtils.isEmpty(timeMillis))
            return "";
        long time = 0;
        try {
            time = Long.parseLong(timeMillis);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return millisToStr(time);
    }

    public static String getStringBySimpleData(String data) {
        Date date = new Date(data);
        if (new SimpleDateFormat("yyyy").format(date).equals("9999")) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
    }

    // public static String getSimpleTime(long timeMillis) {
    // Date date = new Timestamp(timeMillis);
    // SimpleDateFormat sf = new SimpleDateFormat(FORMATE_DATE_STR);
    // return sf.format(date);
    // }

    public static String getSimpleTime(long timeMillis) {
        long m;
        if (timeMillis % 1000 == 0) {
            m = timeMillis;
        } else {
            m = (timeMillis / 1000 + 1) * 1000;
        }
        Date date = new Timestamp(m);
        SimpleDateFormat sf = new SimpleDateFormat(FORMATE_DATE_STR);
        return sf.format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateString(String date1) {
        //老数据可能后面会有
        if (date1.contains("00:00:00")) {
            return date1;
        }
        return date1 + " 00:00:00";
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYearMonthDay(String date1) {
        int index = date1.indexOf(" ");
        if (index >= 0) {
            return date1.substring(0, index);
        }
        return date1;
    }

    /**
     * yyyy-MM-dd HH:mm
     *
     * @param timeMillis
     * @return
     */
    public static String getSimpleTime2(long timeMillis) {
        Date date = new Timestamp(timeMillis);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(date);
    }

    public static String toTime(long timeMillis) {
        StringBuffer result = new StringBuffer();
        long diff = System.currentTimeMillis() - timeMillis;
        Calendar oldTimeCalendar = Calendar.getInstance();
        oldTimeCalendar.setTimeInMillis(timeMillis);
        int oldYear = oldTimeCalendar.get(Calendar.YEAR);
        int oldMonth = oldTimeCalendar.get(Calendar.MONTH) + 1;
        int oldDay = oldTimeCalendar.get(Calendar.DAY_OF_MONTH);
        if (diff < 0) {
            return result.append(oldYear).append("-").append(oldMonth).append("-").append(oldDay).toString();
        }
        int days = (int) (diff / (24 * 60 * 60 * 1000));
        int minutes = (int) (diff / (60 * 1000));
        if (days < 1) {
            if (minutes >= 60) {
                result.append(minutes / 60).append("小时前");
            } else {
                if (minutes == 0) {
                    result.append("1分钟前");
                } else {
                    result.append(minutes).append("分钟前");
                }
            }
        } else if (days == 1) {
            result.append("昨天");
        } else if (days == 2) {
            result.append("前天");
        } else if (days == 3) {
            result.append("三天前");
        } else {
            result.append(oldYear).append("-").append(oldMonth).append("-").append(oldDay);
        }
        return result.toString();
    }

    /**
     * 根据毫秒数算时间
     */
    public static String activeTime(long timeMillis) {
        Date dat = new Date(timeMillis);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(gc.getTime());
    }

    /**
     * 根据毫秒数算时间
     */
    public static String activeTimeShort(long timeMillis) {
        Date dat = new Date(timeMillis);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

        return format.format(gc.getTime());
    }

    // 根据年龄计算出生年月
    public static String startAgeTime(String timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(new Date())) - Integer.parseInt(timeMillis);
        return year + "";
    }

    /**
     * 完整日期没有时分秒。
     *
     * @param timeMillis
     * @return
     */
    public static String showTime(long timeMillis) {
        // StringBuffer result = new StringBuffer();
        // Calendar oldTimeCalendar = Calendar.getInstance();
        // oldTimeCalendar.setTimeInMillis(timeMillis);
        // int oldYear = oldTimeCalendar.get(Calendar.YEAR);
        // int oldMonth = oldTimeCalendar.get(Calendar.MONTH) + 1;
        // int oldDay = oldTimeCalendar.get(Calendar.DAY_OF_MONTH);
        //
        // Calendar nowTimeCalendar = Calendar.getInstance();
        // int nowYear = nowTimeCalendar.get(Calendar.YEAR);
        // int nowMonth = nowTimeCalendar.get(Calendar.MONTH) + 1;
        // int nowDay = nowTimeCalendar.get(Calendar.DAY_OF_MONTH);
        //
        // if (oldYear == nowYear && nowMonth == oldMonth && nowDay == oldDay) {
        // // 同年月日
        // result.append(new SimpleDateFormat("HH:mm").format(new Date(
        // timeMillis)));
        // } else if (oldYear == nowYear && nowMonth == oldMonth
        // && (nowDay - oldDay) == 1) {// 昨天
        // result.append("昨天").append(
        // new SimpleDateFormat("HH:mm").format(new Date(timeMillis)));
        // } else if (oldYear == nowYear && nowMonth == oldMonth
        // && (nowDay - oldDay) == 2) {// 前天
        // result.append("前天").append(
        // new SimpleDateFormat("HH:mm").format(new Date(timeMillis)));
        // } else if (oldYear == nowYear) {// 同年
        // result.append(new SimpleDateFormat("MM-dd").format(new Date(
        // timeMillis)));
        // } else {// 不同年
        // result.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date(
        // timeMillis)));
        // }
        // return result.toString();
        return showTime(timeMillis, false);
    }

    /**
     * @param timeMillis
     * @param sfm        是否日期显示时分秒
     * @return
     */
    public static String showTime(long timeMillis, boolean sfm) {
        StringBuffer result = new StringBuffer();
        Calendar oldTimeCalendar = Calendar.getInstance();
        oldTimeCalendar.setTimeInMillis(timeMillis);
        int oldYear = oldTimeCalendar.get(Calendar.YEAR);
        int oldMonth = oldTimeCalendar.get(Calendar.MONTH) + 1;
        int oldDay = oldTimeCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar nowTimeCalendar = Calendar.getInstance();
        int nowYear = nowTimeCalendar.get(Calendar.YEAR);
        int nowMonth = nowTimeCalendar.get(Calendar.MONTH) + 1;
        int nowDay = nowTimeCalendar.get(Calendar.DAY_OF_MONTH);

        if (oldYear == nowYear && nowMonth == oldMonth && nowDay == oldDay) { // 同年月日
            result.append("今天").append(new SimpleDateFormat("HH:mm").format(new Date(timeMillis)));
        } else if (oldYear == nowYear && nowMonth == oldMonth && (nowDay - oldDay) == 1) {// 昨天
            result.append("昨天").append(new SimpleDateFormat(" HH:mm").format(new Date(timeMillis)));
        } else if (oldYear == nowYear && nowMonth == oldMonth && (nowDay - oldDay) == 2) {// 前天
            result.append("前天").append(new SimpleDateFormat("  HH:mm").format(new Date(timeMillis)));
        } else if (oldYear == nowYear) {// 同年
            if (sfm) {
                result.append(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(timeMillis)));
            } else {
                result.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeMillis)));
            }
        } else {// 不同年
            if (sfm) {
                result.append(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(timeMillis)));
            } else {
                result.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeMillis)));
            }
        }
        return result.toString();
    }

    public static String FORMATE_DATE_STR_1 = "yyyy-MM-dd HH:mm:ss";

    public static long strToMillis(String date) {
        if (TextUtils.isEmpty(date))
            return 0;
        SimpleDateFormat sf = new SimpleDateFormat(FORMATE_DATE_STR_1);
        Date d = new Date();
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    public static String strToStr(String str) {
        String result = str;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sf.parse(str);
            sf.applyPattern("yyyy-MM-dd");
            str = sf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 计算星座
     *
     * @param strDate
     * @return
     */
    public static String getAstro(String strDate) {
        try {
            Date date = new Date(strDate);
            return getAstro(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getAstro(Date date) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(date)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        return dayOfMonthNow < dayArr[monthNow - 1] ? constellationArr[monthNow - 1] : constellationArr[monthNow];
    }

    public static String getAstro(String month, String day) {
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);
        return d < dayArr[m - 1] ? constellationArr[m - 1] : constellationArr[m];
    }

    public static String getAstro(int month, int day) {

        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    /**
     * 长整型转date
     *
     * @param dateFormat （类型“yyyy-mm-dd”）
     * @param millSec
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 获取上个月日期
     */
    public static String getBeforeMoth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(c.getTime());
        // //System.out.println("上个月是:"+time);
        return time;
    }

    /**
     * 获取当月日期
     */
    public static String getMoth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(c.getTime());
        // //System.out.println("上个月是:"+time);
        return time;
    }

    /**
     * 获取当月日期
     */
    public static String getYearMoth(long vtime) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(vtime));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        // //System.out.println("上个月是:"+time);
        return time;
    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0)
            return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp * 1000);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (calendar.before(inputTime)) {
            // 当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "年" + "MM" + "月" + "dd" + "日");
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "昨天" + " " + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M" + "月" + "d" + "日" + " HH:mm");
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "年" + "MM" + "月" + "dd" + "日" + " HH:mm");
                return sdf.format(currenTimeZone);
            }

        }

    }
}
