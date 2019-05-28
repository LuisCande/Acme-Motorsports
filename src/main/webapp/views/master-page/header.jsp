<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


		<spring:message code="master.page.cancel.confirm" var="msgConfirm" />

<div>
	<a href="#"><img src="images/logo.png" height="240" width="600" alt="Acme Motorsports Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					
					<security:authorize access="hasRole('RIDER')">
						<li><a href="rider/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>

					<security:authorize access="hasRole('RACEDIRECTOR')">
						<li><a href="raceDirector/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('REPRESENTATIVE')">
						<li><a href="representative/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="sponsor/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('TEAMMANAGER')">
						<li><a href="teamManager/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="administrator/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					
						<li><a href="message/administrator/edit.do"><spring:message
									code="master.page.administrator.broadcast" /></a></li>
					
					</security:authorize>

					<li><a href="message/create.do"><spring:message
								code="master.page.message.create" /> </a></li>
					
					<li><a href="box/list.do"><spring:message
								code="master.page.box.list" /> </a></li>
								
					<li><a href="socialProfile/list.do"><spring:message
								code="master.page.socialProfile" /> </a></li>
					
					<li><a href="actor/deactivate.do" onclick="return confirm('${msgConfirm}')"><spring:message
								code="master.page.deactivate" /> </a></li>
								
					<li><a href="download/myPersonalData.do"><spring:message
								code="master.page.export" /> </a></li>
							
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				
				</ul>
			</li>
			
		</security:authorize>
		
		<!-- Admin -->
		<security:authorize access="hasRole('ADMIN')">
			
			<li><a class="fNiv"> <spring:message
						code="master.page.administrator" />
			</a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.administrator.dashboard" /></a></li>
								
			<%--		<li><a href="configuration/administrator/display.do"><spring:message
								code="master.page.administrator.configuration" /></a></li>--%>
					<li><a href="administrator/flagSpam.do"><spring:message
								code="master.page.administrator.flagSpam" /></a></li>
					<li><a href="administrator/bannableList.do"><spring:message
								code="master.page.administrator.bannableList" /></a></li>
					<li><a href="administrator/computeScore.do"><spring:message
								code="master.page.administrator.computeScore" /></a></li>
								
				</ul></li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
					<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message
								code="master.page.list.category" /></a></li>
					<li><a href="sector/administrator/list.do"><spring:message
								code="master.page.list.sector" /></a></li>
				</ul>
				</li>
				
				<li><a class="fNiv"><spring:message
						code="master.page.administrator.createAcc" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/create.do"><spring:message
								code="master.page.administrator.create" /></a></li>
				</ul></li>
		</security:authorize>
		
		
		<!-- Race Director -->
		<security:authorize access="hasRole('RACEDIRECTOR')">
				
				<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
					<li><a href="worldChampionship/raceDirector/list.do"><spring:message
								code="master.page.raceDirector.worldChampionship.list" /></a></li>
					<li><a href="forecast/raceDirector/list.do"><spring:message
								code="master.page.raceDirector.forecast.list" /></a></li>
					<li><a href="announcement/raceDirector/list.do"><spring:message
								code="master.page.raceDirector.announcement.list" /></a></li>
								
				</ul>
				
			</li>
		</security:authorize>
		
		<!-- Representative -->
		<security:authorize access="hasRole('REPRESENTATIVE')">
		
		<!-- Lista para logeados -->
			<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
				<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
				<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
				
				</ul>
			</li>
			
		</security:authorize>
		
		<!-- Team manager -->
		<security:authorize access="hasRole('TEAMMANAGER')">
		
			<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
				<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
				<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
				<li><a href="answer/teamManager/list.do"><spring:message
								code="master.page.answer.list" /></a></li>
				
				</ul>
			</li>
			
		</security:authorize>
		
		<!-- Representative -->
		<security:authorize access="hasRole('SPONSOR')">
		
		<!-- Lista para logeados -->
			<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
				<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
				<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
				
				</ul>
			</li>
			
		</security:authorize>
		<!-- Rider -->
		<security:authorize access="hasRole('RIDER')">
		
		<!-- Lista para logeados -->
			<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
				<li><a href="rider/list.do"><spring:message
								code="master.page.list.riders" /></a></li>
				<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
				
				</ul>
			</li>
		

			<li><a href="finder/rider/edit.do"><spring:message
					code="master.page.finder.edit" /></a></li>
					<%--  
			<li><a href="application/hacker/list.do"><spring:message
					code="master.page.application.list" /></a></li>--%>
					
			<li><a href="palmares/rider/display.do"><spring:message
					code="master.page.rider.palmares" /></a></li>
		</security:authorize>
		
		<!-- Rider -->
		<security:authorize access="hasRole('REPRESENTATIVE')">
		
		<!-- Lista para logeados -->
			<li><a class="fNiv"><spring:message
						code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
				<li><a href="fanClub/representative/display.do"><spring:message
								code="master.page.display.fanClub" /></a></li>
				</ul>
			</li>
		

			<li><a href="finder/rider/edit.do"><spring:message
					code="master.page.finder.edit" /></a></li>
					<%--  
			<li><a href="application/hacker/list.do"><spring:message
					code="master.page.application.list" /></a></li>--%>
					
			<li><a href="palmares/rider/display.do"><spring:message
					code="master.page.rider.palmares" /></a></li>
		</security:authorize>
		
		
		<!-- Anonymous -->
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="rider/create.do"><spring:message
								code="master.page.register.rider" /></a></li>
					<li><a href="raceDirector/create.do"><spring:message
								code="master.page.register.raceDirector" /></a></li>
					<li><a href="representative/create.do"><spring:message
								code="master.page.register.representative" /></a></li>
					<li><a href="sponsor/create.do"><spring:message
								code="master.page.register.sponsor" /></a></li>
					<li><a href="teamManager/create.do"><spring:message
								code="master.page.register.manager" /></a></li>

				</ul>
			</li>
				
			<!-- Lista de anonimos -->
			<li><a class="fNiv"><spring:message
					code="master.page.list" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="worldChampionship/list.do"><spring:message
								code="master.page.worldChampionship.list" /></a></li>
			
				</ul>
			</li>
		</security:authorize>
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
