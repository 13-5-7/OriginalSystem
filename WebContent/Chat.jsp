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
<title>チャットroom画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1"></div>
	<h1><%=request.getAttribute("candiName") %>さん　チャットルーム</h1>
	<form action="ChatCon" method="post" id="chatForm">
	<a href="CandidateListCon" id="backToList">応募者一覧に戻る</a>
	<%if(request.getAttribute("error") != null) {
		int error = (int)request.getAttribute("error");
		if(error == 10){%>
	<div class="errorMessege">×リロードは使用できません×</div>
		<%}
	}%>
	<div class="chatList">
		<table>
			<thead>
				<tr>
					<th>名前</th>
					<th>投稿日付</th>
					<th>文章</th>
					<input type="hidden" name="infoNo" value="<%=request.getAttribute("infoNo")%>">
				</tr>
			</thead>
		<%@ page import="model.MessageBean" %>
		<%@ page import="java.util.ArrayList" %>
		<%@ page import="java.text.SimpleDateFormat"%>
		<%if(request.getAttribute("messageList") != null){
			ArrayList<MessageBean> messageList = (ArrayList<MessageBean>)request.getAttribute("messageList");
			if(messageList.size() == 0){%>
			<tbody>
				<tr>
					<td class="chat"></td>
					<td class="chat"></td>
					<td class="chat"></td>
				</tr>
			<%}else{
				for(int i = 0; i < messageList.size(); i++){
					MessageBean messageBean = messageList.get(i);%>
				<tr>
				<%if(request.getAttribute("userIdSession") != null){
					String userIdSession = (String)request.getAttribute("userIdSession");
					if(userIdSession.equals(messageBean.getUserId())){%>
					<td class="sameId"><%=messageBean.getUserId() %></td>
					<td class="sameId"><%=new SimpleDateFormat("MM/dd HH:mm").format(messageBean.getDateTime()) %></td>
					<td class="sameId"><%=messageBean.getMessage() %></td>
					<%}else{ %>
					<td class="chat"><%=messageBean.getUserId() %></td>
					<td class="chat"><%=new SimpleDateFormat("MM/dd HH:mm").format(messageBean.getDateTime()) %></td>
					<td class="chat"><%=messageBean.getMessage() %></td>
					<%}%>
				</tr>
			</tbody>
				<%}
				}
			}
		}%>
		</table>
	</div>
	<table summary="送信フォーム" class="chatWindow">
		<tr>
			<th style="width:120px">名前</th>
			<td class="chat"><input type="text" readonly name="userId" style="width:100%" class="validate[required,maxSize[10]]" value="<%=request.getAttribute("userIdSession")%>"/></td>
		</tr>
		<tr>
			<th>文章(50字以内)</th>
			<td class="chat"><input type="text" name="message" style="width:100%" class="validate[required,maxSize[50]]"/></td>
		</tr>
	</table>
	</div>
		<br>
		<div class="button">
			<input type="submit" value="送　信" class="button" onclick="return confirm('送信しますか？')">&emsp;&emsp;&emsp;
			<input type="reset" class="button" value="リセット">
		</div>
	</form>
	<script>
	jQuery(document).ready(function(){
		jQuery("#chatForm").validationEngine();
	});
	</script>
	<script>
	// reloadを禁止する方法
	// F5キーによるreloadを禁止する方法
	document.addEventListener("keydown", function (e) {

	    if ((e.which || e.keyCode) == 116 ) {
	        e.preventDefault();
	        alert( 'Reload禁止' );
	    }

	});
	</script>
</body>
</html>