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

<spring:message code="worldChampion.team" var="team" />
<spring:message code="worldChampion.year" var="year"/>
<spring:message code="worldChampion.category" var="category"/>
<spring:message code="worldChampion.circuitName" var="circuitName"/>
<spring:message code="worldChampion.photos" var="photos"/>
<spring:message code="worldChampion.points" var="points"/>
<spring:message code="worldChampion.specialThanks" var="specialThanks"/>
<spring:message code="worldChampion.save" var="save" />
<spring:message code="worldChampion.cancel" var="cancel" />
<spring:message code="worldChampion.year.message" var="yearMessage" />
<spring:message code="worldChampion.photo.pattern" var="photoPattern" />

<security:authorize access="hasRole('RIDER')">

	<form:form action="${requestURI}" modelAttribute="worldChampion">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="worldChampion.team" path="team" />
		<br>
		 <form:label path="year">
			<jstl:out value="${year}"/>
		</form:label>	
		<form:input path="year" pattern="\d{4}" title="${yearMessage}" placeholder="1885-2019"/>
		<form:errors path="year" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="worldChampion.category" path="category" />
		<br>
		<acme:textbox code="worldChampion.circuitName" path="circuitName" />
		<br>
		<form:label path="points">
			<jstl:out value="${points}"/>
		</form:label>	
		<form:input path="points" pattern="\d{0,4}" />
		<form:errors path="points" cssClass="error" />
		<br>
		<br>
		<acme:textarea code="worldChampion.photos" path="photos" />
		<br>
		
		<acme:textarea code="worldChampion.specialThanks" path="specialThanks" />
		<br />

		<%-- Buttons --%>
		<jstl:out value="${photoPattern}" />
		<br>
		<br>
		<acme:submit code="worldChampion.save" name="save" />
		<acme:cancel code="worldChampion.cancel" url="/palmares/rider/display.do" />
	</form:form>
</security:authorize>