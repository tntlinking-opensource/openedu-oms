package com.yzsoft.yxxt.statistics.service;

public class ConstellationUtil {

	public static String getConstellationByBirthday(String birthday) {
		Integer day = Integer.parseInt(birthday);
		if (321 <= day && day <= 420) {
			return "白羊座";
		} else if (421 <= day && day <= 521) {
			return "金牛座";
		} else if (522 <= day && day <= 621) {
			return "双子座";
		} else if (622 <= day && day <= 722) {
			return "巨蟹座";
		} else if (723 <= day && day <= 822) {
			return "狮子座";
		} else if (823 <= day && day <= 922) {
			return "处女座";
		} else if (923 <= day && day <= 1023) {
			return "天秤座";
		} else if (1024 <= day && day <= 1122) {
			return "天蝎座";
		} else if (1123 <= day && day <= 1221) {
			return "射手座";
		} else if (1222 <= day || day <= 120) {
			return "摩羯座";
		} else if (121 <= day || day <= 219) {
			return "水瓶座";
		} else if (220 <= day && day <= 320) {
			return "双鱼座";
		}
		return null;
	}

	public static String[] getStartAndEndByConstellation(String constellation) {
		if ("白羊座".equals(constellation)) {
			return new String[]{"0321", "0420"};
		} else if ("金牛座".equals(constellation)) {
			return new String[]{"0421", "0521"};
		} else if ("双子座".equals(constellation)) {
			return new String[]{"0522", "0621"};
		} else if ("巨蟹座".equals(constellation)) {
			return new String[]{"0622", "0722"};
		} else if ("狮子座".equals(constellation)) {
			return new String[]{"0722", "0822"};
		} else if ("处女座".equals(constellation)) {
			return new String[]{"0823", "0922"};
		} else if ("天秤座".equals(constellation)) {
			return new String[]{"0923", "1023"};
		} else if ("天蝎座".equals(constellation)) {
			return new String[]{"1024", "1122"};
		} else if ("射手座".equals(constellation)) {
			return new String[]{"1123", "1221"};
		} else if ("摩羯座".equals(constellation)) {
			return new String[]{"1222", "0120"};
		} else if ("水瓶座".equals(constellation)) {
			return new String[]{"0121", "0219"};
		} else if ("双鱼座".equals(constellation)) {
			return new String[]{"0220", "0320"};
		}
		return null;
	}
}
