package com.kgc.itrip.auth.service;

import com.kgc.itrip.auth.exception.TokenException;
import com.kgc.itrip.beans.model.ItripUser;

/**
 * Created by ASUS on 2019/1/22.
 * Token管理接口
 */
public interface TokenService {
    /**
     * 会话时间
     */
    public final static int SESSION_TIMEOUT=2*60*60;//默认2h
    /**
     * 置换保护时间
     */
    public final static int REPLACEMENT_PROTECTION_TIMEOUT=60*60;
    /**
     * 旧token延迟过期时间
     */
    public final static int REPLACEMENT_DELAY=2*60;
    /**
     * 生成token
     */
    public String generateToKen(String agent, ItripUser itripUser);
    /**
     * 保存用户信息到redis
     */
    public void save(String token,ItripUser itripUser);
    /**
     * 从redis获取用户
     */
    public ItripUser load(String token);
    /**
     * 删除token
     */
    public void del(String token);
    /**
     * 置换token
     */
    public  String replaceToken(String agent,String token)throws TokenException;
    /**
     * 验证token是否失效
     */
    public boolean tokenisNo(String agent,String token);
}
