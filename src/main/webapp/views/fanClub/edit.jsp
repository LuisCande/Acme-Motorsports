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
<spring:message code="fanClub.save" var="save" />
<spring:message code="fanClub.cancel" var="cancel" />
<spring:message code="fanClub.photo.pattern" var="photoPattern" />
<spring:message code="fanClub.formatDate" var="formatDate" />


<security:authorize access="hasRole('REPRESENTATIVE')">

	<form:form action="${requestURI}" modelAttribute="fanClub">

		<%-- Forms --%>

		<form:hidden path="id" />
	
		<jstl:if test="${fanClub.id == 0 }">
			<acme:select code="fanClub.rider" path="rider"
				items="${riders}" itemLabel="name"
				id="rider" />
		</jstl:if>
		
		<acme:textbox code="fanClub.name" path="name" />
		
		<acme:textarea code="fanClub.summary" path="summary" /> 
		
		<form:label path="numberOfFans">
			<jstl:out value="${numberOfFans}"/>
		</form:label>	
		<form:input path="numberOfFans" pattern="\d{0,6}"/>
		<form:errors path="numberOfFans" cssClass="error" />
		
		<acme:textbox code="fanClub.banner" path="banner"/>
		 
		 <acme:textbox code="fanClub.pictures" path="pictures"/>
			
		<acme:select code="fanClub.sector" path="sector"
				items="${sectors}" itemLabel="stand"
				id="sectors" />
			
		
		<%-- Buttons --%>
		<jstl:out value="${photoPattern}" />
		<br>
		
		<acme:submit code="fanClub.save" name="save" />
		<acme:cancel code="fanClub.cancel" url="fanClub/representative/list.do" />
	</form:form>
</security:authorize>