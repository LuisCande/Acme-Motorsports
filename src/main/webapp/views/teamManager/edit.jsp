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

<spring:message code="manager.edit" var="edit" />
<spring:message code="manager.userAccount.username" var="username" />
<spring:message code="manager.userAccount.password" var="password" />
<spring:message code="manager.name" var="name" />
<spring:message code="manager.surnames" var="surnames" />
<spring:message code="manager.photo" var="photo" />
<spring:message code="manager.email" var="email" />
<spring:message code="manager.phone" var="phone" />
<spring:message code="manager.address" var="address" />
<spring:message code="manager.save" var="save" />
<spring:message code="manager.cancel" var="cancel" />
<spring:message code="manager.confirm" var="confirm" />
<spring:message code="manager.phone.pattern1" var="phonePattern1" />
<spring:message code="manager.phone.pattern2" var="phonePattern2" />
<spring:message code="manager.phone.warning" var="phoneWarning" />
<spring:message code="manager.phone.note" var="phoneNote" />

<security:authorize access="hasRole('TEAMMANAGER')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="teamManager">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="manager.name" path="name"/>
		<acme:textbox code="manager.surnames" path="surnames"/>
		<acme:textbox code="manager.photo" path="photo" placeholder="link"/>
		<acme:textbox code="manager.email" path="email" placeholder="manager.mail.ph"/>
		<acme:textbox code="manager.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="manager.address" path="address"/>
		
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
		
		<acme:cancel url="welcome/index.do" code="manager.cancel" />
	</form:form>
</security:authorize>