package com.ljc.project.servlet;

import com.ljc.project.bean.Topic;
import com.ljc.project.bean.User;
import com.ljc.project.dao.TopicDAO;
import com.ljc.project.dao.UserDAO;
import com.ljc.project.service.TopicService;
import com.ljc.project.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeeTopicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        int topicId=Integer.parseInt(req.getParameter("topicId"));
        System.out.println(topicId);
        TopicDAO topicDAO=new TopicService();
        UserDAO userDAO=new UserService();
        Topic topic=topicDAO.findTopicById(topicId);
        // 获取一个session 判断登录状态 不在登录时不能查看
        HttpSession session=req.getSession();
        User user=(User)session.getAttribute("User");
        StringBuilder sb=new StringBuilder();
        // 当user绝对不为空时，可以进行查看或者编辑
        if (user!=null){
            if (topic!=null){
                // 获取作者名
                String name=userDAO.findUserById(topic.getUserId()).getUserName();
                // 设定文章的时间格式
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String created= null;
                try {
                    Date createdTime = simpleDateFormat.parse(topic.getCreated());
                    created=simpleDateFormat.format(createdTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // 当topic对象不为空时可以查看
                sb.append("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>文章详情</title>\n" +
                        "    <link rel=\"stylesheet\" href=\"css/bolg.css\">\n" +
                        "    <script src=\"js/jquery.min.js\"></script>\n" +
                        "    <script src=\"js/bolg.js\"></script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div class=\"topic_box\">\n" +
                        "        <div class=\"topic_title\">\n" +
                        "            <h1>"+topic.getTitle()+"</h1>\n" +
                        "        </div>\n" +
                        "        <div class=\"topic_username\">\n" +
                        "            "+name+"\n" +
                        "            <br>\n" +
                        "            "+created+"\n" +
                        "        </div>\n" +
                        "        <div class=\"topic_content\">\n" +
                        "            "+topic.getContent()+"\n" +
                        "        </div>\n" +
                        "        <div class=\"back\"><a href=\"main\">返回主页</a></div>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>");
            }else {
                // 如果topic为Null判断为文章不存在
                sb.append("<h2>文章不存在</h2>");
                resp.addHeader("refresh","1;main");
            }
            resp.getWriter().write(sb.toString());
        }else {
            // 如果不是登录状态，返回主页登录
            resp.sendRedirect("login.html");
        }
    }
}
