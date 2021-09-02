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

public class EditTopicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        int topicId=Integer.parseInt(req.getParameter("topicId"));
        TopicDAO topicDAO=new TopicService();
        // 获取要修改的topic对象
        Topic topic=topicDAO.findTopicById(topicId);
        // 判断session是否存在
        HttpSession session= req.getSession();
        User user=(User) session.getAttribute("User");
        if (user!=null){
            String title=req.getParameter("title");
            String content=req.getParameter("content");
            if ("".equals(title) || title==null) title="【 无标题 】";
            if ("".equals(content) || content==null) content="【 无内容 】";
            topic.setTitle(title);
            topic.setContent(content);
            int i=topicDAO.editTopic(topic);
            if (i!=0) {
                resp.getWriter().write("修改成功");
            }else {
                resp.getWriter().write("修改失败");
            }
            resp.addHeader("refresh","1;main");
        } else {
            resp.sendRedirect("login.html");
        }
    }
}
