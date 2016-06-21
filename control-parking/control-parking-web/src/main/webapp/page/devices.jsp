<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<center>地锁设备列表(${parkingDevices.size()})
</center>
<body>
	<table border="1" width="98%" style="text-align: center;">
		<tr>
			<td>地锁设备编号</td>
			<td>地锁数量</td>
			<td>最后在线时间</td>
			<td>当前状态</td>
			<td></td>
		</tr>
		<c:forEach items="${parkingDevices}" var="mymap">
			<tr <c:if test="${!mymap['online']}">bgcolor="red"</c:if>>
				<td>${mymap.deviceKey}</td>
				<td>${mymap['portCount']}</td>
				<td>${mymap['lastReciveTime']}</td>
				<td>${mymap['online']}</td>
				<td><a
					href="${ctx}/manager/ports.htm?serverId=${serverId}&deviceKey=${mymap.deviceKey}">查看</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>