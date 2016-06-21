<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<center>${deviceKey}</center>
	<table border="1" width="98%" style="text-align: center;">
		<tr>
			<td>地锁口编号</td>
			<td>当前状态</td>
			<td>是否正在指令</td>
			<td>上次发送控制命令</td>
			<td>上次发送控制命令时间</td>
			<td>上次执行控制结束时间</td>
			<td>上次控制结果</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${dervicePorts}" var="mymap">
			<tr>
				<td>${mymap.port}</td>
				<td>${mymap['currStatus']}</td>
				<td>${mymap['control']}</td>
				<td>${mymap['lastControlCmd']}</td>
				<td>${mymap['lastControlTime']}</td>
				<td>${mymap['lastControlFinishTime']}</td>
				<td>${mymap['lastControlResult']}</td>
				<td><a
					href="${ctx}/control/up.htm?deviceKey=${deviceKey}&port=${mymap.port}">升起地锁</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
					href="${ctx}/control/down.htm?deviceKey=${deviceKey}&port=${mymap.port}">降下地锁</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
					href="${ctx}/control/query.htm?deviceKey=${deviceKey}&port=${mymap.port}">询问状态</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>