package poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;

/**
 * @ClassName A
 * @Description
 * @Author wpj
 * @Date 2021/7/15 18:27
 **/

public class A {

    public static void main(String[] args) throws Throwable {
        SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
        wb.setCompressTempFiles(true);
        Sheet sh = wb.createSheet("123");
        long time = System.currentTimeMillis();
        for(int rownum = 0; rownum < 100; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum <20; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }
            // manually control how rows are flushed to disk
            if(rownum % 100 == 0) {
                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others
                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                // this method flushes all rows
                ;
            }
        }
        FileOutputStream out = new FileOutputStream("D:\\123.xlsx");
        wb.write(out);
        out.flush();
        out.close();
        System.out.println(System.currentTimeMillis()-time);
        // dispose of temporary files backing this workbook on disk
//        wb.dispose();
    }
}
