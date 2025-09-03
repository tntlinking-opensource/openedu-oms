package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.model.StationDate;
import com.yzsoft.yxxt.web.collect.model.StationStudent;
import com.yzsoft.yxxt.web.collect.model.StationTime;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationStudentAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private StudentService studentService;
    @Resource
    private StationService stationService;

    @Override
    protected String getEntityName() {
        return StationStudent.class.getName();
    }

    @Override
    public String index() {
        put("grade", yxxtService.getGrade());
        return super.index();
    }

    public String nav() {
        List<String> grades = studentService.findGrade();
        Collections.reverse(grades);
        put("grades", grades);
        return forward();
    }

    public String count() {
        String grade = get("grade");
        countByIntime(grade);
        countByStation(grade);
        return forward();
    }

    private List<Long> getEducationIds(ProcessBatch batch) {
        List<Long> ids = new ArrayList<Long>();
        for (DictData dictData : batch.getEducations()) {
            ids.add(dictData.getId());
        }
        return ids;
    }

    private void countByIntime(String grade) {
        String sql = "select * from (\n" +
                "select case ss.intime when 1 then 0 when 0 then 1 else 2 end sort, \n" +
                "case ss.intime when 1 then '按时报到' when 0 then '不按时报到' else '未知' end name, \n" +
                "count(*) num\n" +
                "from cp_students s \n" +
                "left join yxw_station_students ss on s.id = ss.student_id\n" +
                "where s.grade = :grade\n" +
                "group by ss.intime) \n" +
                "order by sort";
        SqlBuilder query = SqlBuilder.sql(sql);
        query.param("grade", grade);
        List datas = entityDao.search(query);
        Integer intimeTotal = 0;
        for (Object o : datas) {
            Object[] oo = (Object[]) o;
            intimeTotal += ((Number) oo[2]).intValue();
        }
        put("intimeDatas", datas);
        put("intimeTotal", intimeTotal);
    }

    private void countByStation(String grade) {
        List<Station> stations = stationService.findStation();
        StationDate stationDate = stationService.getDate();
        Date endDate = new Date(stationDate.getStartDate().getTime());
        List<Date> arriveDates = new ArrayList<Date>();
        arriveDates.add(endDate);
        while (endDate.before(stationDate.getEndDate())) {
            endDate = new Date(endDate.getTime() + 24 * 60 * 60 * 1000);
            arriveDates.add(endDate);
        }
        String sql = "select ss.station, ss.arrive_date, ss.arrive_time, count(*) num\n" +
                "  from yxw_station_students ss\n" +
                "  join cp_students s\n" +
                "    on ss.student_id = s.id\n" +
                " where ss.intime = 1\n" +
                "   and ss.station is not null\n" +
                "   and ss.arrive_time is not null\n" +
                "   and ss.need_pickup = 1\n" +
                "   and s.grade = :grade\n" +
                " group by ss.station, ss.arrive_time, ss.arrive_date\n" +
                " order by ss.station, ss.arrive_time, ss.arrive_date\n";

        SqlBuilder query = SqlBuilder.sql(sql);
        query.param("grade", grade);
        List datas = entityDao.search(query);
        put("stationDatas", datas);
        put("stations", stations);
        put("arriveDates", arriveDates);
        put("stationDate", stationDate);
    }

    public String print() {
        count();
        return forward();
    }

    public void exportExcel() {
        count();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        int rowNum = 0;
        sheet.createRow(rowNum++).createCell(0).setCellValue("按时报到统计");
        String[] titles = new String[]{"是否按时报到", "学生人数", "占比"};
        HSSFRow titleRow = sheet.createRow(rowNum++);
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            HSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(title);
        }
        List<Object[]> intimeDatas = (List<Object[]>) getRequest().getAttribute("intimeDatas");
        Integer intimeTotal = (Integer) getRequest().getAttribute("intimeTotal");
        for (Object[] intimeData : intimeDatas) {
            HSSFRow row = sheet.createRow(rowNum++);
            for (int i = 1; i <= 3; i++) {
                HSSFCell cell = row.createCell(i - 1);
                if (i == 1) {
                    cell.setCellValue(intimeData[i].toString());
                } else if (i == 2) {
                    cell.setCellValue(((BigDecimal) (intimeData[2])).longValue());
                } else {
                    if (intimeTotal > 0) {
                        cell.setCellValue(String.format("%.2f%%", ((BigDecimal) (intimeData[2])).longValue() * 100.0 / intimeTotal));
                    }
                }
            }
        }

        rowNum++;
        sheet.createRow(rowNum++).createCell(0).setCellValue("到站时间统计");
        List<Station> stations = (List<Station>) getRequest().getAttribute("stations");
        List<Date> arriveDates = (List<Date>) getRequest().getAttribute("arriveDates");
        StationDate stationDate = (StationDate) getRequest().getAttribute("stationDate");
        List<Object[]> stationDatas = (List<Object[]>) getRequest().getAttribute("stationDatas");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        for (Station station : stations) {
            rowNum++;
            sheet.createRow(rowNum++).createCell(0).setCellValue(station.getName());
            HSSFRow dateTitleRow = sheet.createRow(rowNum++);
            dateTitleRow.createCell(0).setCellValue("时间段\\日期");
            for (int i = 0; i < arriveDates.size(); i++) {
                Date arriveDate = arriveDates.get(i);
                HSSFCell cell = dateTitleRow.createCell(i + 1);
                cell.setCellValue(sdf.format(arriveDate));
            }
            for (StationTime time : stationDate.getTimes()) {
                if (StringUtils.isBlank(time.getTime())) {
                    continue;
                }
                HSSFRow timeRow = sheet.createRow(rowNum++);
                timeRow.createCell(0).setCellValue(time.getTime());
                for (int i = 0; i < arriveDates.size(); i++) {
                    Date arriveDate = arriveDates.get(i);
                    int num = 0;
                    for (Object[] data : stationDatas) {
                        if (data[0].equals(station.getName())
                                && sdf.format(data[1]).equals(sdf.format(arriveDate))
                                && time.getTime().equals(data[2])) {
                            num = ((BigDecimal) data[3]).intValue();
                            break;
                        }
                    }
                    timeRow.createCell(i + 1).setCellValue(num);
                }
            }
        }

        CountUtil.output(wb, "export.xls");
    }
}
