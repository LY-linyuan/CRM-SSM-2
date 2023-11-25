<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<head>
<meta charset="UTF-8">
	<base href="<%=base%>">
</head>
<body>
	<script type="text/javascript">
		document.location.href = "settings/qx/user/toLogin";
	</script>
</body>
</html>
