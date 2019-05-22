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

<spring:message code="victory.team" var="team" />
<spring:message code="victory.year" var="year"/>
<spring:message code="victory.category" var="category"/>
<spring:message code="victory.circuitName" var="circuitName"/>
<spring:message code="victory.photos" var="photos"/>
<spring:message code="victory.attachments" var="attachments"/>
<spring:message code="victory.save" var="save" />
<spring:message code="victory.cancel" var="cancel" />
<spring:message code="victory.year.message" var="yearMessage" />
<spring:message code="victory.photo.pattern" var="photoPattern" />

<security:authorize access="hasRole('RIDER')">

	<form:form action="${requestURI}" modelAttribute="victory">

		<%-- Forms --%>

		<form:hidden path="id" />

		<acme:textbox code="victory.team" path="team" />
		<br>
		<form:label path="year">
			<jstl:out value="${year}"/>
		</form:label>	
		<form:input path="year" pattern="\d{4}" title="${yearMessage}" placeholder="1885-2019"/>
		<form:errors path="year" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="victory.category" path="category" />
		<br>
		<acme:textbox code="victory.circuitName" path="circuitName" />
		<br>
		<acme:textarea code="victory.photos" path="photos" />
		<br>
		<acme:textarea code="victory.attachments" path="attachments" />
		<br />
		
		<%-- Buttons --%>
		<jstl:out value="${photoPattern}" />
		<br>
		<br>
		<acme:submit code="victory.save" name="save" />
		<acme:cancel code="victory.cancel" url="/palmares/rider/display.do" />
	</form:form>
</security:authorize>