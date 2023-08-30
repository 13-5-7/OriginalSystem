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
<title>部署一覧画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href=CandidateRegiCon class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a href="CandidateListCon" class="menu">応募者一覧</a>
		<a tabindex="-1" class="menu">部署一覧</a>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
	</div>
	<h1>部署一覧</h1>
	<% if(request.getAttribute("result") != null){
		int num = (int)request.getAttribute("result");

		if(num == 0){%>
	<div class="errorMessege">×既に登録されている部署です×</div>
		<% }else if(num == 1) {%>
	<div class="comfirmMessege">○更新が完了しました○</div>
		<% }else if(num == 2){ %>
	<div class="errorMessege">×更新済みエラーです×</div>
		<% }else if(num == 3){ %>
	<div class="errorMessege">×削除済みエラーです×</div>
		<%}
	}%>
	<% if(request.getAttribute("resultDelete") != null){
		int num = (int)request.getAttribute("resultDelete");
		if(num == 1){%>
	<div class="comfirmMessege">○削除が完了しました○</div>
		<%}else if(num == 10){%>
	<div class="errorMessege">×ユーザ部署名or応募者の希望部署をご確認ください。該当がある場合は削除できません。×</div>
		<%}
	}%>
	<form method="post" action="SectionUpdateCon"></form>
	<p>ユーザの所属部署、応募者の希望部署に<br>該当部署名がある場合は「更新／削除」ができません。</p>
	<div class="sectionList">
		<table>
			<thead>
				<tr>
					<th>部署NO</th>
					<th>部署名</th>
					<th>項目選択</th>
				</tr>
			</thead>
		<%@ page import="model.SecBean" %>
		<%@ page import="java.util.ArrayList" %>
		<%if(request.getAttribute("allSectionList") != null){
			ArrayList<SecBean> allSectionList = (ArrayList<SecBean>)request.getAttribute("allSectionList");
			ArrayList<Integer> secInfoList = (ArrayList<Integer>)request.getAttribute("secInfoList");
			for(int num = 0; num < allSectionList.size(); num++){
				SecBean secBean = allSectionList.get(num); %>
			<tbody>
				<tr>
					<td><%=secBean.getSecNo()%></td>
					<td><%=secBean.getSecName()%></td>
					<td>
			<%if(secInfoList.get(num) > 0) {%>
						<a tabindex="-1" id="nonActive">更新</a>&nbsp;&nbsp;&nbsp;
						<a tabindex="-1" id="nonActive">削除</a></td>
			<%}else{ %>
						<a href="http://localhost:8080/OriginalSystem/SecntionUpdateCon?secNo=<%=secBean.getSecNo()%>" onclick="return confirm('更新しますか？')">更新</a>&nbsp;&nbsp;&nbsp;
						<a href="http://localhost:8080/OriginalSystem/SectionDeleteCon?secNo=<%=secBean.getSecNo()%>" onclick="return confirm('削除しますか？')">削除</a></td>
			<%} %>
				</tr>
			</tbody>
			<%}
		}%>
		</table>
	</div>
	<script type="text/javascript">
	//更新リンクのfunction
	function UpdateLink(secCode){
		if(window.confirm("更新しますか？")){
			var form = document.forms[0];
			var input = document.getElementById(secCode)
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