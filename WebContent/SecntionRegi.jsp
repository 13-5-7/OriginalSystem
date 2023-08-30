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
<title>部署登録画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募者登録</a>
		<a tabindex="-1" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
	</div>
	<h1>部署登録</h1>
	<%if(request.getAttribute("num") != null) {
		int num = (int)request.getAttribute("num");
		if(num == 0){%>
	<div class="errorMessege">×既に登録されている部署です×</div>
		<%}else{%>
	<div class="comfirmMessege">●登録が完了しました●</div>
		<%}
		}%>
	<form action="SectionRegiCon" method="post" id="SectionRegi">
		<table border="1">
			<tr>
				<th>項目</th>
				<th>入力欄</th>
			</tr>
			<tr>
				<td>部署NO</td>
				<td><input disabled type="text" name="secNo" size="20" placeholder="自動採番で入力されます"></td>
			</tr>
			<tr>
				<td>部署名</td>
				<td><input type="text" name="secName" size="20" class="validate[required]"></td>
			</tr>
		</table>
		<br>
		<br>
		<div class="button">
			<input type="submit" value="登　録" class="button" onclick="return confirm('登録しますか？')">&emsp;&emsp;&emsp;
			<input type="reset" class="button" value="リセット">
		</div>
	</form>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("#SectionRegi").validationEngine();
	});
	</script>

</body>
</html>