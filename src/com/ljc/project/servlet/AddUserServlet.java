package com.ljc.project.servlet;

import com.ljc.project.bean.User;
import com.ljc.project.dao.UserDAO;
import com.ljc.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String userName=req.getParameter("username");
        String userPass=req.getParameter("userpass");
        UserDAO userDAO=new UserService();
        if ("".equals(userName) || "".equals(userPass) || userName==null || userPass==null || userName.length()==0 || userPass.length()==0){
            resp.getWriter().write("注册失败");
        }else {
            int i = userDAO.addUser(userName, userPass);
            if (i!=0) resp.getWriter().write("注册成功");
            else resp.getWriter().write("注册失败");
        }
        resp.addHeader("refresh","1;url=login.html");
    }
}
