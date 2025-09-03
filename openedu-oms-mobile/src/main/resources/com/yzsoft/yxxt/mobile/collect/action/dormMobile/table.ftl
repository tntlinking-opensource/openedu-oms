[#ftl]
<ul data-role="listview" class="yx-xxtx-data">
    <li>
        <div class="yx-xxtx-data-a ui-grid-a">
            <span class="ui-block-a yx-xxtx-data-a-title">收费项目</span>
            <span class="ui-block-b yx-xxtx-data-a-title">金额（元）</span>
        </div>
    </li>
        <li>
            <span class="yx-xxtx-data-a ui-grid-a">
                <span class="ui-block-a">${(financeStudentItem.item.name)!}</span>
                <span class="ui-block-b">${(financeStudentItem.feeTotal)!}</span>
            </span>
        </li>
</ul>