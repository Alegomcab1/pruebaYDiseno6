
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="handyWorker.applications" />
<security:authorize access="hasRole('HANDYWORKER')">
<jsp:useBean id="now" class="java.util.Date"/> 

	
	<display:table pagesize="5" name="applications" id="row" class="displaytag" 
					requestURI="/application/handyWorker/list.do">
					
		<jstl:choose>
			<jstl:when test="${row.status.toString()=='ACCEPTED'}">
			
				<jstl:set var="color" value="green" />
			</jstl:when>
			
			<jstl:when test="${row.status.toString()=='REJECTED'}">
				<jstl:set var="color" value="red" />
			</jstl:when>
			
			
			
			<jstl:when test="${row.fixUpTask.realizationTime<now}">
				<jstl:set var="color" value="grey" />
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:set var="color" value="black" />
			</jstl:otherwise>
		</jstl:choose>
		
		<display:column titleKey="handyWorker.workPlan">	
			<!-- Solo deja crear un WorkPlan si est� aceptada-->	
			<jstl:if test="${row.status == 'ACCEPTED'}">
					<spring:url var="statusUrl" value="/workPlan/handyWorker/edit.do?applicationId={appId}">
							<spring:param name="appId" value="${row.id}"/>
					</spring:url>
					<jstl:if test="${row.fixUpTask.phases.size()==0}" >
					<a href="${statusUrl}">
							<spring:message code="application.createWorkPlan"/>
					</a>
					
					</jstl:if>
					
					<jstl:if test="${row.fixUpTask.phases.size()>0}" >
					<a href="${statusUrl}">
							<spring:message code="application.editWorkPlan" />	
					</a>
					</jstl:if>
					
			</jstl:if>	
		
		</display:column>
		
		<div style=<jstl:out value="${color}"/>>
		<display:column property="moment" titleKey="application.moment"	
						format="{0,date,dd/MM/yyyy HH:mm}" style="color:${color}"/>
		</div>
		
		<display:column property="status" titleKey="application.status" style="color:${color}" />	
		
		<display:column property="offeredPrice" titleKey="application.offeredPrice" style="color:${color}"/>	
		
		<div style=<jstl:out value="${color}"/>>
		<display:column titleKey="application.comments">
				<jstl:set var="commentsSize" value="${row.comments.size()}" />
				<spring:url var="commentsUrl" value="/comment/handyWorker/list.do?applicationId={appId}">
							<spring:param name="appId" value="${row.id}"/>
				</spring:url>
				<a href="${commentsUrl}">
							<spring:message var ="viewComments1" code="application.viewComments" />
							<jstl:out value="${viewComments1}(${commentsSize})" />		
				</a>
		</display:column>
		</div>
		
		<!-- Probablemente haya que quitarlo -->
		
<%-- 		<div style=<jstl:out value="${color}"/>>
		<display:column titleKey="application.username">		
				<jstl:out value="${row.customer.userAccount.username}"/>
		</display:column>
		</div>
		
		<div style=<jstl:out value="${color}"/>>
		<display:column titleKey="application.score">		
				<jstl:out value="${row.customer.score}" />
		</display:column>
		</div>
		--%>
		<!-- Hasta aqu� -->

		<display:column titleKey="application.description" style="color:${color}">
				<jstl:out value="${row.fixUpTask.description}"  />
		</display:column>
		
		<display:column titleKey="application.maxPrice" style="color:${color}">
				<jstl:out value="${row.fixUpTask.maxPrice}" />
		</display:column>
		
	</display:table>
	</security:authorize>

