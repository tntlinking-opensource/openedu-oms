package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.dorm.system.service.DormService;
import com.yzsoft.yxxt.welcome.process.bean.StudentPrint;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.print.model.PrintTemplate;
import org.beangle.print.service.PrintTemplateService;
import org.beangle.process.model.ProcessLinkItemPrint;
import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.BaseAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打印
 */
@Controller
public class PrintAction extends BaseAction {

    @Resource
    private PrintTemplateService printTemplateService;

    @Resource
    private DormService dormService;

    public String index() {
        try {
            Long printId = getLong("printId");
            Long studentId = getLong("studentId");
            ProcessLinkItemPrint print = entityDao.get(ProcessLinkItemPrint.class, printId);
            if (print == null) {
                put("errormsg", "打印模版未配置");
                return forward();
            }
            PrintTemplate printTemplate = print.getTemplate();
            put("printTemplate", printTemplate);
            String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/";

            StudentPrint studentPrint = new StudentPrint();
            studentPrint.setStudent(entityDao.get(Student.class, studentId));
            studentPrint.setBed(dormService.getBed(studentId));
            List<StudentPrint> studentPrints = new ArrayList<StudentPrint>();
            studentPrints.add(studentPrint);

            SinglePage students = new SinglePage(1, 20, 1, studentPrints);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("students", students);//向Freemarker模版传递参数
            dataMap.put("base", basePath);
            //FIXME 不加这个参数会报错
            dataMap.put("pageNo", students.getPageNo());
            put("templateContent", printTemplateService.getTemplateContent(printTemplate, dataMap, true, false));
            put("templateStyles", printTemplate.getTemplateStyles());
            put("title", printTemplate.getName());
            return forward("/template/print/printTemplate");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return redirect(new Action(ErrorAction.class));
        }
    }

}
