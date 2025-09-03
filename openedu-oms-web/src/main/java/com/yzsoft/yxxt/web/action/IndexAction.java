package com.yzsoft.yxxt.web.action;

import com.octo.captcha.service.CaptchaService;
import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;
import com.yzsoft.yxxt.web.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.emsapp.portal.service.LoginCache;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.security.auth.LoginService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends IndexParentAction {


}
