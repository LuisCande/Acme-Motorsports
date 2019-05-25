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

<spring:message code="worldChampion.team" var="team" />
<spring:message code="worldChampion.year" var="year"/>
<spring:message code="worldChampion.category" var="category"/>
<spring:message code="worldChampion.circuitName" var="circuitName"/>
<spring:message code="worldChampion.photos" var="photos"/>
<spring:message code="worldChampion.points" var="points"/>
<spring:message code="worldChampion.specialThanks" var="specialThanks"/>

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${team}" />:
	<jstl:out value="${worldChampion.team}" />
	<br /> 
	
	<jstl:out value="${year}" />:
	<jstl:out value="${worldChampion.year}" />
	<br />
	
	<jstl:out value="${category}" />:
	<jstl:out value="${worldChampion.category}" />
	<br />
	
	<jstl:out value="${circuitName}" />:
	<jstl:out value="${worldChampion.circuitName}" />
	<br />
	
	<jstl:out value="${photos}" />:
	<jstl:out value="${worldChampion.photos}" />
	<br />
	
	<jstl:out value="${points}" />:
	<jstl:out value="${worldChampion.points}" />
	<br />
	
	<jstl:out value="${specialThanks}" />:
	<jstl:out value="${worldChampion.specialThanks}" />
	<br />
	
	
