//package canalTest;
//
//import com.alibaba.otter.canal.client.CanalConnector;
//import com.alibaba.otter.canal.client.CanalConnectors;
//
//import java.net.InetSocketAddress;
//import java.util.Queue;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
///**
// * @ClassName TestCanal
// * @Description
// * @Author wpj
// * @Date 2021/6/9 21:58
// **/
//
//public class TestCanal {
//
//
//    private static Queue<String> SQL_QUEUE = new ConcurrentLinkedQueue<>();
//
//    public static void main(String[] args) {
//        //获取canalServer连接：本机地址,端口号
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
//                11111), "example", "", "");
//        int batchSize = 1000;
//        try {
//            //连接canalServer
//            connector.connect();
//            //订阅Desctinstion
//            connector.subscribe();
//            connector.rollback();
//            try {
//                while (true) {
//                    //尝试从master那边拉去数据batchSize条记录，有多少取多少
//                    //轮询拉取数据   上面的where
//                    Message message = connector.getWithoutAck(batchSize);
//                    long batchId = message.getId();
//                    int size = message.getEntries().size();
//                    if (batchId == -1 || size == 0) {
//                        //睡眠
//                        Thread.sleep(1000);
//                    } else {
//                        dataHandle(message.getEntries());
//                    }
//                    connector.ack(batchId);
//                    System.out.println("aa"+size);
//                    //当队列里面堆积的sql大于一定数值的时候就模拟执行
//                    if (SQL_QUEUE.size() >= 10) {
//                        executeQueueSql();
//                    }
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (InvalidProtocolBufferException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            connector.disconnect();
//        }
//
//
//    }
//
//
//
//
//    /**
//     * 模拟执行队列里面的sql语句
//     */
//    public static void executeQueueSql() {
//        int size = SQL_QUEUE.size();
//        for (int i = 0; i < size; i++) {
//            String sql = SQL_QUEUE.poll();
//            System.out.println("[sql]----> " + sql);
//        }
//    }
//
//    /**
//     * 数据处理
//     *
//     * @param entrys
//     */
//    private static void dataHandle(List<CanalEntry.Entry> entrys) throws InvalidProtocolBufferException {
//        for (CanalEntry.Entry entry : entrys) {
//            if (EntryType.ROWDATA == entry.getEntryType()) {
//                RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//                CanalEntry.EventType eventType = rowChange.getEventType();
//                if (eventType == EventType.DELETE) {
//                    saveDeleteSql(entry);
//                } else if (eventType == EventType.UPDATE) {
//                    saveUpdateSql(entry);
//                } else if (eventType == CanalEntry.EventType.INSERT) {
//                    saveInsertSql(entry);
//                }
//            }
//        }
//    }
//
//    /**
//     * 保存更新语句
//     *
//     * @param entry
//     */
//    private static void saveUpdateSql(CanalEntry.Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
//            for (CanalEntry.RowData rowData : rowDatasList) {
//                List<Column> newColumnList = rowData.getAfterColumnsList();
//                StringBuffer sql = new StringBuffer("update " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " set ");
//                for (int i = 0; i < newColumnList.size(); i++) {
//                    sql.append(" " + newColumnList.get(i).getName()
//                            + " = '" + newColumnList.get(i).getValue() + "'");
//                    if (i != newColumnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(" where ");
//                List<Column> oldColumnList = rowData.getBeforeColumnsList();
//                for (Column column : oldColumnList) {
//                    if (column.getIsKey()) {
//                        //暂时只支持单一主键
//                        sql.append(column.getName() + "=" + column.getValue());
//                        break;
//                    }
//                }
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存删除语句
//     *
//     * @param entry
//     */
//    private static void saveDeleteSql(CanalEntry.Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
//            for (CanalEntry.RowData rowData : rowDatasList) {
//                List<Column> columnList = rowData.getBeforeColumnsList();
//                StringBuffer sql = new StringBuffer("delete from " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " where ");
//                for (Column column : columnList) {
//                    if (column.getIsKey()) {
//                        //暂时只支持单一主键
//                        sql.append(column.getName() + "=" + column.getValue());
//                        break;
//                    }
//                }
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 保存插入语句
//     *
//     * @param entry
//     */
//    private static void saveInsertSql(CanalEntry.Entry entry) {
//        try {
//            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
//            List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
//            for (CanalEntry.RowData rowData : rowDatasList) {
//                List<Column> columnList = rowData.getAfterColumnsList();
//                StringBuffer sql = new StringBuffer("insert into " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " (");
//                for (int i = 0; i < columnList.size(); i++) {
//                    sql.append(columnList.get(i).getName());
//                    if (i != columnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(") VALUES (");
//                for (int i = 0; i < columnList.size(); i++) {
//                    sql.append("'" + columnList.get(i).getValue() + "'");
//                    if (i != columnList.size() - 1) {
//                        sql.append(",");
//                    }
//                }
//                sql.append(")");
//                SQL_QUEUE.add(sql.toString());
//            }
//        } catch (InvalidProtocolBufferException e) {
//            e.printStackTrace();
//        }
//    }
//}
