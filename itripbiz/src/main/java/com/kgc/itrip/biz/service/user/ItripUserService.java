package com.kgc.itrip.biz.service.user;



import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.tuils.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripUserService {

    public ItripUser getItripUserById(Long id)throws Exception;

    public List<ItripUser>	getItripUserListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripUserCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripUser(ItripUser itripUser)throws Exception;

    public Integer itriptxModifyItripUser(ItripUser itripUser)throws Exception;

    public Integer itriptxDeleteItripUserById(Long id)throws Exception;

    public Page<ItripUser> queryItripUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
