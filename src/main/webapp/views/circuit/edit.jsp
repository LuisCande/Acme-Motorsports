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

<spring:message code="circuit.name" var="name" />
<spring:message code="circuit.terms" var="terms" />
<spring:message code="circuit.country" var="country" />
<spring:message code="circuit.rightCorners" var="rightCorners" />
<spring:message code="circuit.leftCorners" var="leftCorners" />
<spring:message code="circuit.length" var="length" />
<spring:message code="circuit.save" var="save" />
<spring:message code="circuit.cancel" var="cancel" />

<security:authorize access="hasRole('RACEDIRECTOR')">

	<form:form action="${requestURI}" modelAttribute="circuit">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="circuit.name" path="name" />
		<acme:textarea code="circuit.terms" path="terms" />
		<acme:textbox code="circuit.country" path="country" />
		<form:label path="rightCorners">
			<jstl:out value="${rightCorners}"/>
		</form:label>	
		<form:input path="rightCorners" pattern="\d*" placeholder="num."/>
		<form:errors path="rightCorners" cssClass="error" />
		<br>
		<form:label path="leftCorners">
			<jstl:out value="${leftCorners}"/>
		</form:label>	
		<form:input path="leftCorners" pattern="\d*" placeholder="num."/>
		<form:errors path="leftCorners" cssClass="error" />
		<br>
		<form:label path="length">
			<jstl:out value="${length}"/>
		</form:label>	
		<form:input path="length" pattern="\d*" placeholder="num."/>
		<form:errors path="length" cssClass="error" />
		<br>
		<br>
	
		
		
		<%-- Buttons --%>

		<acme:submit code="circuit.save" name="save" />
		<acme:cancel code="circuit.cancel" url="circuit/list.do" />
	</form:form>
</security:authorize>