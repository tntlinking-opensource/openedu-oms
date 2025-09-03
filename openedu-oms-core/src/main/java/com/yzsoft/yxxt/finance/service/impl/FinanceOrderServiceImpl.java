package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import com.yzsoft.yxxt.finance.service.FinanceOrderService;
import org.beangle.ems.security.User;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FinanceOrderServiceImpl extends EntityDaoSupport implements FinanceOrderService {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

	@Resource
	private StudentService studentService;
	@Resource
	private DictDataService dictDataService;

	@Override
	public List<DictData> findState() {
		List<DictData> states = dictDataService.findDictData(STATE);
		if (states.isEmpty()) {
			String name = "在线支付-支付状态";
			String[] names = new String[]{"未支付", "已支付"};
			states = dictDataService.init(STATE, name, names);
		}
		return states;
	}

	@Override
	public FinanceOrder createOrder(User user, String key, String name, Double money) {
		findState();
		FinanceOrder financeOrder = new FinanceOrder();
		financeOrder.setCode(getCode());
		financeOrder.setUser(user);
		financeOrder.setName(name);
		financeOrder.setMoney(money);
		financeOrder.setCreateTime(new Date());
		financeOrder.setState(dictDataService.getDictData(NOT_PAED));
		financeOrder.setKey(key);
		financeOrder.setFinished(false);
		financeOrder.setEnabled(true);
		Student student = studentService.getStudentByCode(user.getName());
		financeOrder.setStudent(student);
//		entityDao.saveOrUpdate(financeOrder);
		return financeOrder;
	}

	@Override
	public void finishOrder(String code) {
		FinanceOrder financeOrder = entityDao.getEntity(FinanceOrder.class, "code", code);
		if (financeOrder == null || financeOrder.isFinished()) {
			return;
		}
		financeOrder.setFinished(true);
		financeOrder.setFinishTime(new Date());
		financeOrder.setState(dictDataService.getDictData(FINISHED));
		entityDao.saveOrUpdate(financeOrder);
	}

	@Override
	public void cancleOrder(String code) {
		FinanceOrder financeOrder = entityDao.getEntity(FinanceOrder.class, "code", code);
		//订单不存在、已完成、无效时，返回
		if (financeOrder == null || financeOrder.isFinished() || !financeOrder.isEnabled()) {
			return;
		}
		financeOrder.setEnabled(false);
		financeOrder.setState(dictDataService.getDictData(CANCLED));
		entityDao.saveOrUpdate(financeOrder);
	}

	@Override
	public FinanceOrder getOrder(Long userId, String key) {
		OqlBuilder query = OqlBuilder.from(FinanceOrder.class, "o");
		query.where("user.id = :userid", userId);
		query.where("key = :key", key);
		query.where("enabled = true");
		List<FinanceOrder> orders = entityDao.search(query);
		return orders.isEmpty() ? null : orders.get(0);
	}

	private String getCode() {
		Long nextCode = getNextCode();
		return String.format(sdf.format(new Date()) + "%08d", nextCode);
	}

	private Long getNextCode() {
		try {
			String sql = "select SEQ_FINANCE_CODE.nextval from dual";
			List<BigDecimal> list = (List<BigDecimal>) entityDao.searchSqlQuery(sql);
			return list.get(0).longValue();
		} catch (Exception e) {
			String sql = "create sequence SEQ_FINANCE_CODE start with 1 increment by 1";
			entityDao.executeUpdateSql(sql);
			return getNextCode();
		}
	}
}
