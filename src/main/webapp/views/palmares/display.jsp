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

<%-- Stored message variables --%>

<spring:message code="palmares.victory" var="msgVictory" />
<spring:message code="palmares.victory.photos" var="photos" />
<spring:message code="palmares.victory.attachments" var="attachments" />
<spring:message code="palmares.victory.create" var="createVictory" />

<spring:message code="palmares.fastestlap" var="msgFastestLap" />
<spring:message code="palmares.fastestlap.miliseconds" var="miliseconds" />
<spring:message code="palmares.fastestlap.lap" var="lap" />
<spring:message code="palmares.fastestlap.comments" var="comments" />
<spring:message code="palmares.fastestlap.create" var="createFastestLap" />

<spring:message code="palmares.podium" var="msgPodium" />
<spring:message code="palmares.podium.position" var="position" />
<spring:message code="palmares.podium.create" var="createPodium" />

<spring:message code="palmares.pole" var="msgPole" />
<spring:message code="palmares.pole.miliseconds" var="miliseconds" />
<spring:message code="palmares.pole.create" var="createPole" />

<spring:message code="palmares.worldChampion" var="msgWorldChampion" />
<spring:message code="palmares.worldChampion.photos" var="photos" />
<spring:message code="palmares.worldChampion.points" var="points" />
<spring:message code="palmares.worldChampion.specialThanks" var="specialThanks" />
<spring:message code="palmares.worldChampion.create" var="createWorldChampion" />

<spring:message code="palmares.return" var="msgReturn" />
<spring:message code="palmares.delete" var="delete" />
<spring:message code="palmares.details" var="details" />
<spring:message code="palmares.edit" var="edit" />
<spring:message code="palmares.confirm" var="confirm" />
<spring:message code="palmares.team" var="team" />
<spring:message code="palmares.year" var="year" />
<spring:message code="palmares.category" var="category" />
<spring:message code="palmares.circuit" var="circuit" />

<%-- For the palmares in the list received as model, display the following information: --%>

<security:authorize access="hasRole('RIDER')">

	<%-- Victory list--%>

	<h2><jstl:out value="${msgVictory}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="victories" requestURI="${requestURI}" id="row">

		<display:column property="team" title="${team}" />
		<display:column property="year" title="${year}" />
		<display:column property="category" title="${category}" />
		<display:column property="circuitName" title="${circuit}" />

		<spring:url var="editUrl" value="victory/rider/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="victory/rider/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

		<spring:url var="displayUrl" value="victory/rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>
	
	<spring:url var="createVictoryUrl" value="victory/rider/create.do">
	</spring:url>
	<a href="${createVictoryUrl}"><jstl:out value="${createVictory}"/></a>

	<br>

	<!-- FastestLap list -->
	
	<h2><jstl:out value="${msgFastestLap}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="fastestLaps" requestURI="${requestURI}" id="row">

		<display:column property="year" title="${year}" />
		<display:column property="category" title="${category}" />
		<display:column property="circuitName" title="${circuit}" />
		<display:column property="miliseconds" title="${miliseconds}" />
		<display:column property="lap" title="${lap}" />

		<spring:url var="editUrl" value="fastestLap/rider/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="fastestLap/rider/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

		<spring:url var="displayUrl" value="fastestLap/rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>
	
	<spring:url var="createFastestLapUrl" value="fastestLap/rider/create.do">
	</spring:url>
	<a href="${createFastestLapUrl}"><jstl:out value="${createFastestLap}"/></a>

	<br>
	
	<!-- Podium list -->
	
	<h2><jstl:out value="${msgPodium}" /></h2>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="podiums" requestURI="${requestURI}" id="row">

		<display:column property="year" title="${year}" />
		<display:column property="category" title="${category}" />
		<display:column property="circuitName" title="${circuit}" />
		<display:column property="position" title="${position}" />

		<spring:url var="editUrl" value="podium/rider/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="podium/rider/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>

		<spring:url var="displayUrl" value="podium/rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>
	
	<br>
	
	<spring:url var="createPodiumUrl" value="podium/rider/create.do">
	</spring:url>
	<a href="${createPodiumUrl}"><jstl:out value="${createPodium}"/></a>

	<br>

	<!-- Pole list -->
	
	<h2><jstl:out value="${msgPole}" /></h2>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="poles" requestURI="${requestURI}" id="row">
		
		<display:column property="team" title="${team}" />
		<display:column property="year" title="${year}" />
		<display:column property="category" title="${category}" />
		<display:column property="circuitName" title="${circuit}" />
		<display:column property="miliseconds" title="${miliseconds}"/>

		<spring:url var="editUrl" value="pole/rider/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="pole/rider/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
		
		<spring:url var="displayUrl"
			value="pole/rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>

	<br />
	
	<spring:url var="createPoleUrl" value="pole/rider/create.do">
	</spring:url>
	<a href="${createPoleUrl}"><jstl:out value="${createPole}"/></a>
	
	<br>
	
	<!-- WorldChampion list -->
	
	<h2><jstl:out value="${msgWorldChampion}" /></h2>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="worldChampions" requestURI="${requestURI}" id="row">
		
		<display:column property="team" title="${team}" />
		<display:column property="year" title="${year}" />
		<display:column property="category" title="${category}" />
		<display:column property="points" title="${points}"/>

		<spring:url var="editUrl" value="worldChampion/rider/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>
		
		<spring:url var="deleteUrl" value="worldChampion/rider/delete.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${delete}">
			<a href="${deleteUrl}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</display:column>
		
		<spring:url var="displayUrl"
			value="worldChampion/rider/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>

		<display:column title="${details}">
			<a href="${displayUrl}"><jstl:out value="${details}" /></a>
		</display:column>

	</display:table>

	<br />
	
	<spring:url var="createWorldChampionUrl" value="worldChampion/rider/create.do">
	</spring:url>
	<a href="${createWorldChampionUrl}"><jstl:out value="${createWorldChampion}"/></a>
	
	<br>


</security:authorize>