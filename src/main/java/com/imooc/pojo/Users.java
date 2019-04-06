package com.imooc.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象",description = "这是用户对象 ")
public class Users {
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(value = "用户名", name = "username",example = "imoocuser",required = true)
    private String username;

    @ApiModelProperty(value = "密码", name = "password",example = "123456",required = true)
    private String password;

    @ApiModelProperty(hidden = true)
    private String faceImage;

    private String nickname;
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;
    @ApiModelProperty(hidden = true)
    private Integer followCounts;
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

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
}