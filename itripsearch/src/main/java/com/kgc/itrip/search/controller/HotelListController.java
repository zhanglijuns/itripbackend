package com.kgc.itrip.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONToken;
import com.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import com.kgc.itrip.beans.vo.hotel.SearchHotelVO;
import com.kgc.itrip.search.beans.Hotel;
import com.kgc.itrip.search.service.SolrService;
import com.kgc.itrip.tuils.common.Dto;
import com.kgc.itrip.tuils.common.DtoUtil;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 2019/1/24.
 */
@Controller
@Api(value = "API")
@RequestMapping("/api")
public class HotelListController {
    @Resource
    SolrService solrService;
    @ApiOperation(value = "根据热门城市查询酒店", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据热门城市查询酒店" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>20003: 系统异常,获取失败</p>" +
            "<p>20004: 城市id不能为空</p>")
    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO, HttpServletRequest request){
        if(searchHotCityVO.getCityId()!=0){
            try {
                List<Hotel> hotels = solrService.searchHotelByHotCity(searchHotCityVO);
                return DtoUtil.returnSuccess("",hotels);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail("系统异常,获取失败","20003");
            }
        }else {
            return DtoUtil.returnFail("城市id不能为空","20004");
        }
    }
    @ApiOperation(value = "查询酒店分页", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "查询酒店分页(用于查询酒店列表)" +
            "<p>成功：success = ‘true’ | 失败：success = ‘false’ 并返回错误码，如下：</p>" +
            "<p>错误码: </p>" +
            "<p>20001: 系统异常,获取失败</p>" +
            "<p>20002: 目的地不能为空</p>")
    @RequestMapping(value = "/hotellist/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO){
        if (EmptyUtils.isEmpty(searchHotelVO.getDestination())){
            return DtoUtil.returnFail("目的地不能为空","20002");
        }
        try {
            Page<Hotel> page = solrService.searchItripHotelPage(searchHotelVO);
            return DtoUtil.returnDataSuccess(page);
        } catch (Exception e) {
            e.printStackTrace();
            return  DtoUtil.returnFail("系统异常,获取失败","20001");
        }
    }
}
