
<script type="text/javascript">
	//<![CDATA[
	document.write('<input type="hidden" id="datepicker" name="datepicker" />');
	
	$(function() {
		$("#datepicker").datepicker({
			defaultDate: "01/${moiscourant+1}/${anneeselectionnee}"
		}).change(function() {
			$("#form").submit();
		});
	});
	//]]>
</script>