package code_java.stream;

import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc 集合流操作
 * @Author water
 * @date 2019/12/6
 **/
public class CollectionStream {
    class PosbillReport {
        String date;
        int amount;
        int state;
        String billId;

        public PosbillReport(String date, int amount, int state, String billId) {
            this.date = date;
            this.amount = amount;
            this.state = state;
            this.billId = billId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getBillId() {
            return billId;
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }

        @Override
        public String toString() {
            return "PosbillReport{" +
                    "date='" + date + '\'' +
                    ", amount=" + amount +
                    ", state=" + state +
                    ", billId='" + billId + '\'' +
                    '}';
        }
    }

    class TongYongReport{
        String date;
        int amount;
        int state;
        String billId;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getBillId() {
            return billId;
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }
    }

    private List<PosbillReport> posbillReports;

    @Before
    public void before() {
        posbillReports = new ArrayList<>();
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 1).format(DateTimeFormatter.ISO_LOCAL_DATE), 10, 1, "111"));
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 1).format(DateTimeFormatter.ISO_LOCAL_DATE), 10, 1, "112"));
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 1).format(DateTimeFormatter.ISO_LOCAL_DATE), 10, 2, "113"));

        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 2).format(DateTimeFormatter.ISO_LOCAL_DATE), 10, 1, "114"));
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 2).format(DateTimeFormatter.ISO_LOCAL_DATE), 20, 1, "115"));

        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 3).format(DateTimeFormatter.ISO_LOCAL_DATE), 10, 1, "116"));
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 3).format(DateTimeFormatter.ISO_LOCAL_DATE), 20, 2, "117"));
        posbillReports.add(new PosbillReport(LocalDate.of(2019, 9, 3).format(DateTimeFormatter.ISO_LOCAL_DATE), 30, 1, "118"));
    }

    @Test
    public void testReduceGroupBy() {
        Date sdate = new Date();
        /*
        * [9-1 : { state : 1 {}, state : 2 {}}
        * , 9-2 : { state : 1 {}, state : 2 {}}
        * 9-3 : { state : 1 {}, state : 2 {}}
        * ]
        * */
        Map<String, Map<Integer, List<PosbillReport>>> posbillReportMap =
                posbillReports
                        .stream() //item -> item.date
                        .sorted(Comparator.comparing(PosbillReport::getDate).thenComparing(PosbillReport::getState))
                        .collect(Collectors.groupingBy(PosbillReport::getDate,
                                Collectors.groupingBy(PosbillReport::getState)));

        List<PosbillReport> sumPosbillReportList = new ArrayList<>();
        for(Map<Integer, List<PosbillReport>> reportListMap : posbillReportMap.values()) {
            for (List<PosbillReport> posbillReports : reportListMap.values()) {
                sumPosbillReportList.add(posbillReports.stream().reduce((r1, r2) ->  new PosbillReport(r1.date, r1.amount + r2.amount, r1.state, r1.billId)).get());
            }
        }

        //获取list<Object> 中某一元素的list集合  map使用：返回一个流，该流包含将给定函数应用于该流元素的结果。
        List<String> billIdList = sumPosbillReportList.stream().map(PosbillReport::getBillId).collect(Collectors.toList());
        System.out.println("billIdList: ");
        billIdList.stream().forEach(System.out::println);

        System.out.println("分组求和后的结果：");
        sumPosbillReportList.stream().sorted(Comparator.comparing(PosbillReport::getDate)).forEach(System.out::println);
        Date eDate = new Date();
        System.out.printf("耗时：%s ms", eDate.getTime() - sdate.getTime());
    }


//    public void testMap() {
//        Map<String, List<PosbillReport>> posbillReportMap =
//                posbillReports
//                        .stream() //item -> item.date
//                        .sorted(Comparator.comparing(PosbillReport::getDate).thenComparing(PosbillReport::getState))
//                        .collect(Collectors.groupingBy(PosbillReport::getDate));
//
//        List<PosbillReport> sumPosbillReportList = new ArrayList<>();
//
//        Set<String> tongYongDateList = new HashSet<>();
//
//        for (List<PosbillReport> posbillReports : posbillReportMap.values()) {
//            PosbillReport posbillReport = null;
//            if(posbillReports.stream().filter(item -> item.state == 24).collect(Collectors.toList()).size() > 0) {
//                tongYongDateList.add(posbillReports.get(0).getDate());
//                continue;
//            }
//            SettleStateQo settleStateQo = getResultStatus(posbillReports);
//            posbillReport = posbillReports.stream().reduce((r1, r2) ->  new PosbillReport(r1.date, r1.amount + r2.amount, r1.state, r1.billId)).get();
//            posbillReport.setState(settleStateQo.getState());
//
//            sumPosbillReportList.add(posbillReport);
//        }
//
//        //查通用提现list
//        List<TongYongReport> tongYongReportList = query(tongYongDateList, merchantId);
//
//        //..查询出通用list 分组求和，得出每一天结算总金额
//
//        sumPosbillReportList.stream().sorted(Comparator.comparing(PosbillReport::getDate).reversed());
//
//    }
}
