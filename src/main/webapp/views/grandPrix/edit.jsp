<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<%-- Stored message variables --%>

<spring:message code="grandPrix.worldChampionship" var="worldChampionship" />
<spring:message code="grandPrix.circuit" var="circuit" />
<spring:message code="grandPrix.category" var="category" />
<spring:message code="grandPrix.description" var="description" />
<spring:message code="grandPrix.startDate" var="startDate" />
<spring:message code="grandPrix.endDate" var="endDate" />
<spring:message code="grandPrix.maxRiders" var="maxRiders" />
<spring:message code="grandPrix.qualifying.name" var="qualifyingName" />
<spring:message code="grandPrix.qualifying.duration" var="qualDuration" />
<spring:message code="grandPrix.qualifying.startMoment" var="qualifyingStartMoment" />
<spring:message code="grandPrix.qualifying.endMoment" var="qualifyingEndMoment" />
<spring:message code="grandPrix.race.laps" var="raceLaps" />
<spring:message code="grandPrix.race.startMoment" var="raceStartMoment" />
<spring:message code="grandPrix.race.endMoment" var="raceEndMoment" />
<spring:message code="grandPrix.title" var="grandPrixTitle" />
<spring:message code="grandPrix.qualifying.title" var="qualifyingTitle" />
<spring:message code="grandPrix.race.title" var="raceTitle" />
<spring:message code="grandPrix.moment.placeHolder" var="placeHolder" />

<spring:message code="grandPrix.save" var="save" />
<spring:message code="grandPrix.cancel" var="cancel" />

<security:authorize access="hasRole('RACEDIRECTOR')">

	<form:form action="${requestURI}" modelAttribute="fogp">

		<%-- Forms --%>

		<form:hidden path="grandPrixId" />
		
		<h2><jstl:out value="${grandPrixTitle}" /></h2>
	
		<acme:select code="grandPrix.worldChampionship" path="worldChampionship"
				items="${worldChampionships}" itemLabel="name"
				id="worldChampionships" />
		
		<form:label path="description">
			<jstl:out value="${description}"/>
		</form:label>	
		<form:input path="description" required="true"/>
		<form:errors path="description" cssClass="error" />
		<br>
		
		<form:label path="startDate">
			<jstl:out value="${startDate}"/>
		</form:label>	
		<form:input path="startDate" required="true" placeholder="${placeHolder}"/>
		<form:errors path="startDate" cssClass="error" />
		<br>
		
		<form:label path="endDate">
			<jstl:out value="${endDate}"/>
		</form:label>	
		<form:input path="endDate" required="true" placeholder="${placeHolder}"/>
		<form:errors path="endDate" cssClass="error" />
		<br>
		
		<acme:select code="grandPrix.category" path="category"
				items="${categories}" itemLabel="name"
				id="categories" />
			
			<acme:select code="grandPrix.circuit" path="circuit"
				items="${circuits}" itemLabel="name"
				id="circuits" />
			
		
		<form:label path="maxRiders">
			<jstl:out value="${maxRiders}"/>
		</form:label>	
		<form:input path="maxRiders" pattern="\d{0,2}" required="true" />
		<form:errors path="maxRiders" cssClass="error" />
		<br>
		
		<%-- Qualifying --%>
			
		<h2><jstl:out value="${qualifyingTitle}" /></h2>
		<form:label path="qualName">
			<jstl:out value="${qualifyingName}"/>
		</form:label>	
		<form:input path="qualName" required="true"/>
		<form:errors path="qualName" cssClass="error" />
		<br>
		<form:label path="qualStartMoment">
			<jstl:out value="${qualifyingStartMoment}"/>
		</form:label>	
		<form:input path="qualStartMoment" required="true" placeholder="${placeHolder}"/>
		<form:errors path="qualStartMoment" cssClass="error" />
		<br>
		<%--<acme:textbox code="grandPrix.qualifying.name" path="qualName" />--%>
		<form:label path="qualDuration">
			<jstl:out value="${qualDuration}"/>
		</form:label>	
		<form:input path="qualDuration" pattern="\d{0,3}" required="true"/>
		<form:errors path="qualDuration" cssClass="error" />
		<br>
		
		
			
		<%-- Race --%>
		<h2><jstl:out value="${raceTitle}" /></h2>
		
		<form:label path="raceLaps">
			<jstl:out value="${raceLaps}"/>
		</form:label>	
		<form:input path="raceLaps" pattern="\d{0,2}" required="true"/>
		<form:errors path="raceLaps" cssClass="error" />
		<br>
			<form:label path="raceEndMoment">
			<jstl:out value="${raceEndMoment}"/>
		</form:label>	
		<form:input path="raceEndMoment" required="true" placeholder="${placeHolder}"/>
		<form:errors path="raceEndMoment" cssClass="error" />
		<br>
		
<br>
		<acme:submit code="grandPrix.save" name="save" />
		<acme:cancel code="grandPrix.cancel" url="worldChampionship/list.do" />
	</form:form>
</security:authorize>