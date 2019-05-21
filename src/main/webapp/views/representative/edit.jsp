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

<spring:message code="representative.edit" var="edit" />
<spring:message code="representative.userAccount.username" var="username" />
<spring:message code="representative.userAccount.password" var="password" />
<spring:message code="representative.name" var="name" />
<spring:message code="representative.surnames" var="surnames" />
<spring:message code="representative.photo" var="photo" />
<spring:message code="representative.email" var="email" />
<spring:message code="representative.phone" var="phone" />
<spring:message code="representative.address" var="address" />
<spring:message code="representative.save" var="save" />
<spring:message code="representative.cancel" var="cancel" />
<spring:message code="representative.confirm" var="confirm" />
<spring:message code="representative.phone.pattern1" var="phonePattern1" />
<spring:message code="representative.phone.pattern2" var="phonePattern2" />
<spring:message code="representative.phone.warning" var="phoneWarning" />
<spring:message code="representative.phone.note" var="phoneNote" />

<security:authorize access="hasRole('REPRESENTATIVE')">

	<form:form id="form" action="${requestURI}"
		modelAttribute="representative">

		<%-- Forms --%>

		<form:hidden path="id" />
		
		<acme:textbox code="representative.name" path="name"/>
		<acme:textbox code="representative.surnames" path="surnames"/>
		<acme:textbox code="representative.photo" path="photo" placeholder="link"/>
		<acme:textbox code="representative.email" path="email" placeholder="representative.mail.ph"/>
		<acme:textbox code="representative.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="representative.address" path="address"/>
		<br>
		
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
		
		<acme:cancel url="welcome/index.do" code="representative.cancel" />
	</form:form>
</security:authorize>