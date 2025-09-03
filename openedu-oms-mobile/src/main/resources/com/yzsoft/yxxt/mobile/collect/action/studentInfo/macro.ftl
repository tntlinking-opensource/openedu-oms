[#ftl]
[#import "/template/form/utils.ftl" as fu/]
[#assign text = "com.yzsoft.yxxt.web.collect.freemarker.Text"?new()/]

[#macro propertyInfo config]
    [#if config.editable && switch?? && switch.editable]
    <li>
    [#--<a href="${b.url("!edit")}&id=${config.id}" class="yx-xxtx-info-a" data-transition="slide">--]
        <span class="yx-xxtx-info-a-left">${(config.name)!}</span>
        <span class="yx-xxtx-info-a-right">[@propertyInfoValue config/]</span>
    [#--</a>--]
    </li>
    [#else]
    <li>
        <div class="yx-xxtx-info-a">
            <span class="yx-xxtx-info-a-left">${(config.name)!}</span>
            <span class="yx-xxtx-info-a-right">[@propertyInfoValue config/]</span>
        </div>
    </li>
    [/#if]
[/#macro]

[#macro propertyInfoValue config]
    [#if config.property.property == "student.photo"]
    [#elseif  config.property.property == "studentEnroll.photo"]
    [#else]
        ${text(config.property.property)!}
    [/#if]
[/#macro]

[#macro propertyEdit config]
    [#assign property=config.property.property/]
    [#assign validateClass = (config.property.format)!""/]
    [#if config.required]
        [#assign validateClass =  validateClass +" v_required"/]
    [/#if]
    [#if property == "student.qq"]
        [#assign validateClass = validateClass + " v_integer"/]
    [#elseif  property == "student.phone"]
        [#assign validateClass = validateClass + " v_phone"/]
    [#elseif  property == "studentHome.zipcode"]
        [#assign validateClass = validateClass + " v_zipcode"/]
    [#elseif  property == "studentHome.zipcode"]
        [#assign validateClass = validateClass + " v_zipcode"/]
    [#elseif  property == "student.email"]
        [#assign validateClass = validateClass + " v_email"/]
    [/#if]

<li>
    <div class="yx-xxtx-info-a">
        <span class="yx-xxtx-info-a-left margin-top-em">
            ${(config.name)!}
            [#if config.editable && config.required]<span class="requried">*</span>[/#if]
        </span>
        <div class="form-right">
            [#if config.editable]
                [#if "STD_PROPERTY_INPUT_TYPE_01" == (config.property.inputType.code)!""]
                    [#if property == "student.birthday"]
                    [#--生日--]
                        <input name="student.birthday" value="${(student.birthday?date)!}"
                               class="datepicker ${validateClass}"/>
                    [#elseif property == "studentInfo.politicalStatus"]
                    [#--政治面貌--]
                        [@fu.select name="studentInfo.politicalStatus.id" value=(studentInfo.politicalStatus.id)!0 data=politicals class=validateClass/]
                    [#elseif property == "studentInfo.maritalStatus"]
                    [#--婚姻状况--]
                        [@fu.select name="studentInfo.maritalStatus.id" value=(studentInfo.maritalStatus.id)!0 data=hyzks class=validateClass/]
                    [#elseif property == "studentInfo.nation"]
                    [#--民族--]
                        [@fu.select name="studentInfo.nation.id" value=(studentInfo.nation.id)!0 data=nations class=validateClass/]
                    [#elseif property == "studentInfo.health"]
                    [#--健康状况--]
                        [@fu.select name="studentInfo.health.id" value=(studentInfo.health.id)!0 data=healths class=validateClass/]
		            [#elseif property == "student.faith"]
		            [#--宗教信仰--]
		                [@fu.select name="student.faith.id" value=(student.faith.id)!0 data=faiths class=validateClass /]
                    [#elseif property == "student.gender"]
                    [#--性别--]
                    	[@fu.select name="student.gender.id" value=(student.gender.id)!0 data=genders class=validateClass/]
                    [#elseif property == "studentInfo.hmto"]
                    [#--是否澳台--]
                    [#--[@fu.radios name="studentInfo.hmto" value=(studentInfo.hmto?string("1", "0"))!"0" data=[{"id":"1", "name":"是"}, {"id":"0", "name":"否"}] class=validateClass/]--]
                        <div style="float: right;">
                            <select name="studentInfo.hmto" data-role="flipswitch">
                                <option value="0">否</option>
                                <option value="1" [#if studentInfo.hmto?? && studentInfo.hmto]selected[/#if]>是</option>
                            </select>
                        </div>
                    [#elseif property == "studentInfo.accommodationed"]
                    [#--是否住宿--]
                    [#--[@fu.radios name="studentInfo.hmto" value=(studentInfo.hmto?string("1", "0"))!"0" data=[{"id":"1", "name":"是"}, {"id":"0", "name":"否"}] class=validateClass/]--]
                        <div style="float: right;">
                            <select name="studentInfo.accommodationed" data-role="flipswitch">
                                <option value="0">否</option>
                                <option value="1"
                                        [#if studentInfo.accommodationed?? && studentInfo.accommodationed]selected[/#if]>
                                    是
                                </option>
                            </select>
                        </div>
                    [#elseif property == "studentHome.province"]
                    [#--省份/自治区--]
                        [@fu.select name="studentHome.province.id" value=(studentHome.province.id)!0 class="province " + validateClass datavalue=(studentHome.province.id)!0/]
                    [#elseif property == "studentHome.city"]
                    [#--城市/地区--]
                        [@fu.select name="studentHome.city.id" value=(studentHome.city.id)!0 class="city " + validateClass datavalue=(studentHome.city.id)!0/]
                    [#elseif property == "studentHome.county"]
                    [#--区/县--]
                        [@fu.select name="studentHome.county.id" value=(studentHome.county.id)!0 class="county " + validateClass datavalue=(studentHome.county.id)!0/]
                    [#elseif property == "studentInfo.awards"]
                    [#--获奖情况--]
                        <textarea name="${config.property.property}" class="${validateClass}"
                                  maxlength="${config.property.length!30}"
                          [#if config.required]required[/#if]>${text(config.property.property)!}</textarea>
                    [#else]
                    	<input name="${config.property.property}" value="${text(config.property.property)!}"
                               maxlength="${config.property.length!30}" [#if config.required]required[/#if]>
                    [/#if]
                [#elseif "STD_PROPERTY_INPUT_TYPE_02" == (config.property.inputType.code)!""]
                    [#assign property=config.property.property + ".id"/]
                    [@fu.select name=property datavalue=(text(property))!0 class=validateClass/]
                [#elseif "STD_PROPERTY_INPUT_TYPE_90" == (config.property.inputType.code)!""]
                    [@propertyInfoValue config/]
                [#else]
                        <input name="${config.property.property}" value="${text(config.property.property)!}"
                               maxlength="${config.property.length!30}" [#if config.required]required[/#if]>
                [/#if]
            [#else]
                [@propertyInfoValue config/]
            [/#if]
        </div>
    </div>
</li>
[/#macro]

[#function hasConfig type]
    [#list configs as config]
        [#if config.type.id == type.id]
            [#return true/]
        [/#if]
    [/#list]
    [#return false/]
[/#function]