package org.beangle.website.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.beangle.ems.config.model.CProperty;
import org.beangle.ems.config.model.CPropertys;
import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Campus;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Direction;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.StudentFamily;
import org.beangle.product.core.model.StudentHome;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.product.core.model.StudentOther;
import org.beangle.website.system.model.DictData;
import org.beangle.workflow3.model.Ywfj;

import com.yzsoft.yxxt.prepare.model.StudentContact;

/**
 * 学生信息
 *
 * @作者：周建明
 * @创建日期：2016年9月14日 上午10:48:36
 */
@Entity
@CPropertys(name = "学生信息")
public class Student extends LongIdObject {
	
	/**
	 * 数字ID、编号、序号
	 */
    @Size(max = 32)
    @CProperty(label = "编号", listable = true, required = true)
	protected String serial;
	
    /**
     * 学号/学籍号
     */
    @Column(unique = true, length = 32)
    @Size(max = 32)
    @CProperty(label = "学籍号", listable = true, required = true)
    protected String code;

    /**
     * 录取通知书号
     */
    @Column(unique = true, length = 32)
    @Size(max = 32)
    @CProperty(label = "录取通知书号", listable = true)
    protected String letterCode;

    /**
     * 姓名
     */
    @Column(length = 60)
    @CProperty(label = "姓名", listable = true, required = true)
    protected String name;

    /**
     * 姓名拼音
     */
    @Column(length = 300)
    @CProperty(label = "姓名拼音")
    protected String namePinyin;

    /**
     * 英文姓名
     */
    @Column(length = 60)
    @CProperty(label = "英文姓名")
    protected String nameEn;

    /**
     * 性别
     */
    @CProperty(label = "性别", listable = true)
    private DictData gender;

    /**
     * 照片
     */
    @Column(length = 50)
    private String photo;

    /**
     * 学历层次
     */
    @CProperty(label = "学历层次", listable = true)
    private DictData education;

    /**
     * 学历类型/学位类型
     */
    @CProperty(label = "学历类型")
    private DictData degreeType;

    /**
     * 毕业年份
     */
    @CProperty(label = "毕业年份")
    protected Integer graduateYear;

    /**
     * 校区
     */
    @CProperty(label = "校区", listable = false)
    protected Campus campus;

    /**
     * 学院
     */
    @CProperty(label = "学院", listable = true)
    protected Department department;

    /**
     * 所属专业
     */
    @CProperty(label = "专业", listable = true)
    protected Major major;

    /**
     * 所属专业方向
     */
    @CProperty(label = "专业方向")
    protected Direction direction;

    /**
     * 年级
     */
    @Column(length = 10)
    @CProperty(label = "年级")
    protected String grade;

    /**
     * 学制
     */
    @CProperty(label = "学制")
    private Float duration;

    /**
     * 班级
     */
    @CProperty(label = "行政班")
    protected AdminClass adminClass;

    /**
     * 出生日期
     */
    @Temporal(TemporalType.DATE)
    @CProperty(label = "出生日期")
    protected Date birthday;
    
    /**
     * 附件
     */
    @CProperty(label = "业务附件")
    @ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="STU__INFO")
    protected Set<Ywfj> ywfj = new HashSet<Ywfj>();
    
    /**
     * 入学日期
     */
    @Temporal(TemporalType.DATE)
    @CProperty(label = "入学日期")
    protected Date enrollmentDate;

    /**
     * 培养方式/培养类型
     */
    @CProperty(label = "培养方式")
    protected DictData trainType;

    /**
     * 学生类别
     */
    @CProperty(label = "学生类别")
    protected DictData studentType;


    /**
     * 离校日期
     */
    @Temporal(TemporalType.DATE)
    @CProperty(label = "离校日期")
    protected Date leaveDate;

    /**
     * 籍贯
     * @See StudentHome.nativePlace
     */
//    @Column(length = 30)
//    @CProperty(label = "籍贯")
//    protected String nativePlace;

    /**
     * 国家/地区
     */
    @Column(length = 30)
    @CProperty(label = "国家/地区")
    protected String country;

    /**
     * 所属警署
     * @See StudentHome.domicilePlace
     */
