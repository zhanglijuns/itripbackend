package com.kgc.itrip.biz.service.user;

import com.kgc.itrip.beans.model.ItripUser;
import com.kgc.itrip.dao.mapper.ItripUserMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripUserServiceImpl")
public class ItripUserServiceImpl implements ItripUserService {

    @Resource
    private ItripUserMapper itripUserMapper;
    @Override
    public ItripUser getItripUserById(Long id)throws Exception{
        return itripUserMapper.getItripUserById(id);
    }
    @Override
    public List<ItripUser>	getItripUserListByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getItripUserListByMap(param);
    }
    @Override
    public Integer getItripUserCountByMap(Map<String,Object> param)throws Exception{
        return itripUserMapper.getItripUserCountByMap(param);
    }
    @Override
    public Integer itriptxAddItripUser(ItripUser itripUser)throws Exception{
            itripUser.setCreationDate(new Date());
            return itripUserMapper.insertItripUser(itripUser);
    }
    @Override
    public Integer itriptxModifyItripUser(ItripUser itripUser)throws Exception{
        itripUser.setModifyDate(new Date());
        return itripUserMapper.updateItripUser(itripUser);
    }
    @Override
    public Integer itriptxDeleteItripUserById(Long id)throws Exception{
        return itripUserMapper.deleteItripUserById(id);
    }
    @Override
    public Page<ItripUser> queryItripUserPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripUserMapper.getItripUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5: pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUser> itripUserList = itripUserMapper.getItripUserListByMap(param);
        page.setRows(itripUserList);
        return page;
    }

}
