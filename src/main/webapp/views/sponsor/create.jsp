<%--
 * create.jsp
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

<spring:message code="sponsor.edit" var="edit" />
<spring:message code="sponsor.userAccount.username" var="username" />
<spring:message code="sponsor.userAccount.password" var="password" />
<spring:message code="sponsor.name" var="name" />
<spring:message code="sponsor.surnames" var="surnames" />
<spring:message code="sponsor.photo" var="photo" />
<spring:message code="sponsor.email" var="email" />
<spring:message code="sponsor.phone" var="phone" />
<spring:message code="sponsor.address" var="address" />
<spring:message code="sponsor.save" var="save" />
<spring:message code="sponsor.cancel" var="cancel" />
<spring:message code="sponsor.confirm" var="confirm" />
<spring:message code="sponsor.phone.pattern1" var="phonePattern1" />
<spring:message code="sponsor.phone.pattern2" var="phonePattern2" />
<spring:message code="sponsor.phone.warning" var="phoneWarning" />
<spring:message code="sponsor.phone.note" var="phoneNote" />
<spring:message code="sponsor.terms" var="terms" />
<spring:message code="sponsor.acceptedTerms" var="acceptedTerms" />
<spring:message code="sponsor.secondPassword" var="secondPassword" />


<security:authorize access="isAnonymous()">
	<form:form id="form" action="${requestURI}"
		modelAttribute="fos">

		<%-- Forms --%>

			<form:label path="username">
				<jstl:out value="${username}" />:
		</form:label>
			<form:input path="username" />
			<form:errors cssClass="error" path="username" />
			<br />

			<form:label path="password">
				<jstl:out value="${password}" />:
		</form:label>
			<form:password path="password" />
			<form:errors cssClass="error" path="password" />
			<br />
			
			<form:label path="secondPassword">
				<jstl:out value="${secondPassword}" />:
		</form:label>
			<form:password path="secondPassword" />
			<form:errors cssClass="error" path="secondPassword" />
			<br />
		
		<acme:textbox code="sponsor.name" path="name"/>
		<acme:textbox code="sponsor.surnames" path="surnames"/>
		<acme:textbox code="sponsor.photo" path="photo" placeholder="link"/>
		<acme:textbox code="sponsor.email" path="email" placeholder="sponsor.mail.ph"/>
		<acme:textbox code="sponsor.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="sponsor.address" path="address"/>
		<br>

		<form:label path="acceptedTerms" >
        	<jstl:out value="${acceptedTerms}" />:
    </form:label>
    <a href="welcome/terms.do" target="_blank"><jstl:out value="${terms}" /></a>
    <form:checkbox path="acceptedTerms" required="required"/>
    <form:errors path="acceptedTerms" cssClass="error" />
    <br/>
	<br/>
		<jstl:out value="${phoneWarning}" />
		<br />
		<jstl:out value="${phonePattern1}" />
		<br />
		<jstl:out value="${phonePattern2}" />
		<br />
		<br />

		<%-- Buttons --%>

		<input type="submit" name="create" value="${save}"
			onclick="return confirm('${confirm}')" />&nbsp;
		
	<acme:cancel url="welcome/index.do" code="sponsor.cancel" />
	</form:form>
</security:authorize>