package com.kgc.itrip.biz.service.image;

import com.kgc.itrip.beans.model.ItripImage;
import com.kgc.itrip.beans.vo.ItripImageVO;
import com.kgc.itrip.dao.mapper.ItripImageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripImageServiceImpl")
public class ItripImageServiceImpl implements ItripImageService {

    @Resource
    private ItripImageMapper itripImageMapper;

    @Override
    public ItripImage getItripImageById(Long id)throws Exception{
        return itripImageMapper.getItripImageById(id);
    }

    @Override
    public List<ItripImageVO>	getItripImageListByMap(Map<String,Object> param)throws Exception{
        return itripImageMapper.getItripImageListByMap(param);
    }

    @Override
    public Integer getItripImageCountByMap(Map<String,Object> param)throws Exception{
        return itripImageMapper.getItripImageCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripImage(ItripImage itripImage)throws Exception{
            itripImage.setCreationDate(new Date());
            return itripImageMapper.insertItripImage(itripImage);
    }

    @Override
    public Integer itriptxModifyItripImage(ItripImage itripImage)throws Exception{
        itripImage.setModifyDate(new Date());
        return itripImageMapper.updateItripImage(itripImage);
    }

    @Override
    public Integer itriptxDeleteItripImageById(Long id)throws Exception{
        return itripImageMapper.deleteItripImageById(id);
    }

   /* public Page<ItripImage> queryItripImagePageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = itripImageMapper.getItripImageCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripImage> itripImageList = itripImageMapper.getItripImageListByMap(param);
        page.setRows(itripImageList);
        return page;
    }*/

}
