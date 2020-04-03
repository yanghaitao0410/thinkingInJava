package code_java.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @Author water
 * @date 2020/3/9
 **/
public class LocalDateLearn {

    @Test
    public void testLocalDateFormat() {
        LocalDate localDate = LocalDate.parse("2020-02-01", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(localDate.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Test
    public void localDateTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2020-02-03 23:23:23", formatter);
        System.out.println("年份：" + localDateTime.getYear());
        System.out.println("月份：" + localDateTime.getMonthValue());
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy_MM")));

    }

    /**
     * 获取两个时间段内的按月索引分片
     */
    @Test
    public void timePeriodTest() {
        LocalDate startDate = LocalDateTime.parse("2019-01-01 23:22:23", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
        LocalDate endDate = LocalDateTime.parse("2020-01-01 23:22:23", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
        List<String> list = new ArrayList<>();
        while(startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            list.add("alias.lepay.t_order_info_".concat(startDate.format(DateTimeFormatter.ofPattern("yyyy_MM"))));
            startDate = startDate.plusMonths(1);
        }
//        list.stream().forEach(System.out::println);
        String[] strings = new String[list.size()];
        list.toArray(strings);

        for(String string : strings) {
            System.out.println(string);
        }
    }
}
