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
<title>応募者一覧画面</title>
</head>
<body>
	<div class="top"><h2>採用管理システム</h2></div>
	<div class="top1">
		<%@ page import="model.UserBean" %>
		<%if(request.getAttribute("userBean") != null){
			UserBean userBean = (UserBean)request.getAttribute("userBean");
			if(userBean.getSecName().equals("人事部")){%>
		<a href="UserRegiCon"  class="menu">ユーザ登録</a>
		<a href="CandidateRegiCon" class="menu">応募者登録</a>
		<a href="SectionRegiCon" class="menu">部署登録</a>
		<a href="UserListCon"  class="menu">ユーザ一覧</a>
		<a tabindex="-1" class="menu">応募者一覧</a>
		<a href="SectionListCon" class="menu">部署一覧</a>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
			<%}else{ %>
		<a href="LogoutCon" class="menu" onclick="return confirm('ログアウトしますか？')">ログアウト</a>
			<%}
		}%>
	</div>
	<form method="post" action="CandidateListCon" target="CandidateView.jsp"></form>
	<h1>応募者一覧</h1>
	<% if(request.getAttribute("result") != null){
		int num = (int)request.getAttribute("result");

		if(num == 0){%>
			<div class="errorMessege">×既に登録されている応募者です×</div>
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
	<%if(request.getAttribute("resultDelete") != null) {
		int num = (int)request.getAttribute("resultDelete");
		if(num >= 1){%>
			<div class="comfirmMessege">○削除が完了しました○</div>
		<%}
	}%>
	<c:if test="${userBean.secName.equals('人事部')}">
		<p>応募者名が黒の場合は履歴書の登録が済んでいません。応募書類の登録をして下さい。</p>
	</c:if>
	<div class="candidateList">
		<table>
			<thead>
				<tr>
					<th>No</th>
					<th>希望部署</th>
					<th>名前</th>
					<th>応募種類</th>
					<th>選考段階</th>
					<th>面接日付</th>
					<th>面接時間</th>
					<th>登録日付</th>
					<th>結果</th>
					<th>チャット</th>
			<%@ page import="model.UserBean" %>
			<%if(request.getAttribute("userBean") != null){
				UserBean userBean = (UserBean)request.getAttribute("userBean");
				if(userBean.getSecName().equals("人事部")){%>
					<th>項目選択</th>
				<%}else{ %>
				<%}
			}%>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="bean" items="${allCandidateList}">
				<c:choose>
					<c:when test="${userBean.secName.equals('人事部')}">
				<tr>
					<td <c:if test="${bean.newRegi == 1}">class="new"</c:if>>${bean.infoNo}</td>
					<td>${bean.secName}</td>
					<td><input type="hidden" name="infoNo" value="${bean.infoNo}" id="${bean.infoNo}">
					<c:choose>
						<c:when test="${bean.candiPdf != null}">
						<a href="javascript:void(0)" onclick="ViewLink(${bean.infoNo});">${bean.candiName}</a>
						</c:when>
						<c:when test="${bean.candiPdf == null}">
						<a tabindex="-1" id="nonActive">${bean.candiName}</a>
						</c:when>
					</c:choose>
						</td>
					<c:choose>
            			<c:when test="${bean.candiType==0}">
            	    <td>新卒</td>
            			</c:when>
            			<c:otherwise>
                	<td>中途</td>
            			</c:otherwise>
        			</c:choose>
					<td><div class="stage">${bean.candiStage}</div></td>
					<td>${bean.interDate}</td>
					<td>${bean.interTime}</td>
					<td>${bean.regiDate}</td>
					<c:choose>
            			<c:when test="${bean.candiResult==0}">
            		<td><a href="http://localhost:8080/OriginalSystem/CandidateResultCon?candiResult=1&infoNo=${bean.infoNo}" onclick="return confirm('通過に変更しますか？')">通　過</a>&emsp;
						<a href="http://localhost:8080/OriginalSystem/CandidateResultCon?candiResult=2&infoNo=${bean.infoNo}" onclick="return confirm('不通過に変更しますか？')">不通過</a></td>
            			</c:when>
            			<c:when test="${bean.candiResult==1}">
     	       		<td class="yes">通　過</td>
            			</c:when>
            			<c:otherwise>
                	<td class="no">不通過</td>
            			</c:otherwise>
        			</c:choose>
					<td><a href="http://localhost:8080/OriginalSystem/ChatCon?infoNo=${bean.infoNo}" onclick="return confirm('チャットルームに移動しますか？')"
							<c:if test="${!empty bean.messageId and userBean.userId != bean.messageId}">id="lastMessage"</c:if>>No.${bean.infoNo} room</a></td>
					<td>
						<a href="http://localhost:8080/OriginalSystem/CandidateUpdateCon?infoNo=${bean.infoNo}" onclick="return confirm('更新しますか？')">更新</a>&nbsp;
						<a href="http://localhost:8080/OriginalSystem/CandidateDeleteCon?infoNo=${bean.infoNo}" onclick="return confirm('削除しますか？')">削除</a></td>
				</tr>
					</c:when>
					<c:when test="${!userBean.secName.equals('人事部') and bean.secName == userBean.secName}">
				<tr>
					<td <c:if test="${bean.newRegi == 1}">class="new"</c:if>>${bean.infoNo}</td>
					<td>${bean.secName}</td>
					<td><input type="hidden" name="infoNo" value="${bean.infoNo}" id="${bean.infoNo}">
					<c:choose>
						<c:when test="${bean.candiPdf != null}">
						<a href="javascript:void(0)" onclick="ViewLink(${bean.infoNo});">${bean.candiName}</a>
						</c:when>
						<c:when test="${bean.candiPdf == null}">
						<a tabindex="-1" id="nonActive">${bean.candiName}</a>
						</c:when>
					</c:choose>
						</td>
					<c:choose>
            			<c:when test="${bean.candiType==0}">
                	<td>新卒</td>
            			</c:when>
            			<c:otherwise>
                	<td>中途</td>
            			</c:otherwise>
        			</c:choose>
					<td><div class="stage">${bean.candiStage}</div></td>
					<td>${bean.interDate}</td>
					<td>${bean.interTime}</td>
					<td>${bean.regiDate}</td>
					<c:choose>
            			<c:when test="${bean.candiResult==0}">
      		      	<td><a href="http://localhost:8080/OriginalSystem/CandidateResultCon?candiResult=1&infoNo=${bean.infoNo}" onclick="return confirm('通過に変更しますか？')">通　過</a>&emsp;
						<a href="http://localhost:8080/OriginalSystem/CandidateResultCon?candiResult=2&infoNo=${bean.infoNo}" onclick="return confirm('不通過に変更しますか？')">不通過</a></td>
            			</c:when>
            			<c:when test="${bean.candiResult==1}">
            		<td class="yes">通　過</td>
            			</c:when>
            			<c:otherwise>
                	<td class="no">不通過</td>
            			</c:otherwise>
        			</c:choose>
					<td><a href="http://localhost:8080/OriginalSystem/ChatCon?infoNo=${bean.infoNo}" onclick="return confirm('チャットルームに移動しますか？')"
							<c:if test="${!empty bean.messageId and userBean.userId != bean.messageId}">id="lastMessage"</c:if>>No.${bean.infoNo} room</a></td>
				</tr>
					</c:when>
				</c:choose>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<form action="SearchCon" method="post" id="search">
		<select name="searchSec">
				<option value="全体">全体</option>
			<c:forEach var="allSectionList" items="${allSectionList}">
				<option value="${allSectionList.secName}" <c:if test="${serchSec == allSectionList.secName}">selected</c:if>>${allSectionList.secName}</option>
			</c:forEach>
		</select>
		<input type="text" name="searchName" placeholder="部分検索(氏名)" value="${searchName}">
		<button type="submit" id="searchBut">検索</button>
	</form>
	<script type="text/javascript">
	//名前リンクのfunction
	function ViewLink(infoNo){
		if(window.confirm("履歴書を閲覧しますか？")){
			var form = document.forms[0];
			var input = document.getElementById(infoNo)
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