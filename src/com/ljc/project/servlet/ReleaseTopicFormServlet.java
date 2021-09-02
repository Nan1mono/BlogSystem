package com.ljc.project.servlet;

import com.ljc.project.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ReleaseTopicFormServlet extends HttpServlet {
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
        int userId=Integer.parseInt(req.getParameter("userId"));
        StringBuilder sb=new StringBuilder();
        if (user!=null){
            sb.append("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>发布文章</title>\n" +
                    "    <link rel=\"stylesheet\" href=\"css/bolg.css\">\n" +
                    "    <script src=\"js/jquery.min.js\"></script>\n" +
                    "    <script src=\"js/bolg.js\"></script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"edit_box\">\n" +
                    "        <form action=\"release?userId="+userId+"\" method=\"POST\">\n" +
                    "            <input type=\"text\" value=\"标题\" name=\"title\">\n" +
                    "            <textarea name=\"content\" cols=\"30\" rows=\"10\">内容</textarea>\n" +
                    "            <a href=\"main\">返回主页</a>\n" +
                    "            <input type=\"submit\" value=\"发布\">\n" +
                    "        </form>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
            resp.getWriter().write(sb.toString());
        } else {
            resp.sendRedirect("login.html");
        }
    }
}
