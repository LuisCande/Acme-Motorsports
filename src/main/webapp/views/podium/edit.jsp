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

<spring:message code="podium.team" var="team" />
<spring:message code="podium.year" var="year"/>
<spring:message code="podium.category" var="category"/>
<spring:message code="podium.circuitName" var="circuitName"/>
<spring:message code="podium.position" var="position"/>
<spring:message code="podium.save" var="save" />
<spring:message code="podium.cancel" var="cancel" />
<spring:message code="podium.year.message" var="yearMessage" />

<security:authorize access="hasRole('RIDER')">

	<form:form action="${requestURI}" modelAttribute="podium">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="podium.team" path="team" />
		<br>
		<form:label path="year">
			<jstl:out value="${year}"/>
		</form:label>	
		<form:input path="year" pattern="\d{4}" title="${yearMessage}" placeholder="1885-2019"/>
		<form:errors path="year" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="podium.category" path="category" />
		<br>
		<acme:textbox code="podium.circuitName" path="circuitName" />
		<br>
		<form:label path="position">
			<jstl:out value="${position}"/>
		</form:label>	
		<form:input path="position" pattern="\d{1}" placeholder="1-3"/>
		<form:errors path="position" cssClass="error" />
		<br>
		<br>
		
		<%-- Buttons --%>

		<acme:submit code="podium.save" name="save" />
		<acme:cancel code="podium.cancel" url="/palmares/rider/display.do" />
	</form:form>
</security:authorize>