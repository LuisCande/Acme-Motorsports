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

<spring:message code="application.moment" var="moment" />
<spring:message code="application.status" var="status" />
<spring:message code="application.comments" var="comments" />
<spring:message code="application.reason" var="reason" />
<spring:message code="application.grandPrix" var="grandPrix" />
<spring:message code="application.rider" var="rider" />
<spring:message code="application.save" var="save" />
<spring:message code="application.cancel" var="cancel" />

<spring:message code="application.formatDate" var="formatDate" />
<spring:message code="application.moment.placeHolder" var="placeHolder" />

<security:authorize access="hasAnyRole('RACEDIRECTOR','RIDER')">

	<form:form action="${requestURI}" modelAttribute="application">

		<%-- Forms --%>

		<form:hidden path="id" />
	
			<jstl:if test="${actor == 'rider'}">
				<acme:textarea code="application.comments" path="comments" /> 
				<acme:select code="application.grandPrix" path="grandPrix"
						items="${grandPrixes}" itemLabel="ticker"
						id="grandPrix" />
			</jstl:if>
		
			<jstl:if test="${actor == 'raceDirector'}">
				<form:label path="status">
					<jstl:out value="${status}" />:
				</form:label>

				<form:select path="status">
					<form:option label="ACCEPTED" value="ACCEPTED" />
					<form:option label="REJECTED" value="REJECTED" />
				</form:select>
				<form:errors cssClass="error" path="status" />
				<br />
				
				<acme:textarea code="application.reason" path="reason" /> 
			</jstl:if>
		
		<br/>
		<%-- Buttons --%>
		
		<acme:submit code="application.save" name="save" />
		<acme:cancel code="application.cancel" url="application/${actor}/list.do" />
	</form:form>
</security:authorize>