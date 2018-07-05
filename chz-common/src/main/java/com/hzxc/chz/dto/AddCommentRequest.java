package com.hzxc.chz.dto;

/**
 * create by chz on 2018/3/15
 */
public class AddCommentRequest extends CommonRequest{
    private String topicId;
    private String commentId;
    private String content;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
