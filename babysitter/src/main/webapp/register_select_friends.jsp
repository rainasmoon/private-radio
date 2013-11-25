<%-- response.sendRedirect("account/user.action"); --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 帐号管理</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/utils.js" type="text/javascript"></script>
</head>

<body>
<div id="doc3">
<%@ include file="/common/header.jsp" %>


<div>
选五个女友？
</div>
<hr/>
<div>
<a>看看排行榜</a> | <a>随机五个</a>
</div>
<hr/>
<div>
<li><img src="img/1001.gif"/>
<li><img src="img/1002.gif"/>
<li><img src="img/1003.gif"/>
<li><img src="img/1004.gif"/>
<li><img src="img/1005.gif"/>
</div>
<hr/>
<div>
<a href="index.jsp">next</a>
</div>

<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
