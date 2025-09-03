package com.yzsoft.yxxt.process.action;

import org.beangle.process.action.ProcessBatchAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 迎新流程批次
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年12月20日 下午2:34:55
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller
public class EnrollProcessBatchAction extends ProcessBatchAction {

    public String processBatchCheck() {
        return forward();
    }
}
