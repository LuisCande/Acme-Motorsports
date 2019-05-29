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

<spring:message code="meeting.moment" var="moment" />
<spring:message code="meeting.comments" var="comments" />
<spring:message code="meeting.place" var="place" />
<spring:message code="meeting.signatures" var="signatures" />
<spring:message code="meeting.photos" var="photos" />
<spring:message code="meeting.duration" var="duration" />
<spring:message code="meeting.representative" var="representative" />
<spring:message code="meeting.rider" var="rider" />
<spring:message code="meeting.save" var="save" />
<spring:message code="meeting.cancel" var="cancel" />

<spring:message code="meeting.formatDate" var="formatDate" />
<spring:message code="meeting.moment.placeHolder" var="placeHolder" />

<security:authorize access="hasAnyRole('REPRESENTATIVE','RIDER')">

	<form:form action="${requestURI}" modelAttribute="meeting">

		<%-- Forms --%>

		<form:hidden path="id" />
	
			<jstl:if test="${actor == 'rider'}">
				<acme:select code="meeting.representative" path="representative"
					items="${representatives}" itemLabel="name"
					id="representative" />
			</jstl:if>
		
			<jstl:if test="${actor == 'representative'}">
				<acme:select code="meeting.rider" path="rider"
					items="${riders}" itemLabel="name"
					id="rider" />
			</jstl:if>
		<form:label path="moment">
			<jstl:out value="${moment}"/>
		</form:label>	
		<form:input path="moment" placeholder="${placeHolder}"/>
		<form:errors path="moment" cssClass="error" />
		<br>
		
		<acme:textarea code="meeting.comments" path="comments" /> 
		<acme:textbox code="meeting.place" path="place" />
		
		<form:label path="signatures">
			<jstl:out value="${signatures}"/>
		</form:label>	
		<form:input path="signatures" pattern="\d*"/>
		<form:errors path="signatures" cssClass="error" />
		<br/>
		
		<form:label path="photos">
			<jstl:out value="${photos}"/>
		</form:label>	
		<form:input path="photos" pattern="\d*"/>
		<form:errors path="photos" cssClass="error" />
		<br/>
		
		<form:label path="duration">
			<jstl:out value="${duration}"/>
		</form:label>	
		<form:input path="duration" pattern="\d*"/>
		<form:errors path="duration" cssClass="error" />
		<br/>
		<br/>
		<%-- Buttons --%>
		
		<acme:submit code="meeting.save" name="save" />
		<acme:cancel code="meeting.cancel" url="meeting/${actor}/list.do" />
	</form:form>
</security:authorize>