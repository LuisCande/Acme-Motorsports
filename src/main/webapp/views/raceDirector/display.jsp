<%--
 * dashboard.jsp
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

<%-- Stored message variables --%>

<spring:message code="raceDirector.name" var="name" />
<spring:message code="raceDirector.surnames" var="surnames" />
<spring:message code="raceDirector.photo" var="photo" />
<spring:message code="raceDirector.email" var="email" />
<spring:message code="raceDirector.phone" var="phone" />
<spring:message code="raceDirector.address" var="address" />
<spring:message code="raceDirector.suspicious" var="suspicious" />
<spring:message code="raceDirector.Tsuspicious" var="Tsuspicious" />
<spring:message code="raceDirector.Fsuspicious" var="Fsuspicious" />

<spring:message code="raceDirector.return" var="returnMsg" />


	
	<%-- Displays the information of the selected raceDirector --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${raceDirector.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${raceDirector.surnames}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${raceDirector.photo}"><jstl:out value="${raceDirector.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${raceDirector.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${raceDirector.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${raceDirector.address}"/>
	<br />
	
	<security:authorize access="hasRole('ADMIN')" >
	<jstl:out value="${suspicious}" />:
	<jstl:if test="${actor.suspicious eq true}">
	<jstl:out value="${Tsuspicious}"/>
	</jstl:if>
	<jstl:if test="${actor.suspicious eq false}">
	<jstl:out value="${Fsuspicious}"/>
	</jstl:if>
	<br />
  	</security:authorize>
	
	<br />
	
	<a href="welcome/index.do"><jstl:out value="${returnMsg}" /></a>

