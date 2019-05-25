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

<spring:message code="pole.team" var="team" />
<spring:message code="pole.year" var="year"/>
<spring:message code="pole.category" var="category"/>
<spring:message code="pole.circuitName" var="circuitName"/>
<spring:message code="pole.miliseconds" var="miliseconds"/>
<spring:message code="pole.save" var="save" />
<spring:message code="pole.cancel" var="cancel" />
<spring:message code="pole.year.message" var="yearMessage" />

<security:authorize access="hasRole('RIDER')">

	<form:form action="${requestURI}" modelAttribute="pole">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="pole.team" path="team" />
		<br>
		<form:label path="year">
			<jstl:out value="${year}"/>
		</form:label>	
		<form:input path="year" pattern="\d{4}" title="${yearMessage}" placeholder="1885-2019"/>
		<form:errors path="year" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="pole.category" path="category" />
		<br>
		<acme:textbox code="pole.circuitName" path="circuitName" />
		<br>
		<form:label path="miliseconds">
			<jstl:out value="${miliseconds}"/>
		</form:label>	
		<form:input path="miliseconds" pattern="\d{0,8}"/>
		<form:errors path="miliseconds" cssClass="error" />
		<br>
		<br>

		<%-- Buttons --%>

		<acme:submit code="pole.save" name="save" />
		<acme:cancel code="pole.cancel" url="/palmares/rider/display.do" />
	</form:form>
</security:authorize>