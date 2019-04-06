package com.imooc.enums;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 18:03
 */
public enum  VideoStatusEnum {
    SUCCESS(1), //发布成功
    FORBID(2); //禁止播放，管理员操作
    public final int value;
    VideoStatusEnum(int value) {
        this.value = value;
    }
    public int getValue(){
        return value;
    }
}
