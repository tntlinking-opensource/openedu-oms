package com.yzsoft.yxxt.web.ask.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.AskCommon")
public class AskCommon extends LongIdObject {
	//排序
	private Integer sort = 999;
	//咨询内容
	@Column(length = 300)
	private String content;
	//回复内容
	@Column(length = 300)
	private String replyContent;
	//是否置顶
	@Column(name = "is_top")
	private boolean top;
	//是否启用
	private boolean enabled = true;
	//创建时间
	private Date createTime;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
