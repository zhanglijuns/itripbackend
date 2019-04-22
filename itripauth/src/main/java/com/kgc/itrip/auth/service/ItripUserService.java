package com.kgc.itrip.auth.service;

import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.tuils.common.Page;
import com.kgc.itrip.tuils.common.itripException;

import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 2019/1/21.
 */
public interface ItripUserService {
    public ItripUser getById(Long id)throws Exception;
    public List<ItripUser>	getListByMap(Map<String, Object> param)throws Exception;
    public Integer getCountByMap(Map<String, Object> param)throws Exception;
    public Integer save(ItripUser itripUser)throws Exception;
    public Integer modify(ItripUser itripUser)throws Exception;
    public Integer removeById(Long id)throws Exception;
    public ItripUser login(String userCode, String userPassword)throws itripException;
    public Page<List<ItripUser>> queryPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
    public void registerByPhone(ItripUser itripUser)throws itripException;
    public void registerByEmail(ItripUser itripUser)throws itripException;
    public boolean activate(String email,String code) throws Exception;
    /**
     * 短信验证手机号
     * @param phoneNumber
     * @return
     * @throws Exception
     */
    public boolean validatePhone(String phoneNumber,String code) throws Exception;
}
