<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>family-tree</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/utils.js" type="text/javascript"></script>
	<script type="text/javascript">
function sub(){
	var name = document.getElementById("filter_LIKES_name").value;	 
	$.ajax({
		url:"people!search.action?filter_LIKES_name=" + name,
		type:'post',
		data:"SUCCESS",
		success:function(data){			
			$("#content").html(decodeURI(data));
		
		},
		error:function(){$("#error").html(decodeURI(data));}
	});
}
</script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>
<div>Family list</div>

<div id="content">
		<s:iterator value="marriageList">
		<ul>
			<li>${husband.name}&nbsp;</li>
			<li>${wife.name}&nbsp;</li>
			<li><a href="../children/children.action?marriageId=${ id }">view it children</a></li>
		</ul>
	</s:iterator>
</div>
<div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
