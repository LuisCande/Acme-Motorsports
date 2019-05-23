
<%--
 * create.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>
<security:authorize access="hasRole('RIDER')">

	<spring:message code="finder.keyWord" var="msgKeyWord" />
	<spring:message code="finder.minDate" var="msgMinDate" />
	<spring:message code="finder.maxDate" var="msgMaxDate" />
	<spring:message code="finder.category" var="msgCategory" />
	<spring:message code="finder.circuit" var="msgCircuit" />
	<spring:message code="finder.save" var="msgSave" />
	<spring:message code="finder.cancel" var="msgCancel" />
	<spring:message code="finder.clear" var="msgClear" />
	
	<spring:message code="finder.grandPrix.ticker" var="ticker" />
	<spring:message code="finder.grandPrix.startDate" var="startDate" />
	<spring:message code="finder.grandPrix.endDate" var="endDate" />
	<spring:message code="finder.grandPrix.maxRiders" var="maxRiders" />
	<spring:message code="finder.grandPrix.display" var="display" />
	<spring:message code="finder.grandPrix.worldChampionship" var="worldChampionship" />
	
	<spring:message code="finder.grandPrix.formatDate" var="formatDate" />


	<form:form action="${requestURI}" modelAttribute="finder">

	<%-- Form fields --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="grandPrixes" />
	<form:hidden path="moment" />

	
	<acme:textbox code="finder.keyWord" path="keyWord" />
	
	<acme:textbox code="finder.minDate" path="minDate" placeholder="finder.ph"/>
	
	<acme:textbox code="finder.maxDate" path="maxDate" placeholder="finder.ph"/>
	
	<form:label path="category">
		<jstl:out value="${msgCategory}" />:
	</form:label>
			<form:select path="category" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${categories}" 
					itemLabel="name"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="category" />
	<br />
	
	<form:label path="circuit">
		<jstl:out value="${msgCircuit}" />:
	</form:label>
			<form:select path="circuit" >
				<form:option
					label="----"
					value="0" />
				<form:options 
					items="${circuits}" 
					itemLabel="name"
					itemValue="id" />
			</form:select>
			<form:errors cssClass="error" path="circuit" />
	<br />

	
	<%-- Buttons --%>
	
	<acme:submit name="save" code="finder.save" />
	<acme:submit name="clear" code="finder.clear" />
	<acme:cancel url="welcome/index.do" code="finder.cancel" />

	</form:form>
	<br />
	
	
	<%--   ---------------------------------RESULTS-----------------------------------------  --%>
	
	<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="grandPrixes" requestURI="${requestURI}" id="row">
	
	<%-- Attributes --%>
	
	<display:column property="ticker" title="${ticker}" sortable="true" />
	
	<display:column title="${startDate}" sortable="true">
		<fmt:formatDate value="${row.startDate}" pattern="${formatDate}"/>
	</display:column>
	
	<display:column title="${endDate}" sortable="true">
		<fmt:formatDate value="${row.endDate}" pattern="${formatDate}"/>
	</display:column>
	
	<display:column property="maxRiders" title="${maxRiders}" sortable="true" />
	
	<%-- Links towards display, apply, edit and cancel views --%>

	<spring:url var="worldChampionshipUrl"
		value="worldChampionship/display.do">
		<spring:param name="varId"
			value="${row.worldChampionship.id}"/>
	</spring:url>
	
	<spring:url var="displayUrl"
		value="grandPrix/display.do">
		<spring:param name="varId"
			value="${row.id}"/>
	</spring:url>

	<display:column title="${worldChampionship}">
			<a href="${worldChampionshipUrl}"><jstl:out value="${worldChampionship}" /></a>
	</display:column>
	
	<display:column title="${display}">
			<a href="${displayUrl}"><jstl:out value="${display}" /></a>
	</display:column>
	
	</display:table>
	
</security:authorize>