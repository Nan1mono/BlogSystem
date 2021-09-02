package com.ljc.project.servlet;

import com.ljc.project.bean.Topic;
import com.ljc.project.bean.User;
import com.ljc.project.dao.TopicDAO;
import com.ljc.project.service.TopicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteTopicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session=req.getSession();
        User user=(User) session.getAttribute("User");
        TopicDAO topicDAO=new TopicService();
        int topicId=Integer.parseInt(req.getParameter("topicId"));
        if (user!=null){
            topicDAO.deleteTopic(topicId);
            resp.sendRedirect("main");
        }else {
            resp.sendRedirect("login.html");
        }
    }
}
