package com.yzsoft.yxxt.core.service.impl;

import com.yzsoft.yxxt.core.service.YxxtInitService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class YxxtInitServiceImpl extends EntityDaoSupport implements YxxtInitService {

	@Override
	public void init() {
		createViews();
	}

	private void createViews() {
		collectSwitchStateView();
		studentLeaveView();
		studentPrintView();
		welcomeStudentView();
	}

	private void collectSwitchStateView() {
		String sql = "create or replace view yxw_collect_switch_state_views as\n" +
				"select s.id || '_' || cs.id as id,\n" +
				"       css.id state_id,\n" +
				"       s.id student_id,\n" +
				"       cs.id COLLECT_SWITCH_ID\n" +
				"  from yxw_collect_switches cs\n" +
				" cross join cp_students s\n" +
				"  left join YXW_COLLECT_SWITCH_STATES css\n" +
				"    on s.id = css.student_id\n" +
				"   and cs.id = css.collect_switch_id\n" +
				"where cs.enabled = 1";
		entityDao.executeUpdateSql(sql);
	}

	private void studentLeaveView() {
		String sql = " create or replace view YXP_STUDENT_LEAVE_VIEWS as\n" +
				" select S.ID,\n" +
				"        S.ID STUDENT_ID,\n" +
				"        SL.ID LEAVE_ID\n" +
				"   from CP_STUDENTS S\n" +
				"   LEFT JOIN YXP_STUDENT_LEAVES SL\n" +
				"     ON S.ID = SL.STUDENT_ID";
		entityDao.executeUpdateSql(sql);
	}

	private void studentPrintView() {
		String sql = "create or replace view yxp_student_print_views as\n" +
				"select s.id,\n" +
				"       s.id student_id,\n" +
				"       p.id print_id,\n" +
				"       case\n" +
				"         when p.print_notice is null then\n" +
				"          0\n" +
				"         else\n" +
				"          p.print_notice\n" +
				"       end print_notice,\n" +
				"       case\n" +
				"         when p.print_face is null then\n" +
				"          0\n" +
				"         else\n" +
				"          p.print_face\n" +
				"       end print_face\n" +
				"  from cp_students s\n" +
				"  join yxp_student_prepares sp\n" +
				"    on s.code = sp.code\n" +
				"  left join yxp_student_prints p\n" +
				"    on s.id = p.student_id\n" +
				" where sp.enrolled = 1;";
		entityDao.executeUpdateSql(sql);
	}

	private void welcomeStudentView() {

	}
}
