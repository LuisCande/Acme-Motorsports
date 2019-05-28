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

<spring:message code="sector.stand" var="stand" />
<spring:message code="sector.rows" var="rows" />
<spring:message code="sector.columns" var="columns" />
<spring:message code="sector.circuit" var="circuit" />
<spring:message code="sector.create" var="create" />
<spring:message code="sector.edit" var="edit" />

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="sectors" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>

	<display:column property="stand" title="${stand}" sortable="true" />

	<display:column property="rows" title="${rows}" sortable="true" />

	<display:column property="columns" title="${columns}" sortable="true" />

	<display:column property="circuit.name" title="${circuit}"
		sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>

	<security:authorize access="hasRole('ADMIN')">
		<spring:url var="editUrl" value="sector/administrator/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
	</security:authorize>


</display:table>
<security:authorize access="hasRole('ADMIN')">
	<spring:url var="createUrl" value="sector/administrator/create.do">

	</spring:url>
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
</security:authorize>