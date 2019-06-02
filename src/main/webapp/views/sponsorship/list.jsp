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

<spring:message code="sponsorship.create" var="create" />
<spring:message code="sponsorship.edit" var="edit" />
<spring:message code="sponsorship.delete" var="delete" />
<spring:message code="sponsorship.display" var="display" />
<spring:message code="sponsorship.confirm.delete" var="confirm" />
<spring:message code="sponsorship.brandName" var="brandName" />
<spring:message code="sponsorship.banner" var="banner" />
<spring:message code="sponsorship.link" var="link" />
<spring:message code="sponsorship.sponsor" var="sponsor" />
<spring:message code="sponsorship.team" var="team" />

<security:authorize access="hasRole('SPONSOR')">

<%-- Listing grid --%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="sponsorships" requestURI="${requestURI}" id="row">

	<%-- Attributes --%>
	
	<display:column property="brandName" title="${brandName}" sortable="true" />
	
	<display:column property="team.name" title="${team}" sortable="true" />
		
	<%-- Edit --%>	
		<spring:url var="editUrl" value="sponsorship/sponsor/edit.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${edit}">
			<a href="${editUrl}"><jstl:out value="${edit}" /></a>
		</display:column>		

	<%-- Display --%>
		<spring:url var="displayUrl" value="sponsorship/sponsor/display.do">
			<spring:param name="varId" value="${row.id}" />
		</spring:url>
		<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	

	<!--  Pay sponsorship -->

	<spring:url var="deleteURL" value="sponsorship/sponsor/delete.do">
		<spring:param name="varId" value="${row.id}" />
	</spring:url>

	<display:column title="${delete}">
	<jstl:if test="${empty row.team}">
		<a href="${deleteURL}" onclick="return confirm('${confirm}')" ><jstl:out value="${delete}" /></a>
		</jstl:if>
	</display:column>
	
	
</display:table>

	<spring:url var="createUrl" value="sponsorship/sponsor/create.do"/>
	<a href="${createUrl}"><jstl:out value="${create}"/></a>

</security:authorize>