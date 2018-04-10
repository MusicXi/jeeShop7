Freemarker当前版本---->>
<@freemarker> 
${userName}
</@freemarker> 
${systemSetting().getVersion()}
<#--
${request.contextPath}
${request.getContextPath() }
${basepath}
-->


<script type="text/javascript" src="resource/js/jquery.min.js"></script>
<script>
$(function(){
	$.ajax({
			url:"jsonDate",
			data:"",
			dateType:"json",
			type:"POST",
			async : false,
			cache : false,
			success:function(result){
				alert(jQuery.parseJSON(result).errmsg);
				},error:function(){
			}
		});	
});

</script>
