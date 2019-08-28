package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.search.service.ImportItemToSolrService;

@Controller
public class ImportItemToSolrController {
	@Autowired
	private ImportItemToSolrService importItemToSolrService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public LegouResult importItemToSolr() {
		
		LegouResult leouLegouResult = importItemToSolrService.importAllItemToSolr();
		
		return leouLegouResult;
		
	}

}
