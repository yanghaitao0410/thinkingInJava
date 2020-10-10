package code_java.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * @author yht
 * @create 2020/2/26
 */
public class LocalDateLearn {

    @Test
    public void minusTest() {
        Assert.assertEquals( "2020-02-24",LocalDate.parse("2020-02-25", DateTimeFormatter.ISO_LOCAL_DATE).minus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    public void weekTest() {
        LocalDate now = LocalDate.now();

        LocalDate lastDay = now.minusDays(1);
        System.out.println(lastDay.format(DateTimeFormatter.ISO_LOCAL_DATE));

        //获取指定时间所在周的周一
        LocalDate first = now.with(DayOfWeek.MONDAY);

        //获取指定时间所在周的周日
        LocalDate last = now.with(DayOfWeek.SUNDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        //获取指定时间是所在月的第几周 ：方法1
        long weekOfMonth1 = WeekFields.of(Locale.CHINA).weekOfMonth().getFrom(now);

        //获取指定时间是所在月的第几周 ：方法2
        WeekFields weekFields = WeekFields.of(Locale.CHINA);
        int weekOfMonth2 = now.get(weekFields.weekOfMonth());

        //获取所在年的第几周
        int weekOfYear = LocalDate.now().get(weekFields.weekOfWeekBasedYear());

        String temple = "%s年%s月第%s周%s日~%s日";
        String timeStr = String.format(
                temple,
                now.getYear(),
                now.getMonthValue(),
                now.get(weekFields.weekOfMonth()),
                now.with(DayOfWeek.MONDAY).getDayOfMonth(),
                now.with(DayOfWeek.SUNDAY).getDayOfMonth()
        );

        System.out.println(formatter.format(first));
        System.out.println(formatter.format(last));
        System.out.println(weekOfMonth1);
        System.out.println(weekOfMonth2);
        System.out.println(weekOfYear);
        System.out.printf(timeStr);
    }
}
