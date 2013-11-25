<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>input people</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {			
			$("#name").focus();			
			$("#inputForm").validate({
				rules: {					
					name: "required",
					sex: "required"
				}
			});
		});
		
		$(document).keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			     savePeople();
			   } 
		});
		
		function savePeople() {	
			
			$.get("people!save.action?" + $("form").serialize(), function(data) {
				$('#message').text(data).show().fadeOut(2000);
			});
		}
		 
		$(document).keypress(function(event) {
			  if ( event.which == 13 ) {
				  
				  savePeople();				  
			   }
			  window.event.preventDefault();
			});

	</script>
</head>

<body id="content">
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>
<div id="message" style="display:none;"></div>
<form id="inputForm" ac>
        
	<div>name:<input type="text" name="name"  id="name" value="${name}"/></div>
	<div>sex:<input type="radio" id="sex_male" name="sex" value="Male" checked>Male</input><input id="sex_female" type="radio" name="sex" value="Female">Female</input></div>
	<div><input class="button" type="button" value="save" onclick="savePeople();"/></div>
</form>

<div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
