package com.yzsoft.yxxt.web.service.impl;

import com.yzsoft.yxxt.web.model.FootLink;
import com.yzsoft.yxxt.web.model.IconLink;
import com.yzsoft.yxxt.web.model.ImgLink;
import com.yzsoft.yxxt.web.model.WelcomeLink;
import com.yzsoft.yxxt.web.service.IndexService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexServiceImpl extends EntityDaoSupport implements IndexService {

    @Resource
    private DictDataService dictDataService;

    @Override
    public List<ImgLink> findImgLink() {
        OqlBuilder query = OqlBuilder.from(ImgLink.class);
        query.where("enabled = true");
        query.orderBy("sort");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<IconLink> findIconLink() {
        OqlBuilder query = OqlBuilder.from(IconLink.class);
        query.where("enabled = true");
        query.orderBy("sort");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<WelcomeLink> findWelcomeLink() {
        OqlBuilder query = OqlBuilder.from(WelcomeLink.class);
        query.where("enabled = true");
        query.orderBy("sort");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<DictData> findFootLinkGroup() {
        List<DictData> groups = dictDataService.findDictData("FOOT_LINK_GROUP");
        if (groups.isEmpty()) {
            groups = initFootLinkGroup();
        }
        return groups;
    }

    private List<DictData> initFootLinkGroup() {
        String code = "FOOT_LINK_GROUP";
        String name = "页底链接组";
        String[] names = new String[]{"快速导航", "友情链接"};
        List<DictData> dictDatas = dictDataService.init(code, name, names);
        return dictDatas;
    }

    @Override
    public List<FootLink> findFootLink() {
        return findFootLink(null);
    }

    @Override
    public List<FootLink> findFootLink(Long groupId) {
        OqlBuilder query = OqlBuilder.from(FootLink.class);
        if (groupId != null) {
            query.where("footLink.group.id = :groupId", groupId);
        }
        query.where("enabled = true");
        query.orderBy("sort");
        query.cacheable();
        return entityDao.search(query);
    }

}
