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

<spring:message code="announcement.moment" var="moment" />
<spring:message code="announcement.title" var="title" />
<spring:message code="announcement.description" var="description" />
<spring:message code="announcement.attachments" var="attachments" />
<spring:message code="announcement.grandPrix" var="grandPrix" />
<spring:message code="announcement.finalMode" var="finalMode" />
<spring:message code="announcement.formatDate" var="formatDate" />
<spring:message code="announcement.edit" var="edit" />
<spring:message code="announcement.delete" var="delete" />
<spring:message code="announcement.create" var="create" />
<spring:message code="announcement.display" var="display" />

<security:authorize
	access="hasAnyRole('RACEDIRECTOR','TEAMMANAGER')">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="announcements" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

			<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
	</display:column>
		
		<display:column property="title" title="${title}" />
			
		<display:column property="grandPrix.ticker" title="${grandPrix}"
			sortable="true" />
			

		<%-- Links towards display, apply, edit and cancel views --%>
	<display:column title="${display}">
				<spring:url var="displayUrl" value="announcement/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>
		
	<security:authorize access="hasRole('RACEDIRECTOR')">
		<display:column title="${edit}">
				<spring:url var="editUrl" value="announcement/raceDirector/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<jstl:if test="${row.finalMode eq false}">
				<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</jstl:if>
		</display:column>
		
		<display:column title="${delete}">
				<spring:url var="deleteUrl" value="announcement/raceDirector/delete.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
			<jstl:if test="${row.finalMode eq false}">
				<a href="${deleteUrl}"><jstl:out value="${delete}" /></a>
				</jstl:if>
		</display:column>

	</security:authorize>
	</display:table>

	<security:authorize access="hasRole('RACEDIRECTOR')">
	<spring:url var="createUrl" value="announcement/raceDirector/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
		</security:authorize>


</security:authorize>