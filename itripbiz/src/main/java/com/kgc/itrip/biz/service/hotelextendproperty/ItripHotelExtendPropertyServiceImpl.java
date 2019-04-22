package com.kgc.itrip.biz.service.hotelextendproperty;

import com.kgc.itrip.beans.model.ItripHotelExtendProperty;
import com.kgc.itrip.dao.mapper.ItripHotelExtendPropertyMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripHotelExtendPropertyServiceImpl")
public class ItripHotelExtendPropertyServiceImpl implements ItripHotelExtendPropertyService {

    @Resource
    private ItripHotelExtendPropertyMapper itripHotelExtendPropertyMapper;

    @Override
    public ItripHotelExtendProperty getItripHotelExtendPropertyById(Long id)throws Exception{
        return itripHotelExtendPropertyMapper.getItripHotelExtendPropertyById(id);
    }

    @Override
    public List<ItripHotelExtendProperty>	getItripHotelExtendPropertyListByMap(Map<String,Object> param)throws Exception{
        return itripHotelExtendPropertyMapper.getItripHotelExtendPropertyListByMap(param);
    }

    @Override
    public Integer getItripHotelExtendPropertyCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelExtendPropertyMapper.getItripHotelExtendPropertyCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripHotelExtendProperty(ItripHotelExtendProperty itripHotelExtendProperty)throws Exception{
            itripHotelExtendProperty.setCreationDate(new Date());
            return itripHotelExtendPropertyMapper.insertItripHotelExtendProperty(itripHotelExtendProperty);
    }

    @Override
    public Integer itriptxModifyItripHotelExtendProperty(ItripHotelExtendProperty itripHotelExtendProperty)throws Exception{
        itripHotelExtendProperty.setModifyDate(new Date());
        return itripHotelExtendPropertyMapper.updateItripHotelExtendProperty(itripHotelExtendProperty);
    }

    @Override
    public Integer itriptxDeleteItripHotelExtendPropertyById(Long id)throws Exception{
        return itripHotelExtendPropertyMapper.deleteItripHotelExtendPropertyById(id);
    }

    @Override
    public Page<ItripHotelExtendProperty> queryItripHotelExtendPropertyPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripHotelExtendPropertyMapper.getItripHotelExtendPropertyCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelExtendProperty> itripHotelExtendPropertyList = itripHotelExtendPropertyMapper.getItripHotelExtendPropertyListByMap(param);
        page.setRows(itripHotelExtendPropertyList);
        return page;
    }

}
