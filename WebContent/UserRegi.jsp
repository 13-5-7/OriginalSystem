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
		<a tabindex="-1"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
	</div>
	<h1>ユーザ登録</h1>
	<c:if test="${num == 0}">
		<div class="errorMessege">×既に登録されているユーザIDです×</div>
	</c:if>
	<c:if test="${num > 0}">
		<div class="comfirmMessege">●登録が完了しました●</div>
	</c:if>
	<form action="UserRegiCon" method="post" id="UserRegi">
		<table border="1">
			<tr>
				<th>項目</th>
				<th>入力欄</th>
			</tr>
			<tr>
				<td>ユーザID</td>
				<td><input type="text" name="userId" size="20" class="validate[required]"
						<c:if test="${num == 0}">value="${userId}"</c:if>></td>
			</tr>
			<tr>
				<td>パスワード</td>
				<td><input type="password" name="password" size="20" class="validate[required]"></td>
			</tr>
			<tr>
				<td>確認用パスワード</td>
				<td><input type="password" name="checkPassword" size="20" class="validate[required]"></td>
			</tr>
			<tr>
				<td>所属部署</td>
				<td>
					<select name="secName" id="secName">
					<c:forEach var="sections" items="${allSectionList}">
						<option value="${sections.secName}" class="validate[required]"
						<c:if test="${num == 0 and sections.secName == secName}"> selected</c:if>>${sections.secName}</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>メールアドレス</td>
				<td><input name="mailaddress" type="mail" class="validate[required,custom[email]]"
						<c:if test="${num == 0}">value="${mailaddress}"</c:if>></td>
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
		jQuery("#UserRegi").validationEngine();
	});
	</script>

</body>
</html>