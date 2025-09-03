package com.yzsoft.yxxt.web.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.common.model.UploadFile;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 信息
 */
@Entity
@Cacheable
@Cache(region = "yxxt.web", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content extends LongIdObject {
    //栏目
    private Column column;
    //标题
    private String title;
    //内容
    @Lob
    @javax.persistence.Column(columnDefinition = "CLOB")
    private String content;
    //发布日期
    @Temporal(TemporalType.DATE)
    private Date publishTime;
    //附件
    @ManyToMany
    private List<UploadFile> files = new ArrayList<UploadFile>();
    //是否有效、发布
    private boolean enabled;
    //是否置顶
    private boolean stick;
    //发布人
    private User user;
    //创建时间
    private Date createTime;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<UploadFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }

    public boolean isStick() {
        return stick;
    }

    public void setStick(boolean stick) {
        this.stick = stick;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
