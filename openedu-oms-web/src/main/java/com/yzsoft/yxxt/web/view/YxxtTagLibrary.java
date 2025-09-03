package com.yzsoft.yxxt.web.view;

import com.opensymphony.xwork2.util.ValueStack;
import org.beangle.struts2.view.AbstractTagLibrary;
import org.beangle.struts2.view.TagModel;
import org.beangle.struts2.view.template.Theme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class YxxtTagLibrary extends AbstractTagLibrary {

    public YxxtTagLibrary() {
        super();
    }

    public YxxtTagLibrary(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
        theme = new Theme("yxxt");//使用的主题
    }

    @Override
    public Object getModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        YxxtTagLibrary models = new YxxtTagLibrary(stack, req, res);
        return models;
    }

    public TagModel getHead() {
        return get(Head.class);
    }

    public TagModel getFoot() {
        return get(Foot.class);
    }

    public TagModel getColumnLeft() {
        return get(ColumnLeft.class);
    }

}
