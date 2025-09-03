package com.yzsoft.yxxt.web.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Cacheable
@Cache(region = "yxxt.web", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Column extends LongIdObject {

    private String code;
    private Column parent;
    private String name;
    private DictData level;
    private DictData type;
    private Boolean enabled = true;
    private String url;
    @OneToMany(mappedBy = "parent")
    private List<Column> columns;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Column getParent() {
        return parent;
    }

    public void setParent(Column parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DictData getLevel() {
        return level;
    }

    public void setLevel(DictData level) {
        this.level = level;
    }

    public DictData getType() {
        return type;
    }

    public void setType(DictData type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
