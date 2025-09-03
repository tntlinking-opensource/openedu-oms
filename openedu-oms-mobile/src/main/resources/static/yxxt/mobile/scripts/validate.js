jQuery.validator.addClassRules("v_integer", {
    digits: true,
    min: 1
});

jQuery.validator.addClassRules("v_phone", {
    digits: true,
    minlength: 11,
    maxlength: 11,
});