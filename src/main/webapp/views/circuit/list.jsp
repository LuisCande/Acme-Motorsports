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

<spring:message code="circuit.name" var="name" />
<spring:message code="circuit.terms" var="terms" />
<spring:message code="circuit.country" var="country" />
<spring:message code="circuit.edit" var="edit" />
<spring:message code="circuit.delete" var="delete" />
<spring:message code="circuit.confirm" var="msgConfirm" />
<spring:message code="circuit.create" var="create" />
<spring:message code="circuit.display" var="display" />


	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="circuits" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" />
		<display:column property="terms" title="${terms}" sortable="true" />
		<display:column property="country" title="${country}" />

		<%-- Links towards display, apply, edit and cancel views --%>
		<display:column title="${display}">
				<spring:url var="displayUrl" value="circuit/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<security:authorize access="hasRole('RACEDIRECTOR')">
		<display:column title="${edit}">
				<spring:url var="editUrl" value="circuit/raceDirector/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<display:column title="${delete}">
				<spring:url var="deleteUrl" value="circuit/raceDirector/delete.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
		</display:column>

	</security:authorize>
	</display:table>

	<security:authorize access="hasRole('RACEDIRECTOR')">
	<spring:url var="createUrl" value="circuit/raceDirector/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
		</security:authorize>


