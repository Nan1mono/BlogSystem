package com.ljc.project.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Description 配置一个德鲁伊连接池
 */
public class DBUtil {
    // 默认链接方式
    private static String DRIVE_CLASS="com.mysql.jdbc.Driver";
    private static String USERNAME="root";
    private static String USERPASS="123456";
    private static String URL="jdbc:mysql://localhost:3306/book?characterEncoding=UTF-8";

    // 通过外部配置文件获取链接数据
    // 申明数据源对象  链接
    private static DataSource dataSource=null;
    // 加载配置文件，初始化链接信息
    static {
        Properties properties=new Properties();
        InputStream inputStream=null;
        // 加载配置文件
        try {
            inputStream=DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);
            // 获取配置信息
            String driverClass=properties.getProperty("jdbc.classname");    // 先获取驱动包
            DRIVE_CLASS=(driverClass!=null?driverClass:DRIVE_CLASS);        // 做一个判断，如果驱动包为空，使用默认驱动包  以下同理
            USERNAME=properties.getProperty("jdbc.username")!=null?properties.getProperty("jdbc.username"):USERNAME;
            USERPASS=properties.getProperty("jdbc.userpass")!=null?properties.getProperty("jdbc.userpass"):USERPASS;
            URL=properties.getProperty("jdbc.url")!=null?properties.getProperty("jdbc.url"):URL;

            // 使用德鲁伊连接池创建链接
            DruidDataSource druidDS=new DruidDataSource();
            // 设置属性
            druidDS.setUsername(USERNAME);      // 设置数据库账号
            druidDS.setPassword(USERPASS);      // 设置数据库密码
            druidDS.setUrl(URL);                // 设置URL
            druidDS.setInitialSize(Integer.parseInt(properties.getProperty("jdbc.initial")));
            druidDS.setMaxActive(Integer.parseInt(properties.getProperty("jdbc.maxactive")));
            druidDS.setMinIdle(Integer.parseInt(properties.getProperty("jdbc.minidle")));
            druidDS.setMaxWait(Integer.parseInt(properties.getProperty("jdbc.maxwait")));
            druidDS.setUseLocalSessionState(true);  // 开启非公平锁 提升效率
            dataSource=druidDS;         // 传给源数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream!=null) inputStream.close();     // 关闭IO
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Despriction 获取一个有效链接
     * @return
     */
    public static Connection getCon(){
        try {
            // 从dataSource中拿一个链接
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * @Description 关闭所有链接
     */
    public static void closeAll(ResultSet rs, Statement st,Connection con){
        try {
            if (rs!=null) rs.close();
            if (st!=null) st.close();
            if (con!=null) con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
