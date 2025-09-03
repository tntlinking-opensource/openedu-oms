package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchState;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchStateView;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CollectSwitchStateAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private CollectService collectService;

    @Override
    protected String getEntityName() {
        return CollectSwitchStateView.class.getName();
    }

    @Override
    public String index() {
        put("grade", yxxtService.getGrade());
        put("collectSwitchs", collectService.findSwitch());
        return super.index();
    }

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        query.where("collectSwitch.name is not null");
        query.where("collectSwitch.name is not null");
        Boolean collected = getBoolean("collected");
        if (collected != null) {
            if (collected) {
                query.where("state is not null");
            } else {
                query.where("state is null");
            }
        }
    }

    public String count() {
        put("grade", yxxtService.getGrade());
        return forward();
    }

    public String report() {
        String grade = get("grade");
        if (StringUtils.isBlank(grade)) {
            grade = yxxtService.getYear().toString();
        }
        SqlBuilder query = new SqlBuilder();
        query.newFrom("YXW_COLLECT_SWITCH_STATES css");
        query.join("YXW_COLLECT_SWITCHES  cs", "on css.collect_switch_id = cs.id");
        query.join("CP_STUDENTS s", "on css.student_id = s.id");
        query.where("s.grade = :grade", grade);
        query.select("cc.name cname, cs.name fname, count(*)");
        query.groupBy("cc.name, cs.name");
        query.orderBy("cc.name, cs.name");
        String title = CountUtil.where(query);
        List<Object[]> datas = entityDao.search(query);
        put("datas", CountUtil.convert(datas, title));
        return forward();
    }
}
