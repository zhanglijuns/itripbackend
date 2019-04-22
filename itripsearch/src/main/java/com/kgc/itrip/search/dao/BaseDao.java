package com.kgc.itrip.search.dao;

import com.kgc.itrip.tuils.common.EmptyUtils;
import com.kgc.itrip.tuils.common.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS on 2019/1/24.
 */
public class BaseDao<T> {
    private HttpSolrClient solrClient;
    public BaseDao(String url){
        solrClient=new HttpSolrClient(url);
        solrClient.setParser(new XMLResponseParser());
        solrClient.setConnectionTimeout(500);
    }
    public Page<T> queryPage(SolrQuery solrQuery,Integer pageNo,Integer pageSize,Class clazz) throws IOException, SolrServerException {
        Integer currPage= EmptyUtils.isNotEmpty(pageNo)?pageNo:1;
        Integer pSize=EmptyUtils.isNotEmpty(pageSize)?pageSize:5;
        solrQuery.setStart((currPage-1)*pSize);
        solrQuery.setRows(pSize);
        //获取查询结果
        QueryResponse queryResponse=solrClient.query(solrQuery);
        //获取总条数
        Integer total=Long.valueOf(queryResponse.getResults().getNumFound()).intValue();
        Page<T> page=new Page<>(currPage,pSize,total);
        //获取数据
        List<T> rows=queryResponse.getBeans(clazz);
        page.setRows(rows);
        return page;
    }
    public List<T> queryList(SolrQuery solrQuery,Integer pageSize,Class clazz) throws IOException, SolrServerException {
        if (EmptyUtils.isNotEmpty(pageSize)){
            solrQuery.setStart(0);
            solrQuery.setRows(pageSize);
        }
        QueryResponse queryResponse=solrClient.query(solrQuery);
        return queryResponse.getBeans(clazz);
    }
}
