[#ftl]
[#include "/template/importForm.ftl"/]
[@importForm src="!importData" title="学生照片导入" ext="zip" formTarget=""]
<div class="alert alert-info" style="margin-top: 20px;">
    <h4>学生照片导入说明</h4>
    <ol>
        <li>只能导入zip格式的压缩文件（不能直接使用rar格式文件修改后缀为zip）。</li>
        <li>照片文件名的格式为"考生号_其它信息.jpg"。</li>
        <li>单张上传照片大小不要超过100K，zip包大小不要超过50M。</li>
        <li>如果学生的照片已经存在，系统会将该生的照片覆盖。</li>
    </ol>
</div>
[/@importForm]
