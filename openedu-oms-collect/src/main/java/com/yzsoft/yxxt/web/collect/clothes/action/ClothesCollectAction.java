package com.yzsoft.yxxt.web.collect.clothes.action;

import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesSize;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudentSize;
import com.yzsoft.yxxt.web.collect.clothes.service.ClothesService;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesCollectAction extends CollectAction {

	@Resource
	private ClothesService clothesService;

	@Override
	public String getCode() {
		return "03_1";
	}

	@Override
	protected Class<?> getEntityClass() {
		return ClothesStudent.class;
	}

	@Override
	public String index() {
		ClothesStudent clothesStudent = getClothesStudent();
		if (clothesStudent.getId() == null) {
			if (getSwitch().isEditable()) {
				return redirect("edit");
			}
		}
		put("clothesStudent", clothesStudent);
		put("types", clothesService.findType());
		return super.index();
	}

	private ClothesStudent getClothesStudent() {
//		return entityDao.getEntity(ClothesStudent.class, "student.user.id", getUserId());
		return clothesService.getClothesStudent(getUserId());
	}

	public String edit() {
		Student student = getStudent();
		ClothesStudent clothesStudent = getClothesStudent();
		if (clothesStudent == null) {
			clothesStudent = new ClothesStudent();
		}
		put("student", student);
		put("clothesStudent", clothesStudent);
		put("types", clothesService.findType());
		return super.edit();
	}

	@Override
	public String save() {
		Student student = getStudent();
//		Assert.isTrue("XLCC_01".equals(student.getEducation().getCode()), "只有本科生可以使用此功能");
		ClothesStudent clothesStudent = getClothesStudent();
		if (clothesStudent == null) {
			clothesStudent = new ClothesStudent();
		}
//		populate(clothesStudent, "clothesStudent");
		Long[] sizeids = getAll("sizeid", Long.class);
		clothesStudent.getSizes().clear();
		List<ClothesSize> sizes = entityDao.get(ClothesSize.class, sizeids);
		String remark = get("remark");
		clothesStudent.setRemark(remark);
		for (ClothesSize size : sizes) {
			ClothesStudentSize ss = new ClothesStudentSize();
			ss.setStudent(clothesStudent);
			ss.setType(size.getType());
			ss.setSize(size);
//			ss.setName(size.getName());
//			ss.setValue(size.getValue());
//			ss.setNum(getInteger("num_" + size.getType().getId()));
//			ss.setNum(1);
//			ss.setPrice(size.getType().getPrice());
//			ss.setMoney(ss.getPrice() * ss.getNum());
			clothesStudent.getSizes().add(ss);
		}
		//不愿意参加时，清空选择
//		Boolean attend = getBoolean("attend");
//		clothesService.setAttend(clothesStudent, attend);
		beforeSave(clothesStudent);
//		clothesService.setMoney(clothesStudent);
		entityDao.saveOrUpdate(clothesStudent);
		afterSave();
		return redirect("index");
//		return nextAction();
	}
}
