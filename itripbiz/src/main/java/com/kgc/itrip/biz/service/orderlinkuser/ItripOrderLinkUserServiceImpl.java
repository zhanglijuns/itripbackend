package com.kgc.itrip.biz.service.orderlinkuser;


import com.kgc.itrip.beans.model.ItripOrderLinkUser;
import com.kgc.itrip.beans.vo.order.ItripOrderLinkUserVo;
import com.kgc.itrip.dao.mapper.ItripOrderLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripOrderLinkUserServiceImpl")
public class ItripOrderLinkUserServiceImpl implements ItripOrderLinkUserService {

    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

    @Override
    public ItripOrderLinkUser getItripOrderLinkUserById(Long id)throws Exception{
        return itripOrderLinkUserMapper.getItripOrderLinkUserById(id);
    }

    @Override
    public List<ItripOrderLinkUserVo>	getItripOrderLinkUserListByMap(Map<String,Object> param)throws Exception{
        return itripOrderLinkUserMapper.getItripOrderLinkUserListByMap(param);
    }

    @Override
    public Integer getItripOrderLinkUserCountByMap(Map<String,Object> param)throws Exception{
        return itripOrderLinkUserMapper.getItripOrderLinkUserCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception{
            itripOrderLinkUser.setCreationDate(new Date());
            return itripOrderLinkUserMapper.insertItripOrderLinkUser(itripOrderLinkUser);
    }

    @Override
    public Integer itriptxModifyItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception{
        itripOrderLinkUser.setModifyDate(new Date());
        return itripOrderLinkUserMapper.updateItripOrderLinkUser(itripOrderLinkUser);
    }

    @Override
    public Integer itriptxDeleteItripOrderLinkUserById(Long id)throws Exception{
        return itripOrderLinkUserMapper.deleteItripOrderLinkUserById(id);
    }

    @Override
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception{
        return itripOrderLinkUserMapper.getItripOrderLinkUserIdsByOrder();
    }
}
