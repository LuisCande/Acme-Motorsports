<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="worldChampionship.create" var="create" />
<spring:message code="worldChampionship.edit" var="edit" />
<spring:message code="worldChampionship.name" var="name" />
<spring:message code="worldChampionship.description" var="description" />
<spring:message code="worldChampionship.raceDirector" var="raceDirector" />
<spring:message code="worldChampionship.save" var="save" />
<spring:message code="worldChampionship.cancel" var="cancel" />


<security:authorize access="hasRole('RACEDIRECTOR')">

<form:form action="${requestURI}" modelAttribute="worldChampionship">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	
	<acme:textbox code="worldChampionship.name" path="name"/>
	<acme:textarea code="worldChampionship.description" path="description" />
	
	<br />
	<%-- Buttons --%>
	<input type="submit" name="save" value="${save}"/>&nbsp; 
	
	<input type="button" name="cancel" value="${cancel}"
		onclick="javascript: relativeRedir('worldChampionship/raceDirector/list.do');" />

</form:form>

</security:authorize>