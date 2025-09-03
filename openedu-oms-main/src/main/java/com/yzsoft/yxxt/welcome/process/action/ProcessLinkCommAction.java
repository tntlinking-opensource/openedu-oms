package com.yzsoft.yxxt.welcome.process.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.model.DormSetting;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProcessLinkCommAction extends ProcessLinkActionSupport {
	
	@Resource
    private YxxtService yxxtService;
	
	@Override
    protected void save(Long batchId, Student student) {
        super.save(batchId, student);
        synchronized (this) {
        	String xh = "";
        	//生成编号
        	if(StringUtils.isEmpty(student.getCode())){
        		xh = yxxtService.createXh(student);
        	}else{
        		xh = student.getCode();
        		String grade = student.getGrade();
        		String sql = "select max(serial) from cp_students where grade = " + grade;
            	List list = entityDao.searchSqlQuery(sql);
            	//
				//数字id生成规则：年份后两位+0001
            	int i = Integer.parseInt(grade.substring(2,4))*10000;
				//System.out.println(i);
            	if(!CollectionUtils.isEmpty(list)){
            		i = NumberUtils.toInt((String)list.get(0));
            		if(i==0){
            			i = Integer.parseInt(grade.substring(2,4))*10000;
					}
        		}
            	i++;
				//System.out.println(i);
				student.setSerial(i+"");
        	}
//        	student.setCode(xh);
//        	StudentEnroll enroll = (StudentEnroll) entityDao.getEntity(StudentEnroll.class,"student",student);
//        	if(null != enroll){
//        		enroll.setCode(xh);
//        	}
        	
//        	entityDao.saveOrUpdate(enroll,student);
        }
    }
	
	protected void cancel(Long batchId, Student student) {
    	StudentEnroll enroll = (StudentEnroll) entityDao.getEntity(StudentEnroll.class,"student",student);
		student.setSerial("");
//		student.setCode("");
//		enroll.setCode("");
//		entityDao.saveOrUpdate(enroll,student);
	}

}
