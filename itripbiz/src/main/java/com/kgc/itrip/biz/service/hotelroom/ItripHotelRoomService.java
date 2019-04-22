package com.kgc.itrip.biz.service.hotelroom;


import com.kgc.itrip.beans.model.ItripHotelRoom;
import com.kgc.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import com.kgc.itrip.tuils.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelRoomService {

    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception;

    public List<ItripHotelRoomVO>	getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelRoomCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer itriptxModifyItripHotelRoom(ItripHotelRoom itripHotelRoom)throws Exception;

    public Integer itriptxDeleteItripHotelRoomById(Long id)throws Exception;

    public Page<ItripHotelRoomVO> queryItripHotelRoomPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
