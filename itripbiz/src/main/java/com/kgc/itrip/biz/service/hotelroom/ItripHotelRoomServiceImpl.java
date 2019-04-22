package com.kgc.itrip.biz.service.hotelroom;

import com.kgc.itrip.beans.model.ItripHotelRoom;
import com.kgc.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import com.kgc.itrip.dao.mapper.ItripHotelRoomMapper;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("itripHotelRoomServiceImpl ")
public class ItripHotelRoomServiceImpl implements ItripHotelRoomService {

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Override
    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomById(id);
    }

    @Override
    public List<ItripHotelRoomVO> getItripHotelRoomListByMap(Map<String,Object> param)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomListByMap(param);
    }

    @Override
    public Integer getItripHotelRoomCountByMap(Map<String,Object> param)throws Exception{
        return itripHotelRoomMapper.getItripHotelRoomCountByMap(param);
    }

    @Override
    public Integer itriptxAddItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception{
            itripHotelRoom.setCreationDate(new Date());
            return itripHotelRoomMapper.insertItripHotelRoom(itripHotelRoom);
    }

    @Override
    public Integer itriptxModifyItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception{
        itripHotelRoom.setModifyDate(new Date());
        return itripHotelRoomMapper.updateItripHotelRoom(itripHotelRoom);
    }

    @Override
    public Integer itriptxDeleteItripHotelRoomById(Long id)throws Exception{
        return itripHotelRoomMapper.deleteItripHotelRoomById(id);
    }

    @Override
    public Page<ItripHotelRoomVO> queryItripHotelRoomPageByMap(Map<String,Object> param, Integer pageNo, Integer pageSize)throws Exception{
        Integer total = itripHotelRoomMapper.getItripHotelRoomCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelRoomVO> itripHotelRoomList = itripHotelRoomMapper.getItripHotelRoomListByMap(param);
        page.setRows(itripHotelRoomList);
        return page;
    }

}
