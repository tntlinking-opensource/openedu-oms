[#ftl]
[#macro print]
    [@b.head/]
<style>
	body { background: #fff; }
	.print_body { width: 180mm; margin: 5mm auto; }
	.panel-primary > .panel-heading { background: #fff; color: #000; padding: 5px 0; border: none; margin: 2mm 0; }
	.panel-body { padding: 0; }
	.panel-primary { border: none; }
	.table-bordered, .table-bordered td, .table-bordered th { border: 1px solid #000 !important; }
	.page-footer { display: none; }
	.panel-body table:last-child { margin: 0; }
	h4 { font-size: 14px; }
</style>

<div class="print_body">
    [#nested /]
</div>

<script>
	window.print();
</script>
    [@b.foot/]
[/#macro]