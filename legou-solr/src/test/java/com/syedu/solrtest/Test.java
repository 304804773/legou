package com.syedu.solrtest;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;


public class Test {
	@org.junit.Test
	public void testAdd() throws Exception {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", 1);
		document.addField("item_title", "new2 - 阿尔卡特 (OT-927) 炭黑 联通3G手机 双卡双待");
		document.addField("item_sell_point", "清仓！仅北京，武汉仓有货！");
		document.addField("item_price", "29900000");
		server.add(document);
		server.commit();	
	}
	@org.junit.Test
	public void testQuery() throws SolrServerException {
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.set("q", "*:*");
		QueryResponse query=server.query(solrQuery);
		SolrDocumentList results=query.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
		}
		
		
		
	}
}
