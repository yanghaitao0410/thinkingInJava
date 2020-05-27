package code_java.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @Desc 以下实用程序方法将Java 8 LocalTime、LocalDate和LocalDateTime转换为Java .util。日历和java.util.Date。
 * @Author water
 * @date 2020/5/8
 **/
public class LocalDateTimeToDate {

    /**
     * LocalTime转Date
     * @param localTime
     * @return
     */
    public static Date localTimeToDate(LocalTime localTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        //assuming year/month/date information is not important
        calendar.set(0, 0, 0, localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        return calendar.getTime();
    }

    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        //assuming start of day
        calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
        return calendar.getTime();
    }

    /**
     * LocalDateTime 转Date
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(localDateTime.getYear(), localDateTime.getMonthValue()-1, localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date date = localTimeToDate(LocalTime.now());
        System.out.println(date);

        date = localDateToDate(LocalDate.now());
        System.out.println(date);

        date = localDateTimeToDate(LocalDateTime.now());
        System.out.println(date);
    }
}
