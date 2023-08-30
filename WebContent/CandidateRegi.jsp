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
<title>応募者登録画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a tabindex="-1" class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
	</div>
	<h1>応募者登録</h1>
	<% if(request.getAttribute("extension") != null) {
		boolean extension = (boolean)request.getAttribute("extension");%>
		<div class="errorMessege">×その拡張子はサポートされていません×</div>
	<%} %>
	<% if(request.getAttribute("result") != null) {
		int num = (int)request.getAttribute("result");
		if(num == 0){%>
		<div class="errorMessege">×既に登録されている応募者です×</div>
		<%}else if(num == 1){%>
		<div class="comfirmMessege">○登録が完了しました○</div>
		<%}
	}%>
	<form action="CandidateRegiCon" method="post" enctype="multipart/form-data" id="candidateForm">
		<table border="1">
			<tr>
				<th>項目</th>
				<th>入力欄</th>
			</tr>
			<tr>
				<td>No</td>
				<td><input type="text" disabled name="infoNo" size="30" placeholder="自動採番で入力されます"></td>
			</tr>
			<tr>
				<td>希望部署</td>
				<td>
				<select name="secName">
				<c:forEach var="sections" items="${allSectionList}">
					<option value="${sections.secName}"
					<c:if test="${secName == sections.secName}">selected</c:if>>${sections.secName}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>名前</td>
				<%@ page import="model.InfoBean" %>
				<%if(request.getAttribute("infoBean") != null) {
					InfoBean infoBean = (InfoBean)request.getAttribute("infoBean");%>
				<td><input type="text" name="candiName" size="30" class="validate[required]" value="<%=infoBean.getCandiName()%>"></td>
			</tr>
			<tr>
				<td>応募種類</td>
					<%if(infoBean.getCandiType() == 0) {%>
				<td><input type="radio" name="candiType" value="0" class="validate[required]" selected>新卒&emsp;&emsp;&emsp;
					<input type="radio" name="candiType" value="1" class="validate[required]">中途</td>
					<%}else{ %>
				<td><input type="radio" name="candiType" value="0" class="validate[required]">新卒&emsp;&emsp;&emsp;
					<input type="radio" name="candiType" value="1" class="validate[required]" selected>中途</td>
					<%} %>
			</tr>
			<tr>
				<td>応募書類</td>
				<td><input type="file" name="candiPdf"></td>
			</tr>
			<tr>
				<td>選考段階</td>
				<td><input type="text" name="candiStage" size="30" value="<%=infoBean.getCandiStage()%>" class="validate[required]"></td>
			</tr>
			<tr>
				<td>登録日付</td>
				<td><input name="regiDate" type="date" class="validate[required]" value="<%=infoBean.getRegiDate()%>"></td>
				<%}else{
					InfoBean infoBean = (InfoBean)request.getAttribute("infoBean");%>
				<td><input type="text" name="candiName" size="30" class="validate[required]"></td>
			</tr>
			<tr>
				<td>応募種類</td>
				<td>
					<input type="radio" name="candiType" value="0" class="validate[required]">新卒&emsp;&emsp;&emsp;
					<input type="radio" name="candiType" value="1" class="validate[required]">中途
				</td>
			</tr>
			<tr>
				<td>応募書類</td>
				<td><input type="file" name="candiPdf"></td>
			</tr>
			<tr>
				<td>選考段階</td>
				<td><input type="text" name="candiStage" size="30" value="書類選考" class="validate[required]"></td>
			</tr>
			<tr>
				<td>登録日付</td>
				<td><input name="regiDate" type="date" class="validate[required]" value="${today}"></td>
				<%} %>
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
		jQuery("#candidateForm").validationEngine();
	});
	</script>
</body>
</html>