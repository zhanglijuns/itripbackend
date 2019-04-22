package com.kgc.itrip.biz.service.areadic;
import com.kgc.itrip.beans.model.ItripAreaDic;
import com.kgc.itrip.dao.mapper.ItripAreaDicMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripAreaDicServiceImpl")
public class ItripAreaDicServiceImpl implements ItripAreaDicService{

    @Resource
    private ItripAreaDicMapper itripAreaDicMapper;

    @Override
    public ItripAreaDic getItripAreaDicById(Long id)throws Exception{
        return itripAreaDicMapper.getItripAreaDicById(id);
    }

    @Override
    public List<ItripAreaDic> getItripAreaDicListByMap(Map<String,Object> param)throws Exception{
        return itripAreaDicMapper.getItripAreaDicListByMap(param);
    }

    @Override
    public Integer getItripAreaDicCountByMap(Map<String,Object> param)throws Exception{
        return itripAreaDicMapper.getItripAreaDicCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripAreaDic(ItripAreaDic itripAreaDic)throws Exception{
            itripAreaDic.setCreationDate(new Date());
            return itripAreaDicMapper.insertItripAreaDic(itripAreaDic);
    }

    @Override
    public Integer itriptxModifyItripAreaDic(ItripAreaDic itripAreaDic)throws Exception{
        itripAreaDic.setModifyDate(new Date());
        return itripAreaDicMapper.updateItripAreaDic(itripAreaDic);
    }

    @Override
    public Integer itriptxDeleteItripAreaDicById(Long id)throws Exception{
        return itripAreaDicMapper.deleteItripAreaDicById(id);
    }

    @Override
    public Page<ItripAreaDic> queryItripAreaDicPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripAreaDicMapper.getItripAreaDicCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1: pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripAreaDic> itripAreaDicList = itripAreaDicMapper.getItripAreaDicListByMap(param);
        page.setRows(itripAreaDicList);
        return page;
    }

}
