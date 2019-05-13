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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<spring:message code="raceDirector.edit" var="edit" />
<spring:message code="raceDirector.userAccount.username" var="username" />
<spring:message code="raceDirector.userAccount.password" var="password" />
<spring:message code="raceDirector.name" var="name" />
<spring:message code="raceDirector.surnames" var="surnames" />
<spring:message code="raceDirector.photo" var="photo" />
<spring:message code="raceDirector.email" var="email" />
<spring:message code="raceDirector.phone" var="phone" />
<spring:message code="raceDirector.address" var="address" />
<spring:message code="raceDirector.save" var="save" />
<spring:message code="raceDirector.cancel" var="cancel" />
<spring:message code="raceDirector.confirm" var="confirm" />
<spring:message code="raceDirector.phone.pattern1" var="phonePattern1" />
<spring:message code="raceDirector.phone.pattern2" var="phonePattern2" />
<spring:message code="raceDirector.phone.warning" var="phoneWarning" />
<spring:message code="raceDirector.phone.note" var="phoneNote" />

<security:authorize access="hasRole('RACEDIRECTOR')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="raceDirector">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="raceDirector.name" path="name"/>
		<acme:textbox code="raceDirector.surnames" path="surnames"/>
		<acme:textbox code="raceDirector.photo" path="photo" placeholder="link"/>
		<acme:textbox code="raceDirector.email" path="email" placeholder="raceDirector.mail.ph"/>
		<acme:textbox code="raceDirector.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="raceDirector.address" path="address"/>
		
		<br>
		<jstl:out value="${phoneWarning}" />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />

		<%-- Buttons --%>

		<input type="submit" name="save" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
		
		<acme:cancel url="welcome/index.do" code="raceDirector.cancel" />
	</form:form>
</security:authorize>