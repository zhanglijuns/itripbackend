package com.kgc.itrip.biz.service.hotelorder;

import com.kgc.itrip.beans.model.ItripHotelOrder;
import com.kgc.itrip.beans.model.ItripOrderLinkUser;
import com.kgc.itrip.beans.model.ItripUserLinkUser;
import com.kgc.itrip.beans.vo.order.ItripListHotelOrderVO;
import com.kgc.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import com.kgc.itrip.dao.mapper.*;
import com.kgc.itrip.tuils.common.BigDecimalUtil;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_DOWN;

@Service("itripHotelOrderServiceImpl")
public class ItripHotelOrderServiceImpl implements ItripHotelOrderService {

    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;

    @Resource
    private ItripHotelTempStoreMapper itripHotelTempStoreMapper;

    @Resource
    private ItripProductStoreMapper itripProductStoreMapper;

    @Resource
    private ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;


    @Override
    public ItripHotelOrder getItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderById(id);
    }

    @Override
    public List<ItripHotelOrder> getItripHotelOrderListByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderListByMap(param);
    }

    @Override
    public Integer getItripHotelOrderCountByMap(Map<String, Object> param) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
    }

    @Override
    public Map<String, String> itriptxAddItripHotelOrder(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> linkUserList) throws Exception {
        //定义变量map，里面存放订单的id和orderNo返回给前端
        Map<String, String> map = new HashMap<String, String>();
        if (null != itripHotelOrder) {
            int flag=0;
            if (EmptyUtils.isNotEmpty(itripHotelOrder.getId())) {
                //删除联系人
                itripOrderLinkUserMapper.deleteItripOrderLinkUserByOrderId(itripHotelOrder.getId());
                itripHotelOrder.setModifyDate(new Date());
                flag=itripHotelOrderMapper.updateItripHotelOrder(itripHotelOrder);
            } else {
                itripHotelOrder.setCreationDate(new Date());
                flag=itripHotelOrderMapper.insertItripHotelOrder(itripHotelOrder);
            }
            if (flag > 0) {
                Long orderId = itripHotelOrder.getId();
                //添加订单之后还需要往订单与常用联系人关联表中添加记录
                if (orderId > 0) {
                    for (ItripUserLinkUser itripUserLinkUser : linkUserList) {
                        ItripOrderLinkUser itripOrderLinkUser = new ItripOrderLinkUser();
                        itripOrderLinkUser.setOrderId(orderId);
                        itripOrderLinkUser.setLinkUserId(itripUserLinkUser.getId());
                        itripOrderLinkUser.setLinkUserName(itripUserLinkUser.getLinkUserName());
                        itripOrderLinkUser.setCreationDate(new Date());
                        itripOrderLinkUser.setCreatedBy(itripHotelOrder.getCreatedBy());
                        itripOrderLinkUserMapper.insertItripOrderLinkUser(itripOrderLinkUser);
                    }
                }
                map.put("id", itripHotelOrder.getId().toString());
                map.put("orderNo", itripHotelOrder.getOrderNo());
                return map;
            }
        }
        return map;
    }

    @Override
    public Integer itriptxModifyItripHotelOrder(ItripHotelOrder itripHotelOrder) throws Exception {
        ItripHotelOrder modifyItripHotelOrder = itripHotelOrderMapper.getItripHotelOrderById(itripHotelOrder.getId());
        //更新临时表的库存
        Map<String, Object> roomStoreMap = new HashMap<String, Object>();
        roomStoreMap.put("startTime", modifyItripHotelOrder.getCheckInDate());
        roomStoreMap.put("endTime", modifyItripHotelOrder.getCheckOutDate());
        roomStoreMap.put("count", modifyItripHotelOrder.getCount());
        roomStoreMap.put("roomId", modifyItripHotelOrder.getRoomId());
        itripHotelTempStoreMapper.updateRoomStore(roomStoreMap);
        return itripHotelOrderMapper.updateItripHotelOrder(itripHotelOrder);
    }

    @Override
    public Integer itriptxDeleteItripHotelOrderById(Long id) throws Exception {
        return itripHotelOrderMapper.deleteItripHotelOrderById(id);
    }

    @Override
    public Page<ItripHotelOrder> queryItripHotelOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripHotelOrderMapper.getItripHotelOrderCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1 : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripHotelOrder> itripHotelOrderList = itripHotelOrderMapper.getItripHotelOrderListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

    @Override
    public boolean updateHotelOrderStatus(Long id) throws Exception {
        return itripHotelOrderMapper.updateHotelOrderStatus(id,null) > 0 ? true : false;
    }

    @Override
    public int getRoomNumByRoomIdTypeAndDate(Integer roomId, String startDate, String endDate) throws Exception {


        return itripHotelOrderMapper.getRoomNumByRoomIdTypeAndDate(roomId, startDate, endDate);
    }

    @Override
    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception {
        BigDecimal payAmount = null;
        BigDecimal roomPrice = itripHotelRoomMapper.getItripHotelRoomById(roomId).getRoomPrice();
        payAmount = BigDecimalUtil.OperationASMD(count, roomPrice,
                BigDecimalUtil.BigDecimalOprations.multiply,
                2,ROUND_DOWN);
        return payAmount;
    }

    @Override
    public Page<ItripListHotelOrderVO> queryOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = itripHotelOrderMapper.getOrderCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? 1: pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? 5 : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<ItripListHotelOrderVO> itripHotelOrderList = itripHotelOrderMapper.getOrderListByMap(param);
        page.setRows(itripHotelOrderList);
        return page;
    }

    @Override
    public boolean flushOrderStatus(Integer type) throws Exception {
        Integer flag;
        if (type == 1) {
            flag = itripHotelOrderMapper.flushCancelOrderStatus();
        } else {
            flag = itripHotelOrderMapper.flushSuccessOrderStatus();
        }
        return flag > 0 ? true : false;
    }

    /**
     * 订单支付成功之后，根据订单的信息去更新库存 add by donghai
     *
     * @param
     * @return
     * @throws Exception
     */
