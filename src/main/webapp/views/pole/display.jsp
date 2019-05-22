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

<spring:message code="pole.team" var="team" />
<spring:message code="pole.year" var="year"/>
<spring:message code="pole.category" var="category"/>
<spring:message code="pole.circuitName" var="circuitName"/>
<spring:message code="pole.miliseconds" var="miliseconds"/>

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${team}" />:
	<jstl:out value="${pole.team}" />
	<br /> 
	
	<jstl:out value="${year}" />:
	<jstl:out value="${pole.year}" />
	<br />
	
	<jstl:out value="${category}" />:
	<jstl:out value="${pole.category}" />
	<br />
	
	<jstl:out value="${circuitName}" />:
	<jstl:out value="${pole.circuitName}" />
	<br />
	
	<jstl:out value="${miliseconds}" />:
	<jstl:out value="${pole.miliseconds}" />
	<br />
	
	
