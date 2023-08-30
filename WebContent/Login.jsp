<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/Style.css">
<link rel="stylesheet" type="text/css" href="css/validationEngine.jquery.css">

<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js" type="text/javascript" charset="UTF-8"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1"></div>
	<h1>ログイン画面</h1>
	<c:if test="${message != null}">
	<div class="errorMessege">${message}</div>
	</c:if>
	<form action="LoginCon" method="post" id="form" >
			<div class="loginId">ログインID
			<%if(request.getAttribute("userId") != null) {
				String userId = (String)request.getAttribute("userId");%>
				<input type="text" name="userId" size="25" value="<%=userId%>" class="validate[required]">
			<%}else{ %>
				<input type="text" name="userId" size="25" class="validate[required]">
			<%} %>
			</div>
			<div class="password">パスワード
				<input type="password" name="password" size="25" class="validate[required]">
			</div>
		<div class="button">
			<input type="submit" value="ログイン" class="button" onclick="return confirm('ログインしますか？')">&emsp;&emsp;
			<input type="reset" class="button" value="リセット">
		</div>
	</form>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("#form").validationEngine();
	});
	</script>
</body>
</html>