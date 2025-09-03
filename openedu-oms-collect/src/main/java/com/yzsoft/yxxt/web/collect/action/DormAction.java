package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.finance.model.DormSetting;
import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceStudentItem;
import com.yzsoft.yxxt.finance.service.DormService;
import com.yzsoft.yxxt.finance.service.FinanceService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchState;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormAction extends CollectAction {

    @Autowired
    protected FinanceService financeService;
    @Autowired
    protected FinanceStudentService financeStudentService;

    @Autowired
    protected DormService dormService;

    @Override
    public String getCode() {
        return "G041";
    }

    @Override
    public String index() {
        CollectSwitch collectSwitch = getSwitch();
        CollectSwitchState collectSwitchState = collectService.getState(getUserId(), getCode());
        if (collectSwitchState == null && collectSwitch.isEditable()) {
            return redirect("edit");
        }
        return super.index();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        Student student = getStudent();
        StudentInfo studentInfo = student.getInfo();
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        Assert.notNull(financeStudent, "收费信息不存在，无法操作");
        put("studentInfo", studentInfo);
        put("financeStudent", financeStudent);
        put("financeStudentItem", getFinanceStudentItem(financeStudent,"003"));
    }
    
    private FinanceStudentItem getFinanceStudentItem(FinanceStudent financeStudent, String code) {
        if(financeStudent!=null){
            for (FinanceStudentItem item : financeStudent.getItems()) {
                if (item.getItem().getCode().equals(code)) {
                    return item;
                }
            }
        }
		return null;
	}

    @Override
    public String edit() {
        indexSetting();
        List<DormSetting> dormSettings = entityDao.getAll(DormSetting.class);
        put("dormSettings",dormSettings);
        return super.edit();
    }

    @Override
    public String save() {
        boolean accommodationed = getBool("accommodationed");
        Double money = Double.valueOf(get("financeStudentItem.feeTotal"));
        FinanceItem item = entityDao.getEntity(FinanceItem.class, "code","003");
        Student student = getStudent();
        //更新学生的缴费信息
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        if (financeStudent != null) {
            financeStudentService.updateFinanceStudentItem(financeStudent, item, money, accommodationed);
            financeStudentService.count(financeStudent);
            entityDao.saveOrUpdate(financeStudent);
        }
        StudentInfo studentInfo = student.getInfo();
        studentInfo.setAccommodationed(accommodationed);
        entityDao.saveOrUpdate(studentInfo);
        afterSave();
        return redirect("index");
    }

}
