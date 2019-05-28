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

<spring:message code="fanClub.name" var="name" />
<spring:message code="fanClub.summary" var="summary" />
<spring:message code="fanClub.numberOfFans" var="numberOfFans" />
<spring:message code="fanClub.establishmentDate" var="establishmentDate" />
<spring:message code="fanClub.banner" var="banner" />
<spring:message code="fanClub.pictures" var="pictures" />
<spring:message code="fanClub.representative" var="representative" />
<spring:message code="fanClub.rider" var="rider" />
<spring:message code="fanClub.circuit" var="circuit" />
<spring:message code="fanClub.sector" var="sector" />
<spring:message code="fanClub.formatDate" var="formatDate" />

<%-- Display the following information about the Social Profile: --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${fanClub.name}" />
	<br />
	
	<jstl:out value="${summary}" />:
	<jstl:out value="${fanClub.summary}" />
	<br />
	
	<jstl:out value="${numberOfFans}" />:
	<jstl:out value="${fanClub.numberOfFans}" />
	<br />
	
	<jstl:out value="${establishmentDate}" />:
	<fmt:formatDate value="${fanClub.establishmentDate}" pattern="${formatDate}"/>
	<br /> 
	
	<jstl:out value="${banner}" />:
	<jstl:out value="${fanClub.banner}" />
	<br />
	
	<jstl:out value="${pictures}" />:
	<jstl:out value="${fanClub.pictures}" />
	<br />
	
	<jstl:out value="${representative}" />:
	<jstl:out value="${fanClub.representative.name}" />
	<br />
	
	<jstl:out value="${rider}" />:
	<jstl:out value="${fanClub.rider.name}" />
	<br />
	
	<jstl:out value="${circuit}" />:
	<jstl:out value="${fanClub.sector.circuit.name}" />
	<br />
	
	<jstl:out value="${sector}" />:
	<jstl:out value="${fanClub.sector.stand}" />
	<br />
	
	
	
	
	

	
	
	
