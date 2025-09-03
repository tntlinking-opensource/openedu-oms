package com.yzsoft.yxxt.web.service;

import com.yzsoft.yxxt.web.model.Content;
import org.beangle.commons.collection.page.PageLimit;

import java.util.List;

public interface ContentService {

    List<Content> findContent(Long columnId, PageLimit limit);

}
