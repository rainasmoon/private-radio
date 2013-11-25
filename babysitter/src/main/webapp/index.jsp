<%-- response.sendRedirect("account/user.action"); --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>babysitter make the change</title>
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
秋之回忆
</div>
<hr/>
<div>
《小丽》提醒您：今天天气不错，出去玩玩？ YES | NO
</div>
<hr/>
<div>
<ul>
  	<li>小丽<img src="img/1001.gif"/><a>打招呼</a> | <a>发信息</a> | <a>动一下</a></li>
	<li>小白<img src="img/1002.gif"/><a href="/chat/chat!input.action">打招呼</a> | <a>发信息</a> | <a>动一下</a></li>
	<li>小雨<img src="img/1003.gif"/><a>打招呼</a> | <a>发信息</a> | <a>动一下</a></li>
	<li>小内<img src="img/1004.gif"/><a>打招呼</a> | <a>发信息</a> | <a>动一下</a></li>
	<li>小花<img src="img/1005.gif"/><a>打招呼</a> | <a>发信息</a> | <a>动一下</a></li>
</ul>
</div>
<hr/>

<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
