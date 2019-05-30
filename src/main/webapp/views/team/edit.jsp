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

<spring:message code="team.name" var="name" />
<spring:message code="team.contractYears" var="contractYears" />
<spring:message code="team.colours" var="colours" />
<spring:message code="team.logo" var="logo" />
<spring:message code="team.factory" var="factory" />
<spring:message code="team.yearBudget" var="yearBudget" />
<spring:message code="team.delete" var="delete" />
<spring:message code="team.rider.display" var="display" />
<spring:message code="team.edit" var="edit" />
<spring:message code="team.signOut" var="signOut" />
<spring:message code="team.confirm" var="confirm" />
<spring:message code="team.save" var="save" />
<spring:message code="team.cancel" var="cancel" />
<spring:message code="team.placeholder" var="placeholder" />

<security:authorize access="hasRole('TEAMMANAGER')">

	<form:form action="${requestURI}" modelAttribute="team">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="team.name" path="name" />
		<form:label path="contractYears">
			<jstl:out value="${contractYears}"/>
		</form:label>	
		<form:input path="contractYears" pattern="\d{0,2}"/>
		<form:errors path="contractYears" cssClass="error" />
		<br>
		<acme:textbox code="team.colours" path="colours" />
		<acme:textbox code="team.logo" path="logo" placeholder="team.placeholder"/>
		<acme:textbox code="team.factory" path="factory" />
		<form:label path="yearBudget">
			<jstl:out value="${yearBudget}"/>
		</form:label>	
		<form:input path="yearBudget" pattern="\d{0,8}"/>
		<form:errors path="yearBudget" cssClass="error" />
		<br>
		

		<%-- Buttons --%>

		<acme:submit code="team.save" name="save" />

		<acme:cancel code="team.cancel" url="welcome/index.do" />
	</form:form>
</security:authorize>