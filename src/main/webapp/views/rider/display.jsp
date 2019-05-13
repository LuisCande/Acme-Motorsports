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

<spring:message code="rider.name" var="name" />
<spring:message code="rider.surnames" var="surnames" />
<spring:message code="rider.photo" var="photo" />
<spring:message code="rider.email" var="email" />
<spring:message code="rider.phone" var="phone" />
<spring:message code="rider.address" var="address" />
<spring:message code="rider.number" var="number" />
<spring:message code="rider.country" var="country" />
<spring:message code="rider.age" var="age" />
<spring:message code="rider.score" var="score" />
<spring:message code="rider.team" var="team" />
<spring:message code="rider.suspicious" var="suspicious" />
<spring:message code="rider.Tsuspicious" var="Tsuspicious" />
<spring:message code="rider.Fsuspicious" var="Fsuspicious" />

<spring:message code="rider.return" var="returnMsg" />


	
	<%-- Displays the information of the selected rider --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${rider.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${rider.surnames}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${rider.photo}"><jstl:out value="${rider.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${rider.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${rider.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${rider.address}"/>
	<br />
	
	<jstl:out value="${number}" />:
	<jstl:out value="${rider.number}"/>
	<br />
	
	<jstl:out value="${country}" />:
	<jstl:out value="${rider.country}"/>
	<br />
	
	<jstl:out value="${age}" />:
	<jstl:out value="${rider.age}"/>
	<br />
	
	<jstl:out value="${score}" />:
	<jstl:out value="${rider.score}"/>
	<br />
	
	<jstl:out value="${team}" />:
	<jstl:out value="${rider.team}"/>
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

