package com.kgc.itrip.biz.service.labeldic;

import com.kgc.itrip.beans.model.ItripLabelDic;
import com.kgc.itrip.beans.vo.ItripLabelDicVO;
import com.kgc.itrip.dao.mapper.ItripLabelDicMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripLabelDicServiceImpl")
public class ItripLabelDicServiceImpl implements ItripLabelDicService {

    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Override
    public ItripLabelDic getItripLabelDicById(Long id)throws Exception{
        return itripLabelDicMapper.getItripLabelDicById(id);
    }

    @Override
    public List<ItripLabelDic>	getItripLabelDicListByMap(Map<String,Object> param)throws Exception{
        return itripLabelDicMapper.getItripLabelDicListByMap(param);
    }

    @Override
    public Integer getItripLabelDicCountByMap(Map<String,Object> param)throws Exception{
        return itripLabelDicMapper.getItripLabelDicCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripLabelDic(ItripLabelDic itripLabelDic)throws Exception{
            itripLabelDic.setCreationDate(new Date());
            return itripLabelDicMapper.insertItripLabelDic(itripLabelDic);
    }

    @Override
    public Integer itriptxModifyItripLabelDic(ItripLabelDic itripLabelDic)throws Exception{
        itripLabelDic.setModifyDate(new Date());
        return itripLabelDicMapper.updateItripLabelDic(itripLabelDic);
    }

    @Override
    public Integer itriptxDeleteItripLabelDicById(Long id)throws Exception{
        return itripLabelDicMapper.deleteItripLabelDicById(id);
    }

    @Override
    public Page<ItripLabelDic> queryItripLabelDicPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripLabelDicMapper.getItripLabelDicCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripLabelDic> itripLabelDicList = itripLabelDicMapper.getItripLabelDicListByMap(param);
        page.setRows(itripLabelDicList);
        return page;
    }


    /**
     * 根据parentId查询数据字典
     * @param parentId
     * @return
     * @throws Exception
     * add by hanlu 2017-5-11
     */
    public List<ItripLabelDicVO> getItripLabelDicByParentId(Long parentId)throws Exception{
        return itripLabelDicMapper.getItripLabelDicByParentId(parentId);
    }

}
