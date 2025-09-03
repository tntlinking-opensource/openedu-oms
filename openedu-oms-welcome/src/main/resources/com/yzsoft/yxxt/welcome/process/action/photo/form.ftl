[#ftl]
[#include "../comm/lib.ftl"/]
[@edit]
    [@panelEdit title="上传照片"]
    <div class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label">选择照片：</label>
            <div class="col-sm-6">
                <input name="file" type="file" class="form-control v_photo" required accept="image/jpeg">
            </div>
            <div class="col-sm-3">
                <p class="form-control-static">请上传jpg格式照片</p>
            </div>
        </div>
    </div>
    <script>
        jQuery.validator.addClassRules("v_photo", {
            required: true,
            fileType: ["jpg"],
        });
        jQuery.validator.addMethod("fileType", function (value, element, param) {
            var fileType = value.substring(value.lastIndexOf(".") + 1).toLowerCase();
            return this.optional(element) || $.inArray(fileType, param) != -1;
        }, "文件格式错误，要求格式：{0}");
    </script>
    [/@panelEdit]
[/@edit]