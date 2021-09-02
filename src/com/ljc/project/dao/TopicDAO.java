package com.ljc.project.dao;

import com.ljc.project.bean.Topic;
import com.ljc.project.bean.User;

import java.util.List;

public interface TopicDAO {
    /**
     * @Description 分页查询，用于将问章信息填入表格
     * @param pageNum
     * @param size
     */
    List<Topic> queryTopicByPage(int pageNum, int size);

    /**
     * @Description 用于查询文章总数
     * @return
     */
    int topicNum();

    Topic findTopicById(int topicId);

    /**
     * @Description 根据topic对象修改该对象的部分信息
     * @param topic
     * @return
     */
    int editTopic(Topic topic);

    /**
     * 根据topicId删除一片文章，即将status从1变为0
     */
    void deleteTopic(int topicId);

    /**
     * 根据userid，title 和content发布一篇topic
     * 添加成功返回非0 失败返回0
     */
    int releaseTopic(int userId,String title,String content);
}
