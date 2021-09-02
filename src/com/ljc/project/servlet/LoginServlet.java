package com.ljc.project.servlet;

import com.ljc.project.bean.User;
import com.ljc.project.dao.UserDAO;
import com.ljc.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String userName=req.getParameter("username");
        String userPass=req.getParameter("userpass");
        UserDAO userDAO=new UserService();
        User user = userDAO.login(userName, userPass);
        if (user!=null){
            resp.getWriter().write("登录成功");
            // 登录成功之后，生成一个session
            HttpSession session=req.getSession();
            // 当无操作30分钟之后，需要重新登录
            session.setMaxInactiveInterval(1800);
            // 讲登录成功的用户名设置成这个session的属性
            session.setAttribute("User",user);
            resp.sendRedirect("main");
        }else {
            resp.getWriter().write("账号密码不正确");
            resp.addHeader("refresh","1;url=login.html");
        }
    }
}
