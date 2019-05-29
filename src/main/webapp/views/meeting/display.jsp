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

<spring:message code="meeting.moment" var="moment" />
<spring:message code="meeting.comments" var="comments" />
<spring:message code="meeting.place" var="place" />
<spring:message code="meeting.signatures" var="signatures" />
<spring:message code="meeting.photos" var="photos" />
<spring:message code="meeting.duration" var="duration" />
<spring:message code="meeting.representative" var="representative" />
<spring:message code="meeting.rider" var="rider" />
<spring:message code="meeting.return" var="returnMsg" />
<spring:message code="meeting.formatDate" var="formatDate" />

<security:authorize access="hasAnyRole('REPRESENTATIVE','RIDER')">

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${meeting.moment}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${comments}" />:
	<jstl:out value="${meeting.comments}" />
	<br />
	
	<jstl:out value="${place}" />:
	<jstl:out value="${meeting.place}" />
	<br />
	
	<jstl:out value="${signatures}" />:
	<jstl:out value="${meeting.signatures}" />
	<br /> 
	
	<jstl:out value="${photos}" />:
	<jstl:out value="${meeting.photos}" />
	<br />
	
	<jstl:out value="${duration}" />:
	<jstl:out value="${meeting.duration}" />
	<br />
	
	<jstl:out value="${representative}" />:
	<jstl:out value="${meeting.representative.name}" />
	<br />
	
	<jstl:out value="${rider}" />:
	<jstl:out value="${meeting.rider.name}" />
	<br />
	<br />
	<a href="meeting/${actor}/list.do"><jstl:out value="${returnMsg}" /></a>
	
	</security:authorize>