//    @Column(length = 300)
//    @CProperty(label = "所属警署")
//    protected String policeStation;

    /**
     * 证件类型
     */
    @Column(length = 30)
    @CProperty(label = "证件类型")
    protected String cardType;

    /**
     * 证件号
     */
    @Column(length = 30)
    @CProperty(label = "证件号")
    protected String cardcode;

    /**
     * 本科毕业证书编号
     * @See StudentInfo.undergraduateCode
     */
//    @Column(length = 20)
//    @CProperty(label = "本科毕业证书编号")
//    protected String undergraduateCode;

    /**
     * 本科学位证书编号
     * @See StudentInfo.degreeCode
     */
//    @Column(length = 20)
//    @CProperty(label = "本科学位证书编号")
//    protected String degreeCode;

    /**
     * 一卡通号
     * @See StudentInfo.oneCardCode
     */
//    @Column(length = 20)
//    @CProperty(label = "一卡通号")
//    protected String oneCardCode;

    /**
     * 一卡通办理状态
     * @See StudentInfo.oneCardStatus
     */
//    @CProperty(label = "一卡通办理状态")
//    protected DictData oneCardStatus;


    /**
     * 身高
     * @See StudentInfo.height
     */
//    @Column(length = 10)
//    @CProperty(label = "身高")
//    protected String height;

    /**
     * 体重
     * @See StudentInfo.weight
     */
//    @Column(length = 10)
//    @CProperty(label = "体重")
//    protected String weight;

    /**
     * 备注
     */
    @Column(length = 900)
    @CProperty(label = "备注")
    protected String remark;

    /**
     * 手机号码
     */
    @Column(length = 30)
    @CProperty(label = "手机号码")
    protected String phone;
    /**
     * 联系电话
     */
    @Column(length = 30)
    @CProperty(label = "联系电话")
    protected String telephone;

    /**
     * QQ号码
     * @See StudentContact.qq
     */
//    @Column(length = 30)
//    @CProperty(label = "QQ号码")
//    protected String qq;
    /**
     * QQ号码
     * @See StudentContact.wechat
     */
//    @Column(length = 30)
//    @CProperty(label = "微信号")
//    protected String wechat;

    /**
     * 电子邮箱
     */
    @Column(length = 60)
    @CProperty(label = "电子邮箱")
    protected String email;
    
    /**
     * 是否已报道
     */
    @Column(length = 60)
    @CProperty(label = "是否已报道")
    protected String ifBaoDao;
    

    /**
     * 通讯地址-省份/自治区
     * @See StudentContact.province
     */
//    @CProperty(label = "通讯地址-省市")
//    private DictData province;
    /**
     * 通讯地址-城市/地区
     * @See StudentContact.city
     */
//    @CProperty(label = "通讯地址-地级市")
//    private DictData city;
    /**
     * 通讯地址-区/县
     * @See StudentContact.county
     */
//    @CProperty(label = "通讯地址-区县")
//    private DictData county;
    /**
     * 通讯地址-街道
     *
     * @See StudentContact.address
     */
//    @Column(length = 300)
//    private String address;

    /**
     * 家庭住址
     * @See StudentHome.address
     */
//    @Deprecated
//    @Column(length = 100)
//    @CProperty(label = "家庭住址")
//    protected String homeAddress;

    /**
     * 家庭邮编
     * @See StudentHome.zipcode
     */
//    @Deprecated
//    @Column(length = 10)
//    @CProperty(label = "家庭邮编")
//    protected String postcode;

    /**
     * 家庭电话
     * @See StudentHome.phone
     */
//    @Deprecated
//    @Column(length = 20)
//    @CProperty(label = "家庭电话")
//    protected String homePhone;

    /**
     * 户口所在地
     * @See StudentHome.domicilePlace
     */
