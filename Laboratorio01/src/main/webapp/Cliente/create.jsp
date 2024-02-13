<%-- 
    Document   : create
    Created on : 14 sep. 2023, 09:28:27
    Author     : martinsanabria
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="../layouts/header.jsp"/>
<div class="container mt-3">
        <form method="post" action="/Laboratorio01/ClientController?action=create">
            <div class="mb-3 row">
                <label for="nombre" class="col-4 col-form-label">Ingrese su nombre</label>
                <div class="col-8">
                    <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Nombre" required>
                </div>
            </div> 
            
            <div class="mb-3 row">
                <label for="direccion" class="col-4 col-form-label">Ingrese la direccion</label>
                <div class="col-8">
                    <input type="text" class="form-control" name="direccion" id="direccion" placeholder="direccion" required>
                </div>
            </div> 
            
            <div class="mb-3 row">
                <label for="telefono" class="col-4 col-form-label">Ingrese el telefono</label>
                <div class="col-8">
                    <input type="text" class="form-control" name="telefono" id="telefono" placeholder="7895-3234" min="0" maxlength="9"  required>
                </div>
            </div> 
            
            <div class="mb-3 row">
                <label for="email" class="col-4 col-form-label">Ingrese el email</label>
                <div class="col-8">
                    <input type="text" class="form-control" name="email" id="email" placeholder="Example@example.com" required>
                </div>
            </div> 
         
            <div class="mb-3 row">
                <div class="offset-sm-4 col-sm-8">
                    <button type="submit" class="btn btn-primary">Agregar</button>
                    <a href="/Laboratorio01/ClientController" class="btn btn-secondary "> Regresar</a>
                </div>
            </div>
        </form>
    </div>
<jsp:include page="../layouts/footer.jsp"/>