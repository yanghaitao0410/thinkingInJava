package code_java.time;

import java.util.Calendar;
import java.util.Date;

/**
 * @Desc Calendar和Date 相互转换
 * @Author water
 * @date 2020/5/8
 **/
public class DateToCalender {

    public Calendar dateToCalender(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }

}
