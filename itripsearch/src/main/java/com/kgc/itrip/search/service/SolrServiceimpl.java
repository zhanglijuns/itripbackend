package com.kgc.itrip.search.service;

import com.kgc.itrip.beans.vo.hotel.SearchHotCityVO;
import com.kgc.itrip.beans.vo.hotel.SearchHotelVO;
import com.kgc.itrip.search.beans.Hotel;
import com.kgc.itrip.search.dao.BaseDao;
import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import com.kgc.itrip.tuils.common.PropertiesUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 2018/7/22.
 */
@Service("solrService")
public class SolrServiceimpl implements SolrService {
    private String Url= PropertiesUtils.get("db.properties","Url");
    BaseDao<Hotel> baseDao=new BaseDao(Url);
    @Override
    public List<Hotel> searchHotelByHotCity(SearchHotCityVO searchHotCityVO) throws IOException, SolrServerException, Exception {
        SolrQuery solrQuery=new SolrQuery("*:*");
        if (EmptyUtils.isNotEmpty(searchHotCityVO.getCityId())){
            solrQuery.addFilterQuery("cityId:"+searchHotCityVO.getCityId());
        }
        return baseDao.queryList(solrQuery,searchHotCityVO.getCount(),Hotel.class);
    }

    @Override
    public Page<Hotel> searchItripHotelPage(SearchHotelVO searchHotelVO) throws Exception {
        SolrQuery solrQuery=new SolrQuery("*:*");
        StringBuffer tempQuery=new StringBuffer();
        //拼接全文检索, q
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDestination())){
            tempQuery.append(" destination:"+searchHotelVO.getDestination());
        }
        if (EmptyUtils.isNotEmpty(searchHotelVO.getKeywords())){
            tempQuery.append(" AND keyword:"+searchHotelVO.getKeywords());
        }
        solrQuery.setQuery(tempQuery.toString());
        //过滤查询solrQuery.addFilterQuery(添加搜索条件)
        //酒店级别
        if (EmptyUtils.isNotEmpty(searchHotelVO.getHotelLevel())){
            solrQuery.addFilterQuery("hotelLevel:"+searchHotelVO.getHotelLevel());
        }
        //多id拼接到一起
        //酒店特色id
        if (EmptyUtils.isNotEmpty(searchHotelVO.getFeatureIds())){
            String[] split = searchHotelVO.getFeatureIds().split(",");
            StringBuffer fqQuery = new StringBuffer();
            for (int i=0;i<split.length;i++) {
                String tureId = split[i];
                if (i == 0) {
                    fqQuery.append(" featureIds:*," + tureId + ",*");

                }else{
                    fqQuery.append(" OR featureIds:*,"+tureId+",*");
                }
            }
                solrQuery.addFilterQuery(fqQuery.toString());
        }

        //最高价
        //价格：【一个数  to 另一个数】 俩个数之间  *表示任意一个数
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMaxPrice())){
            solrQuery.addFilterQuery(" maxPrice:[*  TO  "+searchHotelVO.getMaxPrice()+"]");
        }
        //最低价
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMinPrice())){
            solrQuery.addFilterQuery("minPrice:["+searchHotelVO.getMinPrice()+"  TO  *]");
        }
        //商圈id
        //多值相同可一用拼接起来用or隔开
        if (EmptyUtils.isNotEmpty(searchHotelVO.getTradeAreaIds())){
            String[] split = searchHotelVO.getTradeAreaIds().split(",");
            StringBuffer fqQuery = new StringBuffer();
            for (int i=0;i<split.length;i++) {
                String areId = split[i];
                if (i == 0) {
                    fqQuery.append("tradingAreaIds:*," + areId + ",*");

                }else{
                    fqQuery.append(" OR tradingAreaIds:*,"+areId+",*");
                }
            }
            solrQuery.addFilterQuery(fqQuery.toString());
        }
        //字段降序
        //sort是制定排序字段和排序方式
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDescSort())){
            solrQuery.addSort(searchHotelVO.getDescSort(), SolrQuery.ORDER.desc);
        }
        //字段升序
        if (EmptyUtils.isNotEmpty(searchHotelVO.getAscSort())){
            solrQuery.addSort(searchHotelVO.getAscSort(), SolrQuery.ORDER.asc);
        }
        return baseDao.queryPage(solrQuery,searchHotelVO.getPageNo(),searchHotelVO.getPageSize(),Hotel.class);
    }
}
