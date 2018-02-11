<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
	<title>Planner V2 Page</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#999;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:7px 3px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:#999;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:7px 3px;border-style:solid;border-width:2px;overflow:hidden;word-break:normal;border-color:#999;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
	</style>
</head>
<body>
<h1>
	Add a Plan
</h1>
<p>Please note that you must use exact date formatting as stated in "From" and "To" fields, where:
dd - day, MM - month, yyyy - year in 4 digit format, HH - hour (24 hour clock), mm - minutes.</p>

<p>All below fields are required.</p>

<c:url var="addAction" value="/planner/add" ></c:url>

<form:form action="${addAction}" modelAttribute="planner">
<table>
	<c:if test="${!empty planner.user_name}">
	<tr>
		<td>
			<form:label path="plan_id">
				<spring:message text="ID"/>
			</form:label>
		</td>
		<td>
			<form:input path="plan_id" readonly="true" size="8"  disabled="true" />
			<form:hidden path="plan_id" />
		</td> 
	</tr>
	</c:if>
	<tr>
		<td>
			<form:label path="user_name">
				<spring:message text="Username (alphanumerical, max 20 characters)"/>
			</form:label>
		</td>
		<td>
			<form:input path="user_name" />
		</td> 
	</tr>
	<tr>
		<td>
			<form:label path="fromDate">
				<spring:message text="From (dd-MM-yyyy HH:mm)"/>
			</form:label>
		</td>
		<td>
			<fmt:formatDate value="${planner.fromDate}" pattern="dd-MM-yyyy HH:mm" var="frmFrom"/>
			<form:input path="fromDate" value = "${frmFrom}"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="toDate">
				<spring:message text="To (dd-MM-yyyy HH:mm)"/>
			</form:label>
		</td>
		<td>
			<fmt:formatDate value="${planner.toDate}" pattern="dd-MM-yyyy HH:mm" var="frmTo"/>
			<form:input path="toDate" value="${frmTo}"/>
		</td>
	</tr>
	<tr>
		<td>
			<form:label path="room">
				<spring:message text="Room number (numerical only, min 0 max 60000)"/>
			</form:label>
		</td>
		<td>
			<form:input path="room" />
		</td> 
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${!empty planner.user_name}">
				<input type="submit"
					value="<spring:message text="Edit plan"/>" />
			</c:if>
			<c:if test="${empty planner.user_name}">
				<input type="submit"
					value="<spring:message text="Add plan"/>" />
			</c:if>
		</td>
	</tr>
</table>
	<p>
		<c:if test="${DATE_PICK_RESULT!=null}">
			<spring:message text="${DATE_PICK_RESULT}" />
		</c:if>
	</p>
</form:form>
<br>
<h3>All current plans</h3>
<c:if test="${!empty listPlanners}">
	<table class="tg">
	<tr>
		<th width="80">Plan ID</th>
		<th width="120">Username</th>
		<th width="120">From date</th>
		<th width="120">To date</th>
		<th width="60">Room</th>
		<th width="60">Edit</th>
		<th width="60">Delete</th>
	</tr>
	<c:forEach items="${listPlanners}" var="planner">
		<tr>
			<td>${planner.plan_id}</td>
			<td>${planner.user_name}</td>
			<td>${planner.fromDate}</td>
			<td>${planner.toDate}</td>
			<td>${planner.room}</td>
			<td><a href="<c:url value='/edit/${planner.plan_id}' />" >Edit</a></td>
			<td><a href="<c:url value='/remove/${planner.plan_id}' />" >Delete</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
