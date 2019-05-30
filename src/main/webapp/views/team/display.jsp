<%--
 * display.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="team.name" var="name" />
<spring:message code="team.moment" var="moment" />
<spring:message code="team.contractYears" var="contractYears" />
<spring:message code="team.colours" var="colours" />
<spring:message code="team.logo" var="logo" />
<spring:message code="team.factory" var="factory" />
<spring:message code="team.yearBudget" var="yearBudget" />

<spring:message code="team.rider.list" var="riders" />
<spring:message code="team.rider.name" var="riderName" />
<spring:message code="team.rider.surnames" var="riderSurnames" />
<spring:message code="team.rider.number" var="riderNumber" />
<spring:message code="team.rider.country" var="country" />

<spring:message code="team.delete" var="delete" />
<spring:message code="team.rider.display" var="display" />
<spring:message code="team.edit" var="edit" />
<spring:message code="team.signOut" var="signOut" />
<spring:message code="team.confirm" var="confirm" />
<spring:message code="team.formatDate" var="formatDate" />

<%-- For the team in the list received as model, display the following information: --%>

	
	<jstl:if test="${sponsorship != null}">
		<hr>
			<br />
			<a href="${sponsorship.link}">
				<img src="${sponsorship.banner}" height="80" width="80" >
			</a>
			<br/>
		<hr>
	</jstl:if>

	<jstl:out value="${name}" />:
	<jstl:out value="${team.name}" />
	<br /> 
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${team.moment}" pattern="${formatDate}"/>
	<br />
	
	<jstl:out value="${contractYears}" />:
	<jstl:out value="${team.contractYears}" />
	<br />
	
	<jstl:out value="${colours}" />:
	<jstl:out value="${team.colours}" />
	<br />
	
	<jstl:out value="${logo}" />:
	<img src="${team.logo}"  height="80" width="80">
	<br />
	
	<jstl:out value="${factory}" />:
	<jstl:out value="${team.factory}" />
	<br />
	
	<jstl:out value="${yearBudget}" />:
	<jstl:out value="${team.yearBudget}" />
	<br />
		<security:authorize access="hasRole('TEAMMANAGER')">
	
		<spring:url var="editUrl" value="team/teamManager/edit.do">
			<spring:param name="varId" value="${team.id}" />
		</spring:url>

			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		
		<spring:url var="deleteUrl" value="team/teamManager/delete.do">
			<spring:param name="varId" value="${team.id}" />
		</spring:url>

			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>

	</security:authorize>

	<!-- FastestLap list -->
	
	<h2><jstl:out value="${riders}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="riders" requestURI="${requestURI}" id="row">

		<display:column property="name" title="${riderName}" />
		<display:column property="surnames" title="${riderSurnames}" />
		<display:column property="number" title="${riderNumber}" />
		<display:column property="country" title="${country}" />
		
<security:authorize access="hasRole('TEAMMANAGER')">

		<spring:url var="signOutUrl" value="team/teamManager/signOut.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${signOut}">
			<a href="${signOutUrl}" onclick="return confirm('${confirm}')"><jstl:out value="${signOut}" /></a>
		</display:column>
	</security:authorize>
			

		<spring:url var="displayUrl" value="rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>

	</display:table>
	
