[#ftl]
[#if b.isMobile()]
    [#include "/template/mobile/login.ftl"/]
[#else]
[#--    [#if Parameters["admin"]??]
        [#include "/pages/login_default.ftl"/]
    [#else]
    <script>
        location.href = "${base}/web/index2.action";
    </script>
    [/#if]--]

    [#include "/pages/login_default.ftl"/]
[/#if]
