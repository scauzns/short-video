package com.imooc.vo;

/**
 * @功能描述：TODO
 * @创建日期: 2019/4/1 21:35
 */
public class PublisherVideo {
    public UsersVO publisher;
    private boolean userLikeVideo;

    public UsersVO getPublisher() {
        return publisher;
    }

    public void setPublisher(UsersVO publisher) {
        this.publisher = publisher;
    }

    public boolean isUserLikeVideo() {
        return userLikeVideo;
    }

    public void setUserLikeVideo(boolean userLikeVideo) {
        this.userLikeVideo = userLikeVideo;
    }
}
