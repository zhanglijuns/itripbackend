package com.kgc.itrip.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.kgc.itrip.auth.exception.TokenException;
import com.kgc.itrip.auth.service.TokenService;
import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.tuils.common.MD5;
import com.kgc.itrip.tuils.common.RedisAPI;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ASUS on 2019/1/22.
 */
@Service("tokenServiceImpl")
public class TokenServiceImpl implements TokenService {
    private Logger logger=Logger.getLogger(TokenServiceImpl.class);
    @Resource
    private RedisAPI redisAPI;
    private int expire=SESSION_TIMEOUT;
    @Override
    public String generateToKen(String agent, ItripUser itripUser) {
        StringBuffer sb=new StringBuffer();
        //在Redis数据库中新建一个文件夹
        sb.append("token:");
        sb.append("PC-");
        sb.append(MD5.getMd5(itripUser.getUserCode(),32)+"-");
        sb.append(itripUser.getId()+"-");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"-");
        sb.append(MD5.getMd5(agent,6));
        return sb.toString();
    }

    @Override
    public void save(String token, ItripUser itripUser) {
        redisAPI.set(token, JSON.toJSONString(itripUser));
    }

    @Override
    public ItripUser load(String token) {
        return JSON.parseObject(redisAPI.get(token),ItripUser.class);
    }

    @Override
    public void del(String token) {
        if (redisAPI.exists(token)){
            redisAPI.del(token);
        }
    }

    @Override
    public String replaceToken(String agent, String token) throws TokenException{
    if (!redisAPI.exists(token)){
        new TokenException("未知的token或者已经过期");
    }
        Date startData;
    String[] tokens=token.split("-");
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            startData=simpleDateFormat.parse(tokens[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e);
            throw new TokenException("token格式错误"+token);
        }
        long passed=Calendar.getInstance().getTimeInMillis()-startData.getTime();
        if (passed<REPLACEMENT_PROTECTION_TIMEOUT*1000){
            throw  new TokenException("token处于置换保护时间,剩余"+(REPLACEMENT_PROTECTION_TIMEOUT*1000-passed)/1000+"(s),禁止置换");
        }
        //上面的情况都符合，进行token置换
        String newToken;
        ItripUser itripUser=load(token);
        long ttl=redisAPI.ttl(token);
        if (ttl>0){
            newToken=this.generateToKen(agent,itripUser);
            this.save(newToken,itripUser);
            redisAPI.set(token,JSON.toJSONString(itripUser),REPLACEMENT_DELAY);
        }else {
            throw  new TokenException("当前token过期时间异常，禁止置换");
        }
        return newToken;
    }

    @Override
    public boolean tokenisNo(String agent, String token) {
        if (!redisAPI.exists(token)) {// token不存在
            return false;
        }
        try {
            Date TokenGenTime;// token生成时间
            String agentMD5;
            String[] tokenDetails = token.split("-");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            TokenGenTime = formatter.parse(tokenDetails[3]);
            long passed = Calendar.getInstance().getTimeInMillis()
                    - TokenGenTime.getTime();
            if(passed>SESSION_TIMEOUT*1000) {
                return false;
            }
            agentMD5 = tokenDetails[4];
            if(MD5.getMd5(agent, 6).equals(agentMD5)) {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
        return false;
    }
}
