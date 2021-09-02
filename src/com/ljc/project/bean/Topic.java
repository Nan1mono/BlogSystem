package com.ljc.project.bean;

public class Topic {
    private int topicId;
    private String title;
    private String content;
    private String created;
    private int userId;
    private int status=1;

    public Topic() {
    }

    public Topic(int topicId, String title, String content, String created, int userId, int status) {
        this.topicId = topicId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.userId = userId;
        this.status = status;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
