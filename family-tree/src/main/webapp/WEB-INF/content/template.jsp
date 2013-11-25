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
	
	$(document).ready(function() {			
		$("#name").focus();	
	});

</script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>

<div>
	<form id="mainForm" action="people.action" method="get">
	    <div id="error">
	        <s:actionerror theme="custom" cssClass="fail" />
	    </div>
		<div id="message">
			<s:actionmessage theme="custom" cssClass="success" />			
		</div>
		<div id="filter">
			name: <input type="text" id="filter_LIKES_name" name="filter_LIKES_name"
				value="${param['filter_LIKES_name']}"/> <input
				type="button" value="search" onclick="search();" />
		</div>		
	</form>
</div>

<div id="content">
		<s:iterator value="peopleList">
		<ul>
			<li>${name}&nbsp;</li>
			<li>${sex}&nbsp;</li>
			<li><a href="people!love.action?peopleId=${ id }">love each other</a></li>
		</ul>
	</s:iterator>
</div>



<div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
