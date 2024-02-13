<%@page import="DaoModels.DaoPedidos"%>
<%@page import="Models.Pedidos"%>
<%@page import="Models.Clientes"%>
<%@page import="java.util.List"%>
<%@page import="DaoModels.DaoClientes"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="../layouts/header.jsp"/>


<div class="container mt-3">
    <form action="/Laboratorio01/PedidoController?action=update&id=${pedidoEdit.getId()}" method="post">
        
        <div class="mb-3">
            <label for="cliente">Cliente </label>
            <select class="form-control" id="cliente" name="cliente" required>
                <option value="" selected>Selecciona una opción</option>
                <c:forEach var="client" items="${clientes}">
                    <c:choose>
                        <c:when test="${client.getId() == pedidoEdit.getId_cliente()}">
                            <option value="${client.getId()}" selected>${client.getNombre()}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${client.getId()}">${client.getNombre()}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        
        <div class="mb-3">
            <label for="total">Total </label>
            <fmt:formatNumber value="${pedidoEdit.getTotal()}" type="number" pattern="#,##0.00" var="formattedTotal" />
            <input type="text" class="form-control" id="total" name="total" value="${formattedTotal}" required readonly>
        </div>

            
         <div class="mb-3">
            <label for="fecha">Fecha: </label>
            <input type="date" class="form-control" id="fecha" name="fecha" value="${pedidoEdit.getFecha()}" required>
        </div>
        
        <div class="mb-3">
            <label for="estado">Estado</label>
            <select class="form-control" id="estado" name="estado" required>
                <c:choose>
                    <c:when test="${pedidoEdit.getEstado() == 0}">
                        <option value="0" selected>Activo</option>
                        <option value="1">En proceso</option>
                        <option value="2">Entregado</option>
                    </c:when>
                    <c:when test="${pedidoEdit.getEstado() == 1}">
                        <option value="0">Activo</option>
                        <option value="1" selected>En proceso</option>
                        <option value="2">Entregado</option>
                    </c:when>
                    <c:when test="${pedidoEdit.getEstado() == 2}">
                        <option value="0">Activo</option>
                        <option value="1">En proceso</option>
                        <option value="2" selected>Entregado</option>
                    </c:when>
                </c:choose>
            </select>
        </div>

        <div class="mb-3 row mt-3">
                <div class="offset-sm-5 col-sm-8">
                    <button type="submit" class="btn btn-primary">Actualizar</button>
                    <a href="/Laboratorio01/PedidoController" class="btn btn-secondary "> Regresar</a>
                </div>
            </div>
    </form>
</div>

<jsp:include page="../layouts/footer.jsp"/>
