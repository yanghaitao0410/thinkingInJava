package code_java.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author yht
 * @create 2020/2/26
 */
public class LocalDateLearn {

    @Test
    public void minusTest() {
        Assert.assertEquals( "2020-02-24",LocalDate.parse("2020-02-25", DateTimeFormatter.ISO_LOCAL_DATE).minus(1, ChronoUnit.DAYS).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
