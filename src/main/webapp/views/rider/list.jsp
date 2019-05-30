<%--
 * suspiciousList.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>

<spring:message code="rider.email" var="email" />
<spring:message code="rider.phone" var="phone" />
<spring:message code="rider.completeName" var="completeName" />
<spring:message code="rider.socialProfiles" var="socialProfiles" />
<spring:message code="rider.display" var="display" />
<spring:message code="rider.return" var="msgReturn" />
<spring:message code="rider.sign" var="sign" />

<jsp:useBean id="now" class="java.util.Date"/>

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="riders" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	<display:column title="${completeName}" sortable="true">
			<jstl:out value="${row.name} " /><jstl:out value="${row.surnames}" />
	</display:column>
	<display:column property="email" title="${email}" sortable="true" />
	<display:column property="phone" title="${phone}" sortable="true" />

	<%-- Links towards display, apply, edit and cancel views --%>
	
	<security:authorize access="hasRole('TEAMMANAGER')">
	
	<spring:url var="signUrl"
		value="team/teamManager/sign.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<display:column title="${sign}">
			<a href="${signUrl}"><jstl:out value="${sign}" /></a>
	</display:column>
	
	</security:authorize>
	
	
	<spring:url var="socialProfileUrl"
		value="socialProfile/listByRider.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="rider/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>
	

	<display:column title="${socialProfiles}">
			<a href="${socialProfileUrl}"><jstl:out value="${socialProfiles}" /></a>
	</display:column>
	
	<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>



</display:table>
<a href="welcome/index.do"><jstl:out value="${msgReturn}" /></a>

