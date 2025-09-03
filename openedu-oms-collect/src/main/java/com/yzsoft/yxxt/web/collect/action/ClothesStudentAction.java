package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.service.ClothesService;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesStudentAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;

    @Override
    protected String getEntityName() {
        return ClothesStudent.class.getName();
    }

    @Override
    public String index() {
        put("year", yxxtService.getYear());
        return super.index();
    }

    public String count() {
        put("year", yxxtService.getYear());
        return forward();
    }

    public String report() {
        countClothes();
        countShoes();
        return forward();
    }

    private void countClothes() {
        count("clothes", "clothes_size");
        List<String> names = (List<String>) getRequest().getAttribute("clothesNames");
        final List<String> sizes = new ArrayList<String>(Arrays.asList("XXS", "XS", "S", "M", "L", "XL", "XXL", "XXXL"));
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return sizes.indexOf(o1) - sizes.indexOf(o2);
            }
        });
    }

    private void count(String shortName, String property) {
        String year = get("year");
        if (StringUtils.isBlank(year)) {
            year = yxxtService.getYear().toString();
        }
        SqlBuilder query = new SqlBuilder();
        query.newFrom("YXW_CLOTHES_STUDENTS cs");
        query.where("cs.student_id = s.id");
        query.where("cs.clothes_size is not null");
        query.where("cs.shoes_size is not null");
        query.where("s.grade = :grade", year);
        query.select("cc.name, cs." + property + ", count(*)");
        query.groupBy("cc.name, cs." + property);
        query.orderBy("cc.name, cs." + property);
        query.join("CP_STUDENTS s", "on cs.student_id = s.id");
        String title = CountUtil.where(query);
        List<Object[]> datas = entityDao.search(query);
        List<String> titles = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        for (Object[] data : datas) {
            if (titles.indexOf(data[0]) < 0) {
                titles.add((String) data[0]);
            }
            if (names.indexOf(data[1]) < 0) {
                names.add((String) data[1]);
            }
        }

        put(shortName + "Title", title);
        put(shortName + "Titles", titles);
        put(shortName + "Names", names);
        put(shortName + "Datas", datas);
    }

    private void countShoes() {
        count("shoes", "shoes_size");
    }

    public String print() {
        countClothes();
        countShoes();
        return forward();
    }

    public void exportExcel() {
        countClothes();
        countShoes();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        int rowNum = 0;

        rowNum = addTable(sheet, rowNum, "服装尺码统计", "clothesTitle", "clothesTitles", "clothesNames", "clothesDatas");
        rowNum++;
        rowNum = addTable(sheet, rowNum, "鞋子尺码统计", "shoesTitle", "shoesTitles", "shoesNames", "shoesDatas");

        CountUtil.output(wb, "export.xls");
    }

    private int addTable(HSSFSheet sheet, int rowNum, String tableTitle, String headTitleName, String titlesName, String namesName, String datasName) {
        sheet.createRow(rowNum++).createCell(0).setCellValue(tableTitle);
        HttpServletRequest request = getRequest();
        String headTitle = (String) request.getAttribute(headTitleName);
        HSSFRow headRow = sheet.createRow(rowNum++);
        headRow.createCell(0).setCellValue(headTitle);
        List<String> names = (List<String>) request.getAttribute(namesName);
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            headRow.createCell(i + 1).setCellValue(name);
        }
        List<String> titles = (List<String>) request.getAttribute(titlesName);
        List<Object[]> datas = (List<Object[]>) request.getAttribute(datasName);
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(title);
            for (int j = 0; j < names.size(); j++) {
                String name = names.get(j);
                int num = 0;
                for (Object[] data : datas) {
                    if (data[0].equals(title)
                            && data[1].equals(name)) {
                        num = ((BigDecimal) data[2]).intValue();
                        break;
                    }
                }
                row.createCell(j + 1).setCellValue(num);
            }
        }
        return rowNum;
    }

}
