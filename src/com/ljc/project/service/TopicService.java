package com.ljc.project.service;

import com.ljc.project.bean.Topic;
import com.ljc.project.bean.User;
import com.ljc.project.dao.TopicDAO;
import com.ljc.project.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopicService implements TopicDAO {
    private Connection conn=null;
    private PreparedStatement pst=null;
    private ResultSet rs=null;

    /**
     * 分页查询文章，将查询到的信息填入到表格中
     * @param pageNum 页码
     * @param size  每页个数
     * @return 查询到的文章集合
     */
    @Override
    public List<Topic> queryTopicByPage(int pageNum, int size) {
        // 转换一下页面，可以百度一下limit的特性，便于理解为什么要做一个转换
        int page=(pageNum-1)*size;
        // 准备一个集合用于存放查询到的文章信息
        List<Topic> topics=new ArrayList<>();
        String queryBypage="select * from topic where status=1 limit ?,?";
        Date now=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(queryBypage);
            pst.setInt(1,page);
            pst.setInt(2,size);
            rs=pst.executeQuery();
            while (rs.next()){
                Topic topic=new Topic();
                topic.setTopicId(rs.getInt("topicId"));
                topic.setTitle(rs.getString("title"));
                topic.setContent(rs.getString("content"));
                topic.setUserId(rs.getInt("userId"));
                topic.setStatus(rs.getInt("status"));
                topics.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return topics;
    }

    /**
     * 查询文章总数 可以用来计算文章一共有多少页
     * @return
     */
    @Override
    public int topicNum() {
        String queryNum="select count(*) from topic where status=1";
        int num=0;
        try {
            conn= DBUtil.getCon();
            pst=conn.prepareStatement(queryNum);
            rs= pst.executeQuery();
            if (rs.next()) num=rs.getInt(1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return num;
    }

    /**
     * @Description 根据topicid查询topic
     * @param topicId
     * @return 返回查询到的topic对象，如果对象不存在返回null
     */
    @Override
    public Topic findTopicById(int topicId) {
        String findTopic="select * from topic where topicId=?";
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(findTopic);
            pst.setInt(1,topicId);
            rs= pst.executeQuery();
            if (rs.next()){
                String topicTitle=rs.getString("title");
                StringBuilder content=new StringBuilder(rs.getString("content"));
                String created=rs.getString("created");
                int userId=rs.getInt("userId");
                int status=rs.getInt("status");
                return new Topic(topicId,topicTitle,content.toString(),created,userId,status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return null;
    }

    @Override
    public int editTopic(Topic topic) {
        String update="update topic set title=?,content=?,created=? where topicId=?";
        Date now=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(update);
            pst.setString(1,topic.getTitle());
            pst.setString(2,topic.getContent());
            pst.setString(3, simpleDateFormat.format(now));
            pst.setInt(4,topic.getTopicId());
            pst.executeUpdate();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
        return 0;
    }

    /**
     * @Description 根据id删除一篇文章，将status从1改为0
     * @param topicId
     */
    @Override
    public void deleteTopic(int topicId) {
        String delete="update topic set status=? where topicId=?";
        try {
            conn=DBUtil.getCon();
            pst=conn.prepareStatement(delete);
            pst.setInt(1,0);
            pst.setInt(2,topicId);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeAll(rs,pst,conn);
        }
    }

    @Override
    public int releaseTopic(int userId, String title, String content) {
        String insert="insert into topic (title,content,created,userId) values (?,?,?,?)";
        Date now=new Date();
        int result=0;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conn=DBUtil.getCon();
            pst= conn.prepareStatement(insert);
            pst.setString(1,title);
            pst.setString(2,content);
            pst.setString(3, simpleDateFormat.format(now));
            pst.setInt(4,userId);
            result=pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
