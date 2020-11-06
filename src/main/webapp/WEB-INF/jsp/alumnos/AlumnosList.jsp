<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vademecun">
    <h2>Alumnos</h2>

    <table id="diseasesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>DNI</th>
            <th>Telefono</th>
            <th>Telefono 2</th>
            <th>Direccion</th>
            <th>Correo Electrónico</th>
            <th>Fecha Nacimiento</th>     
            <th>Editar</th>
            <th>Eliminar</th>    
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${alumnos}" var="alumno">
            <tr>
                <td>
                    <c:out value="${alumno.name}"/>
                </td>
                <td>
                    <c:out value="${alumno.dni}"/>
                </td> 
                <td>
                    <c:out value="${alumno.telefono}"/>
                </td> 
                <td>
                    <c:out value="${alumno.telefono2}"/>
                </td> 
                <td>
                    <c:out value="${alumno.direccion}"/>
                </td> 
                <td>
                    <c:out value="${alumno.correo}"/>
                </td> 
                <td>
                    <c:out value="${alumno.fechanacimiento}"/>
                </td> 
                <td>
                   <a href="/alumnos/${alumno.id}/edit">
                   	<span class="glyphicon glyphicon-pencil"></span>
                   </a>
                </td>
                <td>
                   <a href="/alumnos/${alumno.id}/delete">
                   	<span class="glyphicon glyphicon-trash"></span>
                   </a>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>