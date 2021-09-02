package com.ljc.project.servlet;

import com.ljc.project.bean.User;
import com.ljc.project.dao.TopicDAO;
import com.ljc.project.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReleaseTopicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        int userId=Integer.parseInt(req.getParameter("userId"));
        String  title=req.getParameter("title");
        String content=req.getParameter("content");
        if ("".equals(title) || title==null) title="【 无标题 】";
        if ("".equals(content) || content==null) content="【 无内容 】";
        HttpSession session=req.getSession();
        User user=(User) session.getAttribute("User");
        TopicDAO topicDAO=new TopicService();
        if (user!=null){
            int i = topicDAO.releaseTopic(userId, title, content);
            if (i!=0){
                resp.getWriter().write("发布成功");
            }else {
                resp.getWriter().write("发布失败");
            }
            resp.addHeader("refresh","1;main");
        }else {
            resp.sendRedirect("login.html");
        }
    }
}
