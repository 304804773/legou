package com.legou.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchItem;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.search.mapper.ItemMapper;
import com.legou.search.service.ImportItemToSolrService;

@Service
public class ImportItemToSolrServiceImpl implements ImportItemToSolrService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public LegouResult importAllItemToSolr() {

		try {
			// 得到所有商品的集合
			List<SearchItem> itemList = itemMapper.getItemList();
			// 遍历集合
			for (SearchItem tbItem : itemList) {
				// 每遍历一次创建一条新的记录
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", tbItem.getId());
				document.addField("item_title", tbItem.getTitle());
				document.addField("item_sell_point", tbItem.getSell_point());
				document.addField("item_price", tbItem.getPrice());
				document.addField("item_image", tbItem.getImage());
				document.addField("item_category_name", tbItem.getCategory_name());
				solrServer.add(document);
			}
			//添加事物
			solrServer.commit();
			return LegouResult.ok();
		} catch (Exception e) {
			 e.printStackTrace();
				return LegouResult.build(500, "导入失败");
		}
	}

}
