<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<div id="hd">
	<div id="title">
		<h1>family-tree</h1>
		<h2>--Demo</h2>
	</div>
	<div id="menu">
		<ul>
		    <li><a href="${ctx}/people/people.action">people list</a></li>
		    <li><a href="${ctx}/marriage/marriage.action">marriage list</a></li>
		    <li><a href="${ctx}/people/people!input.action">add people</a></li>
		    <li><a href="${ctx}/map/map.action">map</a></li>
		    <li><a href="${ctx}/map/map.action">map</a></li>			    
		</ul>
	</div>
</div>