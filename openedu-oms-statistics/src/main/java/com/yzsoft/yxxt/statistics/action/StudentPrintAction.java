package com.yzsoft.yxxt.statistics.action;

import org.beangle.print.model.PrintTemplate;
import org.beangle.print.service.PrintTemplateService;
import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentPrintAction extends EntityDrivenAction {

    @Resource
    private PrintTemplateService printTemplateService;

    public String printWelcome() throws IOException {
        print("DYMBLX_XSBD");
        put("title", "新生报到打印单");
        return forward("/template/print/printTemplate");
    }

    private void print(String code) throws IOException {
        PrintTemplate printTemplate = printTemplateService.getPrintTemplateByCode(code);
        put("printTemplate", printTemplate);
        String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/";
        //学生集合
        Long[] stdIds = getEntityIds("student", Long.class);
        List<Student> stdList = entityDao.get(Student.class, stdIds);
        //向Freemarker模版传递参数
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("students", stdList);
        dataMap.put("base", basePath);
        dataMap.put("pageNo", 1);
        put("templateContent", printTemplateService.getTemplateContent(printTemplate, dataMap, true, false));
        put("templateStyles", printTemplate.getTemplateStyles());
    }

}
