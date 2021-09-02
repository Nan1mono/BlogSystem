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

/**
 * 修改页面
 */
public class EditTopicFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        int topicId=Integer.parseInt(req.getParameter("topicId"));
        // 根据topicId获取topic对象
        TopicDAO topicDAO=new TopicService();
        Topic topic=topicDAO.findTopicById(topicId);
        // 判断session是否存在，不存在不能修改
        HttpSession session= req.getSession();
        User user=(User) session.getAttribute("User");
        StringBuilder sb=new StringBuilder();
        // 当user不为空，即session存在，有登陆状态，可以进行修改
        if (user!=null){
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>修改文章</title>\n" +
                    "    <link rel=\"stylesheet\" href=\"css/bolg.css\">\n" +
                    "    <script src=\"js/jquery.min.js\"></script>\n" +
                    "    <script src=\"js/bolg.js\"></script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"edit_box\">\n" +
                    "        <form action=\"edit?topicId="+topicId+"\" method=\"POST\">\n" +
                    "            <input type=\"text\" value=\""+topic.getTitle()+"\" name=\"title\">\n" +
                    "            <textarea name=\"content\" cols=\"30\" rows=\"10\">"+topic.getContent()+"</textarea>\n" +
                    "            <a href=\"main\">返回主页</a>"+
                    "            <input type=\"submit\" value=\"修改\">\n" +
                    "        </form>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        }else {
            resp.sendRedirect("login.html");
        }
        resp.getWriter().write(sb.toString());
    }
}
