package database;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * @author YXH_XianYu
 * Created On 2022-07-12
 *
 * SQLiteJDBC类
 * （从java书上手抄的）
 *
 * 表格名为GAME_LOG
 */
public class SQLiteJDBC {
    /**
     * 单例模式
     */
    private static SQLiteJDBC Instance = null;

    /**
     * 单例模式
     */
    public static SQLiteJDBC getInstance() {
        if(Instance == null)
            Instance = new SQLiteJDBC();
        return Instance;
    }

    /**
     * 连接数据库的Connection对象
     */
    private Connection connection = null;

    /**
     * 操作数据库的Statement对象
     */
    private Statement statement = null;

    /**
     * 初始化，并构建表格
     */
    public SQLiteJDBC() {
        try {
            // 加载 JDBC 驱动程序
            Class.forName("org.sqlite.JDBC");
            // 数据源，数据库文件名为test.db
            String dataSource = "jdbc:sqlite:game_log.db";
            // 连接数据库
            connection = DriverManager.getConnection(dataSource);
            connection.setAutoCommit(false);
            System.out.println("Open Database successfully.");
            // 创建操作数据库的Statement对象
            statement = connection.createStatement();
            // 创建数据表
            if(!tableExist()) {
                createTable();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 查询GAME_LOG表格是否存在
     * @return true if exist
     */
    public boolean tableExist() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            String[] type = {"TABLE"};
            ResultSet resultSet = meta.getTables(null, null, "GAME_LOG", type);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建数据库表
     */
    public void createTable() {
        try {
            statement.executeUpdate("drop table if exists GAME_LOG");
            String sql = "CREATE TABLE GAME_LOG"
                    + "(ID INT PRIMARY KEY NOT NULL,"
                    + " NAME TEXT,"
                    + " IP CHAR(30) NOT NULL,"
                    + " LOGIN_TIME TEXT)";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入数据库表记录
     * @param name 名字
     * @param ip IP
     * @param time 时间
     * @return 数据库中的记录编号
     */
    public int insertData(String name, String ip, String time) {
        try {
            int index = size() + 1;
            String sql = "INSERT INTO GAME_LOG (ID,NAME,IP,LOGIN_TIME)"
                    + "VALUES (" + index + ",'" + name + "','" + ip + "','" + time + "');";
            statement.executeUpdate(sql);
            connection.commit();
            // System.out.println("成功添加数据库表记录");
            return index;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void updateNameByID(int ID, String name) {
        try {
            String sql = "UPDATE GAME_LOG set NAME = '" + name + "' where ID = " + ID + ";";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ID删除一条记录
     * @param ID ID
     */
    public void deleteDataByID(int ID) {
        try {
            String sql = "DELETE from GAME_LOG where ID=" + ID + ";";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示数据
     */
    public void showData() {
        try {
            // SELECT 操作
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GAME_LOG");
            System.out.println("查询数据记录结果如下：");
            System.out.println("ID\t NAME\t IP\t LOGIN_TIME");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String ip = resultSet.getString("ip");
                String time = resultSet.getString("login_time");
                System.out.printf("%d\t %s\t %s\t %s\n", id, name, ip, time);
            }
            System.out.println();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据库表行数
     * @return 行数(大小)
     */
    public int size() {
        try {
            String sql = "SELECT COUNT(0) FROM GAME_LOG";
            ResultSet resultSet = statement.executeQuery(sql);
            int cnt = 0;
            if(resultSet.next())
                cnt = resultSet.getInt(1);
            return cnt;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取操作数据库对象Statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * 关闭数据库对象
     */
    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试程序入口
     */
    public static void main(String[] args) {

        SQLiteJDBC.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SQLiteJDBC.getInstance().insertData("TEST1", "127.0.0.1", formatter.format(new java.util.Date(System.currentTimeMillis())));
        SQLiteJDBC.getInstance().insertData("TEST2", "127.0.0.1", formatter.format(new java.util.Date(System.currentTimeMillis())));
        SQLiteJDBC.getInstance().insertData("TEST3", "127.0.0.1", formatter.format(new java.util.Date(System.currentTimeMillis())));

        SQLiteJDBC.getInstance().showData();

        SQLiteJDBC.getInstance().close();

        System.out.println("执行完成");
    }
}
