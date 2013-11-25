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

	function search(){
		$.getJSON("children!search.action?" + $("form").serialize(), function(data) {	
			var html="<ul>";
			
			for (var i = 0; i<data.peopleList.length; i++) {											
				html += "<li>" + data.peopleList[i].name + "&nbsp;</li>";
				html += "<li>" + data.peopleList[i].sex + "&nbsp;</li>";	
				html += "<li><a href=\"javascript:makeChild(" + data.peopleList[i].id + ");\">make a child</a>&nbsp;</li>";
			    html += "<br/>";
			}
			
			html += "</ul>";
			
			$('#content').html(html);			
					
		});
	}
	
	function makeChild(id) {
		$.getJSON("children!child.action?peopleId=" + id, function(data) {	
			refreshChildren();				
		});
	}
	
	function abandonChild(id) {
		$.getJSON("children!delete.action?childrenId=" + id, function(data) {	
			refreshChildren();				
		});
	}
	
	function refreshChildren() {
		$.getJSON("children!children.action", function(data) {	
            var html="";			
			for (var i = 0; i<data.children.length; i++) {	
				html += "<ul id=\"child" + data.children[i].id + "\">";
				html += "<li>" + data.children[i].marriage.husband.name + "&nbsp;</li>";
				html += "<li>" + data.children[i].people.name + "&nbsp;</li>";	
				html += "<li><a href=\"javascript:abandonChild(" + data.children[i].id + ");\">abandon</a>&nbsp;</li>";
			    
			    html += "</ul>";
			}
			
			$('#children').html(html);	
		});
	}
</script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>
</div>
<div>children list</div>

<div>family: ${ marriage.husband.name } + ${ marriage.wife.name }</div>

<div id="children">
<s:iterator value="childrenList">
		<ul id="child${people.id}">
			<li>${marriage.husband.name}&nbsp;</li>
			<li>${people.name}&nbsp;</li>
			<li><a href="javascript:abandonChild(${ id });">abandon</a></li>
		</ul>
	</s:iterator>
</div>

<div>
	<form id="mainForm" >
	    <div id="error"></div>
		<div id="message">
			<s:actionmessage theme="custom" cssClass="success" />
		</div>
		<div id="filter">
			name: <input type="text" id="filter_LIKES_name" name="filter_LIKES_name"
				value="${param['filter_LIKES_name']}" size="9" /> <input
				type="button" value="search" onclick="search();" />
		</div>		
	</form>
</div>

<div id="content">
<s:iterator value="peopleList">
		<ul>
			<li>${name}&nbsp;</li>
			<li>${sex}&nbsp;</li>
			<li><a href="${ id }">make a child</a></li>
		</ul>
	</s:iterator>
</div>

<div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
