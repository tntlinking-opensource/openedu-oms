package com.yzsoft.dorm.system.service.impl;

import com.yzsoft.dorm.system.service.DormSystemInitService;
import com.yzsoft.dorm.utils.DictDataUtils;
import com.yzsoft.dorm.utils.DictTypeUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宿舍系统参数初始化
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年4月14日 上午11:45:16
 */
@Service
public class DormSystemInitServiceImpl extends BaseServiceImpl implements DormSystemInitService{
	
	@Autowired
	private DictDataService dictDataService;
	
	public void initSystem(){
		//初始字典数据
		//1.宿舍相关数据
		this.initRent();
		this.initFloor();
		this.initFloorStart();
		this.initRoomType();//房间类型（用途）
		this.initRoomSxp();//上下铺
		this.initRoomCpfw();//床铺方位
		this.initBedNum();
		this.initDirection();
		this.initDormChangeType();
	}
	
	/**
	 * 初始化租金
	 * 
	 * @author 周建明
	 * @createDate 2017年4月14日 上午11:38:10
	 */
	public void initRent(){
		List<DictData> rentList = dictDataService.findDictData("DORM_ROOM_RENT");
	    if(rentList.isEmpty()){
	        String code = "DORM_ROOM_RENT";
	        String name = "宿舍房间租金";
	        String[] names = new String[]{"1200"};
	        dictDataService.init(code, name, names);
	    }
	}
	
	/**
	 * 初始楼层
	 * 
	 * @author 周建明
	 * @createDate 2017年4月14日 上午11:39:04
	 */
	public void initFloor(){
		List<DictData> floorList = dictDataService.findDictData("DORM_FLOOR_NUM");
        if(floorList.isEmpty()){
            String code = "DORM_FLOOR_NUM";
            String name = "宿舍楼层数";
            String[] names = new String[]{"1","2","3","4","5","6","7","8","9","10"};
            dictDataService.init(code, name, names);
        }
	}
	
	/**
	 * 初始起始层数
	 * 
	 * @author 周建明
	 * @createDate 2017年4月14日 上午11:40:33
	 */
	public void initFloorStart(){
		 //寝室起始层数
        List<DictData> floorStart = dictDataService.findDictData("DORM_FLOOR_START");
        if(floorStart.isEmpty()){
            String code = "DORM_FLOOR_START";
            String name = "宿舍楼起始楼层数";
            String[] names = new String[]{"1","2","3","4","5","6","7","8","9","10"};
            dictDataService.init(code, name, names);
        }
	}
	
	/**
	 * 初始房间用途
	 * 
	 * @author 周建明
	 * @createDate 2017年4月14日 上午11:44:22
	 */
	public void initRoomType(){
		List<DictData> roomTypes = dictDataService.findDictData(DictTypeUtils.DORM_ROOM_TYPE);
        if(roomTypes.isEmpty()){
            String code = DictTypeUtils.DORM_ROOM_TYPE;
            String name = "宿舍-房间用途";
            String[] names = new String[]{"寝室","活动室","其他"};
            dictDataService.init(code, name, names);
        }
	}
	
	/**
	 * 宿舍上下铺
	 * 
	 * @author 周建明
	 * @createDate 2017年4月14日 上午11:48:46
	 */
	public void initRoomSxp(){
		List<DictData> sxps = dictDataService.findDictData(DictTypeUtils.CW_SXP);
        if(sxps.isEmpty()){
            String code = DictTypeUtils.CW_SXP;
            String name = "宿舍-上/下铺";
            String[] names = new String[]{"上铺","下铺"};
            dictDataService.init(code, name, names);
        }
	}
	
	public void initRoomCpfw(){
		List<DictData> sxps = dictDataService.findDictData(DictTypeUtils.CW_CPFW);
        if(sxps.isEmpty()){
            String code = DictTypeUtils.CW_CPFW;
            String name = "宿舍-床铺方位";
            String[] codes = new String[]{"CW_CPFW_01_KC","CW_CPFW_02_KM"};
            String[] names = new String[]{"靠窗","靠门"};
            dictDataService.init(code, name,codes,names);
        }
	}
	
	/**
	 * 宿舍床位数
	 * 
	 * @author 周建明
	 * @createDate 2017年4月24日 下午4:53:21
	 */
	public void initBedNum(){
		List<DictData> sxps = dictDataService.findDictData(DictTypeUtils.DORM_ROOM_BED_NUM);
        if(sxps.isEmpty()){
            String code = DictTypeUtils.DORM_ROOM_BED_NUM;
            String name = "宿舍-床位数";
            String[] codes = new String[]{"DORM_ROOM_BED_NUM_04","DORM_ROOM_BED_NUM_06"};
            String[] names = new String[]{"4","6"};
            dictDataService.init(code, name,codes,names);
        }
	}
	
	/**
	 * 宿舍朝向
	 * 
	 * @author 周建明
	 * @createDate 2017年4月24日 下午4:57:51
	 */
	public void initDirection(){
		List<DictData> sxps = dictDataService.findDictData(DictTypeUtils.DORM_ROOM_DIRECTION);
        if(sxps.isEmpty()){
            String code = DictTypeUtils.DORM_ROOM_DIRECTION;
            String name = "宿舍-朝向";
            String[] names = new String[]{"朝南","朝北"};
            dictDataService.init(code, name, names);
        }
	}
	
	
	public void initDormChangeType(){
		List<DictData> slbdlxs = dictDataService.findDictData(DictTypeUtils.DORM_CHANGE_TYPE);
        if(slbdlxs.isEmpty()){
            String code = DictTypeUtils.DORM_CHANGE_TYPE;
            String name = "宿舍-变动类型";
            String[] codes = new String[]{DictDataUtils.DORM_CHANGE_TYPE_XSFP,DictDataUtils.DORM_CHANGE_TYPE_SGTZ,
            		DictDataUtils.DORM_CHANGE_TYPE_BGSQ,DictDataUtils.DORM_CHANGE_TYPE_TS,DictDataUtils.DORM_CHANGE_TYPE_LX};
            String[] names = new String[]{"新生分配","宿管调整","变更申请","退宿"};
            dictDataService.init(code, name,codes,names);
        }
	}
}
