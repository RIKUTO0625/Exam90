<%-- 学生登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>学生情報登録</h2>

	<form action = "StudentCreateExecute.action" method="post">
		<label>入学年度</label>
		<select name="ent_year">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたent_yearが一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>
		<div>${errors.get("ent_year")}</div>

		<label>学生番号</label>
		<input type="text" name="no"
			placeholder="学生番号を入力してください" maxlength="10" value="${no}" required />
		<div>${errors.get("no")}</div>

		<label>氏名</label>
		<input type="text"
			name="name" placeholder="氏名を入力してください" maxlength="10"
			value="${name}" required />
		<div>${errors.get("name")}</div>

		<label>クラス</label>
		<select name="class_num">
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==class_num_set}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<input type="submit" value="登録して終了">
	</form>

	<a href="StudentList.action">戻る</a>

</body>
</html>