<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<div id="hd">
	<div id="title">
		<h1>BabySitter</h1>
		<h2>--Make changes</h2>
	</div>
	<div id="menu">
		<ul>
		 	<li><a href="${ctx}/">主页</a></li>
			<li><a href="${ctx}/account/user!input.action">注册</a></li>
			<li><a href="${ctx}/account/user.action">帐号列表</a></li>
			<li><a href="${ctx}/account/role.action">角色列表</a></li>
			<li><a href="${ctx}/j_spring_security_logout">退出登录</a></li>
		</ul>
	</div>
</div>