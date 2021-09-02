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
import java.util.List;

public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session=req.getSession();        // 获取LoginServlet发出的session
        User user = (User) session.getAttribute("User");    // 当前登录的账号
//        String userName= user.getUserName();
        TopicDAO topicDAO=new TopicService();
        UserDAO userDAO=new UserService();
        int topicNum=topicDAO.topicNum();           // 先计算文章总数
        int size=10;                                // 默认每页显示10个
        int pageNum=1;                              // 默认显示第一页
        int maxPageNum=(int)Math.ceil((topicNum*1.0/size));
        // 如果没有值，就用默认值
        if(req.getParameter("pageNum")!=null&&req.getParameter("size")!=null){
                size=Integer.parseInt(req.getParameter("size"));
                pageNum=Integer.parseInt(req.getParameter("pageNum"));
        }
        List<Topic> topics=topicDAO.queryTopicByPage(pageNum,size);
        StringBuilder sb=new StringBuilder();       // 生成主要页面
        // 如果LoginServet发出的session中的User对象绝对不为空，就代表会话生效，跳转到登录页面，否则，跳转到登录页面
        if (user!=null) {
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>欢迎您</title>\n" +
                    "    <link rel=\"stylesheet\" href=\"css/bolg.css\">\n" +
                    "    <script src=\"js/jquery.min.js\"></script>\n" +
                    "    <script src=\"js/bolg.js\"></script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"topic_div\">\n" +
                    "        <table class=\"topic_table\">\n" +
                    "            <tr><th>#</th><th>文章标题</th><th>作者名称</th><th>编辑/查看</th></tr>");
            for (Topic topic:topics){
                // 根据topic里的userid查询写这个文章的作者
                User topicUser= userDAO.findUserById(topic.getUserId());
                String topicUserName=null;
                StringBuilder editAndSee=new StringBuilder();
                if (topicUser!=null){
                    // 给作者名字赋值
                    topicUserName=topicUser.getUserName();
                    // 做一个区分，可以修改和删除当前登录账号的文章，只能查看非当前账号登录的文章
                    editAndSee.append("<a href=\"seetopic?topicId="+topic.getTopicId()+"\">查看</a>");       // 默认只能查看
                    // 如果当前文章的userId和当前登录账号的userId相当，那就可以修改和删除
                    if (topic.getUserId()==user.getUserId()){
                        editAndSee.append("/<a href=\"editform?topicId="+topic.getTopicId()+"\">修改</a>/<a href=\"javascript:deleteTopic("+topic.getTopicId()+")\">删除</a>");
                    }
                }
                // 判断当topic的status为1时显示，为0时不显示（删除状态）
                if (topic.getStatus()==1){
                    sb.append("<tr><td>"+topic.getTopicId()+"</td><td>"+topic.getTitle()+"</td><td>"+topicUserName+"</td><td>"+editAndSee+"</td></tr>");
                }
            }

            // 判定能否进行上一页和下一页
            String previousPage="main?pageNum="+(pageNum-1);        //上一页默认可以
            String nextPage="main?pageNum="+(pageNum+1);            // 上一页默认可以
            if (pageNum>=maxPageNum){
                previousPage="main?pageNum="+maxPageNum;                                   // 当页数为最大页数时 设置为空链接
            }
            if ((pageNum-1)<=0){
                nextPage="main?pageNum="+1;                                       // 当页数为最小页数时 设置为空链接
            }

            sb.append("        </table>\n" +
                    "    </div>\n" +
                    "    <div class=\"changepage\">\n" +
                    "        <a href=\"main?pageNum="+((pageNum-1)==0?1:(pageNum-1))+"&size="+size+"\">上一页</a>\n" +
                    "        第"+pageNum+"页/共"+maxPageNum+"页\n" +
                    "        <a href=\"main?pageNum="+((pageNum+1)==(maxPageNum+1)?maxPageNum:(pageNum+1))+"&size="+size+"\">下一页</a>\n" +
                    "        <a href='releaseform?userId="+user.getUserId()+"'>发布文章</a>"+           // 用当前登录的账号发表文章
                    "        <a href='exit'>退出系统</a>"+
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
            resp.getWriter().write(sb.toString());
        }else {
            resp.sendRedirect("login.html");
        }
    }
}
