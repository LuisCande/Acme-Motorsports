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

<spring:message code="team.name" var="name" />
<spring:message code="team.moment" var="moment" />
<spring:message code="team.contractYears" var="contractYears" />
<spring:message code="team.colours" var="colours" />
<spring:message code="team.logo" var="logo" />
<spring:message code="team.factory" var="factory" />
<spring:message code="team.yearBudget" var="yearBudget" />
<spring:message code="team.display" var="display" />

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="teams" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="name" title="${name}"/>

	<display:column property="moment" title="${moment}" sortable="true" />
	
	<display:column property="contractYears" title="${contractYears}" sortable="true" />
	
	<display:column property="colours" title="${colours}"/>
	
	<display:column property="logo" title="${logo}"/>
	
	<display:column property="factory" title="${factory}"/>
	
	<display:column property="yearBudget" title="${yearBudget}"/>

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<spring:url var="displayUrl"
		value="team/display.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${display}">
		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
</display:table>

		