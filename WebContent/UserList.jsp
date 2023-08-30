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
<title>ユーザ一覧画面</title>
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
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
	</div>
	<form method="post" action="UserUpdateCon"></form>
	<h1>ユーザ一覧</h1>
	<% if(request.getAttribute("result") != null){
		int num = (int)request.getAttribute("result");

		if(num == 0){%>
	<div class="errorMessege">×既に登録されているユーザです×</div>
		<% }else if(num == 1) {%>
	<div class="comfirmMessege">○更新が完了しました○</div>
		<% }else if(num == 2){ %>
	<div class="errorMessege">×更新済みエラーです×</div>
		<% }else if(num == 3){ %>
	<div class="errorMessege">×削除済みエラーです×</div>
		<%}else if(num == 10){%>
	<div class="errorMessege">×部署が存在しません。部署登録を先に済ませてください。×</div>
		<%}
	}%>
	<% if(request.getAttribute("resultDelete") != null){
		int num = (int)request.getAttribute("resultDelete");
		if(num >= 1){%>
	<div class="comfirmMessege">○削除が完了しました○</div>
		<%}
	}%>
	<p>更新画面よりパスワードを閲覧ください。<br>応募者の希望部署に該当する部署に所属しているユーザの削除はできません。</p>
	<div class="userList">
		<table>
			<thead>
				<tr>
					<th>ユーザID</th>
					<th>パスワード</th>
					<th>所属部署</th>
					<th>項目選択</th>
				</tr>
			</thead>
		<%@ page import="model.UserBean" %>
		<%@ page import="java.util.ArrayList" %>
		<%if(request.getAttribute("allUserList") != null) {
			ArrayList<UserBean> allUserList = (ArrayList<UserBean>)request.getAttribute("allUserList");
			for(int num = 0; num < allUserList.size(); num++){
				UserBean userBean = allUserList.get(num);%>
			<tbody>
				<tr>
					<td><%=userBean.getUserId()%></td>
					<td>**********</td>
					<td><%=userBean.getSecName()%></td>
					<td>
						<a href="http://localhost:8080/OriginalSystem/UserUpdateCon?userNo=<%=userBean.getUserNo()%>" onclick="return confirm('更新しますか？')">更新</a>&emsp;
						<%if(request.getAttribute("count") != null) {
							ArrayList<Integer> deleteSecName = (ArrayList<Integer>)request.getAttribute("count");
							int count = deleteSecName.get(num);
							if(count > 0){%>
						<a tabindex="-1" id="nonActive">削除</a>
							<%}else{ %>
						<a href="http://localhost:8080/OriginalSystem/UserDeleteCon?userNo=<%=userBean.getUserNo()%>" onclick="return confirm('削除しますか？')">削除</a>
							<%}
						}%>
					</td>
				</tr>
			</tbody>
			<%}
		}%>
		</table>
	</div>
	<script type="text/javascript">
	//更新リンクのfunction
	function UpdateLink(userId){
		if(window.confirm("更新しますか？")){
			var form = document.forms[0];
			var input = document.getElementById(userId);
			form.appendChild(input);
			document.body.appendChild(form);
			form.submit(); //フォームを送信
		}else{
			return false;
		}
	}
	</script>
</body>
</html>