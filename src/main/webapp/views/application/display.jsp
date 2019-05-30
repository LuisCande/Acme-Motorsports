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

<spring:message code="application.moment" var="moment" />
<spring:message code="application.status" var="status" />
<spring:message code="application.comments" var="comments" />
<spring:message code="application.reason" var="reason" />
<spring:message code="application.grandPrix" var="grandPrix" />
<spring:message code="application.rider" var="rider" />
<spring:message code="application.return" var="returnMsg" />
<spring:message code="application.formatDate" var="formatDate" />

<security:authorize access="hasAnyRole('RACEDIRECTOR','RIDER')">

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${application.moment}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${status}" />:
	<jstl:out value="${application.status}" />
	<br />
	
	<jstl:out value="${comments}" />:
	<jstl:out value="${application.comments}" />
	<br />
	
	<jstl:if test="${not empty appliation.reason}">
	<jstl:out value="${reason}" />:
	<jstl:out value="${application.reason}" />
	<br /> 
	</jstl:if>
	
	<jstl:out value="${grandPrix}" />:
	<jstl:out value="${application.grandPrix.ticker}" />
	<br />
	
	<jstl:out value="${rider}" />:
	<jstl:out value="${application.rider.name}" />
	<br />
	<br />
	<a href="application/${actor}/list.do"><jstl:out value="${returnMsg}" /></a>
	
	</security:authorize>