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

<spring:message code="announcement.moment" var="moment" />
<spring:message code="announcement.title" var="title" />
<spring:message code="announcement.description" var="description" />
<spring:message code="announcement.attachments" var="attachments" />
<spring:message code="announcement.grandPrix" var="grandPrix" />
<spring:message code="announcement.formatDate" var="formatDate" />

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${announcement.moment}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${title}" />:
	<jstl:out value="${announcement.title}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${announcement.description}" />
	<br />
	
	<jstl:out value="${attachments}" />:
	<jstl:out value="${announcement.attachments}" />
	<br />
	
	<jstl:out value="${grandPrix}" />:
	<jstl:out value="${announcement.grandPrix.ticker}" />
	<br />
	
