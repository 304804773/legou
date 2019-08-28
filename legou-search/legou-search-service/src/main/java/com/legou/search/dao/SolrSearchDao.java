package com.legou.search.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.legou.common.pojo.SearchItem;
import com.legou.common.pojo.SearchResult;

@Repository
public class SolrSearchDao {

	@Autowired
	SolrServer solrServer;

	public SearchResult search(SolrQuery query) {
		//新建搜索结果集合
		SearchResult searchResult = new SearchResult();

		try {
			//定义一个查询条件
			QueryResponse queryResponse = solrServer.query(query);
			//创建记录集合
			SolrDocumentList results = queryResponse.getResults();
			//定义一个搜索商品集合
			List<SearchItem> itemList = new ArrayList<SearchItem>();
			//遍历集合
			for (SolrDocument solrDocument : results) {
				//每遍历一次创建一个商品
				SearchItem item = new SearchItem();
				//设置商品的id为查询商品的id
				item.setId((String) solrDocument.get("id"));
				//设置商品的名字为查询商品的名字
				item.setCategory_name((String) solrDocument.get("item_category_name"));
				//设置商品的图片为查询商品的图片
				item.setImage((String) solrDocument.get("item_image"));
				//设置商品的状态为查询商品的状态
				item.setSell_point((String) solrDocument.get("item_sell_point"));
				//设置商品的名字为查询商品的名字
				item.setPrice((long) solrDocument.get("item_price"));
				//设置商品的标题为查询商品的标题
				item.setTitle((String) solrDocument.get("item_title"));
				//往搜素商品集合中添加商品
				itemList.add(item);
			}
			//设置搜索结果集合的集合的值
			searchResult.setItemList(itemList);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		//返回搜索结果
		return searchResult;
	}

}
