package com.yzsoft.dorm.system.service;

import com.yzsoft.dorm.model.*;
import com.yzsoft.dorm.model.rule.DormRule;
import com.yzsoft.dorm.std.bean.DormTimeLineYearBean;
import org.beangle.ems.security.User;
import org.beangle.product.core.model.Campus;

import java.util.List;

/**
 * 宿舍服务
 */
public interface DormService {
	
	/**
	 * 是否是宿舍管理员
	 * @param user 用户
	 * @return
	 * @author 周建明
	 * @createDate 2017年2月13日 下午2:33:31
	 */
	boolean isDormAdmin(User user);
	
	/**
	 * 判断是否是辅导员
	 * @param user 用户
	 * @return 
	 * @author 周建明
	 * @createDate 2017年5月5日 上午10:32:07
	 */
	boolean isInstructor(User user);
	
	/**
	 * 退宿
	 * @param studentId
	 */
	void checkOutInterim(Long studentId, DormBed dormBed);

	/**
	 * 临时入住
	 * @param studentId
	 * @param bedId
	 * @param reason
	 */
	void interim(Long studentId, Long bedId, String reason);

	/**
	 * 记录日志
	 * @param studentId
	 */
	void Logging(Long studentId);

	/**
	 * 退宿
	 * @param studentId
	 */
	void checkOut(Long studentId);

	/**
	 * 入住
	 * @param studentId
	 * @param bedId
	 */
	void add(Long studentId, Long bedId);

	/**
	 * 新生入住
	 * @param studentId
	 * @param bedId
	 */
	void check(Long studentId, Long bedId);
	/**
	 * 更换床位
	 *
	 * @param studentId 学生ID
	 * @param newBedId  床位ID
	 */
	void change(Long studentId, Long newBedId, boolean admin);

	/**
	 * 查询学生床位
	 * @param studentId
	 * @return
	 */
	DormBed getBed(Long studentId);

	void save(DormZone dormZone);

	List<Campus> findCampus();

	/**
	 * 根据校区Id获取宿舍区
	 * @param campusId
	 * @return
	 * @author 周建明
	 * @createDate 2017年2月15日 下午1:18:43
	 */
	List<DormZone> findZone(Long campusId);

	List<DormBuilding> findBuilding(Long zoneId);

	List<DormFloor> findFloor(Long buildingId);

	List<String> findGrades();

	List<DormRoom> findRoom(Long floorId);

	List<DormRule> findRule();
	/**
	 * 更换床位
	 *
	 * @param studentId 学生ID
	 * @param newBedId  新床位ID
	 * @param oldBedId  旧床位ID
	 */
	void changeBed(Long studentId, Long newBedId, DormBed oldBed);
	
	/**
	 * 根据楼栋更新楼层数、宿舍数量
	 * @param dormBuilding 楼栋
	 * @author 周建明
	 * @createDate 2017年3月3日 上午10:23:18
	 */
	void updateDormNum(DormBuilding dormBuilding);
	
	/**
	 * 查找所以的楼栋管理员
	 * @return 楼栋管理员集合
	 * @author 周建明
	 * @createDate 2017年3月7日 上午10:40:35
	 */
	List findDormManagerUsers();
	
	/**
	 * 根据学生学号合成宿舍历程
	 * @param stdNo
	 * @return
	 * @author 周建明
	 * @createDate 2017年5月19日 下午1:44:29
	 */
	List<DormTimeLineYearBean> getDormTimeLineYearBeans(String stdNo);
}
