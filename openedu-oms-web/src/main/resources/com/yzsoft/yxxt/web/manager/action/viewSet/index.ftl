[#ftl]
[@b.head/]
<style type="text/css">
    .view_set_div { background-color: #fff; }
    a.viewSet {
        float: left;
        width: 100px;
        border: 1px #555 solid;
        padding: 37px 0;
        text-align: center;
        cursor: pointer;
        margin: 10px;;
    }
    a.viewSet:hover {
        background: #eee
    }
</style>
<div class="view_set_div clearfix">
[#list list as v]
    <a href="${b.url('${v[1]}')}" class="viewSet" [#if v[2]==""]onclick="return bg.Go(this,null,true)"[#else]target="${v[2]!}"[/#if]>
        <div>
            <span>${v[0]}设置</span>
        </div>
    </a>
[/#list]
</div>
[@b.foot/]
