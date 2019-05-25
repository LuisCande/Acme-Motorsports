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

<spring:message code="category.name" var="name" />
<spring:message code="category.parent" var="parent" />
<spring:message code="category.edit" var="edit" />
<spring:message code="category.delete" var="delete" />
<spring:message code="category.confirm" var="msgConfirm" />
<spring:message code="category.create" var="create" />
<spring:message code="category.display" var="display" />

<security:authorize access="hasRole('ADMIN')">
	<%-- Listing grid --%>

	<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="categories" requestURI="${requestURI}" id="row">

		<%-- Attributes --%>

		<display:column property="name" title="${name}" />
		
				<spring:url var="displayParentUrl" value="category/administrator/display.do">
					<spring:param name="varId" value="${row.parent.id}" />
				</spring:url>
				
		<display:column title="${parent}" sortable="true">
			<a href="${displayParentUrl}"><jstl:out value="${row.parent.name}" /></a>
		</display:column>
		
		<%-- Links towards display, apply, edit and cancel views --%>
		<display:column title="${display}">
				<spring:url var="displayUrl" value="category/administrator/display.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>

				<a href="${displayUrl}"><jstl:out value="${display}" /></a>
		</display:column>
		
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${edit}">
				<spring:url var="editUrl" value="category/administrator/edit.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<jstl:if test="${not empty row.parent}">
					<a href="${editUrl}"><jstl:out value="${edit}" /></a>
				</jstl:if>
		</display:column>
		
		<display:column title="${delete}">
				<spring:url var="deleteUrl" value="category/administrator/delete.do">
					<spring:param name="varId" value="${row.id}" />
				</spring:url>
				<jstl:if test="${not empty row.parent}">
					<a href="${deleteUrl}" onclick="return confirm('${msgConfirm}')"><jstl:out value="${delete}" /></a>
				</jstl:if>
		</display:column>

	</security:authorize>
	</display:table>

	<security:authorize access="hasRole('ADMIN')">
	<spring:url var="createUrl" value="category/administrator/create.do" />
	<a href="${createUrl}"><jstl:out value="${create}" /></a>
		</security:authorize>

</security:authorize>
