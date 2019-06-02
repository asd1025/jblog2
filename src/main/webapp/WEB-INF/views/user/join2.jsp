<!--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script
	src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
	// form 보내기 
	function checkAll(obj){
  	 	if(!$('#img-checkId').is(':visible')){
 	 		alert('ID 중복체크를 하세요! ')
	 		return false;
	 	}
    	console.log('가입 시킨다. .. '+'${usersVo}');
    	//console.log(obj);
    	location.href='${pageContext.servletContext.contextPath}/user/join';
		//return true;
	}
	
$(function(){
	// 아이디 값이 변화되었나? 
	$("#check_id").on("change paste keyup", function() {
		$('#img-checkId').hide();
		 $('#btn-checkId').show();
		 console.log('.......');
		});
	 
	 
	// 쭝복체크 버튼 
	 $('#btn-checkId').click(function(){
		 var id=$('#check_id').val();
		 console.log('btn-ceckID '+id);	
		 if(id==''){
			 return;
		 }
		 
		 /* Ajax 통신 */
		 $.ajax({
			 url:"${pageContext.servletContext.contextPath}/user/checkId?id="+id,
			 type:"get",
			 dataType:"json",
			 data:"",
			 success:function(response){
				if(response.result!="success"){
					console.error(response.message);
					return;
				}
				if(response.data==true){
					alert('이미 존재하는 아이디입니다.\n 다른 아이디를 사용해주세요');
					$('#check_id').focus();
					$('#check_id').val('');
					return;
				}
				 $('#btn-checkId').hide();

 				$('#img-checkId').show();
				
			 },
			error:function(xhr,error){
				console.log("error:"+error);
			 }			 
		 });
 	 });
	
	
	
});
</script>
</head>
<body>
	<div class="center-content">
		<a href="${pageContext.servletContext.contextPath}/"><h1 class="logo">JBlog</h1></a>
	<ul class="menu">
		<c:choose>
		<c:when test="${empty authUser}">
			<li><a href="${pageContext.servletContext.contextPath}/user/login">로그인</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/user/join">회원가입</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="${pageContext.servletContext.contextPath}/user/logout">로그아웃</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/user/blog">내블로그</a></li>
		</c:otherwise>
		</c:choose>
		</ul>
		
		
  <form:form modelAttribute="usersVo"  onsubmit="return checkAll(this.form)" 
	
	 class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label>
			<form:input path="name" /> 
					<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0 ">
			<form:errors path="name" /></p>
			
			
			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id" id="check_id"  /> 
						<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0 ">
			<form:errors path="id" /></p>
			
			<input id="btn-checkId" type="button" value="id 중복체크">
			<img id="img-checkId" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
						<form:input path="password"/> 
 						<p style="font-weight:bold; color:#f00; text-align:left; padding:0; margin:0 ">
			<form:errors path="password" /></p>
			
			<fieldset>
				<legend>약관동의</legend>
				  <input required="required" type="checkbox" name="agreeProv" id="agree-prov"   value="y" />
				<!-- <input id="agree-prov" type="checkbox" name="agreeProv" value="y">  -->
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기"/ >

		</form:form>
	</div>
</body>
</html>
-->