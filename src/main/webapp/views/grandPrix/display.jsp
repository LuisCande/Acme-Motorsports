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

<spring:message code="grandPrix.worldChampionship" var="worldChampionship" />
<spring:message code="grandPrix.ticker" var="ticker" />
<spring:message code="grandPrix.description" var="description" />
<spring:message code="grandPrix.publishDate" var="publishDate" />
<spring:message code="grandPrix.circuit" var="circuit" />
<spring:message code="grandPrix.category" var="category" />
<spring:message code="grandPrix.startDate" var="startDate" />
<spring:message code="grandPrix.endDate" var="endDate" />
<spring:message code="grandPrix.maxRiders" var="maxRiders" />
<spring:message code="grandPrix.finalMode" var="finalMode" />
<spring:message code="grandPrix.cancelled" var="cancelled" />
<spring:message code="grandPrix.forecast" var="forecastMsg" />
<spring:message code="grandPrix.qualifying.title" var="qualiTitle" />
<spring:message code="grandPrix.qualifying.name" var="qualiName" />
<spring:message code="grandPrix.qualifying.duration" var="duration" />
<spring:message code="grandPrix.qualifying.startMoment" var="qualiStartMoment" />
<spring:message code="grandPrix.qualifying.endMoment" var="qualiEndMoment" />
<spring:message code="grandPrix.race.title" var="raceTitle" />
<spring:message code="grandPrix.race.laps" var="laps" />
<spring:message code="grandPrix.race.startMoment" var="raceStartMomennt" />
<spring:message code="grandPrix.race.endMoment" var="raceEndMoment" />
<spring:message code="grandPrix.formatDate" var="formatDate" />


<%-- Display the following information about the grand prix: --%>
	
	<jstl:out value="${worldChampionship}" />:
	<jstl:out value="${grandPrix.worldChampionship.name}" />
	<br />
	
	<jstl:out value="${ticker}" />:
	<jstl:out value="${grandPrix.ticker}" />
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${grandPrix.description}" />
	<br />
	
	<jstl:out value="${publishDate}" />:
	<fmt:formatDate value="${grandPrix.publishDate}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${circuit}" />:
	<jstl:out value="${grandPrix.circuit.name}" />
	<br />
	
	<jstl:out value="${category}" />:
	<jstl:out value="${grandPrix.category.name}" />
	<br />
	
	<jstl:out value="${startDate}" />:
	<fmt:formatDate value="${grandPrix.startDate}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${endDate}" />:
	<fmt:formatDate value="${grandPrix.endDate}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${maxRiders}" />:
	<jstl:out value="${grandPrix.maxRiders}" />
	<br />
	
	<jstl:out value="${finalMode}" />:
	<jstl:out value="${grandPrix.finalMode}" />
	<br />
	
	<jstl:out value="${cancelled}" />:
	<jstl:out value="${grandPrix.cancelled}" />
	<br />
	
		<spring:url var="forecastUrl" value="forecast/display.do">
				<spring:param name="varId" value="${grandPrix.id}" />
			</spring:url>
			<jstl:if test="${not empty forecast}">
			<a href="${forecastUrl}"><jstl:out value="${forecastMsg}" /></a>
			</jstl:if>
	<br />
	<h2><jstl:out value="${qualiTitle}" /></h2>
	
	<jstl:out value="${qualiName}" />:
	<jstl:out value="${qualifying.name}" />
	<br />
	
	<jstl:out value="${duration}" />:
	<jstl:out value="${qualifying.duration}" />
	<br />
	
	<jstl:out value="${qualiStartMoment}" />:
	<fmt:formatDate value="${qualifying.startMoment}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${qualiEndMoment}" />:
	<fmt:formatDate value="${qualifying.endMoment}" pattern="${formatDate}"/>
	<br /> 
	
	<h2><jstl:out value="${raceTitle}" /></h2>
	
	<jstl:out value="${laps}" />:
	<jstl:out value="${race.laps}" />
	<br />
	
	<jstl:out value="${raceStartMomennt}" />:
	<fmt:formatDate value="${race.startMoment}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${raceEndMoment}" />:
	<fmt:formatDate value="${race.endMoment}" pattern="${formatDate}"/>
	<br /> 
	
