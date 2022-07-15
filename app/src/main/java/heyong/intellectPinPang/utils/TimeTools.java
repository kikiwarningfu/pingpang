package heyong.intellectPinPang.utils;

/**
 * Created by hongfu on 2017/10/23.
 */


/**
 * 将毫秒转换成00：00：00
 */
public class TimeTools {
    public static String getCountTimeByLong(long finishTime) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;
        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(":");
        } else {
            sb.append(minute).append(":");
        }
        if (second < 10) {
            sb.append("0").append(second);
        } else {
            sb.append(second);
        }
        return sb.toString();
    }

    public static void getCountTimeByLong2(long finishTime, CallBack callBack) {
        int totalTime = (int) (finishTime / 1000);//秒
        int day = 0, hour = 0, minute = 0, second = 0;
        String dayS = "", hourS = "", minuteS = "", secondS = "";
        if (3600 * 24 <= totalTime) {
            day = totalTime / 86400;
            dayS = String.valueOf(day);
            totalTime = totalTime - 86400 * day;
        }
        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            hourS = String.valueOf(hour);
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            minuteS = String.valueOf(minute);
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
            secondS = String.valueOf(second);
        }


        if (day == 0) {
            dayS = "0";
        }
        if (day > 0 && day < 10) {
            dayS = "0" + dayS;
        }

        if (hour < 10) {
            hourS = "0" + hourS;
        }
        if (minute < 10) {
            minuteS = "0" + minuteS;
        }
        if (second < 10) {
            secondS = "0" + secondS;
        }
        callBack.back(dayS, hourS, minuteS, secondS);
    }

    public interface CallBack {
        void back(String day, String hour, String minute, String second);
    }
}
