package database;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author YXH_XianYu
 * Created On 2022-07-13
 *
 * Excel导出类
 * 可以将数据库game_log.db导出为Excel表格
 * refer to BV1aV411z7ey
 */
public class SQLiteExcelExporter {
    public static void main(String[] args) {
        try {
            // 初始化数据库
            SQLiteJDBC.getInstance();
            // 创建文件输出流
            FileOutputStream output = new FileOutputStream("game_log.xls");
            // 创建工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 获取工作表
            Sheet sheet = workbook.createSheet("GAME_LOG");
            // 获取第一行
            Row row = sheet.createRow(0);
            // 第一行的数据属性
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Name");
            row.createCell(2).setCellValue("IP");
            row.createCell(3).setCellValue("MY_TIME");
            // 表格内容
            // - 查询数据库
            ResultSet resultSet;
            resultSet = SQLiteJDBC.getInstance().getStatement().executeQuery("SELECT * FROM GAME_LOG");
            // - 循环输出数据数据库
            for(int i = 1; resultSet.next(); i++) {
                row = sheet.createRow(i);
                row.createCell(0).setCellValue(resultSet.getInt("id"));
                row.createCell(1).setCellValue(resultSet.getString("name"));
                row.createCell(2).setCellValue(resultSet.getString("ip"));
                row.createCell(3).setCellValue(resultSet.getString("login_time"));
            }
            // 输出文件
            workbook.write(output);
            output.close();
            workbook.close();
            SQLiteJDBC.getInstance().close();
            System.out.println("Export success.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
