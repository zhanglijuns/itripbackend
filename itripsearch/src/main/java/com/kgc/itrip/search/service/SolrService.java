package com.kgc.itrip.search.service;


import com.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import com.kgc.itrip.beans.vo.hotel.SearchHotelVO;
import com.kgc.itrip.search.beans.Hotel;
import com.kgc.itrip.tuils.common.Page;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 2018/7/22.
 */
public interface SolrService {
    public List<Hotel> searchHotelByHotCity(SearchHotCityVO searchHotCityVO) throws IOException, SolrServerException, Exception;
    //查询酒店分页
    public Page<Hotel> searchItripHotelPage(SearchHotelVO searchHotelVO) throws Exception;
}
