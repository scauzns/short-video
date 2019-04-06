package com.imooc.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 22:33
 */

public class UsersVO {

    private String id;

    private String username;

    private String userToken;

    @JsonIgnore
    private String password;

    private String faceImage;

    private String nickname;

    private Integer fansCounts;

    private Integer followCounts;

    private Integer receiveLikeCounts;

    private Boolean isFollow;

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage == null ? null : faceImage.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
