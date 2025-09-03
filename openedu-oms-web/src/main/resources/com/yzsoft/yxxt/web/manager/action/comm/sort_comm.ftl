[#ftl]
<link rel="stylesheet" href="${base}/static/scripts/jquery-ui/jquery-ui.min.css" />
<script type="text/javascript" src="${base}/static/scripts/jquery-ui/jquery-ui.min.js"></script>
<style>
    .isortable{ cursor: move;}
    .isortable li{width:300px; position: relative;background-color: #eee; margin: 3px 0; padding: 10px;}
    .isortable .up, .isortable .down{position: absolute; top: 12px;}
    .isortable .up{right: 60px;}
    .isortable .down{right: 10px;}
</style>
<script>
    $(function() {
        $( ".isortable" ).sortable({
            stop : function (){
                resetSort();
            }
        }).disableSelection();
        //上移、下移
        $( ".isortable .up, .isortable .down").click(function (){
            var li = $(this).parent();
            if($(this).hasClass("up")){
                li.insertBefore(li.prev());
            }else{
                li.insertAfter(li.next());
            }
            resetSort();
            return false;
        });
        resetSort();
    });

    //重新设置sort的值，上移下移按钮显示
    function resetSort(){
        var lis = $(".isortable li").each(function (i){
            $(this).find(".sort_ipt").val(i + 1);
            $(this).find(".sort_span").html(i + 1 + ". ");
        });
        $(".isortable .up, .isortable .down").show();
        $(".isortable").each(function (){
            var lis = $("li", this);
            lis.first().find(".up").hide();
            lis.last().find(".down").hide();
        });
    }
</script>