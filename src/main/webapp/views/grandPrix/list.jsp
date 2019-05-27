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

<spring:message code="grandPrix.worldChampionship"
	var="worldChampionship" />
<spring:message code="grandPrix.circuit" var="circuit" />
<spring:message code="grandPrix.ticker" var="ticker" />
<spring:message code="grandPrix.category" var="category" />
<spring:message code="grandPrix.description" var="description" />
<spring:message code="grandPrix.publishDate" var="publishDate" />
<spring:message code="grandPrix.startDate" var="startDate" />
<spring:message code="grandPrix.endDate" var="endDate" />
<spring:message code="grandPrix.maxRiders" var="maxRiders" />
<spring:message code="grandPrix.title" var="grandPrixTitle" />
<spring:message code="grandPrix.qualifying.title" var="qualifyingTitle" />
<spring:message code="grandPrix.race.title" var="raceTitle" />
<spring:message code="grandPrix.display" var="display" />
<spring:message code="grandPrix.edit" var="edit" />
<spring:message code="grandPrix.create" var="create" />
<spring:message code="grandPrix.delete" var="delete" />
<spring:message code="grandPrix.cancel" var="cancel" />
<spring:message code="grandPrix.finalMode.action" var="finalMode" />
<spring:message code="grandPrix.formatDate" var="formatDate" />


<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="grandPrixes" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="ticker" title="${ticker}" sortable="true" />

	<display:column title="${publishDate}" sortable="true">
		<fmt:formatDate value="${row.publishDate}" pattern="${formatDate}" />
	</display:column>

	<display:column property="category.name" title="${category}"
		sortable="true" />

	<display:column title="${circuit}"
		sortable="true">
		<spring:url var="circuitUrl" value="circuit/display.do">
				<spring:param name="varId" value="${row.circuit.id}" />
			</spring:url>
			<a href="${circuitUrl}"><jstl:out value="${row.circuit.name}" /></a>
	</display:column>
	<%-- Links towards display, apply, edit and cancel views --%>

	<security:authorize access="hasRole('RACEDIRECTOR')">
		<display:column title="${edit}">
			<spring:url var="editUrl" value="grandPrix/raceDirector/edit.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>
			<jstl:if test="${row.finalMode eq false and row.cancelled eq false}">
				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</jstl:if>
		</display:column>

		<display:column title="${cancel}">
			<spring:url var="cancelUrl" value="grandPrix/raceDirector/cancel.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>
			<jstl:if test="${row.cancelled eq false}">
				<a href="${cancelUrl}"><jstl:out value="${cancel}" /></a>
				</jstl:if>
		</display:column>

		<display:column title="${finalMode}">
			<spring:url var="finalModeUrl" value="grandPrix/raceDirector/finalMode.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>
			<jstl:if test="${row.finalMode eq false}">
				<a href="${finalModeUrl}"><jstl:out value="${finalMode}" /></a>
			</jstl:if>
		</display:column>
		
		<display:column title="${delete}">
			<spring:url var="deleteUrl" value="grandPrix/raceDirector/delete.do">
				<spring:param name="varId" value="${row.id}" />
			</spring:url>
			<jstl:if test="${row.cancelled eq false and row.finalMode eq false}">
				<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
			</jstl:if>
		</display:column>
		
		
	</security:authorize>

	<display:column title="${display}">
		<spring:url var="displayUrl" value="grandPrix/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>

</display:table>
<security:authorize access="hasRole('RACEDIRECTOR')">
	<spring:url var="createUrl" value="grandPrix/raceDirector/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
</security:authorize>
