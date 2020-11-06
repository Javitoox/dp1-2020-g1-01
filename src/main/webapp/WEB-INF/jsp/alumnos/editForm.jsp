<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<petclinic:layout pageName="editForm">
    <h2 style="text-align:center">Editar Alumno</h2>
    <form:form modelAttribute="alumno" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Dni" name="dni"/>
            <petclinic:inputField label="Correo" name="correo"/>
            <petclinic:inputField label="Telefono" name="telefono"/>
            <petclinic:inputField label="Telefono 2" name="telefono2"/>
            <petclinic:inputField label="Dirección" name="direccion"/>
            <petclinic:inputField label="Fecha de nacimiento" name="fechanacimiento"/>            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                  <button class="btn btn-default" type="submit">Editar Alumno</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>