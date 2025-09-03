package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.importer.StudentEnrollImportListener;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentRegisterImportAction extends EntityDrivenAction {

    @Override
    protected String getEntityName() {
        return StudentEnroll.class.getName();
    }

    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("年级", "grade");
        map.put("学号", "code");
        map.put("姓名", "name");
        map.put("是否录取", "enrolled");
        return map;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        return new StudentEnrollImportListener();
    }

    @Override
    protected Class<?> getImportEntity() {
        return StudentEnrollImp.class;
    }
}
