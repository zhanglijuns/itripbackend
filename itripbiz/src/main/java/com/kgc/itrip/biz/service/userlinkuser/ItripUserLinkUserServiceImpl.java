package com.kgc.itrip.biz.service.userlinkuser;


import com.kgc.itrip.beans.model.ItripUserLinkUser;
import com.kgc.itrip.dao.mapper.ItripUserLinkUserMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripUserLinkUserServiceImpl")
public class ItripUserLinkUserServiceImpl implements ItripUserLinkUserService {

    @Resource
    private ItripUserLinkUserMapper itripUserLinkUserMapper;

    @Override
    public ItripUserLinkUser getItripUserLinkUserById(Long id)throws Exception{
        return itripUserLinkUserMapper.getItripUserLinkUserById(id);
    }

    @Override
    public List<ItripUserLinkUser> getItripUserLinkUserByUserId(Long userId)throws Exception{
        return itripUserLinkUserMapper.getItripUserLinkUserByUserId(userId);
    }

    @Override
    public List<ItripUserLinkUser>	getItripUserLinkUserListByMap(Map<String,Object> param)throws Exception{
        List<ItripUserLinkUser> list = itripUserLinkUserMapper.getItripUserLinkUserListByMap(param);
        return list;
    }

    @Override
    public Integer getItripUserLinkUserCountByMap(Map<String,Object> param)throws Exception{
        return itripUserLinkUserMapper.getItripUserLinkUserCountByMap(param);
    }

    @Override
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception{
            itripUserLinkUser.setCreationDate(new Date());
            return itripUserLinkUserMapper.insertItripUserLinkUser(itripUserLinkUser);
    }

    @Override
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception{
        itripUserLinkUser.setModifyDate(new Date());
        return itripUserLinkUserMapper.updateItripUserLinkUser(itripUserLinkUser);
    }

    @Override
    public Integer deleteItripUserLinkUserByIds(Long[] ids)throws Exception{
        return itripUserLinkUserMapper.deleteItripUserLinkUserByIds(ids);
    }

    @Override
    public Page<ItripUserLinkUser> queryItripUserLinkUserPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripUserLinkUserMapper.getItripUserLinkUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripUserLinkUser> itripUserLinkUserList = itripUserLinkUserMapper.getItripUserLinkUserListByMap(param);
        page.setRows(itripUserLinkUserList);
        return page;
    }

}
