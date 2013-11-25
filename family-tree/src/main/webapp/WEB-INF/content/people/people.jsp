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
	
	$(document).keypress(function(event) {
		  if ( event.which == 13 ) {
		     event.preventDefault();
		     search();
		   } 
	});

	
	function search(){
		$.getJSON("people!search.action?" + $("form").serialize(), function(data) {	
			var html="<ul>";
			
			for (var i = 0; i<data.peopleList.length; i++) {											
				html += "<li>" + data.peopleList[i].name + "&nbsp;</li>";
				html += "<li>" + data.peopleList[i].sex + "&nbsp;</li>";	
				html += "<li><a href=\"javascript:love(" + data.peopleList[i].id + ");\">love each other</a>&nbsp;</li>";
			    html += "<br/>";
			}
			
			html += "</ul>";
			
			$('#content').html(html);			
					
		});
	}
	
	function love(id) {
		$.getJSON("people!love.action?peopleId=" + id, function(data){
			
			if (data.people.sex == "Male") {
				$('#husband').text("husband:" + data.people.name);
			}
			else {
				$('#wife').text("wife:" + data.people.name);
			}
		});
	}
	
	function marry() {
		$.getJSON("people!marry.action", function(data){			
			if (data.error != null) {
				alert(data.error);
			}			
			else {
				$('#message').text(data.message).show().fadeOut(2000);
				$('#husband').text("husband:");
				$("#wife").text("wife:");
			}
		});
	}
</script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>
<div>
	<div id="husband">husband:${ husband.name }&nbsp;</div>
	<div id="wife">wife:${ wife.name }&nbsp;</div>
	<div><a href="javascript:marry();">marry</a><div>	
</div>
<div id="message" style="display:none;"></div>
<div>
	<form>
	    
		<div id="filter">
			name: <input type="text" id="filter_LIKES_name" name="filter_LIKES_name"
				value="${param['filter_LIKES_name']}"/> <input
				type="button" value="search" onclick="search();" />
		</div>		
	</form>
</div>


<div id="content">
		
</div>
<div>
		
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
