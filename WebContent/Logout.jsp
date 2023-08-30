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
<title>ログアウト画面</title>
</head>
<body>
	<div class="logout">●ログアウトしました●</div>
	<div class="loginLink"><a href="LoginCon">ログイン</a>画面に戻ります。</div>
<script>
//ブラウザの戻るボタンを無効にする
history.pushState(null, null, location.href);
window.addEventListener('popstate', (e) => {
	history.go(1);
	alert("ブラウザの戻るボタンは使用できません。");
});
</script>
</body>
</html>