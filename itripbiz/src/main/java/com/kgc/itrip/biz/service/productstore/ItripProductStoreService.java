package com.kgc.itrip.biz.service.productstore;


import com.kgc.itrip.beans.model.ItripProductStore;
import com.kgc.itrip.tuils.common.Page;

import java.util.List;
import java.util.Map;

/**
* Created by shang-pc on 2015/11/7.
*/
public interface ItripProductStoreService {

    public ItripProductStore getItripProductStoreById(Long id)throws Exception;

    public List<ItripProductStore>	getItripProductStoreListByMap(Map<String, Object> param)throws Exception;

    public Integer getItripProductStoreCountByMap(Map<String, Object> param)throws Exception;

    public Integer itriptxAddItripProductStore(ItripProductStore itripProductStore)throws Exception;

    public Integer itriptxModifyItripProductStore(ItripProductStore itripProductStore)throws Exception;

    public Integer itriptxDeleteItripProductStoreById(Long id)throws Exception;

    public Page<ItripProductStore> queryItripProductStorePageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
}
