package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBuilding;
import com.yzsoft.dorm.model.DormManager;
import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.ItemImporterException;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.utils.DictTypeUtils;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 宿舍楼栋管理员导入监听
 * 
 */
public class BuildingAdminImportListener extends ItemImporterListener {
	private BspBaseService bspBaseService;
	private DictDataService dictDataService;
	private User cUser;
	public BuildingAdminImportListener(EntityDao entityDao,
			BspBaseService bspBaseService,DictDataService dictDataService,User cUser) {
		super();
		this.entityDao = entityDao;
		this.bspBaseService = bspBaseService;
		this.dictDataService = dictDataService;
		this.cUser = cUser;
	}

	/**
	 * 导入模版字段：职工号、姓名、性别、出生日期、家庭地址、联系电话、所管楼栋、备注、红色为必填字段
	 * 导入规则：职工号，导入后默认创建用户并加入的楼栋管理员用户组
	 * @param tr
	 */
	public void onItemFinish(TransferResult tr) {
		DormManager source = (DormManager) importer.getCurrent();
		DormManager dormManager = getObject(source);
		copyPropertiesIgnoreNull(source, dormManager);
		DictData gender = dictDataService.getDictData(DictTypeUtils.GENDER, source.getGender().getName());
		
		dormManager.setGender(gender);
		if (dormManager.getGender() == null) {
			throw new ItemImporterException("性别有误", source.getGender() == null ? "" : source.getGender().getName());
		}
		
		if(null!=source.getDormBuilding()){
			if(StringUtils.isNotEmpty(source.getDormBuilding().getCode())){
				String[] bdCodes = source.getDormBuilding().getCode().split(",");
				List<DormBuilding> buildings = entityDao.get(DormBuilding.class, "code",bdCodes);
				if(null == buildings){
					throw new ItemImporterException("所管楼栋编号有误", source.getDormBuilding() == null ? "" : source.getDormBuilding().getCode());
				}
				//楼栋
				Set<DormBuilding> buildingSet = new HashSet<DormBuilding>(buildings);
				dormManager.setDormBuildings(buildingSet);
			}
		}
		User user = bspBaseService.createUser(dormManager.getCode(), dormManager.getName(), cUser, 3L, "楼栋管理员");
		dormManager.setUser(user);
		saveOrUpdate(dormManager);
	}

	private DormManager getObject(DormManager source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "职工号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "姓名不能为空");
		Assert.isTrue(null != source.getGender() && StringUtils.isNotBlank(source.getGender().getName()), "性别不能为空");
		Assert.isTrue(null != source.getBirthday(), "出生日期不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getAddress()), "家庭地址不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getPhone()), "联系电话不能为空");
//		Assert.isTrue(source.getDormBuilding() != null && StringUtils.isNotBlank(source.getDormBuilding().getCode()), "所管楼栋不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getRemark()), "备注不能为空");
		List<DormManager> bas = 
				entityDao.get(DormManager.class, 
						new String[]{"code"}, new Object[]{source.getCode()});
		if (bas == null || bas.isEmpty()) {
			return new DormManager();
		}
		return bas.get(0);
	}
	
	private DormBuilding getDormBuilding(String code){
		OqlBuilder<DormBuilding> oql = OqlBuilder.from(DormBuilding.class,"db");
		oql.where("db.code =:code",code);
		List<DormBuilding> dbs = entityDao.search(oql);
		if(dbs.size()>0){
			return dbs.get(0);
		}
		return null;
	}
}
