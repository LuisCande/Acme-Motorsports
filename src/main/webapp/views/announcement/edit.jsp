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

<spring:message code="announcement.moment" var="moment" />
<spring:message code="announcement.title" var="title" />
<spring:message code="announcement.description" var="description" />
<spring:message code="announcement.attachments" var="attachments" />
<spring:message code="announcement.grandPrix" var="grandPrix" />
<spring:message code="announcement.finalMode" var="finalMode" />
<spring:message code="announcement.formatDate" var="formatDate" />
<spring:message code="announcement.save" var="save" />
<spring:message code="announcement.cancel" var="cancel" />

<security:authorize access="hasRole('RACEDIRECTOR')">

	<form:form action="${requestURI}" modelAttribute="announcement">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="announcement.title" path="title" />
		<acme:textarea code="announcement.description" path="description" />
		<acme:textarea code="announcement.attachments" path="attachments" />
		<acme:select code="announcement.grandPrix" path="grandPrix"
				items="${grandPrixes}" itemLabel="ticker"
				id="grandPrixes" />
			<br />
			
	
		<form:label path="finalMode">
			<jstl:out value="${finalMode}" />:
		</form:label>
			<form:select path="finalMode" >
				<form:option
					label="NO"
					value="false" />
				<form:option
					label="YES"
					value="true" />
			</form:select>
		<br>
		<br>
		
		
		<%-- Buttons --%>

		<acme:submit code="announcement.save" name="save" />
		<acme:cancel code="announcement.cancel" url="announcement/raceDirector/list.do" />
	</form:form>
</security:authorize>