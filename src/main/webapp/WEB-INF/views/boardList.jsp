<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="js/jquery.serializeObject.js"></script>



<script>
	function logout() {
		$("#logoutFrm").submit();
		
	}
</script>
<style>
html, body {
	height: 100%;
	margin: 0
}

#articleView_layer {
	display: none;
	position: fixed;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%
}

#articleView_layer.open {
	display: block;
	color: red
}

#articleView_layer #bg_layer {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: #000;
	opacity: .5;
	filter: alpha(opacity = 50);
	z-index: 100
}

#contents_layer {
	position: absolute;
	top: 40%;
	left: 40%;
	width: 400px;
	height: 400px;
	margin: -150px 0 0 -194px;
	padding: 28px 28px 0 28px;
	border: 2px solid #555;
	background: #fff;
	font-size: 12px;
	z-index: 200;
	color: #767676;
	line-height: normal;
	white-space: normal;
	overflow: scroll
}
</style>

</head>

<body>
${msg}
<h1>boardList.jsp</h1>
<h3>${result }</h3>
<c:if test="${!empty id }">
 <div align="right">
 
 <!-- <a href="logout">로그아웃</a> --> 
 <form id="logoutFrm"action="logout" method="post">
 <a href="javascript:logout()">로그아웃</a>
 </form>
 
 </div>
</c:if>
<table id="one_table">
<tr height="30">
<td width="80" bgcolor="pink" align="center">ID</td>
<td><a href="#;" id="m_id">${mb.m_id }</a></td>
</tr>

<tr height="30">
<td width="80" bgcolor="pink" align="center">NAME</td>
<td>${mb.m_name }</td>
</tr>

<tr height="30">
<td width="80" bgcolor="pink" align="center">GNAME</td>
<td>${mb.g_name}</td>
</tr>

<tr height="30">
<td width="80" bgcolor="pink" align="center">POINT</td>
<td>${mb.m_point }</td>
</tr>


</table>

<table>
<tr bgcolor="skyplue" height="30">
<th width="100">번호</th>
<th width="100">제목</th>
<th width="100">작성자</th>
<th width="100">작성일</th>
<th width="100">조회수</th>
</tr>

<c:forEach var="board" items="${bList}">
	<tr height="25">
	<td align="center">${board.b_num }</td>
	<!--# : 페이지 맨 위  #; : 현재 위치  -->
	<td align="center"><a href="#;" onclick="articleView(${board.b_num })">${board.b_title }</a></td>
	<td align="center">${board.b_id }</td>
	<td align="center">${board.b_date }</td>
	<td align="center">${board.b_views }</td>
	</tr>
</c:forEach>
</table>

<!--글쓰기  -->
<form action="writefrm">
<button>글쓰기</button>
</form>


<!--페이징  -->
<div align="center">${paging }</div>

<!--모달 박스  -->
<div id="articleView_layer">
	<div id="bg_layer"></div>
		<div id="contents_layer"></div>
</div>

<form action="test">
      컬럼명:<input type="text" name="cName"><br>
      검색:<input type="text" name="search"/>
      <button>컬럼 검색</button>
   </form>

<script>



$("#m_id").on("click",function() {
	$("#articleView_layer").addClass('open');	//modal박스 오픈
	
	$.ajax({
		type:'get',
		url:'myinfo',
		dataType:'json',	
		success:function(data){
		//	$("#contents_layer").html(data);
			console.log(data);
		},
		error:function(error){
			console.log(error);
		}
		
	});//ajax End
	var $layerWindow=$("#articleView_layer");
	$layerWindow.find("#bg_layer").on("mousedown",function(event){
		console.log(event);
		$layerWindow.removeClass("open");
	});

	
}); //fct End




function articleView(num) {
	$("#articleView_layer").addClass('open');	//modal박스 오픈
	$.ajax({
		type:'get',
		url:'contents',
		data:{bNum:num},
		dataType:'html',	//boardContentsAjax.jsp을 html형식으로 쫙~ 긁어온다.
		success:function(data){
			$("#contents_layer").html(data);
		},
		error:function(error){
			console.log(error);
		}
		
	});//ajax End
	
	//모달박스 해제
	var $layerWindow=$("#articleView_layer");
	$layerWindow.find("#bg_layer").on("mousedown",function(event){
		console.log(event);
		$layerWindow.removeClass("open");
	}); //on End
	
	$(document).keydown(function (event) {
		if(event.keyCode!=27) return;
		else if($layerWindow.hasClass('open')){
		$layerWindow.removeClass('open');
		}
	});	//keydown End
}//fct End

console.log("bNum="+'${bNum}');

$(function () {
	var result='${bNum}';
	
if(result===''){
	return;
}
if(parseInt(result)>0){
	alert("${bNum}"+"번 글을 삭제했습니다.");
}
});


</script>
</body>
</html>