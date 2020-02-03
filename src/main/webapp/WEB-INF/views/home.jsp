<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>home.jsp(로그인 페이지)</h1>

<form action="access" method="post">
<table border="1">
<tr>
<td colspan="2" align="center" bgcolor="pink">로그인</td>
</tr>
<tr>
<td><input type="text" name="m_id"  /></td>
<td rowspan="2"><button>로그인</button></td>
</tr>
<tr>
<td><input type="password" name="m_pwd" /></td>
</tr>
<tr>
<td colspan="2" align="center" bgcolor="pink" >com.board.lim</td>
</tr>
<tr>
<td colspan="2" align="center" bgcolor="pink"><a href="./joinfrm">회원가입</a></td>
</tr>
</table>
</form>

<script>
var chk=${check};
if(chk==1){
	alert("회원가입 성공");
	
	if(chk==2){
		alert("로그인 실패");
	}
}
</script>
</body>
</html>
