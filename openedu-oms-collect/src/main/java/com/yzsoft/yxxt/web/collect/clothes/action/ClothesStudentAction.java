package com.yzsoft.yxxt.web.collect.clothes.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudentSize;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;
import com.yzsoft.yxxt.web.collect.clothes.service.ClothesImportListener;
import com.yzsoft.yxxt.web.collect.clothes.service.ClothesService;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesStudentAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private ClothesService clothesService;

    @Override
    protected Class<?> getEntityClass() {
        return ClothesStudentSize.class;
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("grade", yxxtService.getGrade());
        put("types", clothesService.findType());
    }

    public String count() {
        put("grade", yxxtService.getGrade());
        return forward();
    }

    public String report() {
        List<ClothesType> types = clothesService.findType();
        List<Object> datas = new ArrayList<Object>();
        for (ClothesType type : types) {
            List<List> data = count(type);
            datas.add(new Object[]{type, data});
        }
        put("datas", datas);
        return forward();
    }

    private List<List> count(ClothesType type) {
        String grade = get("grade");
        if (StringUtils.isBlank(grade)) {
            grade = yxxtService.getGrade();
        }
        SqlBuilder query = new SqlBuilder();
        query.newFrom("YXWC_CLOTHES_STUDENT_SIZES css");
        query.join("YXWC_CLOTHES_STUDENTS cs", "on css.student_id = cs.id");
        query.join("CP_STUDENTS s", "on cs.student_id = s.id");
        query.join("SYS_DICT_DATAS g", "on s.gender_id = g.id");
        query.join("YXWC_CLOTHES_SIZES csz", "on css.size_id = csz.id");
        query.where("css.size_id is not null");
        query.where("csz.type_id = :typeid", type.getId());
        query.where("s.grade = :grade", grade);
        query.select("g.name gname, csz.name sname, count(*)");
        query.groupBy("g.name, csz.name");
        query.orderBy("g.name desc, csz.name");
//		CountUtil.whereCondition(query);
        List<Object[]> datas = entityDao.search(query);
        return CountUtil.convert(datas, "性别");
    }


    public String print() {
        report();
        return forward();
    }

    public void exportExcel() {
        report();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        int rowNum = 0;
        List<ClothesType> types = clothesService.findType();
        for (ClothesType type : types) {
            List<List> datas = count(type);
            rowNum = addTable(sheet, rowNum, type, datas);
            rowNum++;
        }
        CountUtil.output(wb, "export.xls");
    }

    private int addTable(HSSFSheet sheet, int rowNum, ClothesType type, List<List> datas) {
        sheet.createRow(rowNum++).createCell(0).setCellValue(type.getName());
        for (List data : datas) {
            HSSFRow row = sheet.createRow(rowNum++);
            for (int i = 0; i < data.size(); i++) {
                Object obj = data.get(i);
                if (obj instanceof Number) {
                    row.createCell(i).setCellValue(((Number) obj).intValue());
                } else {
                    row.createCell(i).setCellValue(obj.toString());
                }
            }
        }
        return rowNum;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        ItemImporterListener importListener = new ClothesImportListener();
        return importListener;
    }

    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("学号", "student.student.code");
        map.put("服装类别", "type.name");
        map.put("服装尺码", "size.name");
        return map;
    }
}
