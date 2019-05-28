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

<spring:message code="circuit.name" var="name" />
<spring:message code="circuit.terms" var="terms" />
<spring:message code="circuit.country" var="country" />
<spring:message code="circuit.rightCorners" var="rightCorners" />
<spring:message code="circuit.leftCorners" var="leftCorners" />
<spring:message code="circuit.length" var="length" />
<spring:message code="circuit.return" var="returnMsg" />
<spring:message code="circuit.sectors" var="sectors" />

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${circuit.name}" />
	<br /> 
	
	<jstl:out value="${terms}" />:
	<jstl:out value="${circuit.terms}" />
	<br />
	
	<jstl:out value="${country}" />:
	<jstl:out value="${circuit.country}" />
	<br />
	
	<jstl:out value="${rightCorners}" />:
	<jstl:out value="${circuit.rightCorners}" />
	<br />
	
	<jstl:out value="${leftCorners}" />:
	<jstl:out value="${circuit.leftCorners}" />
	<br />
	
	<jstl:out value="${length}" />:
	<jstl:out value="${circuit.length}" />
	<br/>

	<spring:url var="sectorsUrl" value="sector/list.do">
		<spring:param name="varId" value="${circuit.id}" />
	</spring:url>

	<a href="${sectorsUrl}"><jstl:out value="${sectors}" /></a>
	<br>
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>
