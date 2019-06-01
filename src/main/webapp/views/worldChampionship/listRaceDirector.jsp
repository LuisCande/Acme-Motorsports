<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- Stored message variables --%>
<spring:message code="worldChampionship.name" var="name" />
<spring:message code="worldChampionship.description" var="description" />
<spring:message code="worldChampionship.raceDirector" var="raceDirector" />

<spring:message code="worldChampionship.create" var="create" />
<spring:message code="worldChampionship.save" var="save" />
<spring:message code="worldChampionship.cancel" var="cancel" />
<spring:message code="worldChampionship.display" var="display" />
<spring:message code="worldChampionship.grandPrix" var="grandPrixMsg" />
<spring:message code="worldChampionship.grandPrixes.manage" var="manageGrandPrixesMsg" />


<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="worldChampionships" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:url var="rDirectorDisplayUrl" value="raceDirector/display.do">
			<spring:param name="varId" value="${row.raceDirector.id}" />
	</spring:url>
		
		<display:column title="${raceDirector}">
			<a href="${rDirectorDisplayUrl}"><jstl:out value="${row.raceDirector.name} "/><jstl:out value="${row.raceDirector.surnames} "/></a>
		</display:column>
	
	<%-- Display --%>
		<spring:url var="displayUrl" value="worldChampionship/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<security:authorize access="hasRole('RACEDIRECTOR')">
		
		<display:column title="${grandPrixMsg}">
				<spring:url var="grandPrixesUrl" value="grandPrix/raceDirector/list.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
		
				<a href="${grandPrixesUrl}"><jstl:out value="${grandPrixMsg}" /></a>
		</display:column>
		
			</security:authorize>	
			
</display:table>
	<security:authorize access="hasRole('RACEDIRECTOR')">
		<spring:url var="createUrl" value="worldChampionship/raceDirector/create.do"/>
			<a href="${createUrl}"><jstl:out value="${create}"/></a>
	</security:authorize>