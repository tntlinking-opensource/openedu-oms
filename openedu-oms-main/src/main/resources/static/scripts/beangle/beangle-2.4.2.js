// core beangle
(function (window, undefined) {
    if (beangle)
        return;
    var beangle = function () {
        return true;
    };
    /** extend function */
    beangle.extend = function (map) {
        for (attr in map) {
            var attrs = attr.split("."), obj = beangle, i;
            for (i = 0; i < attrs.length - 1; i++) {
                obj[attrs[i]] = obj[attrs[i]] || {};
                obj = obj[attrs[i]];
            }
            obj[attrs[attrs.length - 1]] = map[attr];
        }
    }
    window.beangle = beangle;
    window.bg = beangle;
    beangle.extend({
        // jump to href or anchor
        Go: function (obj, target, ajaxHistory) {
            var url = obj;
            if (typeof obj == "object" && obj.tagName.toLowerCase() == "a") {
                url = obj.href;
                if (!target) {
                    target = bg.findTarget(obj);
                }
                var a = $(obj);
                if (a.attr("anchor")) {
                    var iurl = location.href;
                    if (iurl.indexOf("#") > 0) {
                        iurl = iurl.substring(0, iurl.indexOf("#") - 1);
                    }
                    location = iurl + "#" + a.attr("anchor");
                }
            }
            if (!target)
                target = "_self";
            if ("_self" == target) {
                self.location = url;
            } else if ("_parent" == target) {
                self.parent.location = url;
            } else if ("_top" == target) {
                self.top.location = url;
            } else if ("_new" == target || "_blank" == target) {
                window.open(url);
            } else {
                $('#' + target).attr("url", url);
                if (!bg.isAjaxTarget(target)) {
                    if (document.getElementById(target))
                        document.getElementById(target).src = url;
                } else {
                    if (!ajaxHistory) {
                        jQuery('#' + target).load(url);
                    } else {
                        beangle.history.historyGo(url, target);
                    }
                }
            }
            //window.top.bg.goToTop();
            return false;
        },
        getContextPath: function () {
            if (contextPath != undefined) {
                return contextPath;
            } else {
                return self.location.pathname.substring(0,
                    self.location.pathname.substring(1).indexOf('/') + 1);
            }
        },
        ready: function (fn) {
            if (window.addEventListener) {
                window.addEventListener("load", fn, false);
            } else if (window.attachEvent) {
                window.attachEvent("onload", fn);
            } else {
                window.onload = fn;
            }
        },
        isAjaxTarget: function (target) {
            if (!target)
                return false;
            if (target == "" || target == "_new" || target == "_blank"
                || target == "_self" || target == "_parent"
                || target == "_top") {
                return false;
            }
            targetEle = document.getElementById(target);
            if (!targetEle)
                return false;
            tagName = targetEle.tagName.toLowerCase();
            if (tagName == "iframe" || tagName == "object") {
                return false;
            }
            return true;
        },
        normalTarget: function (target) {
            if (target == "" || target == "new" || target == "_blank"
                || target == "_self" || target == "_parent") {
                return target;
            }
            var targetObj = document.getElementById(target);
            if (!targetObj || targetObj.tagName.toLowerCase() != "iframe")
                return "_self";
            else
                return target;
        },
        findTarget: function (ele) {
            if (!ele)
                return "_self";
            var p = ele.parentNode, finalTarget = "_self";
            while (p) {
                // FIXME ui-tabs-panel
                if (p.id && p.className
                    && (p.className.indexOf("_ajax_target") > -1)) {// ||p.className.indexOf("ui-tabs-panel")>-1   忽略列表Div&& (p.className.indexOf("dataList") < 0)
                    finalTarget = p.id;
                    break;
                } else {
                    if (p == p.parentNode)
                        p = null;
                    else
                        p = p.parentNode;
                }
            }
            ele.target = finalTarget;
            return finalTarget;
        },
        goToTop: function () {
            $("body").scrollTop(0);
        }
    });
    // Assert------------------------
    beangle.extend({
        assert: {
            notNull: function (object, message) {
                if (null == object) {
                    alert(message);
                }
            }
        }
    });
    bg.extend({
        randomInt: function () {
            var num = Math.random() * 10000000;
            num -= num % 1;
            return num;
        }
    });
    // Logger----------------------------
    beangle.extend({
        logger: {
            // debug=0;info=1;warn=2;error=3;fatal=4;disabled=5
            level: 1,
            debug: function (message) {
                if (beangle.logger.level <= 0) {
                    var msg = '[beangle] ' + message;
                    if (window.console && window.console.log) {
                        window.console.log(message);
                    } else if (window.opera && window.opera.postError) {
                        window.opera.postError(msg);
                    } else {
                        alert(msg);
                    }
                }
            }
        }
    });
    // Event--------------------------------------------------
    beangle.extend({
        event: {
            portable: function (e) {
                if (!e) {
                    return window.event;
                } else {
                    return e;
                }
            },
            /**
             * 获得事件背后的元素
             */
            getTarget: function (e) {
                e = bg.event.portable(e);
                return e.target || e.srcElement;
            }
        }
    });
    // Input----------------------------------------------------
    beangle.extend({
        input: {
            toggleCheckBox: function (chk, event) {
                bg.input.boxAction(chk, "toggle", event);
            },
            /**
             * 返回单选列表中选择的值
             *
             * @return 没有选中时,返回""
             */
            getRadioValue: function (radioName) {
                return bg.input.boxAction(
                    document.getElementsByName(radioName), "value");
            },
            /**
             * 返回多选列表中选择的值
             *
             * @return 多个值以,相隔.没有选中时,返回""
             */
            getCheckBoxValues: function (chkname) {
                var tmpIds = bg.input.boxAction(document
                    .getElementsByName(chkname), "value");
                if (tmpIds == null)
                    return "";
                else
                    return tmpIds;
            },
            /**
             * modify by chaostone 2006-8-2 将反选取消,改为全选或者全部取消
             */
            boxAction: function (box, action, event) {
                var val = "", srcElement, i;
                if (box) {
                    if (!box[0]) {
                        if (action == "selected") {
                            return box.checked;
                        } else if (action == "value") {
                            if (box.checked)
                                val = box.value;
                        } else if (action == "toggle") {
                            srcElement = beangle.event.getTarget(event);
                            if (!box.disabled) {
                                box.checked = srcElement.checked;
                                $(box).change();
                                if (typeof box.onchange == "function") {
                                    box.onchange();
                                }
                            }
                        }
                    } else {
                        for (i = 0; i < box.length; i++) {
                            if (action == "selected") {
                                if (box[i].checked)
                                    return box[i].checked;
                            } else if (action == "value") {
                                if (box[i].checked) {
                                    if (box[i].type == "radio") {
                                        val = box[i].value;
                                    } else if (box[i].type == "checkbox") {
                                        if (val != "")
                                            val = val + ",";
                                        val = val + box[i].value;
                                    }
                                }
                            } else if (action == "toggle") {
                                srcElement = beangle.event.getTarget(event);
                                if (!box[i].disabled) {
                                    box[i].checked = srcElement.checked;
                                    $(box[i]).change();
                                    if (typeof box[i].onchange == "function") {
                                        box[i].onchange();
                                    }
                                }
                            }
                        }
                    }
                }
                if (action == "selected")
                    return false;
                else
                    return val;
            }
        }
    });
    // IFrame--------------------------------------------------------
    beangle
        .extend({
            iframe: {
                adaptSelf: function () {
                    bg.iframe.adapt(self);
                },
                /**
                 * iframe 页面自适应大小
                 *
                 * @targObj iframe
                 * @extraHight
                 */
                adapt: function (targObj, extraHight) {
                    var frames, targWin, totalHeight, myHeight;
                    if (null == targObj || targObj.name == "")
                        return;
                    if (targObj.parent == targObj)
                        return;
                    if (targObj.parent == window.top) {
                        if (targObj.parent.document.body.style.overflowY == "hidden")
                            return;
                    }
                    frames = targObj.parent.document
                        .getElementsByName(targObj.name);
                    if (frames.length < 1)
                        return;
                    targWin = frames[0];
                    if (targWin != null
                        && (targWin.scrolling == "no" || targWin.className == "autoadapt")) {
                        totalHeight = targObj.document.body.scrollHeight
                            + ((null == extraHight) ? 0 : extraHight);
                        myHeight = 0;
                        if (targWin.style.height) {
                            myHeight = parseInt(targWin.style.height
                                .substring(0,
                                    targWin.style.height.length - 2));
                        }
                        if ((totalHeight > 0) && totalHeight > myHeight) {
                            targWin.style.height = totalHeight + "px";
                            bg.logger.debug('adapt frame:' + targObj.name
                                + " height " + targWin.style.height);
                        }
                    }
                    bg.iframe.adapt(targObj.parent);
                }
            }
        });
    beangle
        .extend({
            form: {
                submit: function (myForm, action, target, onsubmit, ajax,
                                  noHistory) {
                    if (noHistory == undefined) {
                        noHistory = false;
                    }
                    var submitTarget, rs, sumbitBtnId, submitx, origin_target, origin_action, options_submit;
                    if ((typeof myForm) == 'string')
                        myForm = document.getElementById(myForm);
                    // 1. submit hook
                    $(myForm).find("[name='nohistory']").length > 0 && (noHistory = true);
                    if (onsubmit) {
                        rs = null;
                        if (typeof onsubmit == "function") {
                            try {
                                rs = onsubmit(myForm);
                            } catch (e) {
                                alert(e.message);
                                return;
                            }
                        } else {
                            rs = eval(onsubmit);
                        }
                        if (rs == false) {
                            return;
                        }
                    }
                    // 2.native onsubmit
                    if (myForm.onsubmit) {
                        rs = null;
                        try {
                            rs = myForm.onsubmit();
                        } catch (e) {
                            alert(e);
                            return;
                        }
                        if (rs == false) {
                            return;
                        }
                    }
                    // remove file input
                    jQuery(myForm).find(".flashFileUpload").remove();
                    // disabled button
                    if (!$(myForm).hasClass("multi_submit") && $(myForm).find(".search_box").length == 0) {
                        jQuery(myForm).find(":button,:submit,:reset").prop("disabled", true);
                    }
                    // fixed ckeditor
                    $(".ickeditor").each(function () {
                        var ta = $(this);
                        ta.val(CKEDITOR.instances[ta.attr("id")].getData());
                    });
                    // 3. check target and action
                    submitTarget = (null != target) ? target
                        : myForm.target;
                    if (!submitTarget) {
                        submitTarget = bg.findTarget(myForm);
                    }
                    if (action == null) {
                        action = myForm.action;
                    }
                    if (action.indexOf("http://") == 0) {
                        action = action.substring(action.indexOf("/", 7));
                    }
                    if (null == ajax || ajax) {
                        ajax = bg.isAjaxTarget(submitTarget)
                    }
                    origin_target = myForm.target;
                    origin_action = myForm.action;
                    // 4. fire
                    myForm.action = action;
                    if (ajax) {
                        // fix myForm without id
                        if (null == myForm.id || '' == myForm.id) {
                            myForm.setAttribute("id", myForm.name);
                        }
                        sumbitBtnId = myForm.id + "_submit";
                        submitx = document.getElementById(sumbitBtnId);
                        if (null == submitx) {
                            submitx = document.createElement('input');
                            submitx.setAttribute("id", sumbitBtnId);
                            submitx.setAttribute("type", 'submit');
                            submitx.style.display = "none";
                            myForm.appendChild(submitx);
                        }
                        options_submit = {
                            id: sumbitBtnId,
                            jqueryaction: "button",
                            targets: submitTarget,
                            href: '#',
                            formids : myForm.id,
                        };
                        if (typeof jQuery != "undefined") {
                            if (typeof noHistory != "undefined"
                                && !noHistory
                                && jQuery("input:file", myForm).length == 0) {
                                beangle.history.historySubmit(myForm.id,
                                    action, submitTarget);
                            } else {
                                jQuery.struts2_jquery.bind(jQuery('#'
                                    + sumbitBtnId), options_submit);
                            }
                        }
                        bg.ui.search.removePlaceholder(myForm.id);
                        submitx.click();
                        bg.ui.search.addPlaceholder(myForm.id);
                        jQuery("#" + sumbitBtnId).unbind();
                    } else {
                        myForm.target = bg.normalTarget(submitTarget);
                        myForm.submit();
                        myForm.target = origin_target;
                    }
                    // 5. cleanup
                    if (myForm) {
                        myForm.action = origin_action;
                        myForm.target = origin_target;
                    }
                    //window.top.bg.goToTop();
                },
                /**
                 * 提交要求含有id的表单
                 *
                 * @param form
                 *            带提交的表单
                 * @param id
                 *            要提交id的名称
                 * @param isMulti(可选)是否允许多个id选择,默认支持一个
                 * @param action
                 *            (可选) 指定form的action
                 */
                submitId: function (form, id, isMulti, action, promptMsg, ajax) {
                    var selectId = bg.input.getCheckBoxValues(id);
                    if (null == isMulti)
                        isMulti = false;
                    if ("" == selectId) {
                        alert(isMulti ? "请选择一个或多个进行操作" : "请选择一个进行操作");
                        return;
                    }
                    if (!isMulti && (selectId.indexOf(",") > 0)) {
                        alert("请仅选择一个");
                        return;
                    }
                    if (null != action) {
                        form.action = action;
                    } else {
                        action = form.action;
                    }
                    bg.form.addInput(form, (isMulti ? (id + 's') : id),
                        selectId, "hidden");
                    if (null != promptMsg) {
                        if (!confirm(promptMsg))
                            return;
                    }
                    bg.form.submit(form, action, null, null, ajax);
                },
                /**
                 * 向form中添加一个input。
                 *
                 * @param form
                 *            要添加输入的form
                 * @param name
                 *            input的名字
                 * @param value
                 *            input的值
                 * @param type
                 *            input的类型，默认为hidden
                 * @author chaostone 2006-4-7
                 */
                addInput: function (form, name, value, type) {
                    // 防止设置form的属性
                    if (form[name] != null
                        && (typeof form[name]) != "string") {
                        form[name].value = value;
                    } else {
                        if (null == type)
                            type = "hidden";
                        var input = document.createElement('input');
                        input.setAttribute("name", name);
                        input.setAttribute("value", value);
                        input.setAttribute("type", type);
                        form.appendChild(input);
                    }
                },
                ecodeParams: function (params) {
                    if ("" == params)
                        return "";
                    var paramsPair = params.split("&"), newParams = "", i, eqIndex;
                    for (i = 0; i < paramsPair.length; i++) {
                        if (paramsPair[i] != "") {
                            eqIndex = paramsPair[i].indexOf("=");
                            newParams += "&"
                                + paramsPair[i].substr(0, eqIndex);
                            if (-1 != eqIndex) {
                                newParams += "=";
                                newParams += escape(paramsPair[i]
                                    .substr(eqIndex + 1));
                            }
                        }
                    }
                    return newParams;
                },
                /**
                 * 从form表单中，抽出含有指定前缀的输出参数， 将其作为一个参数加入到to表单中。
                 */
                setSearchParams: function (from, to, prefix) {
                    bg.form.addInput(to, 'params', "");
                    var params = bg.form
                        .getInputParams(from, prefix, false);
                    bg.form.addInput(to, 'params', params);
                },
                addHiddens: function (form, paramSeq) {
                    bg.assert.notNull(paramSeq,
                        "paramSeq for addHiddens must not be null");
                    var params = paramSeq.split("&"), i, name, value;
                    for (i = 0; i < params.length; i++) {
                        if (params[i] != "") {
                            name = params[i].substr(0, params[i]
                                .indexOf("="));
                            value = params[i]
                                .substr(params[i].indexOf("=") + 1);
                            bg.form.addInput(form, name, value, "hidden");
                        }
                    }
                },
                addParamsInput: function (form, value) {
                    bg.form.addInput(form, "params", value, "hidden");
                },
                transferParams: function (from, to, prefix, getEmpty) {
                    if (getEmpty == null)
                        getEmpty = true;
                    var params = bg.form.getInputParams(from, prefix,
                        getEmpty);
                    bg.form.addHiddens(to, params);
                },
                /**
                 * 收集给定form中的input||select参数（不论input的类型）.<b>
                 * 但不收集params的input,这个作为保留名称
                 *
                 * @param form
                 * @param prefix
                 *            指明所有input||select的前缀，如果没有前缀可以忽略
                 * @param getEmpty
                 *            是否收集值为空的属性
                 * @return 返回参数列表串形如：&input1=...&input2=...
                 * @author chaostone 2006-4-7
                 *
                 */
                getInputParams: function (form, prefix, getEmpty) {
                    var elems = form.elements, params = "", i;
                    if (null == getEmpty)
                        getEmpty = true;
                    for (i = 0; i < elems.length; i++) {
                        if ("" != elems[i].name) {
                            if ("params" == elems[i].name)
                                continue;
                            // alert(elems[i].tagName+":"+elems[i].value);
                            if ((elems[i].value == "")
                                && (getEmpty == false))
                                continue;
                            if (null != prefix) {
                                if (elems[i].name.indexOf(prefix) == 0
                                    && elems[i].name.indexOf(".") > 1) {
                                    if ((elems[i].type == "radio" || elems[i].type == "checkbox")
                                        && elems[i].checked == false)
                                        continue;
                                    if (elems[i].value.indexOf('&') != -1) {
                                        params += "&" + elems[i].name + "="
                                            + escape(elems[i].value);
                                    } else {
                                        params += "&" + elems[i].name + "="
                                            + elems[i].value;
                                    }
                                }
                            } else {
                                if ((elems[i].type == "radio" || elems[i].type == "checkbox")
                                    && elems[i].checked == false)
                                    continue;
                                if (elems[i].value.indexOf('&') != -1) {
                                    params += "&" + elems[i].name + "="
                                        + escape(elems[i].value);
                                } else {
                                    params += "&" + elems[i].name + "="
                                        + elems[i].value;
                                }
                            }
                        }
                    }
                    // alert("[getInputParams]:"+params);
                    return params;
                },
                goToPage: function (form, pageNo, pageSize, orderBy) {
                    if ((typeof form) != "object") {
                        alert("[goToPage:]form is not well defined.");
                        return;
                    }
                    // form.method="post"; for avoid "method" input
                    if (null != pageNo) {
                        if (isNaN(pageNo)) {
                            alert("输入分页的页码是:" + pageNo + ",它不是个整数");
                            return;
                        }
                        bg.form.addInput(form, "pageNo", pageNo, "hidden");
                    } else {
                        bg.form.addInput(form, "pageNo", 1, "hidden");
                    }
                    if (null != pageSize) {
                        if (isNaN(pageSize)) {
                            alert("输入分页的页长是:" + pageSize + ",它不是个整数");
                            return;
                        }
                        bg.form.addInput(form, "pageSize", pageSize,
                            "hidden");
                    } else {
                        bg.form.addInput(form, "pageSize", "", "hidden");
                    }
                    if (null != orderBy && orderBy != "null") {
                        bg.form
                            .addInput(form, "orderBy", orderBy,
                                "hidden");
                    } else {
                        bg.form.addInput(form, "orderBy", "", "hidden");
                    }
                    // alert("in goToPage");
                    form.submit();
                },
                goToFirstPage: function (form) {
                    bg.form.goToPage(form, 1);
                },
                checkAll: function (ele, reg, msg) {
                    ele.each(function () {
                        if (!reg.test(this.value)) {
                            this.focus();
                            throw new Error(msg);
                        }
                    });
                }
            }
        });
    // select---------------------
    beangle
        .extend({
            select: {
                getValues: function (select) {
                    var val = "", i, options = select.options;
                    for (i = 0; i < options.length; i++) {
                        if (val != "")
                            val = val + ",";
                        val = val + options[i].value;
                    }
                    return val;
                },
                getSelectedValues: function (select) {
                    var val = "", i, options = select.options;
                    for (i = 0; i < options.length; i++) {
                        if (options[i].selected) {
                            if (val != "")
                                val = val + ",";
                            val = val + options[i].value;
                        }
                    }
                    return val;
                },
                hasOption: function (select, op) {
                    for (var i = 0; i < select.length; i++) {
                        if (select.options[i].value == op.value)
                            return true;
                    }
                    return false;
                },
                moveSelected: function (srcSelect, destSelect) {
                    var i, op;
                    for (i = 0; i < srcSelect.length; i++) {
                        if (srcSelect.options[i].selected) {
                            op = srcSelect.options[i];
                            if (!bg.select.hasOption(destSelect, op)) {
                                destSelect.options[destSelect.length] = new Option(
                                    op.text, op.value);
                            }
                        }
                    }
                    bg.select.removeSelected(srcSelect);
                    bg.select.clearStatus(srcSelect);
                },
                clearStatus: function (select) {
                    for (var i = 0; i < select.options.length; i++)
                        select.options[i].selected = false;
                },
                selectAll: function (select) {
                    for (var i = 0; i < select.options.length; i++)
                        select.options[i].selected = true;
                    return select.options.length > 0;
                },
                removeSelected: function (select) {
                    var options = select.options, i;
                    for (i = options.length - 1; i >= 0; i--) {
                        if (options[i].selected) {
                            options[i] = null;
                        }
                    }
                },
                /**
                 * 设定选择框中的选择项(单项)
                 */
                setSelected: function (select, idSeq) {
                    if (idSeq.indexOf(',') != 0) {
                        idSeq = "," + idSeq;
                    }
                    if (idSeq.lastIndexOf(',') != idSeq.length - 1) {
                        idSeq = idSeq + ",";
                    }
                    for (var i = 0; i < select.options.length; i++) {
                        if (idSeq.indexOf(',' + select.options[i].value
                            + ',') != -1)
                            select.options[i].selected = true;
                    }
                }
            }
        });
    // Cookie----------------------------------------------------------------------------------------
    beangle.extend({
        cookie: {
            get: function (cookieName) {
                var cookieString = document.cookie, start = cookieString
                    .indexOf(cookieName + '='), end;
                // 加上等号的原因是避免在某些 Cookie 的值里有
                // 与 cookieName 一样的字符串。
                if (start == -1) // 找不到
                    return null;
                start += cookieName.length + 1;
                end = cookieString.indexOf(';', start);
                if (end == -1)
                    return unescape(cookieString.substring(start));
                return unescape(cookieString.substring(start, end));
            },
            set: function (name, value, path) {
                if (null == path)
                    path = "/";
                var expires = new Date();
                expires.setTime(expires.getTime() + (86400 * 30));
                document.cookie = name + "=" + value + "; expires="
                    + expires.toGMTString() + "; path=" + path;
            }
        }
    });
    // Page---------------------------------------------------------------------
    function Page(action, target, pageNo, pageSize, total) {
        this.formid = "form_" + bg.randomInt();
        this.actionurl = action;
        this.target = target;
        this.paramMap = {};
        this.params = function () {
            return this.paramMap;
        }
        this.pageInfo = function (pageNo, pageSize, total) {
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.total = total;
            if (null != total && null != pageSize && null != pageNo) {
                quotient = Math.floor(total / pageSize);
                this.maxPageNo = (0 == total % pageSize) ? quotient
                    : (quotient + 1);
                this.startNo = (pageNo - 1) * pageSize + 1;
                this.endNo = (this.startNo + pageSize - 1) <= total ? (this.startNo
                        + pageSize - 1)
                    : total;
            } else {
                this.maxPageNo = 1;
            }
        }
        this.pageInfo(pageNo, pageSize, total);
        this.action = function (actionurl) {
            this.actionurl = actionurl;
            return this;
        }
        this.orderBy = function (newstring) {
            this.orderby = newstring;
            return this;
        }
        this.target = function (givenTarget, elemId) {
            if (givenTarget) {
                this.target = givenTarget;
            } else if (elemId) {
                this.target = bg.findTarget(document.getElementById(elemId));
            }
            return this;
        }
        this.getForm = function () {
            myForm = document.getElementById(this.formid);
            if (null == myForm) {
                myForm = document.createElement("form");
                myForm.setAttribute("id", this.formid);
                myForm.setAttribute("action", this.actionurl);
                myForm.setAttribute("method", "POST");
                if (document.getElementById(this.target)) {
                    document.getElementById(this.target).appendChild(myForm);
                } else {
                    document.body.appendChild(myForm);
                }
            }
            return myForm;
        }
        this.addParams = function (paramSeq) {
            bg.assert.notNull(paramSeq,
                "paramSeq for addHiddens must not be null");
            this.paramstr = paramSeq;
            var paramArray = paramSeq.split("&"), i, name, value;
            for (i = 0; i < paramArray.length; i++) {
                oneParam = paramArray[i];
                if (oneParam != "") {
                    name = oneParam.substr(0, oneParam.indexOf("="));
                    value = oneParam.substr(oneParam.indexOf("=") + 1);
                    this.paramMap[name] = value;
                }
            }
            return this;
        }
        // 检查分页参数
        this.checkPageParams = function (pageNo, pageSize, orderBy) {
            if (null != pageNo) {
                if (isNaN(pageNo)) {
                    bg.alert("输入分页的页码是:" + pageNo + ",它不是个整数");
                    return false;
                }
                if (this.maxPageNo != null) {
                    if (pageNo > this.maxPageNo) {
                        pageNo = this.maxPageNo;
                    }
                }
                this.paramMap['pageNo'] = pageNo;
            }
            if (null != pageSize) {
                if (isNaN(pageSize)) {
                    bg.alert("输入分页的页长是:" + pageSize + ",它不是个整数");
                    return false;
                }
                this.paramMap["pageSize"] = pageSize;
            }
            if (null != orderBy && orderBy != "null") {
                this.paramMap["orderBy"] = orderBy;
            }
            return true;
        }
        // jump to page using form submit
        this.goPageNormal = function (pageNo, pageSize, orderBy) {
            var myForm = document.createElement("form"), key, value;
            myForm.setAttribute("action", this.actionurl);
            myForm.setAttribute("method", "POST");
            for (key in this.paramMap) {
                value = this.paramMap[key];
                if (value != "") {
                    bg.form.addInput(myForm, key, value, "hidden");
                }
            }
            document.body.appendChild(myForm);
            myForm.submit();
        }
        // jump to page using ajax
        this.goPageAjax = function (pageNo, pageSize, orderBy) {
            var myForm = this.getForm(), key, value, submitBtnId, submitx, options_submit;
            for (key in this.paramMap) {
                value = this.paramMap[key];
                if (value != "") {
                    bg.form.addInput(myForm, key, value, "hidden");
                }
            }
            submitBtnId = this.formid + "_submitx";
            submitx = document.getElementById(submitBtnId);
            if (null == submitx) {
                submitx = document.createElement('button');
                submitx.setAttribute("id", submitBtnId);
                submitx.setAttribute("type", 'submit');
                submitx.style.display = 'none';
                myForm.appendChild(submitx);
            }
            options_submit = {};
            options_submit.jqueryaction = "button";
            options_submit.id = submitBtnId;
            options_submit.targets = this.target;
            options_submit.href = this.actionurl;
            options_submit.formids = this.formid;
            jQuery.struts2_jquery.bind(jQuery('#' + submitBtnId),
                options_submit);
            submitx.click();
        }
        this.goPage = function (pageNo, pageSize, orderBy) {
            if (this.checkPageParams(pageNo, pageSize, orderBy)) {
                if (this.target && document.getElementById(this.target)) {
                    this.goPageAjax(pageNo, pageSize, orderBy);
                } else {
                    this.goPageNormal(pageNo, pageSize, orderBy);
                }
            }
        }
    }

    bg.extend({
        page: function (action, target) {
            return new Page(action, target);
        }
    });
    bg.onReturn = function (event, action) {
        if (!event) {
            event = window.event;
        }
        if (event && event.keyCode && event.keyCode == 13) {
            action();
        }
    };
    bg.extend({
        lastDivId: null,
        message: function (divId, time, title, position) {
            try {
                time = time || 2;
                title = title || "提示信息";
                position = position || "top";
                var div = $("#" + divId);
                if (div.length == 0) {
                    div = $('<div class="ui-widget messages" style="display: none;"><div class="actionMessage"><div> <p><div style="line-height: 18px;"><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' + divId + '</div></p></div></div></div>');
                    $("body").append(div);
                } else {
                    if (this.lastDivId == divId) {
                        return;
                    }
                    this.lastDivId = divId;
                }
                if (!title) {
                    title = "提示信息";
                }
                var os = {
                    show: {
                        effect: 'drop',
                        direction: "up"
                    },
                    hide: {
                        effect: 'drop',
                        direction: "up"
                    },
                    //position : position,
                    title: title,
                    minHeight: "30px",
                    //zIndex:9999,
                    resizable: false
                    //modal: true
                };
                $(function () {
                    div.dialog(os);
                    setTimeout(function () {
                        div.dialog("close");
                    }, time * 1 * 1000);
                });
            } catch (e) {
            }
        }
    });
    // alert(document.body);
    beangle.ready(beangle.iframe.adaptSelf);
})(window);
// History
(function (window, undefined) {
    if (beangle.history)
        return;
    jQuery.struts2_jquery
        .require(
            "/struts/js/plugins/jquery.form"
            + jQuery.struts2_jquery.minSuffix + ".js",
            function () {
                window.beangle.history = {
                    init: function () {
                        if (document.location.protocol === 'file:') {
                            alert('The HTML5 History API (and thus History.js) do not work on files, please upload it to a server.');
                        }
                        jQuery(document).ready(function () {
                            History.Adapter.bind(window, 'statechange', function (e) {
                                var currState = History.getState();
                                if (jQuery.type((currState.data || {}).target) != "undefined" && jQuery.type((currState.data || {}).html) != "undefined") {
                                    jQuery(currState.data.target).empty();
                                    console.log(currState.data.html);
                                    jQuery(currState.data.target).html(currState.data.html);
                                }
                            });
                        });
                    },
                    back: function (queue) {
                        return History.back(queue);
                    },
                    forward: function (queue) {
                        return History.forward(queue);
                    },
                    historyGo: function (url, target) {
                        var off = url.indexOf(" ");
                        if (off >= 0) {
                            var selector = url.slice(off, url.length);
                            url = url.slice(0, off);
                        }
                        jQuery.ajax({
                            url: url,
                            type: "GET",
                            dataType: "html",
                            complete: function (jqXHR, status,
                                                responseText) {
                                responseText = jqXHR.responseText;
                                if (jqXHR.success()) {//isResolved
                                    jqXHR.done(function (r) {
                                        responseText = r;
                                    });
                                    var html = selector ? jQuery(
                                        "<div>").append(
                                        responseText.replace(
                                            rscript, "")).find(
                                        selector) : responseText;
                                    try {
                                        History.pushState({
                                            html: html,
                                            target: "#" + target
                                        }, document.title, url);
                                    } catch (e) {
                                    }
                                }
                            }
                        });
                    },
                    historySubmit: function (form, action, target) {
                        if (jQuery.type(form) == "string"
                            && form.indexOf("#") != 0) {
                            form = "#" + form;
                        }
                        if (jQuery.type(target) == "string"
                            && target.indexOf("#") != 0) {
                            target = "#" + target;
                        }
                        jQuery(form)
                            .ajaxForm(
                                function (result, message,
                                          response) {
                                    var html = result;
                                    if (message === "success"
                                        && response.status == 200
                                        && response.readyState == 4) {
                                        History.pushState({
                                            html: html,
                                            target: target
                                        }, document.title, action);
                                    }
                                    return false;
                                });
                    }
                }
            }, bg.getContextPath());
    beangle.history.init();
    beangle.changeCaptcha = function (obj) {
        //获取当前的时间作为参数，无具体意义
        var timenow = new Date().getTime();
        //每次请求需要一个不同的参数，否则可能会返回同样的验证码
        //这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
        obj.src = base + "/security/captcha.action?d=" + timenow;
    }
})(window);
$name = function (name) {
    return $("[name='" + name + "']");
}
if (!c) {
    var c = {};
}
c.dialogClosed = true;
c.onprogress = function () {
    if (!c.dialogClosed) {
        return;
    }
    c.dialogClosed = false;
    c.dialog = $("<div>正在处理，请稍候...</div>").dialog({
        title: '友情提示',
        show: 'fade',
        hide: 'fade',
        modal: true,
        close: function () {
            c.dialogClosed = true;
        }
    });
}
c.closeOnprogress = function () {
    c.dialog.dialog("close");
}
var submenus = {};
submenus.getMenuA = function (a) {
    if (a == undefined) {
        return;
    }
    a = $(a);
    var firstA = $("<a href='" + a.attr("href") + "'>" + a.attr("mytitle") + "</a>");
    firstA.click(function (e) {
        return bg.Go(this, 'main', true);
    });
    return firstA;
};
(function ($) {
    function filterTd(table) {
        if (!window.localStorage) {
            return;
        }
        var localStorageKey = getLocalStorageKey(table);
        if (localStorage.getItem(localStorageKey) != null) {
            var configs = getfilterTdConfig(localStorageKey);
            table.find("tr").each(function () {
                $(this).find("th,td").each(function (i) {
                    if (configs[i]) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                })
            });
        }
    }

    function getfilterTdConfig(localStorageKey) {
        var configs = localStorage.getItem(localStorageKey);
        configs = JSON.parse(configs);
        return configs;
    }

    function getLocalStorageKey(table) {
        return table.attr("id") + "_filterTd_config";
    }

    function config(table) {
        if (!window.localStorage) {
            alert("您的浏览器不支持过滤列表功能，请升级到最新版本浏览器后再试。")
            return;
        }
        var localStorageKey = getLocalStorageKey(table);
        var div = $('<div style="background-color: #fff; padding: 10px;"><div style="margin: 10px 0;"><button class="revert_btn">反选</button></div></div>');
        div.find(".revert_btn").click(function () {
            div.find("input:checkbox").each(function () {
                $(this).prop("checked", !$(this).prop("checked"));
            });
        });
        table.find("thead th").each(function (i) {
            var th = $('<div><label style="padding: 5px 0; cursor: pointer;"><input type="checkbox" value="1" style="margin-right: 5px;"/></label></div>');
            var text = $(this).text();
            if (text == "" && i == 0) {
                text = "全选/全不选";
            }
            th.find("label").append(text);
            div.append(th);
        });
        var configs = getfilterTdConfig(localStorageKey);
        if (configs != null) {
            div.find("input:checkbox").each(function (i) {
                $(this).prop("checked", configs[i]);
            });
        }
        div.append('<div style="margin: 10px 0;"><button class="save_btn">确定</button></div>');
        div.find(".save_btn").click(function () {
            var showths = [];
            div.find("input:checkbox").each(function () {
                showths.push($(this).prop("checked"));
            });
            var json = JSON.stringify(showths);
            localStorage.setItem(getLocalStorageKey(table), json);
            filterTd(table);
            $.colorbox.close();
        });
        $.colorbox({
            width: 300,
            html: div,
        });
    }

    $.fn.filterTd = function (method) {
        if ("config" == method) {
            config(this);
        } else {
            filterTd(this);
        }
    };
    $.ajaxSetup({
        cache: false,
    });
})(jQuery);
