[#ftl]
[@b.head/]
<style>
    body { background-color: #fff; }
    .tab-pane .checkbox { margin-right: 20px; min-width: 160px; }
</style>
<div style="margin: 20px 0;">
    <div class="row">
        <div class="col-xs-3">
            <ul class="nav nav-tabs tabs-left">
            [#list departments as department]
                <li [#if department_index==0]class="active"[/#if]>
                    <a href="#tab_${(department.id)!}" data-toggle="tab" aria-expanded="false"
                       class="a_department_${department.id}">
                        <span class="department_name_${department.id}">${(department.name)!}</span>
                        <span class="num depart_num_${department.id}" style="display: none;"></span>
                    </a>
                </li>
            [/#list]
            </ul>
        </div>
        <div class="col-xs-9">
            <div class="tab-content">
            [#list departments as department]
                <div class="tab-pane [#if department_index==0]active[/#if] form-inline"
                     id="tab_${(department.id)!}">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="${department.id}"
                                   class="checkbox_department"/>全选/全不选
                        </label>
                    </div>
                    [#list majors as major]
                        [#if major.department.id == department.id]
                            <div class="checkbox" style="margin-right: 20px;">
                                <label>
                                    <input type="checkbox" value="${major.id}"
                                           class="checkbox_major major_${major.id} major_department_${department.id}"/>${major.name}
                                </label>
                            </div>
                        [/#if]
                    [/#list]
                </div>
            [/#list]
            </div>
        </div>
    </div>
    <div style="margin: 20px;">
        <button class="btn blue ok_btn" type="button">确定<i class="fa fa-check "></i></button>
    </div>
</div>
<script>
    $(function () {

        var majorIds = "${majorIds!}".split(",");
        majorIds.forEach(function (id) {
            $(".major_" + id).prop("checked", true);
        });

        $(".checkbox_department").click(function (e) {
            var ipt = $(this);
            var checked = ipt.prop("checked");
            $(".major_department_" + ipt.val()).prop("checked", checked);
            updateNum();
        });

        $(".checkbox_major").click(function () {
            updateNum();
        });

        function updateNum() {
            $(".checkbox_department").each(function () {
                var ipt = $(this);
                var num = $(".major_department_" + ipt.val() + ":checked").length;
                var numSpan = $(".depart_num_" + ipt.val());
                numSpan.html("（" + num + "）");
                if (num > 0) {
                    numSpan.show();
                } else {
                    numSpan.hide();
                }
            });
        }

        updateNum();

        $(".ok_btn").click(function () {
            var names = [], ids = "";

            $(".checkbox_department").each(function () {
                var ipt = $(this);
                var majors = $(".major_department_" + ipt.val() + ":checked");
                if (majors.length > 0) {
                    var name = "";
                    majors.each(function () {
                        if (ids != "") {
                            ids += ",";
                        }
                        if(name != ""){
                            name += "、";
                        }
                        ids += this.value;
                        name += $(this).parent().text();
                    });
                    names.push($(".department_name_" + ipt.val()).text() + "：" + name);
                }
            });
            if (parent.selectedMajor) {
                parent.selectedMajor(names, ids);
            } else {
                alert("parent not have function selectedMajor(names, ids)");
            }
        });
    });
</script>
[@b.foot nofooter="1"/]