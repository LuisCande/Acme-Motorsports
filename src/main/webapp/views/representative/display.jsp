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

<spring:message code="representative.name" var="name" />
<spring:message code="representative.surnames" var="surnames" />
<spring:message code="representative.photo" var="photo" />
<spring:message code="representative.email" var="email" />
<spring:message code="representative.phone" var="phone" />
<spring:message code="representative.address" var="address" />
<spring:message code="representative.suspicious" var="suspicious" />
<spring:message code="representative.Tsuspicious" var="Tsuspicious" />
<spring:message code="representative.Fsuspicious" var="Fsuspicious" />

<spring:message code="representative.return" var="returnMsg" />


	
	<%-- Displays the information of the selected representative --%>
	
	<jstl:out value="${name}" />:
	<jstl:out value="${representative.name}"/>
	<br />
	
	<jstl:out value="${surnames}" />:
	<jstl:out value="${representative.surnames}"/>
	<br />
	
	<jstl:out value="${photo}" />:
	<a href="${representative.photo}"><jstl:out value="${representative.photo}"/></a>
	<br />
	
	<jstl:out value="${email}" />:
	<jstl:out value="${representative.email}"/>
	<br />
	
	<jstl:out value="${phone}" />:
	<jstl:out value="${representative.phone}"/>
	<br />
	
	<jstl:out value="${address}" />:
	<jstl:out value="${representative.address}"/>
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

