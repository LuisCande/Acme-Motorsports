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

<spring:message code="answer.moment" var="moment" />
<spring:message code="answer.comment" var="comment" />
<spring:message code="answer.agree" var="agree" />
<spring:message code="answer.reason" var="reason" />
<spring:message code="answer.announcement" var="announcement" />
<spring:message code="answer.teamManager" var="teamManager" />
<spring:message code="answer.formatDate" var="formatDate" />
<spring:message code="answer.create" var="create" />
<spring:message code="answer.display" var="display" />
<jstl:set var="localeCode" value="${pageContext.response.locale}" />

<security:authorize
	access="hasAnyRole('TEAMMANAGER','RACEDIRECTOR')">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="answers" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

			<display:column title="${moment}" sortable="true">
		<fmt:formatDate value="${row.moment}" pattern="${formatDate}"/>
	</display:column>
	
		<display:column property="teamManager.name" title="${teamManager}" />
		
		<display:column property="agree" title="${agree}" />
			
		<display:column property="announcement.title" title="${announcement}"
			sortable="true" />
			

		<%-- Links towards display, apply, edit and cancel views --%>
	<display:column title="${display}">
				<spring:url var="displayUrl" value="answer/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>

	</display:table>
	</security:authorize>
	<security:authorize access="hasRole('TEAMMANAGER')">
	<spring:url var="createUrl" value="answer/teamManager/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
		</security:authorize>

