package org.beangle.website.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 上传文件
 */
@Entity
@Cache(region = "website.common", usage = CacheConcurrencyStrategy.READ_WRITE)
public class UploadFile extends LongIdObject {

	private static final long serialVersionUID = 4145420754034376271L;
	
	/**
	 * 文件名称
	 */
	private String name;
	
	/**
	 * 扩展名
	 */
	@Size(max=30)
	@Column(length=30)
	private String extension;
	
	/**
	 * 文件路径
	 */
	private String path;
	
	/**
	 * UUID
	 */
	private String uuid;
	
	/**
	 * 文件大小
	 */
	@Column(name = "size_")
	private long size;
	
	/**
	 * 是否更新
	 */
	private boolean updated;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public String getExtension() {
		int splitIndex = name.lastIndexOf(".");
        return name.substring(splitIndex + 1);
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * 获取文件名
	 * @return
	 */
	public String getFileName() {
		int splitIndex = name.lastIndexOf(".");
        return name.substring(0, splitIndex);
	}
	
	/**
	 * 获取文件类型
	 * @return
	 */
	public String getFileSufix(){
		int splitIndex = name.lastIndexOf(".");
        return name.substring(splitIndex + 1);
	}
}