//    public int updateRoomStore(ItripHotelOrder hotelorder) throws Exception{
//        ItripHotelOrder modifyItripHotelOrder = new ItripHotelOrder();
//        modifyItripHotelOrder = itripHotelOrderMapper.getItripHotelOrderById(hotelorder.getId());
//        int result = 0;
//        //获取预定日期，并按天拆分
//        List<Date> dates = DateUtil.getBetweenDates(modifyItripHotelOrder.getCheckInDate(), modifyItripHotelOrder.getCheckOutDate());
//        ItripHotelTempStore tempStore = new ItripHotelTempStore();
//        //遍历日期，每个日期都对应一条库存记录
//        Long hotelId = modifyItripHotelOrder.getHotelId();
//        Long roomId = modifyItripHotelOrder.getRoomId();
//        Integer count = modifyItripHotelOrder.getCount();
//        List<ItripHotelTempStore> tempStoreList = null;
//        for(int i=0; i<dates.size(); i++) {
//            tempStore.setHotelId(hotelId.intValue());
//            tempStore.setRoomId(roomId);
//            tempStore.setRecordDate(dates.get(i));
//            //根据日期和roomId去临时库存表判断有没有对应的记录，如果有则更新此记录的库存数量，
//            //否则根据日期和roomId去库存表查询对应的记录，并以此记录计算库存余量，此时临时库存表需新增记录
//            Map<String, Object> tempStoreMap = new HashMap<String, Object>();
//            tempStoreMap.put("roomId", roomId);
//            tempStoreMap.put("recordDate", dates.get(i));
//            tempStoreList = itripHotelTempStoreMapper.getItripHotelTempStoreListByMap(tempStoreMap);
//            //临时库存表中有记录
//            if (null != tempStoreList && 1 == tempStoreList.size()) {
//                tempStore = tempStoreList.get(0);
//                //设置临时库存数量为当前量减去订单消耗量
//                tempStore.setStore(tempStore.getStore() - count);
//                result = itripHotelTempStoreMapper.updateItripHotelTempStore(tempStore);
//
//            }
//        }
//        return result;
//    }
    @Override
    public ItripPersonalOrderRoomVO getItripHotelOrderRoomInfoById(Long orderId) throws Exception {
        return itripHotelOrderMapper.getItripHotelOrderRoomInfoById(orderId);
    }
}
