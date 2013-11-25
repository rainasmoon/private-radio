<%-- response.sendRedirect("account/user.action"); --%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babysitter 开始神奇之旅</title>
<%@ include file="/common/meta.jsp"%>
<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/utils.js" type="text/javascript"></script>
</head>

<body>
	<div id="doc3">
		<%@ include file="/common/header.jsp"%>
		<div id="bd">
			<div id="yui-main">
				<div class="yui-b">
					<h2>注册新用户么?</h2>
					<form id="inputForm" action="user!register.action" method="post">
						<!-- glen begin -->						
						<hr />
						<div>
							<ul>
								<li>ID（推荐使用常用邮箱）:<input type="text" name="loginName" size="40" id="loginName" value="${loginName}"/>
								</li>
								<li>密码:<input></input>
								</li>
								<li>确认密码:<input></input>
								</li>
								<li>昵称:<input></input>
								</li>
								<li>性别:<input></input>
								</li>
								<li>年龄:<input></input>
								</li>
								<li>一句话介绍:<input></input>
								</li>
							</ul>
						</div>
						<hr />
						<div>
							<a href="javascript:inputForm.submit();">next</a>
						</div>
						<!-- glen end -->
					</form>
				</div>
			</div>
		</div>
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>

