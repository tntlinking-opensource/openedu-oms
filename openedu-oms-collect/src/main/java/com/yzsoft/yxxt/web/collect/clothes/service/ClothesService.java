package com.yzsoft.yxxt.web.collect.clothes.service;

import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;

import java.util.List;

public interface ClothesService {


	List<ClothesType> findType();

	ClothesStudent getClothesStudent(Long userId);

}
