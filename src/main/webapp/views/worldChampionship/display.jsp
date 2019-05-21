<%--
 * display.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- Stored message variables --%>

<spring:message code="worldChampionship.name" var="name" />
<spring:message code="worldChampionship.description" var="description" />
<spring:message code="worldChampionship.raceDirector" var="raceDirector" />
<spring:message code="worldChampionship.return" var="returnMsg" />

<%-- For the selected floatAcme, display the following information: --%>

	<jstl:out value="${name}" />
	<jstl:out value="${worldChampionship.name}"/>
	<br />
	
	<jstl:out value="${description}" />:
	<jstl:out value="${worldChampionship.description}"/>
	<br />
	
	<jstl:out value="${raceDirector}" />:
	<jstl:out value="${worldChampionship.raceDirector.name} ${worldChampionship.raceDirector.surnames}"/>
	<br />
	<br>
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>
