[#ftl]
[#include "../comm/lib.ftl"/]

[#function isWitchFinished switch states]
    [#list states as state]
        [#if switch.code == state.collectSwitch.code]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]

[@edit]
    [@panel]
    <style>
        .row-xxx .col-xs-3{padding: 5px;}
    </style>
	<div class="row row-xxx">
        [#list switches as switch]
			<div class="col-xs-3" style="text-align: right;">
            ${switch.name}
			</div>
			<div class="col-xs-3" style="text-align: left;">
                [#if switch.editable]
                    [#if isWitchFinished(switch, states)]
						<span style="color: green;">（已填写）</span>
                    [#else]
						<span style="color: red;">（未填写）</span>
                    [/#if]
                [/#if]
			</div>
        [/#list]
	</div>
    [/@panel]
[/@edit]