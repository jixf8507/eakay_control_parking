<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<center>地锁设备列表(${parkingDevices.size()})</center>
<body>
	<table border="1" width="98%" style="text-align: center;">
		<tr>
			<td>地锁服务名称</td>
			<td>IP</td>
			<td>URL</td>
			<td></td>
		</tr>
		<c:forEach items="${parkingServers}" var="mymap">
			<tr>
				<td>${mymap.ip}</td>
				<td>${mymap['serverURL']}</td>
				<td><a href="${ctx}/manager/devices.htm?serverId=${mymap.id}">查看</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>