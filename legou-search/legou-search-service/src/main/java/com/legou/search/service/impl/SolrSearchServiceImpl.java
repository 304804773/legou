package com.legou.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchResult;
import com.legou.search.dao.SolrSearchDao;
import com.legou.search.service.SolrSearchService;
@Service
public class SolrSearchServiceImpl implements SolrSearchService {
	@Autowired
	private SolrSearchDao solrSearchDao;
	@Override
	public SearchResult search(String keyword) {
		//创建搜索条键
		SolrQuery query = new SolrQuery();
		//设置要查询的内容条件
		query.setQuery(keyword);
		query.set("df", "item_title");
		query.setRows(100);
		//调用solrSearchDao方法设置查询条件返回搜索结果
		SearchResult searchResult = solrSearchDao.search(query);
		//返回搜过结果
		return searchResult;
	}

}
