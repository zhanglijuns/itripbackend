package com.kgc.itrip.tuils.userinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by ASUS on 2019/1/22.
 */
@ApiModel(value ="ItripTokenVO",description = "用户认证凭证信息")
public class ItripTokenVO {
    @ApiModelProperty("用户认证凭据")
    private String token;
    @ApiModelProperty("过期时间，单位：毫秒")
    private long expTime;
    @ApiModelProperty("生成时间，单位：毫秒")
    private long genTime;

    public ItripTokenVO() {
    }

    public ItripTokenVO(String token, long expTime, long genTime) {
        this.token = token;
        this.expTime = expTime;
        this.genTime = genTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpTime() {
        return expTime;
    }

    public void setExpTime(long expTime) {
        this.expTime = expTime;
    }

    public long getGenTime() {
        return genTime;
    }

    public void setGenTime(long genTime) {
        this.genTime = genTime;
    }
}
