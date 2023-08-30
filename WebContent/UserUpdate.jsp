<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="css/Style.css">
<link rel="stylesheet" type="text/css" href="css/validationEngine.jquery.css">

<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="UTF-8"></script>
<script src="js/languages/jquery.validationEngine-ja.js" type="text/javascript" charset="UTF-8"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザ更新画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a tabindex="-1" class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
	</div>
	<table border="1">
	<a href="UserListCon" id="backToList">ユーザ一覧に戻る</a>
	<form actions="UserUpdateCon" method="post" id="form">
		<tr>
			<th>項目</th>
			<th>詳細内容</th>
		</tr>
		<tr>
			<td>ユーザNO</td>
			<%@ page import="model.UserBean" %>
			<%@ page import="model.SecBean" %>
			<%@ page import="java.text.SimpleDateFormat"%>
			<%if(request.getAttribute("infoBean") != null) {
				UserBean userBean = (UserBean)request.getAttribute("infoBean");%>
			<td><input type="text" readonly name="userNo" value="<%=userBean.getUserNo()%>"></td>
		</tr>
		<tr>
			<td>ユーザID</td>
			<td>
				<input type="text" name="userId" value="<%=userBean.getUserId()%>" class="validate[required]">
				<input type="hidden" name="curUserId" value="<%=userBean.getUserId()%>">
			</td>
		</tr>
		<tr>
			<td>パスワード</td>
			<td><input type="text" name="password" value="<%=userBean.getPassword() %>" class="validate[required]"></td>
		</tr>
		<tr>
			<td>所属部署</td>
			<td><input type="hidden" name="dateTime" value="<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(userBean.getDateTime())%>">
				<select name="secName">
				<%@ page import="model.InfoBean" %>
				<%@ page import="java.util.ArrayList" %>
				<%if(request.getAttribute("allSectionList") != null){
					ArrayList<SecBean> allSectionList = (ArrayList<SecBean>)request.getAttribute("allSectionList");
					for(int i = 0; i < allSectionList.size(); i++){
						SecBean secBean = allSectionList.get(i);
						if(secBean.getSecName().equals(userBean.getSecName())){%>
					<option value="<%=secBean.getSecName()%>" selected><%=secBean.getSecName()%></option>
					  <%}else{%>
					<option value="<%=secBean.getSecName()%>"><%=secBean.getSecName()%></option>
					<%}
					}
				}%>
				</select>
			</td>
			<%} %>
		</tr>
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
		jQuery("#form").validationEngine();
	});
	</script>
</body>
</html>