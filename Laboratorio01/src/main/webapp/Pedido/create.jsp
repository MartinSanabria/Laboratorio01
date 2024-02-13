<%@page import="Models.Pedidos"%>
<%@page import="Models.Clientes"%>
<%@page import="java.util.List"%>
<%@page import="DaoModels.DaoClientes"%>
<%@page import="DaoModels.DaoPedidos"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="../layouts/header.jsp"/>

<div class="container mt-3">
    <form action="/Laboratorio01/PedidoController?action=create" method="post">
        <div class="mb-3">
            <label for="cliente">Cliente </label>
            <select class="form-control" id="cliente" name="cliente" required>
                 <option value="" selected>Selecciona una opción</option>
                <c:forEach var="client" items="${clientes}">
                    <option value="${client.getId()}">${client.getNombre()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="total">Total </label>
            <input type="number" class="form-control" id="total" min="0.00" step="0.01" name="total" required>
        </div>
        <div class="mb-3">
            <label for="fecha">Fecha: </label>
            <input type="date" class="form-control" id="fecha" name="fecha" required>
        </div>

        <div class="mb-3 row mt-3">
                <div class="offset-sm-5 col-sm-8">
                    <button type="submit" class="btn btn-primary">Agregar</button>
                    <a href="/Laboratorio01/PedidoController" class="btn btn-secondary "> Regresar</a>
                </div>
            </div>
    </form>
</div>
<jsp:include page="../layouts/footer.jsp"/>
