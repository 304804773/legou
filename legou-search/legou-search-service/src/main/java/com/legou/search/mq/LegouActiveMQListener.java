package com.legou.search.mq;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;


import com.legou.common.pojo.SearchItem;
import com.legou.search.mapper.ItemMapper;

public class LegouActiveMQListener implements MessageListener {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {
		//TextMessage是message的子类
		TextMessage textMessage=(TextMessage) message;	
		try {	
			//得到topics的文本值
			String id=textMessage.getText();
			System.out.println(id+"asd");
			//将string类型转换为long类型
			long itemid=Long.parseLong(id);
			//根据id查找searchITEM
			SearchItem searchItem=itemMapper.getItemById(itemid);
			//创造solr条件
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			try {
				//往solr中添加
				solrServer.add(document);
				//提交事务
				solrServer.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
