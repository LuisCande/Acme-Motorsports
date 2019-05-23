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

<spring:message code="fastestLap.team" var="team" />
<spring:message code="fastestLap.year" var="year"/>
<spring:message code="fastestLap.category" var="category"/>
<spring:message code="fastestLap.circuitName" var="circuitName"/>
<spring:message code="fastestLap.miliseconds" var="miliseconds"/>
<spring:message code="fastestLap.lap" var="lap"/>
<spring:message code="fastestLap.comments" var="comments"/>
<spring:message code="fastestLap.save" var="save" />
<spring:message code="fastestLap.cancel" var="cancel" />
<spring:message code="fastestLap.year.message" var="yearMessage" />

<security:authorize access="hasRole('RIDER')">

	<form:form action="${requestURI}" modelAttribute="fastestLap">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="fastestLap.team" path="team" />
		<br>
		<form:label path="year">
			<jstl:out value="${year}"/>
		</form:label>	
		<form:input path="year" pattern="\d{4}" title="${yearMessage}" placeholder="1885-2019"/>
		<form:errors path="year" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="fastestLap.category" path="category" />
		<br>
		<acme:textbox code="fastestLap.circuitName" path="circuitName" />
		<br>
		<form:label path="miliseconds">
			<jstl:out value="${miliseconds}"/>
		</form:label>	
		<form:input path="miliseconds" pattern="\d{0,8}"/>
		<form:errors path="miliseconds" cssClass="error" />
		<br>
		<br>
		
		<form:label path="lap">
			<jstl:out value="${lap}"/>
		</form:label>	
		<form:input path="lap" pattern="\d{1,3}"/>
		<form:errors path="lap" cssClass="error" />
		<br>
		<br>
		<acme:textarea code="fastestLap.comments" path="comments" />
		<br />

		<%-- Buttons --%>

		<acme:submit code="fastestLap.save" name="save" />
		<acme:cancel code="fastestLap.cancel" url="/palmares/rider/display.do" />
	</form:form>
</security:authorize>