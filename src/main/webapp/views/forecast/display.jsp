<%--
 * display.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="forecast.moment" var="moment" />
<spring:message code="forecast.asphaltTemperature" var="asphaltTemperature" />
<spring:message code="forecast.ambientTemperature" var="ambientTemperature" />
<spring:message code="forecast.windSpeed" var="windSpeed" />
<spring:message code="forecast.windDirection" var="windDirection" />
<spring:message code="forecast.rainMm" var="rainMm" />
<spring:message code="forecast.grandPrix" var="grandPrix" />
<spring:message code="forecast.cloudPercentage" var="cloudPercentage" />
<spring:message code="forecast.formatDate" var="formatDate" />

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${moment}" />:
	<fmt:formatDate value="${forecast.moment}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${asphaltTemperature}" />:
	<jstl:out value="${forecast.asphaltTemperature}" />
	<br />
	
	<jstl:out value="${ambientTemperature}" />:
	<jstl:out value="${forecast.ambientTemperature}" />
	<br />
	
	<jstl:out value="${windSpeed}" />:
	<jstl:out value="${forecast.windSpeed}" />
	<br />
	
	<jstl:out value="${windDirection}" />:
	<jstl:out value="${forecast.windDirection}" />
	<br />
	
	<jstl:out value="${rainMm}" />:
	<jstl:out value="${forecast.rainMm}" />
	<br />
	
	<jstl:out value="${cloudPercentage}" />:
	<jstl:out value="${forecast.cloudPercentage}" /> %
	<br />
	
	<jstl:out value="${grandPrix}" />:
	<jstl:out value="${forecast.grandPrix.ticker}" /> 
	<br />
	
