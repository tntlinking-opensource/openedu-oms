package com.yzsoft.yxxt.mobile.action;

import com.yzsoft.yxxt.mobile.service.BarcodeUtil;
import com.yzsoft.yxxt.mobile.service.QrcodeService;
import com.yzsoft.yxxt.process.service.YxxtProcessService;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.model.ProcessLinkItemLog;
import org.beangle.process.service.ProcessLinkLogService;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class WelcomeAction extends BaseAction {

    @Resource
    private ProcessLinkLogService processLinkLogService;
    @Resource
    private StudentService studentService;
    @Resource
    private YxxtProcessService yxxtProcessService;
    @Resource
    private QrcodeService qrcodeService;

    public String index() {
        Student student = studentService.getStudentByCode(SecurityUtils.getUsername());
        ProcessBatch batch = yxxtProcessService.getBatch(student);
        Long studentId = student.getId();
        if (batch != null) {
            Long batchId = batch.getId();
            List<ProcessLinkItemLog> logs = processLinkLogService.findLog(batchId, studentId);
            List<ProcessLinkItem> items = new ArrayList<ProcessLinkItem>(batch.getProcess().getProcessLinkItems());
            Collections.sort(items, new MultiPropertyComparator<ProcessLinkItem>("step, px"));
            put("items", items);
            put("logs", logs);
            put("process", batch.getProcess());
        }
        return forward();
    }

    public String info() {
        Student student = studentService.getStudentByCode(SecurityUtils.getUsername());
        String qrcode = qrcodeService.createQRCode(student.getCardcode());
        String barcode = BarcodeUtil.generateBase64(student.getCardcode());
        put("qrcode", qrcode);
        put("barcode", barcode);
        return forward();
    }
}
