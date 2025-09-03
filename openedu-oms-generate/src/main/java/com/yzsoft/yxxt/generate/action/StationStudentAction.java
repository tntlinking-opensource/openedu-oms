package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.model.StationDate;
import com.yzsoft.yxxt.web.collect.model.StationStudent;
import com.yzsoft.yxxt.web.collect.model.StationTime;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.web.util.GeneratePrint;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationStudentAction extends SyncAction {

    @Resource
    private StationService stationService;

    /**
     * http://127.0.0.1:9000/yxxt/generate/station-student.action
     *
     * @return
     * @throws Exception
     */
    public String index() {

        Long batchId = getLong("batchId");
        ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
        List<Student> students = entityDao.getAll(Student.class);
        Random random = new Random();
        List<DictData> vehicles = dictDataService.findDictData("JTGJ");
        StationDate stationDate = stationService.getDate();
        Date endDate = new Date(stationDate.getStartDate().getTime());
        List<Date> arriveDates = new ArrayList<Date>();
        arriveDates.add(endDate);
        while (endDate.before(stationDate.getEndDate())) {
            endDate = new Date(endDate.getTime() + 24 * 60 * 60 * 1000);
            arriveDates.add(endDate);
        }
        List<String> arriveTimes = new ArrayList<String>();
        for (StationTime time : stationDate.getTimes()) {
            if (StringUtils.isNotEmpty(time.getTime())) {
                arriveTimes.add(time.getTime());
            }
        }
        List<Station> stations = stationService.findStation();

        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        try {
            Assert.notEmpty(arriveDates, "到站日期不能为空");
            for (int i = 0; i < students.size(); i++) {
                if (random.nextInt(10) == 0) {
                    continue;
                }
                Student student = students.get(i);
                StationStudent ss = new StationStudent();
                ss.setStudent(student);
//                ss.setUser(student.getUser());
                ss.setIntime(random.nextBoolean());
                ss.setVehicle(vehicles.get(random.nextInt(vehicles.size())));
                ss.setNeedPickup(random.nextBoolean());
                ss.setArriveDate(arriveDates.get(random.nextInt(arriveDates.size())));
                ss.setArriveTime(arriveTimes.get(random.nextInt(arriveTimes.size())));
                ss.setStation(stations.get(random.nextInt(stations.size())).getName());
                ss.setNum(random.nextInt(3));
                ss.setReason(i + "");
                entityDao.saveOrUpdate(ss);
                print.write(student.getName());
            }
        } catch (Exception e) {

        }
        print.end();
        return null;
    }
}
