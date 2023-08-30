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
<title>応募者更新画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a tabindex="-1" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
	</div>

	<a href="CandidateListCon" id="backToList">応募者一覧に戻る</a>
	<form method="post" action="CandidateUpdateCon" enctype="multipart/form-data" id="formID">
	<table  border="1">
			<%@ page import="model.InfoBean" %>
			<%@ page import="java.text.SimpleDateFormat"%>
			<%if(request.getAttribute("infoBean") != null) {
				InfoBean infoBean = (InfoBean)request.getAttribute("infoBean");%>
			<tr>
				<th>項目</th>
				<th class="candiUpdate">詳細内容</th>
			</tr>
			<tr>
				<td>No</td>
				<td><input type="text" readonly name="infoNo" value="<%=infoBean.getInfoNo()%>" size="20"></td>
			</tr>
			<tr>
				<td>希望部署</td>
				<td>
				<select name="secName">
				<%@ page import="model.SecBean" %>
				<%@ page import="java.util.ArrayList" %>
				<%if(request.getAttribute("allSectionList") != null){
					ArrayList<SecBean> allSectionList = (ArrayList<SecBean>)request.getAttribute("allSectionList");
					for(int i = 0; i < allSectionList.size(); i++){
						SecBean secBean = allSectionList.get(i);
						InfoBean infoSecBean = (InfoBean)request.getAttribute("infoBean");
						if(secBean.getSecName().equals(infoSecBean.getSecName())){%>
					<option value="<%=secBean.getSecName()%>" selected><%=secBean.getSecName()%></option>
						<%}else{%>
					<option value="<%=secBean.getSecName()%>"><%=secBean.getSecName()%></option>
						<%}
					}
				}%>
				</select>
				</td>
			</tr>
			<tr>
				<td>名前</td>
				<td><input type="text" name="candiName" value="<%=infoBean.getCandiName()%>" class="validate[required]" size="20"></td>
			</tr>
			<tr>
				<td>応募種類</td>
					<%if(infoBean.getCandiType() == 0){%>
            	<td><input type="radio" name="candiType" value="0" class="validate[required]" checked>新卒&emsp;
            		<input type="radio" name="candiType" value="1" class="validate[required]">中途</td>
					<%}else if(infoBean.getCandiType() == 1){ %>
				<td><input type="radio" name="candiType" value="0" class="validate[required]">新卒&emsp;&emsp;&emsp;
            		<input type="radio" name="candiType" value="1" class="validate[required]" checked>中途</td>
            		<%} %>
            </tr>
            <tr>
            	<td>応募書類</td>
            	<td>
            		<input type="hidden" name="dateTime" value="<%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(infoBean.getDateTime())%>">
            		<input type="file" name="candiPdf">
            	</td>
            </tr>
			<tr>
            	<td>選考段階</td>
				<td><input type="text" name="candiStage" value="<%=infoBean.getCandiStage()%>" class="validate[required]"></td>
			</tr>
			<tr>
				<td>面接日付</td>
				<td><input type="date" name="interDate" value="<%=infoBean.getInterDate()%>" class="validate[required]"></td>
			</tr>
			<tr>
				<td>面接時間</td>
				<td>
					<input type="time" name="interTime" value="<%=infoBean.getInterTime()%>" class="validate[required]" step="1800" list="data-list" min="7:00" max="18:00">
					<datalist id="data-list">
  						<option value="07:00"></option>
  						<option value="07:30"></option>
  						<option value="08:00"></option>
  						<option value="08:30"></option>
  						<option value="09:00"></option>
  						<option value="09:30"></option>
  						<option value="10:00"></option>
  						<option value="10:30"></option>
  						<option value="11:00"></option>
  						<option value="11:30"></option>
  						<option value="12:00"></option>
  						<option value="12:30"></option>
  						<option value="13:00"></option>
  						<option value="13:30"></option>
  						<option value="14:00"></option>
  						<option value="14:30"></option>
  						<option value="15:00"></option>
  						<option value="15:30"></option>
  						<option value="16:00"></option>
  						<option value="16:30"></option>
  						<option value="17:00"></option>
  						<option value="17:30"></option>
  						<option value="18:00"></option>
					</datalist>
				</td>
			</tr>
			<tr>
				<td>登録日付</td>
				<td><input type="date" name="regiDate" value="<%=infoBean.getRegiDate()%>" class="validate[required]"></td>
			</tr>
			<tr>
				<td>結果</td>
				<%if(infoBean.getCandiResult() == 0) {%>
            	<td><input type="radio" name="candiResult" value="0" class="validate[required]" checked>選考中&nbsp;
            		<input type="radio" name ="candiResult" value="1" class="validate[required]">通　過&nbsp;
					<input type="radio" name ="candiResult" value="2" class="validate[required]">不通過</td>
				<%}else if(infoBean.getCandiResult() == 1){ %>
				<td><input type="radio" name="candiResult" value="0" class="validate[required]">選考中&nbsp;
            		<input type="radio" name ="candiResult" value="1" class="validate[required]" checked>通　過&nbsp;
					<input type="radio" name ="candiResult" value="2" class="validate[required]">不通過</td>
				<%}else if(infoBean.getCandiResult() == 2){ %>
				<td><input type="radio" name="candiResult" value="0" class="validate[required]">選考中&nbsp;
            		<input type="radio" name ="candiResult" value="1" class="validate[required]" checked>通　過&nbsp;
					<input type="radio" name ="candiResult" value="2" class="validate[required]">不通過</td>
				<%} %>
			</tr>
			<%} %>
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