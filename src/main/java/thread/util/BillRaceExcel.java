package thread.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//实战运用
public class BillRaceExcel {
    static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/qq";
    static final String JDBC_USER = "123";
    static final String JDBC_PASSWORD = "123";
    static final int threadNum = 10;
    static final CountDownLatch c = new CountDownLatch(threadNum);
    static ExecutorService e = Executors.newFixedThreadPool(threadNum);


    /**
     * 连接数据库
     *
     * @return
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private static String sql(String region_id) {
        return "SELECT\n" +
                "    r.REGION_NAME, do.fee_name , date_format(bill.belong_date,'%Y') as year  ,  sum(debt_amount)    as amount\n" +
                "FROM  qdp_oasis.oas_account_rece account left join qdp_oasis.oas_bill_rece bill  on account.account_id = bill.account_id\n" +
                "left join qdp_oasis.oas_fee_item do on do.fee_id = account.fee_id\n" +
                "left join qding_brick.bd_region r on account.region_id = r.REGION_ID\n" +
                "where  account.region_id ='" + region_id + "'  and  account.fee_id in (102,101,100,99,98,190,215,226,187,262,131,282,287,191,264,136,135,134,139,138,137,141,140)\n" +
                "     and   bill_type in (0,1,2,3,6,7,11)  and bill.commission_type in (0,1,2)\n" +
                " and bill.is_cancel = 0  and   bill.is_del =0  and audit_status =0  and bill.is_freeze = 0\n" +
                "group by  account.region_id,account.fee_id,year;\n";
    }

    static String region = "202003191159019028b," +
            "202104081100368122f," +
            "2021040914280332583";


    public static void main(String[] args) throws SQLException {
        Connection conn = getConnection();
        System.out.println(region);
        List<String> list1 = Arrays.asList(region.split(",").clone());
        //线程数执行任务数
        final int tasks = list1.size() / threadNum;
        List<Map<String, Object>> list = new Vector<>();
        for (int i = 0; i < threadNum; i++) {
            List<String> thisList = list1.subList(i * tasks, i == threadNum - 1 ? list1.size() : tasks * (i+1));
            e.execute(() -> {
                for (String s : thisList) {
                    PreparedStatement ps = null;
                    try {
                        ps = conn.prepareStatement(sql(s));
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String amount = rs.getString("amount");
                            String REGION_NAME = rs.getString("REGION_NAME");
                            String fee_name = rs.getString("fee_name");
                            String year = rs.getString("year");
                            Map<String, Object> row = new LinkedHashMap<>();
                            row.put("项目", REGION_NAME);
                            row.put("费项", fee_name);
                            row.put("金额", amount);
                            row.put("费用归属年", year);
                            System.out.println(row.toString());
                            list.add(row);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                c.countDown();
                System.out.println(c.getCount());

            });
        }
        try {
            c.await();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        conn.close();
        e.shutdown();
        ExcelWriter writer = ExcelUtil.getWriter("C:\\Users\\agan\\Desktop\\write1.xls");
        writer.write(list);
        writer.close();

    }

}