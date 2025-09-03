package org.beangle.product.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import org.beangle.ems.config.model.CProperty;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;

/**
 * 学生信息
 */
@Entity
public class StudentInfo extends LongIdObject {
	
	//曾用户
	@Column(length = 60)
	protected String nameUsed;
	//政治面貌
	@JoinColumn(name = "POLITICAL_STATUS_ID")
	private DictData politicalStatus;
	//民族
	private DictData nation;
	//港澳台侨
	private Boolean hmto = false;
	//婚姻状况
	@JoinColumn(name = "MARITAL_STATUS_ID")
	private DictData maritalStatus;
	//健康状况
	private DictData health;
	//外语水平
	@Column(length = 20)
	private String languageLevel;
	//外语语种
	@Column(length = 20)
	private String language;
	//外语水平
	@Column(length = 20)
	private String computerLevel;
	//特长
	@Column(length = 150)
	private String specialSkill;
	//银行卡号
	@Column(length = 20)
	private String bankCard;
	//是否住宿
	private Boolean accommodationed = false;
	//毕业中学
	@Column(length = 50)
	private String school;
    //身高（cm）
    private Integer height;
    //体重（kg）
    private Integer weight;
    //胸围（cm）
    private Integer bust;
    //腰围（cm）
    private Integer waist;
    //臀围（cm）
    private Integer hip;

	/**
	 * 本科毕业证书编号
	 */
	@Column(length = 20)
	@CProperty(label = "本科毕业证书编号")
	private String undergraduateCode;

	/**
	 * 本科学位证书编号
	 */
	@Column(length = 20)
	@CProperty(label = "本科学位证书编号")
	private String degreeCode;

	/**
	 * 一卡通号
	 */
	@Column(length = 20)
	@CProperty(label = "一卡通号")
	private String oneCardCode;

	/**
	 * 一卡通办理状态
	 */
	@CProperty(label = "一卡通办理状态")
	private DictData oneCardStatus;
	/**
	 * 中考分数
	 */
	private Double midExamScore;
	//获奖情况
	@Column(length = 300)
	private String awards;

	public String getNameUsed() {
		return nameUsed;
	}

	public void setNameUsed(String nameUsed) {
		this.nameUsed = nameUsed;
	}

	public DictData getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(DictData politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public DictData getNation() {
		return nation;
	}

	public void setNation(DictData nation) {
		this.nation = nation;
	}

	public Boolean getHmto() {
		return hmto;
	}

	public void setHmto(Boolean hmto) {
		this.hmto = hmto;
	}

	public DictData getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(DictData maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public DictData getHealth() {
		return health;
	}

	public void setHealth(DictData health) {
		this.health = health;
	}

	public String getLanguageLevel() {
		return languageLevel;
	}

	public void setLanguageLevel(String languageLevel) {
		this.languageLevel = languageLevel;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getComputerLevel() {
		return computerLevel;
	}

	public void setComputerLevel(String computerLevel) {
		this.computerLevel = computerLevel;
	}

	public String getSpecialSkill() {
		return specialSkill;
	}

	public void setSpecialSkill(String specialSkill) {
		this.specialSkill = specialSkill;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Boolean getAccommodationed() {
		return accommodationed;
	}

	public void setAccommodationed(Boolean accommodationed) {
		this.accommodationed = accommodationed;
	}

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getBust() {
        return bust;
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public Integer getWaist() {
        return waist;
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    public Integer getHip() {
        return hip;
    }

    public void setHip(Integer hip) {
        this.hip = hip;
    }

	public String getUndergraduateCode() {
		return undergraduateCode;
	}

	public void setUndergraduateCode(String undergraduateCode) {
		this.undergraduateCode = undergraduateCode;
	}

	public String getDegreeCode() {
		return degreeCode;
	}

	public void setDegreeCode(String degreeCode) {
		this.degreeCode = degreeCode;
	}

	public String getOneCardCode() {
		return oneCardCode;
	}

	public void setOneCardCode(String oneCardCode) {
		this.oneCardCode = oneCardCode;
	}

	public DictData getOneCardStatus() {
		return oneCardStatus;
	}

	public void setOneCardStatus(DictData oneCardStatus) {
		this.oneCardStatus = oneCardStatus;
	}

	public Double getMidExamScore() {
		return midExamScore;
	}

	public void setMidExamScore(Double midExamScore) {
		this.midExamScore = midExamScore;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}
}
