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

<spring:message code="fanClub.name" var="name" />
<spring:message code="fanClub.summary" var="summary" />
<spring:message code="fanClub.numberOfFans" var="numberOfFans" />
<spring:message code="fanClub.establishmentDate" var="establishmentDate" />
<spring:message code="fanClub.representative" var="representative" />
<spring:message code="fanClub.rider" var="rider" />
<spring:message code="fanClub.create" var="create" />
<spring:message code="fanClub.edit" var="edit" />
<spring:message code="fanClub.display" var="display" />
<spring:message code="fanClub.delete" var="delete" />
<spring:message code="fanClub.formatDate" var="formatDate" />
<spring:message code="fanClub.confirm" var="msgConfirm" />

<security:authorize
	access="permitAll">

	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="fanClubs" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

	
		
		<display:column property="name" title="${name}"
			sortable="true" />
			
		<display:column property="summary" title="${summary}"
			sortable="true" />
			
		<display:column property="numberOfFans" title="${numberOfFans}"
			sortable="true" />
			
		<display:column title="${establishmentDate}" sortable="true">
			<fmt:formatDate value="${row.establishmentDate}" pattern="${formatDate}"/>
		</display:column>

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
		
		
		<%-- Links towards display, apply, edit and cancel views --%>

		

		<display:column title="${display}">
				<spring:url var="displayUrl" value="fanClub/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out
						value="${display}" /></a>
		</display:column>
		
		
		<security:authorize access="hasRole('REPRESENTATIVE')">
			<jstl:if test="${principalId == row.representative.id }">
				<display:column title="${edit}">
					<spring:url var="editUrl" value="fanClub/representative/edit.do">
						<spring:param name="varId" value="${row.id}" />
					</spring:url>
	
					<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</display:column>
				
				<display:column title="${delete}">
					<spring:url var="deleteUrl" value="fanClub/representative/delete.do">
						<spring:param name="varId" value="${row.id}" />
					</spring:url>
					<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>
	</display:table>

	<security:authorize access="hasRole('REPRESENTATIVE')">
		<spring:url var="createUrl" value="fanClub/representative/create.do" />
		<a href="${createUrl}"><jstl:out value="${create}" /></a>
	</security:authorize>
	
</security:authorize>
