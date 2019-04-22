package com.kgc.itrip.dao.mapper;


import com.kgc.itrip.beans.model.ItripOrderLinkUser;
import com.kgc.itrip.beans.vo.order.ItripOrderLinkUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripOrderLinkUserMapper {

	public ItripOrderLinkUser getItripOrderLinkUserById(@Param(value = "id") Long id)throws Exception;

	public List<ItripOrderLinkUserVo>	getItripOrderLinkUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripOrderLinkUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

	public Integer updateItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception;

	public Integer deleteItripOrderLinkUserById(@Param(value = "id") Long id)throws Exception;

	public Integer deleteItripOrderLinkUserByOrderId(@Param(value = "orderId") Long orderId)throws Exception;

	public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;
}
