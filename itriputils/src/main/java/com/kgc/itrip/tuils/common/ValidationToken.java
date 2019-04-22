package com.kgc.itrip.tuils.common;


import com.alibaba.fastjson.JSONObject;
import com.kgc.itrip.beans.model.ItripUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Token验证
 * Created by hanlu on 2017/5/7.
 */
@Component
public class ValidationToken {

    private Logger logger = Logger.getLogger(ValidationToken.class);

    private @Resource RedisAPI redisAPI;

    public RedisAPI getRedisAPI() {
        return redisAPI;
    }
    public void setRedisAPI(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }
    public ItripUser getCurrentUser(String tokenString){
        //根据token从redis中获取用户信息
			/*
			 test token:
			 key : token:1qaz2wsx
			 value : {"id":"100078","userCode":"myusercode","userPassword":"78ujsdlkfjoiiewe98r3ejrf","userType":"1","flatID":"10008989"}

			*/
        ItripUser itripUser = null;
        if(null == tokenString || "".equals(tokenString)){
            return null;
        }
        try{
            String userInfoJson = redisAPI.get(tokenString);
            itripUser = JSONObject.parseObject(userInfoJson,ItripUser.class);
        }catch(Exception e){
            itripUser = null;
            logger.error("get userinfo from redis but is error : " + e.getMessage());
        }
        return itripUser;
    }

}
