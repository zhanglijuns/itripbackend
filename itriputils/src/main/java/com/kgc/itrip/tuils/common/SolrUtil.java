package com.kgc.itrip.tuils.common;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by l骆明 on 2018/7/19.
 */
@Component
public class SolrUtil {
    private static HttpSolrClient httpSolrClient;
    private static String url;
    static {
        url="http://localhost:8080/solr/hotel";
         httpSolrClient=new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());
        httpSolrClient.setConnectionTimeout(500);
    }
    public  <T> boolean saveSolrResource(T solrEntity) {

        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument doc = binder.toSolrInputDocument(solrEntity);
        try {
            httpSolrClient.add(doc);
            httpSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除solr 数据
     *
     * @param id
     */
    public  boolean removeSolrData(String id) {
        try {
            httpSolrClient.deleteById(id);
            httpSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 查询
     *
     * @param keywords
     */
    public QueryResponse query(String keywords) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery(keywords);
        QueryResponse rsp = httpSolrClient.query(query);
        return rsp;
    }
}
