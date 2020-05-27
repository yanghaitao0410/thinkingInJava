package code_java.time;

import java.time.*;
import java.util.Date;

/**
 * @Desc 针对特定的时区进行精确转换
 * @Author water
 * @date 2020/5/8
 **/
public class LocalDateTimeToDate2 {

    public static Date localTimeToDate(LocalTime localTime, int year, int month, int day,
                                       ZoneOffset zoneOffset) {
        LocalDateTime dateTime = localTime.atDate(LocalDate.of(year, month, day));
        return localDateTimeToDate(dateTime, zoneOffset);
    }

    public static Date localTimeToDate(LocalTime localTime, int year, int month, int day,
                                       ZoneId zoneId) {
        LocalDateTime dateTime = localTime.atDate(LocalDate.of(year, month, day));
        return localDateTimeToDate(dateTime, zoneId);
    }

    public static Date localDateToDate(LocalDate localDate, ZoneOffset zoneOffset) {
        LocalDateTime dateTime = localDate.atStartOfDay();//assuming start of day
        return localDateTimeToDate(dateTime, zoneOffset);
    }

    public static Date localDateToDate(LocalDate localDate, ZoneId zoneId) {
        LocalDateTime dateTime = localDate.atStartOfDay();//assuming start of day
        return localDateTimeToDate(dateTime, zoneId);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime, ZoneId zoneId) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zoneDateTimeToDate(zonedDateTime);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        OffsetDateTime offsetDateTime = localDateTime.atOffset(zoneOffset);
        return offsetDateTimeToDate(offsetDateTime);
    }

    public static Date offsetDateTimeToDate(OffsetDateTime offsetDateTime) {
        return Date.from(offsetDateTime.toInstant());
    }

    public static Date zoneDateTimeToDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    public static void main(String[] args) {
        System.out.println("-- using ZoneOffset --");
        ZoneOffset zoneOffset = ZoneOffset.from(OffsetDateTime.now());
        System.out.println("ZoneOffset: " + zoneOffset);

        Date date = localTimeToDate(LocalTime.now(), 2000, 1, 1, zoneOffset);
        System.out.println(date);

        date = localDateToDate(LocalDate.now(), zoneOffset);
        System.out.println(date);

        date = localDateTimeToDate(LocalDateTime.now(), zoneOffset);
        System.out.println(date);

        date = offsetDateTimeToDate(OffsetDateTime.now());
        System.out.println(date);

        System.out.println("-- using ZoneId --");
        ZoneId zoneId = ZoneId.from(ZonedDateTime.now());
        System.out.println("ZoneId: " + zoneId);

        date = localTimeToDate(LocalTime.now(), 2000, 1, 1, zoneOffset);
        System.out.println(date);

        date = localDateToDate(LocalDate.now(), zoneOffset);
        System.out.println(date);

        date = localDateTimeToDate(LocalDateTime.now(), zoneOffset);
        System.out.println(date);

        date = zoneDateTimeToDate(ZonedDateTime.now());
        System.out.println(date);
    }

}
