package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.model.StationStudent;
import com.yzsoft.yxxt.web.collect.service.ClothesService;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesAction extends CollectAction {

    @Resource
    private ClothesService clothesService;

    @Override
    public String getCode() {
        return "03";
    }

    @Override
    public String index() {
        ClothesStudent clothesStudent = entityDao.getEntity(ClothesStudent.class, "student.user.id", getUserId());
        if (clothesStudent == null) {
            if (getSwitch().isEditable()) {
                return redirect("edit");
            }
        }
        put("clothesSizes", clothesService.findClothesSize());
        put("shoesSizes", clothesService.findShoesSize());
        put("clothesStudent", clothesStudent);
        return super.index();
    }

    @Override
    public String edit() {
        ClothesStudent clothesStudent = entityDao.getEntity(ClothesStudent.class, "student.user.id", getUserId());
        if (clothesStudent == null) {
            clothesStudent = new ClothesStudent();
        }
        put("clothesSizes", clothesService.findClothesSize());
        put("shoesSizes", clothesService.findShoesSize());
        put("clothesStudent", clothesStudent);
        return super.edit();
    }

    @Override
    public String save() {
        ClothesStudent clothesStudent = entityDao.getEntity(ClothesStudent.class, "student.user.id", getUserId());
        if(clothesStudent == null){
            clothesStudent = new ClothesStudent();
        }
        populate(clothesStudent, "clothesStudent");
        beforeSave(clothesStudent);
        entityDao.saveOrUpdate(clothesStudent);
        afterSave();
        return redirect("index");
    }
}
