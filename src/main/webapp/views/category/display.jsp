<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="category.name" var="name" />
<spring:message code="category.parent" var="parent" />
<spring:message code="category.return" var="returnMsg" />

<security:authorize access="hasRole('ADMIN')">

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${category.name}" />
	<br /> 
	
	<jstl:out value="${parent}" />:
	<jstl:out value="${category.parent.name}" />
	<br />
	<br/>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>
</security:authorize>