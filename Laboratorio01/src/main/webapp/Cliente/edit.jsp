<%-- 
    Document   : edit
    Created on : 14 sep. 2023, 08:15:56
    Author     : martinsanabria
--%>

<%@page import="Models.Clientes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../layouts/header.jsp"/>

<div class="container mt-3">
        <form method="post" action="ClientController?action=update&id=${ClientEdit.getId()}">          
            <div class="mb-3 row">
                <label for="nombre" class="col-4 col-form-label">Nombre</label>
                <div class="col-8">
                    <input type="text" value="${ClientEdit.getNombre()}" class="form-control" name="nombre" id="nombre" required>
                </div>
            </div>  
                
            <div class="mb-3 row">
                <label for="direccion" class="col-4 col-form-label">Direccion</label>
                <div class="col-8">
                    <input type="text" value="${ClientEdit.getDireccion()}" class="form-control" name="direccion" id="direccion" required>
                </div>
            </div>  
                
            <div class="mb-3 row">
                <label for="telefono" class="col-4 col-form-label">Telefono</label>
                <div class="col-8">
                    <input type="text" value="${ClientEdit.getTelefono()}" class="form-control" name="telefono" id="telefono" required>
                </div>
            </div>  
                
            <div class="mb-3 row">
                <label for="email" class="col-4 col-form-label">Email</label>
                <div class="col-8">
                    <input type="text" value="${ClientEdit.getEmail()}" class="form-control" name="email" id="email" required>
                </div>
            </div>  
                
            <div class="mb-3 row">
                <div class="offset-sm-4 col-sm-8 mt-3">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                     <a href="/Laboratorio01/ClientController" class="btn btn-secondary "> Regresar</a>
                </div>
            </div>
        </form>
</div>
<jsp:include page="../layouts/footer.jsp"/>