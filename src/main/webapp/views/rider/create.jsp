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

<spring:message code="rider.edit" var="edit" />
<spring:message code="rider.userAccount.username" var="username" />
<spring:message code="rider.userAccount.password" var="password" />
<spring:message code="rider.name" var="name" />
<spring:message code="rider.surnames" var="surnames" />
<spring:message code="rider.photo" var="photo" />
<spring:message code="rider.email" var="email" />
<spring:message code="rider.phone" var="phone" />
<spring:message code="rider.address" var="address" />
<spring:message code="rider.number" var="number" />
<spring:message code="rider.country" var="country" />
<spring:message code="rider.age" var="age" />
<spring:message code="rider.numberPH" var="numberPH" />
<spring:message code="rider.agePH" var="agePH" />
<spring:message code="rider.save" var="save" />
<spring:message code="rider.cancel" var="cancel" />
<spring:message code="rider.confirm" var="confirm" />
<spring:message code="rider.phone.pattern1" var="phonePattern1" />
<spring:message code="rider.phone.pattern2" var="phonePattern2" />
<spring:message code="rider.phone.warning" var="phoneWarning" />
<spring:message code="rider.phone.note" var="phoneNote" />
<spring:message code="rider.terms" var="terms" />
<spring:message code="rider.acceptedTerms" var="acceptedTerms" />
<spring:message code="rider.secondPassword" var="secondPassword" />


<security:authorize access="isAnonymous()">
	<form:form id="form" action="${requestURI}"
		modelAttribute="fori">

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
		
		<acme:textbox code="rider.name" path="name"/>
		<acme:textbox code="rider.surnames" path="surnames"/>
		<acme:textbox code="rider.photo" path="photo" placeholder="link"/>
		<acme:textbox code="rider.email" path="email" placeholder="rider.mail.ph"/>
		<acme:textbox code="rider.phone" path="phone" placeholder="phone.ph"/>
		<acme:textbox code="rider.address" path="address"/>
		<form:label path="number">
			<jstl:out value="${number}"/>:
		</form:label>	
		<form:input path="number" pattern="\d{1,3}" placeholder="${numberPH}"/>
		<form:errors path="number" cssClass="error" />
		<br>
		<acme:textbox code="rider.country" path="country"/>
		<form:label path="age">
			<jstl:out value="${age}"/>:
		</form:label>	
		<form:input path="age" pattern="\d{2}" placeholder="${agePH}"/>
		<form:errors path="age" cssClass="error" />
		<br>
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
		
	<acme:cancel url="welcome/index.do" code="rider.cancel" />
	</form:form>
</security:authorize>