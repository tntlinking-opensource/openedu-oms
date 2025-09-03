package com.yzsoft.yxxt.web.collect.clothes.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClothesType extends LongIdObject {

	//代码
	private String code;
	//名称
	private String name;
	//图片
	private String img;
	//材质
	private String mate;
	//价格
	private Double price;
	//购买数量限制
	private Integer numLimit;
	//尺码单位：脚长、鞋码等
	private String sizeName;
	//属性名
	private String propName;
	//尺码
	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("code")
	private List<ClothesSize> sizes = new ArrayList<ClothesSize>();

	private Boolean enabled = true;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getMate() {
		return mate;
	}

	public void setMate(String mate) {
		this.mate = mate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumLimit() {
		return numLimit;
	}

	public void setNumLimit(Integer numLimit) {
		this.numLimit = numLimit;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public List<ClothesSize> getSizes() {
		return sizes;
	}

	public void setSizes(List<ClothesSize> sizes) {
		this.sizes = sizes;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public List<String> getColTitle() {
//		List<String> titles = new ArrayList<String>();
//		if (isSingleSizeName()) {
//			titles.add(sizeName);
//			titles.add("尺码");
//		} else {
//			titles.add("");
//			for (ClothesSize size : sizes) {
//				String ctitle = size.getValue().split(" ")[0];
//				if (!titles.contains(ctitle)) {
//					titles.add(ctitle);
//				}
//			}
//		}
		return Arrays.asList(sizeName.split(" "));
	}

	public boolean isSingleSizeName() {
		return sizeName.indexOf(" ") < 0;
	}

	public List<String> getRowTitle() {
		List<String> titles = new ArrayList<String>();
		if (isSingleSizeName()) {
			for (ClothesSize size : sizes) {
				titles.add(size.getValue());
			}
		} else {
			for (ClothesSize size : sizes) {
				if (size.getValue().indexOf(" ") < 0) {
					continue;
				}
				String rtitle = size.getValue().split(" ")[1];
				if (!titles.contains(rtitle)) {
					titles.add(rtitle);
				}
			}
		}
		return titles;
	}

	public ClothesSize getSize(String ctitle, String rtitle) {
		if (isSingleSizeName()) {
			for (ClothesSize size : sizes) {
				if (size.getValue().equals(rtitle)) {
					return size;
				}
			}
		} else {
			String value = ctitle + " " + rtitle;
			for (ClothesSize size : sizes) {
				if (size.getValue().equals(value)) {
					return size;
				}
			}
		}
		return null;
	}
}
