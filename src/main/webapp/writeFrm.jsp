<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style>
table {
	width: 100%;
}

table, tr, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px;
	text-align: center;
}

input[type='text'] {
	width: 100%;
}

textarea {
	width: 98%;
	
}
</style>
</head>
<body>
	<form action="boardwrite" id="frm" method="post"
		enctype="multipart/form-data">
		<table border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="b_title" id="b_title" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="b_contents" id="b_contents" rows="20"></textarea></td>
			</tr>
			<tr>
				<td>파일첨부</td>
				<td><input type="file" name="files" id="files" multiple /> <input
					type="hidden" id="fileCheck" name="fileCheck" value="0" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="submit" value="글작성" />
				<input type="button" id="formData" value="Form" />
					<input type="reset" id="reset" value="취소" /> 
					<input type="button" onclick="locatiom.href='./boardlist'" value="리스트 보기" />
					</td>
			</tr>
		</table>
	</form>

	<script>
	$("#formData").on("click",function(){
		var $obj=$("#files");
		console.log($obj);	//jQuery객체
		console.log($obj[0]);	//js객체(배열로 변환)
		
		console.dir($obj[0]);	//파일엘리먼트
		console.dir($obj[0].files);	//첨부된 파일리스트
		console.dir($obj[0].files.length);	//첨부된 파일리스트 개수
		console.dir($obj[0].files[0]);	//1번째 파일정보
		console.dir($obj[0].files[1]);	//2번째 파일정보
		
		//FormData 사용 목적
		//1. multipart/form-data를 전송시 무조건 사용(파일 업로드)
		//2. ajax를 이용해서 서버로 넘긴다. (restFul에서 사용함)	
		//3. FormData객체는 form의 일부 데이터만 서버에 전송할때도 좋다.
		
		//var formData=new FormData($("#frm"));	//에러
		var formData= new FormData(document.getElementById("frm"));	//폼데이터 객체
		console.log(formData.get("b_title"));
		console.log(formData.get("b_contents"));
		
		var formData=new FormData();
		formData.append("b_title",$("#b_title").val());
		formData.append("b_contents",$("#b_contents").val());
		formData.append("fileCheck",$("#fileCheck").val());	//0,1
		
		var files=$obj[0].files;	//첨부된 파일
		for(var i=0;i<files.length;i++){
			formData.append("files",files[i]);	//Map과 달리 속성(키)이 같아도 중복 저장함
		}
		console.log(formData.get("b_title"));
		console.log(formData.getAll("files"));
		
		$.ajax({
			
			url:'rest/boardwrite',
			type: "post",	//enctype=multipart/form-data를 전송시 반드시 post
			data:formData,
			processData:false,	//application/x-www-form-urlencoded
			contentType:false,	//multipart의 경우 contenType을 false로
			dataType:'json',
			success:function(data){
				alert("성공");
				console.log(data);
			},
			error:function(error){
				alert("실패ㅋ");
				console.log(error);
				
			}
			
			
		})	//ajax End
		
	});
	
	
	
	
	
	
		$("#files").on('change', function() {
			console.log(this); //첨부된 파일
			console.log(this.value); //첨부된 파일
			if (this.value == '') {
				console.log("empty");
				$("#fileCheck").val(0); //첨부안되면 0
			} else {
				console.log("not empty");
				$("#fileCheck").val(1); //첨부됨 1
			}
			console.log($("#fileCheck").val());
		});

		//옛날방식
		/* 	function fileChk(elem) {
		 console.log(elem.value);	//첨부된 파일
		 if(elem.value==''){
		 console.log("empty");
		 $("#fileCheck").val(0);
		 }
		 else{
		 console.log("not empty");
		 $("#fileCheck").val(1);
		 }
		 console.log($("#fileCheck").val());
		 }// fct End */

		$("#reset").click(function() {
			$("#fileCheck").val(0);
			$("#files").val("");
			console.log($("#fileCheck").val());
			console.log($("#files").val());
		});
	</script>

</body>
</html>