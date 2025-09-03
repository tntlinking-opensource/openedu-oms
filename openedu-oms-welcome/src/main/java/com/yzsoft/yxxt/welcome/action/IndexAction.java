package com.yzsoft.yxxt.welcome.action;

import com.yzsoft.yxxt.process.service.YxxtProcessService;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.process.bean.ProcessBatchCount;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.process.service.ProcessLinkService;
import org.beangle.struts2.action.BaseAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends BaseAction {

	@Resource
	private ProcessBatchService processBatchService;

	@Resource
	private YxxtProcessService yxxtProcessService;
	@Resource
	private UserService userService;
	@Resource
	private ProcessLinkService processLinkService;

	public String index() {
		put("batchs", processBatchService.findBatch());
		return forward();
	}

	public String info() {
		Long userid = SecurityUtils.getUserId();
		User user = entityDao.get(User.class, userid);
		Long id = getLong("id");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, id);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		List<ProcessBatchCount> processBatchCounts = processBatchService.countItem(batch);
		Map<String, Long> itemNumMap = new HashMap<String, Long>();
		for (ProcessBatchCount processBatchCount : processBatchCounts) {
			itemNumMap.put(processBatchCount.getItemId().toString(), processBatchCount.getNum());
		}
		Collections.sort(batch.getProcess().getProcessLinkItems(), new MultiPropertyComparator<ProcessLinkItem>("step, px"));
		put("batch", batch);
		put("items", batch.getProcess().getProcessLinkItems());
		put("studentNum", studentNum);
		put("itemNumMap", itemNumMap);
		put("userGroups", userService.getGroups(user, GroupMember.Ship.MEMBER));
		return forward();
	}

	public String deal() {
		Long batchId = getLong("batchId");
		Long itemId = getLong("itemId");
		ProcessLinkItem item = entityDao.get(ProcessLinkItem.class, itemId);
		String code = item.getProcessLink().getCode();
		Class action = processLinkService.getAction(code);
		return redirect(new Action(action, null, "batchId=" + batchId + "&itemId=" + itemId));
	}

}
