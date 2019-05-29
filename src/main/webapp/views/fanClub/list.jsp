<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="forecast.moment" var="moment" />
<spring:message code="forecast.asphaltTemperature" var="asphaltTemperature" />
<spring:message code="forecast.ambientTemperature" var="ambientTemperature" />
<spring:message code="forecast.windSpeed" var="windSpeed" />
<spring:message code="forecast.windDirection" var="windDirection" />
<spring:message code="forecast.rainMm" var="rainMm" />
<spring:message code="forecast.cloudPercentage" var="cloudPercentage" />
<spring:message code="forecast.create" var="create" />
<spring:message code="forecast.edit" var="edit" />
<spring:message code="forecast.display" var="display" />
<spring:message code="forecast.formatDate" var="formatDate" />

<security:authorize
	access="hasRole('RACEDIRECTOR')">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="forecasts" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

			<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
	</display:column>
		
		<display:column property="asphaltTemperature" title="${asphaltTemperature}"
			sortable="true" />
			
		<display:column property="windSpeed" title="${windSpeed}"
			sortable="true" />
			
		<display:column property="rainMm" title="${rainMm}"
			sortable="true" />

		<%-- Links towards display, apply, edit and cancel views --%>

		<display:column title="${edit}">
				<spring:url var="editUrl" value="forecast/raceDirector/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>

		<display:column title="${display}">
				<spring:url var="displayUrl" value="forecast/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>

	</display:table>

	<spring:url var="createUrl" value="forecast/raceDirector/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>

</security:authorize>