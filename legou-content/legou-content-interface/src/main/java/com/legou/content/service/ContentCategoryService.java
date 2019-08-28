package com.legou.content.service;

import java.util.List;

import com.legou.common.pojo.EasyUiTreeNode;
import com.legou.common.utils.LegouResult;

public interface ContentCategoryService {

	List<EasyUiTreeNode> getConteCategoryList(long id);

	LegouResult addContentNode(Long parentId, String name);

	LegouResult updateContentNode(Long id, String name);

}