//    @Deprecated
//    @Column(length = 100)
//    @CProperty(label = "户口所在地")
//    protected String domicilePlace;

    /**
     * 生源地
     */
    @Column(length = 100)
    @CProperty(label = "生源地")
    protected String enrollSource;

    /**
     * 生源地-省份/自治区
     */
    @CProperty(label = "生源地-省市")
    private DictData enrollProvince;

    /**
     * 生源地-城市/地区
     */
    @CProperty(label = "生源地-地级市")
    private DictData enrollCity;
    /**
     * 生源地-区县
     */
    private DictData enrollCounty;

    /**
     * 入学方式/录取类别
     */
    @CProperty(label = "入学方式")
    protected DictData enrollType;

    /**
     * 生源性质
     */
    protected DictData enrollSourceType;

    /**
     * 毕业学校
     */
    @Column(length = 100)
    @CProperty(label = "毕业学校")
    protected String graduateSchool;

    /**
     * 学籍状态
     */
    @CProperty(label = "学籍状态")
    protected DictData schoolState;

    /**
     * 是否有学籍
     */
    @Column(length = 10)
    @CProperty(label = "是否有学籍")
    protected boolean registed = true;

    /**
     * 是否在校
     */
    @CProperty(label = "是否在校")
    protected boolean inSchooled = true;

    /**
     * 用户
     */
    protected User user;
    //    双向关联会很麻烦
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//  @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private StudentContact contact;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentHome home;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentInfo info;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentOther other;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sort")
    private List<StudentFamily> families = new ArrayList<StudentFamily>();
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentContact> contacts = new ArrayList<StudentContact>();
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentHome> homes = new ArrayList<StudentHome>();
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentInfo> infos = new ArrayList<StudentInfo>();
//    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentOther> others = new ArrayList<StudentOther>();
    
    private UploadFile stdInfoFile;

    public Student() {
    }

    public Student(Long id) {
        super(id);
    }

    public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public DictData getGender() {
        return gender;
    }

    public void setGender(DictData gender) {
        this.gender = gender;
    }

    public DictData getEducation() {
        return education;
    }

    public void setEducation(DictData education) {
        this.education = education;
    }

    public Integer getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(Integer graduateYear) {
        this.graduateYear = graduateYear;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public AdminClass getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(AdminClass adminClass) {
        this.adminClass = adminClass;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public DictData getEnrollType() {
        return enrollType;
    }

    public void setEnrollType(DictData enrollType) {
        this.enrollType = enrollType;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public DictData getTrainType() {
        return trainType;
    }

    public void setTrainType(DictData trainType) {
        this.trainType = trainType;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

//    public String getNativePlace() {
//        return nativePlace;
//    }
//
//    public void setNativePlace(String nativePlace) {
//        this.nativePlace = nativePlace;
//    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getQq() {
//        return qq;
//    }
//
//    public void setQq(String qq) {
//        this.qq = qq;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getHomeAddress() {
//        return homeAddress;
//    }
//
//    public void setHomeAddress(String homeAddress) {
//        this.homeAddress = homeAddress;
//    }

//    public String getPostcode() {
//        return postcode;
//    }
//
//    public void setPostcode(String postcode) {
//        this.postcode = postcode;
//    }
//
//    public String getHomePhone() {
//        return homePhone;
//    }
//
//    public void setHomePhone(String homePhone) {
//        this.homePhone = homePhone;
//    }
//
//    public String getDomicilePlace() {
//        return domicilePlace;
//    }
//
//    public void setDomicilePlace(String domicilePlace) {
//        this.domicilePlace = domicilePlace;
//    }

    public String getEnrollSource() {
        return enrollSource;
    }

    public void setEnrollSource(String enrollSource) {
        this.enrollSource = enrollSource;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }


    public boolean isRegisted() {
        return registed;
    }

    public void setRegisted(boolean registed) {
        this.registed = registed;
    }

    public boolean isInSchooled() {
        return inSchooled;
    }

    public void setInSchooled(boolean inSchooled) {
        this.inSchooled = inSchooled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public DictData getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DictData degreeType) {
        this.degreeType = degreeType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

//    public String getWechat() {
//        return wechat;
//    }
//
//    public void setWechat(String wechat) {
//        this.wechat = wechat;
//    }


    public StudentContact getContact() {
        return contact == null ? new StudentContact() : contact;
    }

    public void setContact(StudentContact contact) {
        this.contact = contact;
    }

    public StudentHome getHome() {
        return home == null ? new StudentHome() : home;
    }

    public void setHome(StudentHome home) {
        this.home = home;
    }

    public StudentInfo getInfo() {
        return info == null ? new StudentInfo() : info;
    }

    public void setInfo(StudentInfo info) {
        this.info = info;
    }

    public StudentOther getOther() {
        return other == null ? new StudentOther() : other;
    }

    public void setOther(StudentOther other) {
        this.other = other;
    }

    public List<StudentFamily> getFamilies() {
        return families;
    }

    public void setFamilies(List<StudentFamily> families) {
        this.families = families;
    }

    public String getLetterCode() {
        return letterCode;
    }

    public void setLetterCode(String letterCode) {
        this.letterCode = letterCode;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public DictData getStudentType() {
        return studentType;
    }

    public void setStudentType(DictData studentType) {
        this.studentType = studentType;
    }

//    public String getPoliceStation() {
//        return policeStation;
//    }
//
//    public void setPoliceStation(String policeStation) {
//        this.policeStation = policeStation;
//    }

//    public String getUndergraduateCode() {
//        return undergraduateCode;
//    }
//
//    public void setUndergraduateCode(String undergraduateCode) {
//        this.undergraduateCode = undergraduateCode;
//    }
//
//    public String getDegreeCode() {
//        return degreeCode;
//    }
//
//    public void setDegreeCode(String degreeCode) {
//        this.degreeCode = degreeCode;
//    }
//
//    public String getOneCardCode() {
//        return oneCardCode;
//    }
//
//    public void setOneCardCode(String oneCardCode) {
//        this.oneCardCode = oneCardCode;
//    }
//
//    public DictData getOneCardStatus() {
//        return oneCardStatus;
//    }
//
//    public void setOneCardStatus(DictData oneCardStatus) {
//        this.oneCardStatus = oneCardStatus;
//    }

//    public String getHeight() {
//        return height;
//    }
//
//    public void setHeight(String height) {
//        this.height = height;
//    }
//
//    public String getWeight() {
//        return weight;
//    }
//
//    public void setWeight(String weight) {
//        this.weight = weight;
//    }
//
//    public DictData getProvince() {
//        return province;
//    }
//
//    public void setProvince(DictData province) {
//        this.province = province;
//    }
//
//    public DictData getCity() {
//        return city;
//    }
//
//    public void setCity(DictData city) {
//        this.city = city;
//    }
//
//    public DictData getCounty() {
//        return county;
//    }
//
//    public void setCounty(DictData county) {
//        this.county = county;
//    }

//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public DictData getEnrollProvince() {
        return enrollProvince;
    }

    public void setEnrollProvince(DictData enrollProvince) {
        this.enrollProvince = enrollProvince;
    }

    public DictData getEnrollCity() {
        return enrollCity;
    }

    public void setEnrollCity(DictData enrollCity) {
        this.enrollCity = enrollCity;
    }

    public DictData getEnrollCounty() {
        return enrollCounty;
    }

    public void setEnrollCounty(DictData enrollCounty) {
        this.enrollCounty = enrollCounty;
    }

    public DictData getSchoolState() {
        return schoolState;
    }

    public void setSchoolState(DictData schoolState) {
        this.schoolState = schoolState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInSchooledStr() {
        return inSchooled ? "在校" : "离校";
    }

    public DictData getEnrollSourceType() {
        return enrollSourceType;
    }

    public void setEnrollSourceType(DictData enrollSourceType) {
        this.enrollSourceType = enrollSourceType;
    }

	public String getIfBaoDao() {
		return ifBaoDao;
	}

	public void setIfBaoDao(String ifBaoDao) {
		this.ifBaoDao = ifBaoDao;
	}

	public Set<Ywfj> getYwfj() {
		return ywfj;
	}

	public void setYwfj(Set<Ywfj> ywfj) {
		this.ywfj = ywfj;
	}

	public UploadFile getStdInfoFile() {
		return stdInfoFile;
	}

	public void setStdInfoFile(UploadFile stdInfoFile) {
		this.stdInfoFile = stdInfoFile;
	}
	
}
