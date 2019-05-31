<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Stored message variables --%>

<%-- Stored message variables --%>

<spring:message code="sponsorship.brandName" var="brandName" />
<spring:message code="sponsorship.banner" var="banner" />
<spring:message code="sponsorship.link" var="link" />
<spring:message code="sponsorship.team" var="team" />
<spring:message code="sponsorship.creditCard" var="creditCard" />
<spring:message code="sponsorship.creditCard.holder" var="holder" />
<spring:message code="sponsorship.creditCard.make" var="make" />
<spring:message code="sponsorship.creditCard.number" var="number" />
<spring:message code="sponsorship.creditCard.expMonth" var="expMonth" />
<spring:message code="sponsorship.creditCard.expYear" var="expYear" />
<spring:message code="sponsorship.creditCard.cvv" var="cvv" />
<spring:message code="sponsorship.save" var="save" />
<spring:message code="sponsorship.cancel" var="cancel" />


<security:authorize access="hasRole('SPONSOR')">

<form:form action="${requestURI}" modelAttribute="sponsorship">

	<%-- Form fields --%>
	
	<form:hidden path="id" />
	
	<acme:textbox code="sponsorship.brandName" path="brandName" />
	<br />
	<acme:textbox code="sponsorship.banner" path="banner" />
	<br />
	<acme:textbox code="sponsorship.link" path="link" />
	<br />

		<acme:select code="sponsorship.team" path="team"
			items="${teams}" itemLabel="name" id="teams" />
		<br />
	
	<fieldset>
		<legend><jstl:out value="${creditCard}"/></legend>
		<acme:textbox code="sponsorship.creditCard.holder" path="creditCard.holder" />
	
	<form:label path="creditCard.make">
			<jstl:out value="${make}" />:
	</form:label>

		<form:select path="creditCard.make">
			<form:option label="----" value="0" />
			<form:options items="${makes}" />
		</form:select>

		<form:errors cssClass="error" path="creditCard.make" />
		<br />
		<form:label path="creditCard.number">
			<jstl:out value="${number}"/>
		</form:label>	
		<form:input path="creditCard.number" pattern="\d{16}" placeholder="num."/>
		<form:errors path="creditCard.number" cssClass="error" />
		<br>
		<form:label path="creditCard.expMonth">
			<jstl:out value="${expMonth}"/>
		</form:label>	
		<form:input path="creditCard.expMonth" pattern="\d{1,2}" placeholder="${monthPH}"/>
		<form:errors path="creditCard.expMonth" cssClass="error" />
		<br>
		
		<form:label path="creditCard.expYear">
			<jstl:out value="${expYear}"/>
		</form:label>	
		<form:input path="creditCard.expYear" pattern="\d{4}" placeholder="${yearPH}"/>
		<form:errors path="creditCard.expYear" cssClass="error" />
		<br>
		<form:label path="creditCard.cvv">
			<jstl:out value="${cvv}"/>
		</form:label>	
		<form:input path="creditCard.cvv" pattern="\d{3}" placeholder="${cvvPH}"/>
		<form:errors path="creditCard.cvv" cssClass="error" />
		<br>
		</fieldset>
		<br>

	<%-- Buttons --%>
	<acme:submit code="sponsorship.save" name="save" />

		<acme:cancel code="sponsorship.cancel" url="/sponsorship/sponsor/list.do" />

</form:form>

</security:authorize>