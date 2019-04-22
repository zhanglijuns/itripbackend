package com.kgc.itrip.biz.service.hotelfeature;



import com.kgc.itrip.beans.model.ItripHotelFeature;
import com.kgc.itrip.tuils.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripHotelFeatureService {

    public ItripHotelFeature getItripHotelFeatureById(Long id)throws Exception;

    public List<ItripHotelFeature>	getItripHotelFeatureListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripHotelFeatureCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripHotelFeature(ItripHotelFeature itripHotelFeature)throws Exception;

    public Integer itriptxModifyItripHotelFeature(ItripHotelFeature itripHotelFeature)throws Exception;

    public Integer itriptxDeleteItripHotelFeatureById(Long id)throws Exception;

    public Page<ItripHotelFeature> queryItripHotelFeaturePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
