$.fn.initForm = function (opts) {
    opts = $.extend({
        highlight: function (element) {
            $(element).closest(".ui-field-contain,.apply_reasons_textinput").addClass("error");
        },
        success: function (label) {
            label.closest(".ui-field-contain,.apply_reasons_textinput").removeClass("error");
            label.remove();
        },
        errorPlacement: function (error, element) {
            element.closest(".form-right").append(error);
        },
    }, opts);
    this.validate(opts);
    this.find(".submit").click(function () {
        var form = $(this).closest("form");
        if (form.valid()) {
            form.submit();
        } else {
            alert("表单填写有误");
        }
    });
};