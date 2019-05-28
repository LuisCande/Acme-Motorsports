<%--
 * edit.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<%-- Stored message variables --%>

<spring:message code="answer.moment" var="moment" />
<spring:message code="answer.comment" var="comment" />
<spring:message code="answer.agree" var="agree" />
<spring:message code="answer.reason" var="reason" />
<spring:message code="answer.announcement" var="announcement" />
<spring:message code="answer.teamManager" var="teamManager" />
<spring:message code="answer.formatDate" var="formatDate" />
<spring:message code="answer.save" var="save" />
<spring:message code="answer.cancel" var="cancel" />
<jstl:set var="localeCode" value="${pageContext.response.locale}" />

<security:authorize access="hasRole('TEAMMANAGER')">

	<form:form action="${requestURI}" modelAttribute="answer">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:select code="answer.announcement" path="announcement"
			items="${announcements}" itemLabel="title" id="announcements" />
		<br />

		<form:label path="agree">
			<jstl:out value="${agree}" />:
		</form:label>
		<jstl:if test="${localeCode == 'en'}">
			<form:select path="agree">
				<form:option label="NO" value="false" />
				<form:option label="YES" value="true" />
			</form:select>
		</jstl:if>
		<jstl:if test="${localeCode == 'es'}">
			<form:select path="agree">
				<form:option label="NO" value="false" />
				<form:option label="SI" value="true" />
			</form:select>
		</jstl:if>
		<br>
		<acme:textarea code="answer.reason" path="reason" />
		<acme:textarea code="answer.comment" path="comment" />
		<br>
		<%-- Buttons --%>

		<acme:submit code="answer.save" name="save" />
		<acme:cancel code="answer.cancel"
			url="answer/teamManager/list.do" />

	</form:form>
</security:authorize>