<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    
    <div class="row">
    	<h2>Proyecto ${title}</h2>
    	<p><h2>Grupo ${group}</h2></p>
    	<p><ul>
    		<c:forEach items="${persons}" var="person">
    			<li>${person.firstName}${person.lastName}</li>
    		</c:forEach>
    	</ul></p>
        
        <div class="col-md-12">
            <spring:url value="/resources/images/apple.png" htmlEscape="true" var="petsImage"/>
            <img class="img-responsive" src="${petsImage}"/>
        </div>
      <div id="like_button_container"></div>
    </div>
</petclinic:layout>
