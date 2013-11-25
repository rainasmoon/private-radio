<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>

<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/utils.js" type="text/javascript"></script>

<script type="text/javascript">
function sub(){
	var name = document.getElementById("filter_LIKES_name").value;	 
	$.ajax({
		url:"people!list.action?filter_LIKES_name=" + name,
		type:'post',
		data:"SUCCESS",
		success:function(data){			
			$("#content").html(decodeURI(data));
		
		},
		error:function(){$("#error").html(decodeURI(data));}
	});
}
</script>

<div>
	
	<div id="error"></div>
		<div id="message">
			<s:actionmessage theme="custom" cssClass="success" />
		</div>
		<div id="filter">
			name: <input type="text" id="filter_LIKES_name"
				value="${param['filter_LIKES_name']}" size="9" /> <input
				type="button" value="search" onclick="sub();" />
		</div>		

</div>

<div>
	<a href="people!input.action">add people</a>
</div>
<div id="content">
		<s:iterator value="peopleList">
		<ul>
			<li>${name}&nbsp;</li>
			<li>${sex}&nbsp;</li>

		</ul>
	</s:iterator>
</div>
