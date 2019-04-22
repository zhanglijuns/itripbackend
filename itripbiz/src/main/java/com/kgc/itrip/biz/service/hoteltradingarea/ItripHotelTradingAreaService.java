package com.kgc.itrip.biz.service.hoteltradingarea;


import com.kgc.itrip.beans.model.ItripHotelTradingArea;
import com.kgc.itrip.tuils.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelTradingAreaService {

    public ItripHotelTradingArea getItripHotelTradingAreaById(Long id)throws Exception;

    public List<ItripHotelTradingArea>	getItripHotelTradingAreaListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelTradingAreaCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripHotelTradingArea(ItripHotelTradingArea itripHotelTradingArea)throws Exception;

    public Integer itriptxModifyItripHotelTradingArea(ItripHotelTradingArea itripHotelTradingArea)throws Exception;

    public Integer itriptxDeleteItripHotelTradingAreaById(Long id)throws Exception;

    public Page<ItripHotelTradingArea> queryItripHotelTradingAreaPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
