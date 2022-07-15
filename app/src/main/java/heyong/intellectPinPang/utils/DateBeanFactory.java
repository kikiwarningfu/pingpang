package heyong.intellectPinPang.utils;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateBeanFactory {

    //    public static final String[] MONTHS = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    public static final Integer[] MONTHS = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    static final Calendar CALENDAR = Calendar.getInstance();
    static final int CUR_YEAR;
    static final int CUR_MONTH;
    static final int CUR_DAY;

    static {
        CUR_YEAR = CALENDAR.get(Calendar.YEAR);
        CUR_MONTH = CALENDAR.get(Calendar.MONTH);
        CUR_DAY = CALENDAR.get(Calendar.DAY_OF_MONTH);
    }


    public static List<DateBean> generateData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        //获取周几 -->month的第一天为周几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //若一周第一天为星期天，则-1
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        int totalLen = 14;
        List<DateBean> list = new ArrayList<>();

        for (int i = dayOfWeek - 1; i > 0; i--) {
            int dayi = date - i;
            int thisMoney = month;
            String weekDay = dateToWeek(year + "-" + thisMoney + "-" + dayi);
            boolean am = false, pm = false;

            list.add(new DateBean(true, year, month, dayi, String.valueOf(dayi), weekDay, am, pm));

        }

        totalLen -= dayOfWeek - 1;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //第一天是星期日
        cal.roll(Calendar.DAY_OF_MONTH, false);
        int allDates = cal.get(Calendar.DAY_OF_MONTH);
        if (allDates - date + 1 >= totalLen) {
            for (int i = date; i < date + totalLen; i++) {     ///再获取一个月中 第一天的1号为周几 1——月底
                int dayi = i;
                int thisMoney = month;
                String weekDay = dateToWeek(year + "-" + thisMoney + "-" + dayi);
                boolean am = false, pm = false;

                list.add(new DateBean(
                        isUnableDate(year, month, dayi), year, month, dayi, String.valueOf(dayi), weekDay, am, pm));
            }
        } else {
            //从月底到当前日期是几天
            int i1 = allDates - date + 1;
            for (int i = date; i < date + i1; i++) {     ///再获取一个月中 第一天的1号为周几 1——月底
                int dayi = i;
                int thisMoney = month;
                String weekDay = dateToWeek(year + "-" + thisMoney + "-" + dayi);
                boolean am = false, pm = false;

                list.add(new DateBean(
                        isUnableDate(year, month, dayi), year, month, dayi, String.valueOf(dayi), weekDay, am, pm));
            }
            //month最大是11  如果大于11则是下一年
            if (month + 1 > 11) {
                month = (month + 1) % 12;
                year = year + 1;
            } else {
                month = month + 1;
            }

            totalLen -= i1;
            for (int i = 0; i < totalLen; i++) {     ///再获取一个月中 第一天的1号为周几 1——月底
                int dayi = i + 1;
                int thisMoney = month;
                String weekDay = dateToWeek(year + "-" + thisMoney + "-" + dayi);
                boolean am = false, pm = false;
                list.add(new DateBean(
                        isUnableDate(year, month, dayi), year, month, dayi, String.valueOf(dayi), weekDay, am, pm));
            }
        }
        return list;
    }

    /**
     * 根据日期来获取当前是星期几
     *
     * @param datetime
     * @return
     */
    private static String dateToWeek(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"sun", "mon", "tues", "wed", "thur", "fri", "sat"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); ///0,1,2,3,4,5,6
        //1周日 2周一 3周二 4周三 5周四 6周五 7周六
        return weekDays[dayOfWeek - 1];
    }

    private static boolean isUnableDate(int year, int month, int day) {

        if (year == CUR_YEAR && month == CUR_MONTH && day < CUR_DAY) {
            return true;
        }
//        if (CALENDAR.get(Calendar.YEAR) == year
//                && CALENDAR.get(Calendar.MONTH) == month
//                && CALENDAR.get(Calendar.DAY_OF_MONTH) == day) {
//            return true;
//        }

        return false;
    }

    public static class DateBean implements Comparable, Serializable {

        public String text;
        private boolean disable = true;
        private int year;
        private int month;
        private int day;
        private String weekDay; //星期
        private boolean am; //上午
        private boolean pm; //下午

        public DateBean(String text) {
            this.text = text;
        }

        public DateBean(boolean disable, int year, int month, int day, String text, String weekDay, boolean am, boolean pm) {   //
            this.disable = disable;
            this.year = year;
            this.month = month;
            this.day = day;
            this.text = text;
            this.weekDay = weekDay;
            this.am = am;
            this.pm = pm;
        }

        public boolean isDisable() {
            return disable;
        }


        public void setDisable(boolean disable) {
            this.disable = disable;
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            if (o == null || !(o instanceof DateBean)) {
                return -1;
            }
            DateBean dateBean = (DateBean) o;

            if (year == dateBean.year && month == dateBean.month && day == dateBean.day) {
                return 0;
            }
            if (year == dateBean.year && month == dateBean.month) {
                return day - dateBean.day;
            }
            if (year == dateBean.year) {
                return month - dateBean.month;
            }
            return year - dateBean.year;

        }

        public int compare(int year, int month, int day) {
            if (this.year == year && this.month == month && this.day == day) {
                return 0;
            }
            if (this.year == year && this.month == month) {
                return this.day - day;
            }
            if (this.year == year) {
                return this.month - month;
            }
            return this.year - year;
        }

        public String getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        public boolean isAm() {
            return am;
        }

        public void setAm(boolean am) {
            this.am = am;
        }

        public boolean isPm() {
            return pm;
        }

        public void setPm(boolean pm) {
            this.pm = pm;
        }
    }
}
