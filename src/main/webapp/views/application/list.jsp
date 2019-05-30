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

<spring:message code="application.moment" var="moment" />
<spring:message code="application.status" var="status" />
<spring:message code="application.rider" var="rider" />

<spring:message code="application.create" var="create" />
<spring:message code="application.edit" var="edit" />
<spring:message code="application.display" var="display" />
<spring:message code="application.formatDate" var="formatDate" />
<spring:message code="application.confirm" var="msgConfirm" />

<security:authorize access="hasAnyRole('RACEDIRECTOR','RIDER')">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="applications" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>
		
	<jstl:if test="${row.status.name == 'PENDING'}">
		<jstl:set var="colorValue" value="yellow"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'ACCEPTED'}">
		<jstl:set var="colorValue" value="green"/>
	</jstl:if>
	<jstl:if test="${row.status.name == 'REJECTED'}">
		<jstl:set var="colorValue" value="red"/>
	</jstl:if>

		<display:column title="${moment}" sortable="true">
			<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
		</display:column>
		
		<display:column property="status.name" title="${status}" sortable="true" style="background-color:${colorValue}"/>
		
		<display:column property="rider.name" title="${rider}" sortable="true" />
			
		<display:column title="${display}">
				<spring:url var="displayUrl" value="application/${actor}/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>
		<security:authorize access="hasRole('RACEDIRECTOR')">
		<display:column title="${edit}">
			<jstl:if test="${row.status.name == 'PENDING'}">
				<spring:url var="editUrl" value="application/raceDirector/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${editUrl}"><jstl:out
						value="${edit}" /></a>
			</jstl:if>
		</display:column>
		</security:authorize>
		
	</display:table>
	<security:authorize access="hasRole('RIDER')">
		<spring:url var="createUrl" value="application/rider/create.do" />
		<a href="${createUrl}"><jstl:out value="${create}" /></a>
	</security:authorize>
</security:authorize>
