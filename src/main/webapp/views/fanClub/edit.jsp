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

<spring:message code="forecast.moment" var="moment" />
<spring:message code="forecast.asphaltTemperature" var="asphaltTemperature" />
<spring:message code="forecast.ambientTemperature" var="ambientTemperature" />
<spring:message code="forecast.windSpeed" var="windSpeed" />
<spring:message code="forecast.windDirection" var="windDirection" />
<spring:message code="forecast.rainMm" var="rainMm" />
<spring:message code="forecast.cloudPercentage" var="cloudPercentage" />
<spring:message code="forecast.grandPrix" var="grandPrix" />
<spring:message code="forecast.save" var="save" />
<spring:message code="forecast.cancel" var="cancel" />

<security:authorize access="hasRole('RACEDIRECTOR')">

	<form:form action="${requestURI}" modelAttribute="forecast">

		<%-- Forms --%>

		<form:hidden path="id" />
	
		<form:label path="asphaltTemperature">
			<jstl:out value="${asphaltTemperature}"/>
		</form:label>	
		<form:input path="asphaltTemperature" pattern="\d{0,2}"/>
		<form:errors path="asphaltTemperature" cssClass="error" />
		<br><br>
		<form:label path="ambientTemperature">
			<jstl:out value="${ambientTemperature}"/>
		</form:label>	
		<form:input path="ambientTemperature" pattern="\d{0,2}"/>
		<form:errors path="ambientTemperature" cssClass="error" />
		<br>
		<br>
		<form:label path="windSpeed">
			<jstl:out value="${windSpeed}"/>
		</form:label>	
		<form:input path="windSpeed" pattern="\d{0,4}"/>
		<form:errors path="windSpeed" cssClass="error" />
		<br>
		<br>
		<acme:textbox code="forecast.windDirection" path="windDirection" />
		<br>
		<form:label path="rainMm">
			<jstl:out value="${rainMm}"/>
		</form:label>	
		<form:input path="rainMm" pattern="\d{0,9}"/>
		<form:errors path="rainMm" cssClass="error" />
		<br>
		<br>
		<form:label path="cloudPercentage">
			<jstl:out value="${cloudPercentage}"/>
		</form:label>	
		<form:input path="cloudPercentage" pattern="\d{0,2}"/>
		<form:errors path="cloudPercentage" cssClass="error" />
		<br>
		<br>
		
		<jstl:if test="${forecast.id == 0}">
		<acme:select code="forecast.grandPrix" path="grandPrix"
				items="${grandPrixes}" itemLabel="ticker"
				id="grandPrixes" />
			<br />
		</jstl:if>
		<%-- Buttons --%>

		<acme:submit code="forecast.save" name="save" />
		<acme:cancel code="forecast.cancel" url="forecast/raceDirector/list.do" />
	</form:form>
</security:authorize>