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

<spring:message code="category.name" var="name" />
<spring:message code="category.parent" var="parent" />
<spring:message code="category.save" var="save" />
<spring:message code="category.cancel" var="cancel" />

<security:authorize access="hasRole('ADMIN')">

	<form:form action="${requestURI}" modelAttribute="category">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="category.name" path="name" />
		<acme:select code="category.parent" path="parent"
				items="${categories}" itemLabel="name"
				id="categories" />

		<br>
	
		
		
		<%-- Buttons --%>

		<acme:submit code="category.save" name="save" />
		<acme:cancel code="category.cancel" url="category/administrator/list.do" />
	</form:form>
</security:authorize>