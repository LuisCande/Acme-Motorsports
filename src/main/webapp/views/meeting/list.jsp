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

<spring:message code="meeting.moment" var="moment" />
<spring:message code="meeting.place" var="place" />
<spring:message code="meeting.duration" var="duration" />
<spring:message code="meeting.representative" var="representative" />
<spring:message code="meeting.rider" var="rider" />

<spring:message code="meeting.create" var="create" />
<spring:message code="meeting.edit" var="edit" />
<spring:message code="meeting.display" var="display" />
<spring:message code="meeting.formatDate" var="formatDate" />
<spring:message code="meeting.confirm" var="msgConfirm" />

<security:authorize access="hasAnyRole('REPRESENTATIVE','RIDER')">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="meetings" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column title="${moment}" sortable="true">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
		</display:column>
		
		<display:column property="place" title="${place}" sortable="true" />
			
		<display:column property="duration" title="${duration}" sortable="true" />
			
		<display:column title="${representative}">
				<spring:url var="representativeUrl" value="representative/display.do">
					<spring:param name="varId" value="${row.representative.id}" />
				</spring:url>

				<a href="${representativeUrl}"><jstl:out
						value="${row.representative.name}" /></a>
		</display:column>
		
		<display:column title="${rider}">
				<spring:url var="riderUrl" value="rider/display.do">
					<spring:param name="varId" value="${row.rider.id}" />
				</spring:url>

				<a href="${riderUrl}"><jstl:out
						value="${row.rider.name}" /></a>
		</display:column>
		
		
		<%-- Links towards display, apply, edit views --%>
		<display:column title="${display}">
				<spring:url var="displayUrl" value="meeting/${actor}/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>
				<spring:url var="editUrl" value="meeting/${actor}/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<display:column title="${edit}">
				<jstl:if test="${row.representative.id == principalId and row.riderToRepresentative eq false}">
					<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</jstl:if>
				<jstl:if test="${row.rider.id == principalId and row.riderToRepresentative eq true}">
					<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</jstl:if>
				</display:column>
	</display:table>

		<spring:url var="createUrl" value="meeting/${actor}/create.do" />
		<a href="${createUrl}"><jstl:out value="${create}" /></a>
	
</security:authorize>
