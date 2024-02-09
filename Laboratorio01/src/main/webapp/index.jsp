<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="./layouts/header.jsp" />
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<div class="container mt-3">
    <h1>Bienvenido </h1>
</div>
<c:if test="${not empty successMessage}">
    <script>
        Swal.fire({
           position: 'top-end',
           icon: 'success',
           title: '${successMessage}',
           showConfirmButton: false,
           timer: 1500
         });
    </script>
</c:if>
    

<jsp:include page="./layouts/footer.jsp" />
