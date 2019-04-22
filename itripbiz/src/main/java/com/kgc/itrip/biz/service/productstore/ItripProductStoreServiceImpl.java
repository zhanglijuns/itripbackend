package com.kgc.itrip.biz.service.productstore;


import com.kgc.itrip.beans.model.ItripProductStore;
import com.kgc.itrip.dao.mapper.ItripProductStoreMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripProductStoreServiceImpl")
public class ItripProductStoreServiceImpl implements ItripProductStoreService {

    @Resource
    private ItripProductStoreMapper itripProductStoreMapper;

    @Override
    public ItripProductStore getItripProductStoreById(Long id)throws Exception{
        return itripProductStoreMapper.getItripProductStoreById(id);
    }

    @Override
    public List<ItripProductStore>	getItripProductStoreListByMap(Map<String,Object> param)throws Exception{
        return itripProductStoreMapper.getItripProductStoreListByMap(param);
    }

    @Override
    public Integer getItripProductStoreCountByMap(Map<String,Object> param)throws Exception{
        return itripProductStoreMapper.getItripProductStoreCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripProductStore(ItripProductStore itripProductStore)throws Exception{
            itripProductStore.setCreationDate(new Date());
            return itripProductStoreMapper.insertItripProductStore(itripProductStore);
    }

    @Override
    public Integer itriptxModifyItripProductStore(ItripProductStore itripProductStore)throws Exception{
        itripProductStore.setModifyDate(new Date());
        return itripProductStoreMapper.updateItripProductStore(itripProductStore);
    }

    @Override
    public Integer itriptxDeleteItripProductStoreById(Long id)throws Exception{
        return itripProductStoreMapper.deleteItripProductStoreById(id);
    }

    @Override
    public Page<ItripProductStore> queryItripProductStorePageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripProductStoreMapper.getItripProductStoreCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripProductStore> itripProductStoreList = itripProductStoreMapper.getItripProductStoreListByMap(param);
        page.setRows(itripProductStoreList);
        return page;
    }

}
