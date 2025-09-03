package com.yzsoft.yxxt.web.collect.service;

import com.yzsoft.yxxt.web.collect.model.ClothesSize;
import com.yzsoft.yxxt.web.collect.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.model.ShoesSize;

import java.util.List;

public interface ClothesService {

	List<ClothesSize> findClothesSize();

	List<ShoesSize> findShoesSize();

	ClothesStudent get(Long userId);
}
