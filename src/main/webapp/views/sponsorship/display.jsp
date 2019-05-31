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


<spring:message code="sponsorship.brandName" var="brandName" />
<spring:message code="sponsorship.banner" var="banner" />
<spring:message code="sponsorship.link" var="link" />
<spring:message code="sponsorship.sponsor" var="sponsor" />
<spring:message code="sponsorship.team" var="team" />
<spring:message code="sponsorship.creditCard" var="creditCard" />
<spring:message code="sponsorship.creditCard.holder" var="holder" />
<spring:message code="sponsorship.creditCard.make" var="make" />
<spring:message code="sponsorship.creditCard.number" var="number" />
<spring:message code="sponsorship.creditCard.expMonth" var="expMonth" />
<spring:message code="sponsorship.creditCard.expYear" var="expYear" />
<spring:message code="sponsorship.creditCard.cvv" var="cvv" />


<%-- For the selected floatAcme, display the following information: --%>

	<jstl:out value="${brandName}" />:
	<jstl:out value="${sponsorship.brandName}"/>
	<br />
	
	<jstl:out value="${banner}" />:
		<img src="${sponsorship.banner}" height="80" width="80" >
	<br />
	
	<jstl:out value="${link}" />:
	<a href="${sponsorship.link}">
				<jstl:out value="${sponsorship.link}"/>
			</a>
	<br />
	
	<jstl:out value="${team}" />:
	<jstl:out value="${sponsorship.team.name}"/>
	<br />
	
	<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		
	<jstl:out value="${holder}" />:
	<jstl:out value="${sponsorship.creditCard.holder}"/>
	<br />
	
	<jstl:out value="${make}" />:
	<jstl:out value="${sponsorship.creditCard.make}"/>
	<br />
	
	<jstl:out value="${expMonth}" />:
	<jstl:out value="${sponsorship.creditCard.expMonth}"/>
	<br />
	
	<jstl:out value="${expYear}" />:
	<jstl:out value="${sponsorship.creditCard.expYear}"/>
	<br />
	
	<jstl:out value="${cvv}" />:
	<jstl:out value="${sponsorship.creditCard.cvv}"/>
	<br />
	</fieldset>
