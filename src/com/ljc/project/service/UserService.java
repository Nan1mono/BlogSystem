package com.ljc.project.service;

import com.ljc.project.bean.User;
import com.ljc.project.dao.UserDAO;
import com.ljc.project.util.DBUtil;

import javax.swing.text.Style;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService implements UserDAO {
    private Connection conn=null;
    private PreparedStatement pst=null;
    private ResultSet rs=null;

    /**
     * @Description 根据用户名查找数据库登录 思路：首先根据判断用户名是否存在，再判断密码是否正确
     * @param userName
     * @return 成功true 失败false
     */
    @Override
    public User login(String userName,String userPass) {
        String sql="select * from user where userName=?";       // 搜索指定userName的用户
        String name=null;
        String pass=null;
        User user=null;
        try {
            conn= DBUtil.getCon();
            pst=conn.prepareStatement(sql);
            pst.setString(1,userName);
            rs=pst.executeQuery();
            if (rs.next()){
                try {
                    name=rs.getString(2);
                    pass=rs.getString(3);
                } catch (SQLException e) {}
                if (name!=null && pass!=null && name.equals(userName) && pass.equals(userPass)){
                    return user=new User(rs.getInt("userId"),name,pass);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return user;
    }

    /**
     * @Description 根据Id查询用户
     * @param userId
     * @return 返回这个用户的user对象 没有找到则返回Null
     */
    @Override
    public User findUserById(int userId) {
        String findUser="select * from user where userId=?";
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(findUser);
            pst.setInt(1,userId);
            rs=pst.executeQuery();
            if (rs.next()){
                String userName=rs.getString("userName");
                String userPass=rs.getString("userPass");
                return new User(userId,userName,userPass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return null;
    }

    @Override
    public int addUser(String userName, String userPass) {
        String add="insert into user (userName,userPass) values (?,?)";
        int result=0;
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(add);
            pst.setString(1,userName);
            pst.setString(2,userPass);
            result=pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return result;
    }
}
