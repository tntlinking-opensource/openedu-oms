[#ftl]
[#import "/template/utils/PageSet.ftl" as ps/]
<script type="text/javascript">
    function onIframeLoad(pageSet) {
        pageSet.hover({selector: ".foot_info_div", href: "${b.url('!footInfo')}"});
        pageSet.hover({selector: ".app_div", href: "${b.url('!app')}"});
        pageSet.hover({selector: ".process_gz_img_div", href: "${b.url('!processImg?type=1')}"});
        pageSet.hover({selector: ".process_zz_img_div", href: "${b.url('!processImg?type=0')}"});
    }
</script>
[@ps.body "首页设置" "${b.url('/web/index2')}" "onIframeLoad"]
<style>
    .page-footer { display: none; }
</style>
[/@]