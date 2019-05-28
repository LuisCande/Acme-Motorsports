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

<spring:message code="sector.stand" var="stand" />
<spring:message code="sector.rows" var="rows" />
<spring:message code="sector.columns" var="columns" />
<spring:message code="sector.circuit" var="circuit" />
<spring:message code="sector.save" var="save" />
<spring:message code="sector.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

	<form:form action="${requestURI}" modelAttribute="sector">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:select code="sector.circuit" path="circuit"
					items="${circuits}" itemLabel="name"
					id="circuits" />

		<acme:textbox code="sector.stand" path="stand" />
		
		<form:label path="rows">
			<jstl:out value="${rows}" />
		</form:label>
		<form:input path="rows" pattern="\d{1,4}" title="${rows}" />
		<form:errors path="rows" cssClass="error" />
		<br>
		
		
		<form:label path="columns">
			<jstl:out value="${columns}" />
		</form:label>
		<form:input path="columns" pattern="\d{1,4}" title="${columns}" />
		<form:errors path="columns" cssClass="error" />
		<br>
		<br />

		<%-- Buttons --%>

		<acme:submit code="sector.save" name="save" />
		<acme:cancel code="sector.cancel" url="/sector/administrator/list.do" />
	</form:form>
</security:authorize>