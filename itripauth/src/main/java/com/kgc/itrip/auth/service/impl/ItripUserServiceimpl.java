package com.kgc.itrip.auth.service.impl;

import com.kgc.itrip.auth.service.ItripUserService;
import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.dao.mapper.ItripUserMapper;
import com.kgc.itrip.tuils.common.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2019/1/21.
 */
@Service("itripUserService")
public class ItripUserServiceimpl implements ItripUserService {
    private Logger logger=Logger.getLogger(ItripUserServiceimpl.class);
    @Resource
    private ItripUserMapper itripUserMapper;
    @Resource
    private RedisAPI redisAPI;
    @Resource
    private SendMailText sendMailText;
    @Resource
    private SMSSendUtil sendSms;
    @Override
    public ItripUser getById(Long id) throws Exception {
        return itripUserMapper.getItripUserById(id);
    }

    @Override
    public List<ItripUser> getListByMap(Map<String, Object> param) throws Exception {
        return itripUserMapper.getItripUserListByMap(param);
    }

    @Override
    public Integer getCountByMap(Map<String, Object> param) throws Exception {
        return itripUserMapper.getItripUserCountByMap(param);
    }

    @Override
    public Integer save(ItripUser itripUser) throws Exception {
        itripUser.setCreationDate(new Date());
        return itripUserMapper.insertItripUser(itripUser);
    }

    @Override
    public Integer modify(ItripUser itripUser) throws Exception {
        itripUser.setModifyDate(new Date());
        return itripUserMapper.updateItripUser(itripUser);
    }

    @Override
    public Integer removeById(Long id) throws Exception {
        return itripUserMapper.deleteItripUserById(id);
    }

    @Override
    public ItripUser login(String userCode, String userPassword) throws itripException {
        Map<String, Object> map = new HashMap();
        map.put("userCode", userCode);
        List<ItripUser> list = null;
        try {
            list = getListByMap(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            throw  new itripException("用户名为空",ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }else {
            String digest = DigestUtil.hmacSign(userPassword, "kgc");
            if(list.get(0).getUserPassword().equals(digest)){
                if (list.get(0).getActivated() == 1) {
                    return list.get(0);
                } else {
                    throw new itripException("用户未激活", ErrorCode.AUTH_AUTHENTICATION_FAILED);
                }
            }else {
                throw new itripException("用户密码错误", ErrorCode.AUTH_PARAMETER_ERROR);
            }
        }
    }

    @Override
    public Page<List<ItripUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripUserMapper.getItripUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize,  total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUser> itripUserList = itripUserMapper.getItripUserListByMap(param);
        page.setRows(itripUserList);
        return page;
    }

    @Override
    public void registerByPhone(ItripUser itripUser) throws itripException {
        itripUser.setActivated(0);
        try {
            Integer integer = save(itripUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new itripException("注册异常，请稍后再试",ErrorCode.AUTH_UNKNOWN);
        }
        int code = DigestUtil.randomCode();
//手机号验证码发送

        sendSms.sendSms(itripUser.getUserCode(),"1",new String[]{""+code,"5"});
        redisAPI.set("activation:"+itripUser.getUserCode(),""+code,60*5);
    }

    @Override
    public void registerByEmail(ItripUser itripUser) throws itripException {
        itripUser.setActivated(0);
        int code;
        try {
            save(itripUser);
            code=DigestUtil.randomCode();
            sendMailText.main(itripUser.getUserCode(),code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new itripException("注册异常，请稍后再试",ErrorCode.AUTH_UNKNOWN);
        }
        redisAPI.set("email:"+itripUser.getUserCode(),""+code,60*5);
    }

    @Override
    public boolean activate(String email, String code) throws Exception {
        String key="email:"+email;
        if(redisAPI.exists(key)) {
            if (redisAPI.get(key).equals(code)) {
                Map map=new HashMap();
                map.put("userCode",email);
                List<ItripUser> list = getListByMap(map);
                if (EmptyUtils.isNotEmpty(list.get(0))) {
                    logger.debug("激活用户" + email);
                    list.get(0).setActivated(1);//激活用户
                    list.get(0).setUserType(0);//自注册用户
                    list.get(0).setFlatID(list.get(0).getId());
                    itripUserMapper.updateItripUser(list.get(0));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean validatePhone(String phoneNumber, String code) throws Exception {
        String key="activation:"+phoneNumber;
        if(redisAPI.exists(key)) {
            if (redisAPI.get(key).equals(code)) {
                Map map=new HashMap();
                map.put("userCode",phoneNumber);
                List<ItripUser> list = getListByMap(map);
                if (EmptyUtils.isNotEmpty(list.get(0))) {
                    logger.debug("用户手机验证已通过：" + phoneNumber);
                    list.get(0).setActivated(1);//激活用户
                    list.get(0).setUserType(0);//自注册用户
                    list.get(0).setFlatID(list.get(0).getId());
                    itripUserMapper.updateItripUser(list.get(0));
                    return true;
                }
            }
        }
        return false;
    }
}
