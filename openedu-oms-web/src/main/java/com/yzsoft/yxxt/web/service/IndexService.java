package com.yzsoft.yxxt.web.service;

import com.yzsoft.yxxt.web.model.FootLink;
import com.yzsoft.yxxt.web.model.IconLink;
import com.yzsoft.yxxt.web.model.ImgLink;
import com.yzsoft.yxxt.web.model.WelcomeLink;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface IndexService {

    List<ImgLink> findImgLink();

    List<IconLink> findIconLink();

    List<WelcomeLink> findWelcomeLink();

    List<DictData> findFootLinkGroup();

    List<FootLink> findFootLink();

    List<FootLink> findFootLink(Long groupId);
}
