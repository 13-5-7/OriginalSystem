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
<title>部署更新画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募報登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a tabindex="-1" class="menu">部署一覧</a>
	</div>
	<a href="SectionListCon" id="backToList">部署一覧に戻る</a>
	<form method="post" action="SectionUpdateCon" id="formID">
	<table border="1">
		<tr>
			<th>項目</th>
			<th>詳細内容</th>
		</tr>
		<tr>
			<td>No</td>
			<%@ page import="model.SecBean" %>
			<%@ page import="java.text.SimpleDateFormat"%>
			<%if(request.getAttribute("infoBean") != null){
				SecBean secBean = (SecBean)request.getAttribute("infoBean");%>
			<td><input readonly type="text" size="15" name="secNo" value="<%=secBean.getSecNo() %>"></td>
		</tr>
		<tr>
			<td>部署名</td>
			<td>
				<input type="hidden" name="dateTime" value="<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(secBean.getDateTime())%>">
				<input type="text" size="15" name="secName" value="<%=secBean.getSecName() %>" class="validate[required]">
			</td>
		</tr>
			<%}%>
	</table>
	<br>
	<br>
		<div class="button">
			 <input type="submit" value="更　新" class="button" onclick="return confirm('更新しますか？')">&emsp;&emsp;&emsp;
			 <input type="reset" class="button" value="リセット">
		</div>
	</form>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		jQuery("#formID").validationEngine();
	});
	</script>
</body>
</html>